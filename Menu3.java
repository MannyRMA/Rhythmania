package MainMenu;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import javafx.application.Application;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;


public class Menu extends Application {
	
	private GameMenu gameMenu; 
	//private Stage primStage;
	
	@Override
	public void start(Stage primaryStage) throws Exception{
		
		//primStage = primaryStage;
		
		Pane root = new Pane();
		root.setPrefSize(800, 600);
		
		InputStream is = Files.newInputStream(Paths.get("res/images/rythmania.jpg"));
		Image img = new Image(is);
		is.close();
		
		ImageView imgView = new ImageView(img);
		imgView.setFitWidth(800);
		imgView.setFitHeight(600);
		
		gameMenu = new GameMenu();
		gameMenu.setVisible(false);
		
		root.getChildren().addAll(imgView, gameMenu);
		
		Scene scene1 = new Scene(root);
		scene1.setOnKeyPressed(event ->{
			if(event.getCode() == KeyCode.ENTER) {
				if(!gameMenu.isVisible()) {
					FadeTransition ft = new FadeTransition(Duration.seconds(0.5), gameMenu);
					ft.setFromValue(0);
					ft.setToValue(1);
					
					gameMenu.setVisible(true);
					ft.play();
				}
				else {
					FadeTransition ft = new FadeTransition(Duration.seconds(0.5), gameMenu);
					ft.setFromValue(1);
					ft.setToValue(0);
					ft.setOnFinished(evt -> gameMenu.setVisible(false));
					ft.play();
				}
			}
		});
		
		primaryStage.setScene(scene1);
		primaryStage.show();	
	}
	
	private class GameMenu extends Parent{
		public GameMenu() {
			VBox menuMain = new VBox(10);
			VBox menuSongSelector = new VBox(10);
			
			menuMain.setTranslateX(150);
			menuMain.setTranslateY(300);
			
			menuSongSelector.setTranslateX(150);
			menuSongSelector.setTranslateY(300);
			
			final int offset = 400;
			
			menuSongSelector.setTranslateX(offset);
			
			MenuButton btnIgnore = new MenuButton("Ignore");
			btnIgnore.setOnMouseClicked(event ->{
				FadeTransition ft = new FadeTransition(Duration.seconds(0.5), this); //check lower duration here
				ft.setFromValue(1);
				ft.setToValue(0);
				ft.setOnFinished(evt -> setVisible(false));
				ft.play();
			
			});
			
			MenuButton btnPlay = new MenuButton("PLAY");
			btnPlay.setOnMouseClicked(event ->{
				getChildren().add(menuSongSelector);
				
				TranslateTransition tt = new TranslateTransition(Duration.seconds(0.1), menuMain);
				tt.setToX(menuMain.getTranslateX() - offset);
				
				TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.5), menuSongSelector);
				tt.setToX(menuMain.getTranslateX());
				
				tt.play();
				tt1.play();
				
				tt.setOnFinished(evt ->{
					getChildren().remove(menuMain);
				});
			});
			
			MenuButton btnExit = new MenuButton("EXIT");
			btnExit.setOnMouseClicked(event ->{
				System.exit(0);
			});
			
			MenuButton btnBack = new MenuButton("GO BACK");
			btnBack.setOnMouseClicked(event ->{
				getChildren().add(menuMain);
				
				TranslateTransition tt = new TranslateTransition(Duration.seconds(0.1), menuSongSelector);
				tt.setToX(menuSongSelector.getTranslateX() + offset); //incase! check here
				
				TranslateTransition tt1 = new TranslateTransition(Duration.seconds(0.5), menuMain);
				tt.setToX(menuSongSelector.getTranslateX());
				
				tt.play();
				tt1.play();
				
				tt.setOnFinished(evt ->{
				getChildren().remove(menuSongSelector);
				});
			});
			
			MenuButton btnTakeOnMe = new MenuButton("Take on Me");
			btnTakeOnMe.setOnMouseClicked(event ->{
				//Main main = new Main();
				//main.launch(null);
				//Scene s = new Scene();
				
				//primStage.setScene(s);
				//main.start(primStage);
			});
			
			MenuButton btnDannyPhantom = new MenuButton("Danny Phantom Theme Song");
			
			MenuButton btnFinale = new MenuButton("Finale by Madeon");
			
			
			menuMain.getChildren().addAll(btnPlay, btnExit, btnIgnore);
			menuSongSelector.getChildren().addAll(btnTakeOnMe, btnDannyPhantom, btnFinale, btnBack);
			
			Rectangle bg = new Rectangle(800,600);
			bg.setFill(Color.GREY);
			bg.setOpacity(0.4);
			
			getChildren().addAll(bg, menuMain);
			
		}
	}
	
	private static class MenuButton extends StackPane {
		private Text text;
		
		public MenuButton(String name) {
			text = new Text(name);
			text.setFont(this.text.getFont().font(20));
			text.setFill(Color.WHITE);
			
			Rectangle bg = new Rectangle(255, 30);
			bg.setOpacity(0.6);
			bg.setFill(Color.BLACK);
			bg.setEffect(new GaussianBlur(3.5));
				
			setAlignment(Pos.CENTER_LEFT);
			setRotate(-0.6);
			getChildren().addAll(bg, text);
			
			setOnMouseEntered(event ->{
				bg.setTranslateX(10);
				text.setTranslateX(10);
				bg.setFill(Color.WHITE);
				text.setFill(Color.BLACK);
			});
			
			setOnMouseExited(event ->{
				bg.setTranslateX(0);
				text.setTranslateX(0);
				bg.setFill(Color.BLACK);
				text.setFill(Color.WHITE);
			});
			
			DropShadow drop = new DropShadow(50, Color.WHITE);
			drop.setInput(new Glow());
			
			setOnMousePressed(event -> setEffect(drop));
			setOnMouseReleased(event -> setEffect(null));	
		}
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
