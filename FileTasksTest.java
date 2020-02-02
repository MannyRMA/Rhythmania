package application;

import static org.junit.Assert.*;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.junit.Test;

/**
 * JUnits for the FileTasks class
 * @author Manuel Rodriguez
 *
 */
public class FileTasksTest {

	/**
	 * tests to see if the file does not exist then it prints it out on the console
	 */
	@Test
	public void test_NoFile() 
	{
		//String expectedConsoleOutput = "cannot opne the input file";
		//String ActulaConsoleOutput = System.console().readLine();
		ArrayList<NewNote> result = FileTasks.readNoteFile("");
	    assertEquals(null, result);	
	   // assertEquals(expectedConsoleOutput, ActulaConsoleOutput);
	}
	
	
	/**
	 * tests to see if the correct amount of NewNotes are created from the Take one Me text file
	 */
	@Test
	public void test_TakeOnMe() 
	{
		ArrayList<NewNote> result =  FileTasks.readNoteFile("Take on Me.txt");
		int res = result.size();
		assertEquals(123, res);
	}
	
	
	/**
	 * tests if the note is constructed properly from Take on Me
	 */
	@Test
	public void test_TakeOnMe_Note()
	{
		ArrayList<NewNote> notesList = FileTasks.readNoteFile("Take on Me.txt");
		NewNote test = notesList.get(49);
		assertEquals("f", test.getKey());
		assertEquals(27.25, test.getTime(), 0);
		assertEquals(-5050, test.getYCoordinate());
		assertEquals(49, test.getID());
	}
	
	/**
	 * tests to see if the correct amount of NewNotes is created from the Madeon text file
	 */
	@Test
	public void test_Madeon()
	{
		ArrayList<NewNote> result =  FileTasks.readNoteFile("Madeon - Finale.txt");
		int res = result.size();
		assertEquals(109, res);
	}
	
	/**
	 * tests if the note is constructed properly from Madeon
	 */
	@Test
	public void test_Madeon_Note()
	{
		ArrayList<NewNote> notes =  FileTasks.readNoteFile("Madeon - Finale.txt");
		NewNote test = notes.get(82);
		assertEquals("k", test.getKey());
		assertEquals(45.6, test.getTime(), 0);
		assertEquals(-36000, test.getYCoordinate());
		assertEquals(82, test.getID());
	}

	/**
	 * tests the FinalStats method
	 */
	@Test
	public void test_Final()
	{
		int final_score = 25000;
		double final_accuracy = 85.675;
		FileTasks.FinalStats("Final Stats", final_score, final_accuracy);
		Scanner scan = null;
		try
		{
			scan = new Scanner(new FileReader("Final Stats"));
		}
		catch(IOException e) {}
		String first_line = scan.nextLine();
		String second_line = scan.nextLine();
		String third_line = scan.nextLine();
		assertEquals("Overall Performance!", first_line);
		assertEquals("Final Score: 25000", second_line);
		assertEquals("Final Accuracy: 85.675%", third_line);
	}
	

}
