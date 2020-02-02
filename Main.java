package application;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.effect.*;
import javafx.scene.input.KeyCode;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

/**
 * Main class for the game. Handles all of the lambda expressions,
 * graphics of the game board, etc.
 * 
 * @author Manuel Rodriguez, Evan Argenal
 * 
 */
public class Main extends Application {

	private static int score = 0;
	private static int totalNotes = 0;
	private static int hitNotes = 0;
	private static int multiplier = 1;
	private static int noteMulti = 0;
	private static double accuracy = 100.0;
	private static ArrayList<NewNote> arrayNotes = new ArrayList<NewNote>();
	private RefreshTask kill;
	
	/**
	 * The following are setters and getters for variables
	 * including scores, notes, accuracy, multiplier, etc.
	 */
	public static int getScore() {
		return score;
	}
	
	public static void setScore(int score) {
		Main.score = score;
	}

	public static int getTotalNotes() {
		return totalNotes;
	}
	
	public static void setTotalNotes(int totalNotes) {
		Main.totalNotes = totalNotes;
	}

	public static int getHitNotes() {
		return hitNotes;
	}

	public static void setHitNotes(int hitNotes) {
		Main.hitNotes = hitNotes;
	}
	
	public static int getMultiplier() {
		return multiplier;
	}

	public static void setMultiplier(int multiplier) {
		Main.multiplier = multiplier;
	}
	
	public static double getAccuracy() {
		return accuracy;
	}
	
	public static void setAccuracy(double accuracy) {
		Main.accuracy = accuracy;
	}
	
	public static int getNoteMulti() {
		return noteMulti;
	}
	
	public static void setNoteMulti(int noteMulti) {
		Main.noteMulti = noteMulti;
	}

	public static ArrayList<NewNote> getArrayNotes() {
		return arrayNotes;
	}

	public static void setArrayNotes(ArrayList<NewNote> arrayNotes) {
		Main.arrayNotes = arrayNotes;
	}
	
	/**
	 * Used for rounding values
	 * 
	 * Taken from:
	 * https://stackoverflow.com/questions/2808535/round-a-double-to-2-decimal-places
	 */
	public static double round(double value, int places) {
		if (places < 0) throw new IllegalArgumentException();
		
		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}
	
