package application;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import application.NewNote;

/**
 * FileTasks class that deals with with any tasks the involve files
 * for the game
 * 
 * @author Manuel Rodriguez, Evan Argenal
 * Madeon - Finale.txt @author Jared Hoshizaki
 * Take On Me.txt @author Angeli Manipon
 *
 */
public class FileTasks {
	
	/**
	 * reads in a text file and creates an ArrayList of NewNotes 
	 * that are going to be the notes for a specific song of the game
	 * 
	 * @param input, the input file to be read from a text file
	 * @return an ArrayList of NewNotes that are going to be used for a song on the game
	 */
	public static ArrayList<NewNote> readNoteFile(String input)
	{
		ArrayList<NewNote> NoteList = new ArrayList<NewNote>();
		Scanner scan = null;
		int ID = 0;
		
		try {
			scan = new Scanner(new FileReader(input));
		}
		catch(IOException e) {
			ArrayList<NewNote> emptyList = null;
			System.out.println("cannot open the input note file");
			return emptyList;
		}
		
		while(scan.hasNext()) {
			
			String colour = scan.next();
			double time = scan.nextDouble();
			int YCoordinate = scan.nextInt();
			String Key = "";
			
			switch(colour)
			{
			case "green" : Key = "d";
			    break;
			
			case "red" : Key = "f";
				break;
				  
			case "yellow" : Key = " ";
				break;
			
			case "blue" : Key = "j";
				break;
				
			case "orange" : Key = "k";
				break;
			}
			
			NewNote note = new NewNote(Key, time, YCoordinate, ID);
			NoteList.add(note);
			ID++;
		}
		scan.close();
		return NoteList;
	}
	
	/**
	 * method that prints the final stats after the song is finished
	 * 
	 * @param output, the output file
	 * @param score, the overall score
	 * @param accuracy, overall accuracy
	 */
	public static void FinalStats(String output, int score, double accuracy)
	{
		PrintWriter pw = null;
		
		try
		{
			pw = new PrintWriter(new FileWriter(output));
		}
		
		catch(IOException e)
		{
			System.out.println("cannot open text file to write");
		}
		pw.println("Overall Performance!");
		pw.println("Final Score: " + score);
		pw.println("Final Accuracy: " + accuracy + "%");
		pw.println("Notes hit: " + NewNote.getHitNotes() + "/" + NewNote.getTotalNotes());
		pw.close();
		System.out.println("Overall Performance!");
		System.out.println("Final Score: " + score);
		System.out.println("Final Accuracy: " + accuracy + "%");
		System.out.println("Notes Hit: " + NewNote.getHitNotes() + "/" + NewNote.getTotalNotes());
	}
	
	/**
	 * Function is responsible for playing the music
	 * when the board is launched.
	 */
	static void PlayMusic(String songFile) {
        try {
            File music = new File(songFile);
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(music));
            clip.start();
        }
        catch(Exception e) {
            System.out.println(e);
        }
	}
}