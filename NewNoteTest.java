package application;

import static org.junit.Assert.*;
import javafx.animation.PathTransition;
import javafx.scene.shape.Circle;

import org.junit.Test;

/**
 * 
 * JUnit test cases for the NewNote class
 * 
 * @author Manuel Rodriguez
 *
 */
public class NewNoteTest {

	/**
	 * testing the basics of the constructor
	 */
	@Test
	public void test_constructor1() 
	{
		String key = "f";
		double seconds = 8.560;
		int Ycoord = -300;
		int ID = 32;
		NewNote test1 = new NewNote(key, seconds, Ycoord, ID);
		assertEquals("f", test1.getKey());
		assertEquals(seconds, test1.getTime(), 0);
		assertEquals(200, test1.getX());
		assertEquals(Ycoord, test1.getYCoordinate());
		assertEquals(ID, test1.getID());
	}

	/**
	 * testing if a positive YCoordinate would actually be set to 0
	 */
	@Test
	public void test_constructor2() 
	{
		String code = " ";
		double secs = 12.542;
		int y = 500;
		NewNote test2 = new NewNote(code, secs, y, 24);
		assertEquals(code, test2.getKey());
		assertEquals(secs, test2.getTime(), 0);
		assertEquals(325, test2.getX());
		assertEquals(0, test2.getYCoordinate());
		assertEquals(24, test2.getID());
	}
	
}