	/**
	 * drawing the Main Menu and the gameboard.
	 * Everything is handled here
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			// All the graphical components for the GUI
			
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,1200,800);
			
			// the lines that separates the Note Board into five sections
			Line leftBorder = new Line(10, 0, 10, 800);
			Line rightBorder = new Line(630, 0, 630, 800);
			Line Border1 = new Line(134,  0, 134, 800);
			Line Border2 = new Line(258, 0, 258, 800);
			Line Border3 = new Line(384,  0 , 384, 800);
			Line Border4 = new Line(506, 0, 506, 800);
			Rectangle NoteBoard = new Rectangle(0, 0, 640, 810); // rectangle that represents the note board
			
			// the hitzones on the NoteBoard defined as arcs
			Arc GreenZone = new Arc(75, 720, 50, 60, 180, 180);  // (x, y, w, h, start, extent)
			Arc RedZone = new Arc(200, 720, 50, 60, 180, 180);
			Arc YellowZone = new Arc(325, 720, 50, 60, 180, 180);
			Arc BlueZone = new Arc(448, 720, 50, 60, 180, 180);
			Arc OrangeZone = new Arc(572, 720, 50, 60, 180, 180);
			
			// Rectangles that would serve as a ScoreBoard and a FeedBack Box
			Rectangle ScoreBoard = new Rectangle(640, 0, 560, 400);
			Rectangle FeedBack = new Rectangle(640, 400, 560, 400);
			
			ScoreBoard.setFill(Color.rgb(117, 10, 175)); //(red value, green value, blue value)
			FeedBack.setFill(Color.rgb(255, 255, 75));
			
			// the ScoreLabel Text
			Text ScoreLabel = new Text(700, 50, "Score:");
			ScoreLabel.setFont(Font.font("HaettenSchweiler", 50.0));
			ScoreLabel.setFill(Color.rgb(255, 255, 75));
			
			// Score Display text
			Text ScoreDisplay = new Text(700, 150, Integer.toString(getScore()));
			ScoreDisplay.setFont(Font.font("HaettenSchweiler", 50.0));
			ScoreDisplay.setFill(Color.rgb(255, 255, 75));
			
			// Accuracy display text
			Text accuracyDisplay = new Text(700, 350, Double.toString(getAccuracy()) + "%");
			accuracyDisplay.setFont(Font.font("HaettenSchweiler", 50.0));
			accuracyDisplay.setFill(Color.rgb(255, 255, 75));
			
			// multiplier text
			Text multiplier = new Text(700, 550, Integer.toString(getMultiplier()) + "x");
			multiplier.setFont(Font.font("HaettenSchweiler", 50.0));
			multiplier.setFill(Color.rgb(117, 10, 175));
			
			// a refletion effect used for any text in the ScoreBoard or FeedBackBox
			Reflection reflect = new Reflection();
			reflect.setFraction(0.6f);
			ScoreLabel.setEffect(reflect);
			ScoreDisplay.setEffect(reflect);
			accuracyDisplay.setEffect(reflect);
			multiplier.setEffect(reflect);
			
			// a shine effect so that when a key is being pressed
			// the specific hitzone lights up
			Glow shine = new Glow();
			shine.setLevel(150);
			
			// A line glow effect to make the lines on the NoteBoard more visible
			Glow LineGlow = new Glow();
			LineGlow.setLevel(356.5);
			
			leftBorder.setEffect(LineGlow);
			rightBorder.setEffect(LineGlow);
			Border1.setEffect(LineGlow);
			Border2.setEffect(LineGlow);
			Border3.setEffect(LineGlow);
			Border4.setEffect(LineGlow);			
				
			// the AccuracyLabel text
			Text AccuracyLabel = new Text(700, 250, "Accuracy:");
			AccuracyLabel.setFont(Font.font("HaettenSchweiler", 50.0));
			AccuracyLabel.setFill(Color.rgb(255, 255, 75));
			AccuracyLabel.setEffect(reflect);
			
			// the text label for the hitzones to specify which key is linked to which zone
			Text DKey = new Text(65, 760, "D");
			Text FKey = new Text(195, 760, "F");
			Text Space = new Text(310, 760, "Sp");
			Text JKey = new Text(440, 760, "J");
			Text KKey = new Text(565, 760, "K");
			
			// set the font and color of the letters in the keys
			DKey.setFont(Font.font("HaettenSchweiler", 40.0));
			FKey.setFont(Font.font("HaettenSchweiler", 40.0));
			Space.setFont(Font.font("HaettenSchweiler", 40.0));
			JKey.setFont(Font.font("HaettenSchweiler", 40.0));
			KKey.setFont(Font.font("HaettenSchweiler", 40.0));
	
			DKey.setFill(Color.CRIMSON);
			FKey.setFill(Color.LIMEGREEN);
			Space.setFill(Color.rgb(117, 10, 175));
			JKey.setFill(Color.ORANGE);
			KKey.setFill(Color.DODGERBLUE);
			
			// the mutliplierLabel text
			Text MultiplierLabel = new Text(700, 450, "Multiplier:");
			MultiplierLabel.setFont(Font.font("HaettenSchweiler", 50.0));
			MultiplierLabel.setFill(Color.rgb(117, 10, 175));
			MultiplierLabel.setEffect(reflect);
			
			// the feedback label text
			Text FeedBackLabel = new Text(700, 650, "FeedBack:");
			FeedBackLabel.setFont(Font.font("HaettenSchweiler", 50.0));
			FeedBackLabel.setFill(Color.rgb(117, 10, 175));
			FeedBackLabel.setEffect(reflect);
			
			// setting the colours of the lines and the hitzones
			leftBorder.setStroke(Color.CYAN);
			rightBorder.setStroke(Color.CYAN);
			Border1.setStroke(Color.CYAN);
			Border2.setStroke(Color.CYAN);
			Border3.setStroke(Color.CYAN);
			Border4.setStroke(Color.CYAN);
			
			// colors the circles on the board
			GreenZone.setFill(Color.LIMEGREEN);
			RedZone.setFill(Color.CRIMSON);
			YellowZone.setFill(Color.rgb(255, 255, 75));
			BlueZone.setFill(Color.DODGERBLUE);
			OrangeZone.setFill(Color.ORANGE);
			
			// adding all the graphical components to the GUI
			root.getChildren().addAll(NoteBoard, leftBorder, rightBorder, Border1, Border2, Border3, Border4, ScoreBoard);
			root.getChildren().addAll(GreenZone, RedZone, YellowZone, BlueZone, OrangeZone, FeedBack);	
			root.getChildren().addAll(ScoreLabel, AccuracyLabel, MultiplierLabel, FeedBackLabel, ScoreDisplay, accuracyDisplay, multiplier);
			root.getChildren().addAll(DKey, FKey, Space, JKey, KKey);
			
			// the Pane layouts for the Main Menu and SongMenu scenes
			Pane MainMenuLayout = new Pane();
			Pane SongMenuLayout = new Pane();
			
			// the scenes for the main menu and songs menu
			Scene MainMenu = new Scene(MainMenuLayout, 800, 600);
			Scene SongMenu = new Scene(SongMenuLayout, 800, 600);
			
			// the two buttons for the main menu
			Button PlayButton = new Button("Play");
			Button ExitButton = new Button("Exit");
			
			// the two buttons for the song menus each symbolizing a song choice
			Button FinaleButton = new Button("Finale");
			Button TakeOnMeButton = new Button("Take On Me");
			
			// the objects of CUstomMenuGraphics to custom the two menu scenes
			CustomMenuGraphics CustomMainMenu = new CustomMenuGraphics();
			CustomMenuGraphics CustomSongMenu = new CustomMenuGraphics();
			
			// adding the background image and editing the two buttons of the two menu scenes
			CustomMainMenu.AddBackGroundImage(MainMenuLayout);
			CustomMainMenu.EditBothButtons(PlayButton, ExitButton);
			CustomSongMenu.AddBackGroundImage(SongMenuLayout);
			CustomSongMenu.EditBothButtons(FinaleButton, TakeOnMeButton);
			
			/**
			 * All lambda expressions for hitting the
			 * buttons in the Main Menu. 
			 * Each button has their own corresponding
			 * action to switch scenes.
			 */
			PlayButton.setOnAction(e ->{
				primaryStage.setScene(SongMenu);
				primaryStage.setTitle("Songs");
			});
			FinaleButton.setOnAction(e ->{
				setArrayNotes(FileTasks.readNoteFile("Madeon - Finale.txt"));
				kill = new RefreshTask(arrayNotes);
				kill.start();
				for(NewNote temp : getArrayNotes()) {
					root.getChildren().add(temp.note);
				}
				FileTasks.PlayMusic("music/Finale.wav");
				primaryStage.setScene(scene);
				primaryStage.setTitle("Rhythmania");
			});
			TakeOnMeButton.setOnAction(e ->{
				setArrayNotes(FileTasks.readNoteFile("Take On Me.txt"));
				kill = new RefreshTask(arrayNotes);
				kill.start();
				for(NewNote temp : getArrayNotes()) {
					root.getChildren().add(temp.note);
				}
				FileTasks.PlayMusic("music/Take On Me.wav");
				primaryStage.setScene(scene);
				primaryStage.setTitle("Rhythmania");
			});
			ExitButton.setOnAction(e ->{
	    		primaryStage.close();
	    		System.out.println("Game has been closed. (Exit button was clicked)");
			});
			
