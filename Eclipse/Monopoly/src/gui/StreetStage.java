package gui;

import javafx.application.Application;
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

	
	StreetStage(Board board, String name) {
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
		Button versteigern = new Button("Versteigern");
		Button haus = new Button("Haus kaufen");
		Label player = new Label("");
		
		kaufen.setOnAction( new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				System.out.println("Hier koennte man eine Strasse kaufen.");
				player.setText("Spieler 1");
			}
		});
		
		versteigern.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				System.out.println("Hier koennte man eine Versteigerung starten.");
			}
		});
		
		haus.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				System.out.println("Hier koennte man ein Haus kaufen.");
			}
		});
		
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
	}
	
	private void buttonStyle(Button...buttons) {
		for (Button x: buttons) {
			x.setStyle("-fx-background-color: black; -fx-border-color: black; -fx-font-size: 2em; -fx-text-fill: white");
			x.setPrefWidth(200);
			x.setPrefHeight(20);
		}
	}
	
	

}
