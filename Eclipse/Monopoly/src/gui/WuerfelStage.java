package gui;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class WuerfelStage {
	int x;
	int y;
	Stage stage;
	Board game;
	ArrayList<ImageView> views = new ArrayList<ImageView>();
	private Semaphore leertaste ;

	public WuerfelStage(Board board) {
		game = board;
		try {
			views.add(new ImageView(getClass().getResource("/wuerfel/1.png").toExternalForm()));
			views.add(new ImageView(getClass().getResource("/wuerfel/2.png").toExternalForm()));
			views.add(new ImageView(getClass().getResource("/wuerfel/3.png").toExternalForm()));
			views.add(new ImageView(getClass().getResource("/wuerfel/4.png").toExternalForm()));
			views.add(new ImageView(getClass().getResource("/wuerfel/5.png").toExternalForm()));
			views.add(new ImageView(getClass().getResource("/wuerfel/6.png").toExternalForm()));

			views.add(new ImageView(getClass().getResource("/wuerfel/1.png").toExternalForm()));
			views.add(new ImageView(getClass().getResource("/wuerfel/2.png").toExternalForm()));
			views.add(new ImageView(getClass().getResource("/wuerfel/3.png").toExternalForm()));
			views.add(new ImageView(getClass().getResource("/wuerfel/4.png").toExternalForm()));
			views.add(new ImageView(getClass().getResource("/wuerfel/5.png").toExternalForm()));
			views.add(new ImageView(getClass().getResource("/wuerfel/6.png").toExternalForm()));
			leertaste = new Semaphore(0);
			run();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	
	public void run() {
		startWuerfelStage();
	}
	
	public void showWuerfel(Stage primaryStage,int wuerfel1 , int wuerfel2) {
		HBox hbox = new HBox();
		int x=wuerfel1;
		int y=wuerfel2;
		hbox.getChildren().add(views.get(x - 1));
		hbox.getChildren().add(views.get((y + 6) - 1));
		Scene scene = new Scene(hbox);
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				switch (event.getCode()) {
				
				case SPACE:
					leertaste.release();
					break;
				case W:
					leertaste.release();
					break;
				case F1:
					game.helpMe();
					break;
				case ENTER:
					stage.close();
					break;
				default:
					break;
				}
			}
		});
		stage = new Stage();
		stage.setScene(scene);
		stage.initStyle(StageStyle.UNDECORATED);
		stage.show();
	}



	public Semaphore getLeertaste() {
		return leertaste;
	}

	
	public void startWuerfelStage()  {
		leertaste = new Semaphore(0);
		HBox hbox = new HBox();
		hbox.getChildren().add(views.get(1));
		hbox.getChildren().add(views.get((7)));
		Scene scene = new Scene(hbox);
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				switch (event.getCode()) {
				
				case SPACE:
					leertaste.release();
					break;
				case W:
					leertaste.release();
					break;
				case F1:
					game.helpMe();
					break;
				default:
					break;
				}
			}
		});
		stage = new Stage();
		stage.setScene(scene);
		stage.initStyle(StageStyle.UNDECORATED);
		stage.show();
		
	}
}