			// adding the two buttons to their respective menu scenes
			MainMenuLayout.getChildren().addAll(PlayButton, ExitButton);
			SongMenuLayout.getChildren().addAll(TakeOnMeButton, FinaleButton);
			
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			// start the game from the Main Menu
			primaryStage.setTitle("Rhythmania");
			primaryStage.setScene(MainMenu);
			primaryStage.show();
			
			/**
			 * All key events for the buttons.
			 * When each key is pressed:
			 * - Button will light up
			 * - check to see if a note is in range to "hit"
			 * - updated the GUI of the game
			 */		
			scene.setOnKeyPressed(e -> {
			    if (e.getCode() == KeyCode.D) {
			    	GreenZone.setEffect(shine);
			    	for(NewNote temp : getArrayNotes()) {
			    		if(temp.getKey() == "d") {
			    			temp.disappear(root);
			    		}
			    	}
			    	ScoreDisplay.setText(Integer.toString(getScore()));
			    	accuracyDisplay.setText(Double.toString(round(getAccuracy(), 1)) + "%");
			    	multiplier.setText(Integer.toString(getMultiplier()) + "x");
			    }
			    else if (e.getCode() == KeyCode.F) {
			    	RedZone.setEffect(shine);
			    	for(NewNote temp : getArrayNotes()) {
			    		if(temp.getKey() == "f") {
			    			temp.disappear(root);
			    		}
					}
			    	ScoreDisplay.setText(Integer.toString(getScore()));
			    	accuracyDisplay.setText(Double.toString(round(getAccuracy(), 1)) + "%");
			    	multiplier.setText(Integer.toString(getMultiplier()) + "x");
			    }
			    else if (e.getCode() == KeyCode.SPACE) {
			    	YellowZone.setEffect(shine);
			    	for(NewNote temp : getArrayNotes()) {
			    		if(temp.getKey() == " ") {
			    			temp.disappear(root);
			    		}
					}
			    	ScoreDisplay.setText(Integer.toString(getScore()));
			    	accuracyDisplay.setText(Double.toString(round(getAccuracy(), 1)) + "%");
			    	multiplier.setText(Integer.toString(getMultiplier()) + "x");
			    	
			    }
			    else if (e.getCode() == KeyCode.J) {
			    	BlueZone.setEffect(shine);
			    	for(NewNote temp : getArrayNotes()) {
			    		if(temp.getKey() == "j") {
			    			temp.disappear(root);
			    		}
					}
			    	ScoreDisplay.setText(Integer.toString(getScore()));
			    	accuracyDisplay.setText(Double.toString(round(getAccuracy(), 1)) + "%");
			    	multiplier.setText(Integer.toString(getMultiplier()) + "x");
			    }
			    else if (e.getCode() == KeyCode.K) {
			    	OrangeZone.setEffect(shine);
			    	for(NewNote temp : getArrayNotes()) {
			    		if(temp.getKey() == "k") {
			    			temp.disappear(root);
			    		}
					}
			    	ScoreDisplay.setText(Integer.toString(getScore()));
			    	accuracyDisplay.setText(Double.toString(round(getAccuracy(), 1)) + "%");
			    	multiplier.setText(Integer.toString(getMultiplier()) + "x");
			    }
			    else if (e.getCode() == KeyCode.ESCAPE) {		// to close the game
		    		primaryStage.close();
		    		System.out.println("Game has been closed. (Escape key was pressed)");
		    		kill.ShutDown(); // stop the thread properly
		    		System.out.println();
		    		FileTasks.FinalStats("Final Stats.txt", getScore(), getAccuracy());
			    }
			});
			scene.setOnKeyReleased(e -> {
			    if (e.getCode() == KeyCode.D) {
			    	GreenZone.setEffect(null);			        
			    }
			    else if (e.getCode() == KeyCode.F) {
			    	RedZone.setEffect(null);			        
			    }
			    else if (e.getCode() == KeyCode.SPACE) {
			    	YellowZone.setEffect(null);		        
			    }
			    else if (e.getCode() == KeyCode.J) {
			    	BlueZone.setEffect(null);			        
			    }
			    else if (e.getCode() == KeyCode.K) {
			    	OrangeZone.setEffect(null);			        
			    }
			});
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Launches the game board.
	 */
	public static void main(String[] args)
	{
		launch(args);
	}
}