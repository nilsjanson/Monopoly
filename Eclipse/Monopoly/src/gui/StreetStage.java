package gui;

import java.util.concurrent.Semaphore;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class StreetStage extends Application {

	Board board;
	Stage stage;
	ImageView img;
	boolean kaufbar;
	boolean versteigerbar;
	boolean hausKaufbar;
	boolean aktionErforderlich;
	int playerNumber;
	public Semaphore readySem = new Semaphore(0);
	public Semaphore actionSem = new Semaphore(0);
	public int action;
	
	

	
	public StreetStage(Board board, String name, int playerNumber,boolean kaufbar, boolean versteigerbar, boolean hausKaufbar,boolean aktionErforderlich) {
		board.streetStageOpen = this;
		this.kaufbar= kaufbar;
		this.versteigerbar= versteigerbar;
		this.hausKaufbar = hausKaufbar;
		this.playerNumber= playerNumber;
		this.aktionErforderlich = aktionErforderlich;
		img = new ImageView(getClass().getResource("/besitzkarten/"+name+".jpg").toExternalForm());
		this.board= board;
		img.setFitWidth(board.getMax()*0.55);
		img.setFitHeight(board.getMax()*0.55);
		try {
			this.start(board.prime);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		
	StreetStage(Board board, ImageView img) {
		this.img = img;
		this.board = board;
	}

	public void start(Stage primaryStage) throws Exception {
		HBox hbox = new HBox();
		VBox vbox = new VBox();
		Button kaufen = new Button("Straﬂe Kaufen");
		kaufen.setVisible(kaufbar);
		
		Button versteigern = new Button("Versteigern");
		versteigern.setVisible(versteigerbar);
		Button haus = new Button("Haus kaufen");
		haus.setVisible(hausKaufbar);
		Label player = new Label("Spieler " + playerNumber);
		if(aktionErforderlich) {
		kaufen.setOnAction( new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				System.out.println("Sie haben die Straﬂe gekauft");
				action = 1;
				actionSem.release();	
				stage.close();
				
			}
		});
		
		versteigern.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				System.out.println("Versteigerung starten");
				action = 2;
				actionSem.release();
				stage.close();
			}
		});
		
		haus.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				System.out.println("Haus gekauft.");
				action = 3;
				actionSem.release();
			}
		});
		
		}
		player.setStyle("-fx-font-size: 2em; -fx-text-fill: red;");
		
		buttonStyle(kaufen,versteigern,haus);		
		
		vbox.getChildren().addAll(kaufen,versteigern,haus,player);
		vbox.setAlignment(Pos.CENTER);
		vbox.setSpacing(10);
		vbox.setStyle("-fx-background-color: white;");
		hbox.setStyle("-fx-background-color: white;");
		hbox.getChildren().addAll(img,vbox);
		hbox.setSpacing(20);
		Scene scene = new Scene(hbox);
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				switch (event.getCode()) {
				case F1:
					board.helpMe();
					break;
				case ENTER:
					stage.close();
					break;
				case ESCAPE:
					stage.close();
					break;
				case C:
					stage.centerOnScreen();
					break;
				default:
					System.out.println(event.getCode());
					break;
				}
			}
		});
		stage = new Stage();
		stage.setScene(scene);
		stage.initStyle(StageStyle.UNDECORATED);
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.show();
		stage.centerOnScreen();
		board.streetStageOpenSemaphore.release();
		
	}
	public void close() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				
				stage.close();
				
			}
		});
	}
	private void buttonStyle(Button...buttons) {
		for (Button x: buttons) {
			x.setStyle("-fx-background-color: black; -fx-border-color: black; -fx-font-size: 2em; -fx-text-fill: white");
			x.setPrefWidth(200);
			x.setPrefHeight(20);
		}
	}
	
	

}
