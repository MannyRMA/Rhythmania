package application;

import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import java.io.File;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javafx.animation.PathTransition;
import javafx.util.Duration;
import application.Main;

/**
 * NewNote is responsible for handling the properties of a given note,
 * checking to see if has been hit, and sound effects.
 * 
 * @author Jared Hoshizaki, Angeli Manipon, Evan Argenal, Manny Rodriguez
 *
 */
public class NewNote extends Main {
	
	private int x;
	private int ID;
	private int YCoordinate;
	private double time;
	private String key;
	private Boolean noteHit = false;
	public Circle note;
	
	/**
	 * constructor used for creating every single note in a text file
	 * 
	 * @param key, which note to make it (sets the color accordingly)
	 * @param speed, speed of the note
	 * @param y, how high to place the note on the board when spawned
	 * @param ID, every note is assigned an ID for RefreshTask
	 */
	public NewNote(String key, double speed, int y, int ID) {
		this.key = key;
		note = new Circle(50);
		this.time = speed;
		this.setID(ID);
		
		// for constructors, note must be less than 0
		if(y > 0) {
			this.YCoordinate = 0;
		}
		else {
			this.YCoordinate = y;
		}
		
		// setting x-cord and color depending on which key it is
		if (this.key == "d") {
			this.x = 75;
			note.setFill(Color.LIMEGREEN);
		}
		else if (this.key == "f") {
			this.x = 200;
			note.setFill(Color.CRIMSON);
		}
		else if (this.key == " ") {
			this.x = 325;
			note.setFill(Color.rgb(255, 255, 75));
		}
		else if (this.key == "j") {
			this.x = 448;
			note.setFill(Color.DODGERBLUE);
		}
		else if (this.key == "k") {
			this.x = 572;
			note.setFill(Color.ORANGE);
		}
		
 		PathTransition transition = new PathTransition();		
 		transition.setNode(note);
 		transition.setDuration(Duration.seconds(speed));
 		Line path = new Line(x, y, x, 1000);
 		transition.setPath(path);
 		transition.setCycleCount(1);
 		transition.play();
	}
	
	/**
	 * The following are setters and getters for variables
	 * including key, y-cord, x-cord, ID, etc.
	 */
	public String getKey() {
		return this.key;
	}
	
	public int getYCoordinate()
	{
		return this.YCoordinate;
	}
	
	public double getTime()
	{
		return this.time;
	}
	
	public int getX()
	{
		return this.x;
	}
	
	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		this.ID = iD;
	}
	
	public Boolean getNoteHit() {
		return noteHit;
	}

	public void setNoteHit(Boolean noteHit) {
		this.noteHit = noteHit;
	}
	
	/**
	 * method is called when a user hits a key to check the y-cord
	 * of an approaching note. Too early is a miss and on time is a hit
	 * 
	 * @param root, used to access the gameboard and remove the note
	 * @param prs, used for note detection; all notes are false by default
	 */
	public void disappear(BorderPane root)
	{	
		if(note.getTranslateY() >= 700 && note.getTranslateY() <= 760 && getNoteHit() == false) // if a note is a hit
		{
			setNoteHit(true);
			setNoteMulti(getNoteMulti() + 1);
			setTotalNotes(getTotalNotes() + 1);
			setHitNotes(getHitNotes() + 1);
			setAccuracy(((double)getHitNotes()/(double)getTotalNotes()) * 100);
			setScore(getScore() + (100 * getMultiplier()));
			if(getNoteMulti() == 5) {
				setMultiplier(2);
				System.out.println("Multiplier is now 2.");
			}
			if(getNoteMulti() == 10) {
				setMultiplier(3);
				System.out.println("Multiplier is now 3.");
			}
			if(getNoteMulti() == 15) {
				setMultiplier(4);
				System.out.println("Multiplier is now 4.");
			}
			if(getNoteMulti() == 20) {
				setMultiplier(5);
				System.out.println("Multiplier is now 5.");
			}
			System.out.println("Hit. New score is: " + getScore());
			System.out.println("New Accuracy: " + getAccuracy());
			System.out.println();
			root.getChildren().remove(note);
		}
		else if((note.getTranslateY() < 700 && note.getTranslateY() > 640) && getNoteHit() == false) // if you hit a note too early
		{
			setNoteHit(true);
			setNoteMulti(0);
			setMultiplier(1);
			setTotalNotes(getTotalNotes() + 1);
			setAccuracy(((double)getHitNotes()/(double)getTotalNotes()) * 100);
			PlaySound(this.key);
			System.out.println("Miss. (Note hit too early)");
			System.out.println("New Accuracy: " + getAccuracy());
			System.out.println();
			root.getChildren().remove(note);
		}
	}
	
	/**
	 * Thread will call this method if it finds a note has
	 * passed the board without the user hitting it. It will
	 * reset multiplier and lower accuracy
	 */
	public void checkPass() {
		setNoteMulti(0);
		setMultiplier(1);
		setTotalNotes(getTotalNotes() + 1);
		setAccuracy(((double)getHitNotes()/(double)getTotalNotes()) * 100);
		PlaySound(this.key);
		System.out.println("Miss. (Note was not hit at all)");
		System.out.println("New Accuracy: " + getAccuracy());
		System.out.println();
	}
	
	/**
	 * will play a key when a note was missed. Every note
	 * has their own corresponding sound
	 * 
	 * @param key, which key was missed
	 */
	static void PlaySound(String key) {
		
		if (key == "d") {
			try {
			File sound = new File("music/DKey.wav");
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(sound));
			clip.start();
			}
			catch(Exception e) {
				System.out.println(e);
			}
		}
		else if (key == "f") {
			try {
				File sound = new File("music/FKey.wav");
				Clip clip = AudioSystem.getClip();
				clip.open(AudioSystem.getAudioInputStream(sound));
				clip.start();
			}
			catch(Exception e) {
				System.out.println(e);
			}
		}
		else if (key == " ") {
			try {
				File sound = new File("music/SPKey.wav");
				Clip clip = AudioSystem.getClip();
				clip.open(AudioSystem.getAudioInputStream(sound));
				clip.start();
			}
			catch(Exception e) {
				System.out.println(e);
			}
		}
		else if (key == "j") {
			try {
				File sound = new File("music/JKey.wav");
				Clip clip = AudioSystem.getClip();
				clip.open(AudioSystem.getAudioInputStream(sound));
				clip.start();
			}
			catch(Exception e) {
				System.out.println(e);
			}
		}
		else if (key == "k") {
			try {
				File sound = new File("music/KKey.wav");
				Clip clip = AudioSystem.getClip();
				clip.open(AudioSystem.getAudioInputStream(sound));
				clip.start();
			}
			catch(Exception e) {
				System.out.println(e);
			}
		}
	}
}