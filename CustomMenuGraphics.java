package application;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 * CustomMenuGraphics class the edits the graphical components of a Menu Scene
 * 
 * @author Manuel Rodriguez, original menu concept by David Akobundu
 *
 */
public class CustomMenuGraphics {
	
	/**
	 * method that adds the rhythmania jpg image to the backround of a menu scene
	 * 
	 * @param Layout, the Pane layout used to add the image onto it
	 */
	public void AddBackGroundImage(Pane Layout)
	{
		try
		{
			InputStream inputStream = Files.newInputStream(Paths.get("rythmania.jpg"));
			Image BackGroundImage = new Image(inputStream);
			inputStream.close();
			ImageView ViewImage = new ImageView(BackGroundImage);
			ViewImage.setFitWidth(800);
			ViewImage.setFitHeight(600);
			Layout.getChildren().add(ViewImage);
		}
		catch(Exception e)
		{
			System.out.println("Exception caught, most likely image was not found");
		}
	}
	
	/**
	 * method that edits both buttons on a Menu scene
	 * 
	 * @param b1, the first button to be edited
	 * @param b2, the second button to be edited
	 */
	public void EditBothButtons(Button b1, Button b2)
	{
		b1.setLayoutX(275.5);
		b2.setLayoutX(275.5);
		
		b1.setLayoutY(350);
		b2.setLayoutY(425);
		
		b1.setTextFill(Color.BLUE);
		b2.setTextFill(Color.BLUE);
		
		/**
		 * this is how I filled the colour of the buttons
		 * https://www.endeeper.com/blog/benefits-css-styling-javafx-applications/
		 * the website of where i got the idea to fill the button
		 * uses CSS colour codes FFA500 = orange
		 */
		b1.setBackground(new Background(new BackgroundFill(Color.web("FFA500"), null, null)));
		b2.setBackground(new Background(new BackgroundFill(Color.web("FFA500"), null, null)));
	}
}