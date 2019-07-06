package gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ExitStage {
	ExitStage(Stage prime) {
		Thread thread = new Thread() {
			public void start() {
				createExitStage();
			}
		};
		thread.start();
		
	}
	
	private void createExitStage() {
		Stage exit = new Stage();
		exit.initModality(Modality.APPLICATION_MODAL);
		exit.initStyle(StageStyle.UNDECORATED);
		exit.initModality(Modality.APPLICATION_MODAL);
		Button leave = new Button("Beenden");
		Button stay = new Button("Abbrechen");
		stay.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				exit.close();
			}
		});
		leave.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				System.exit(0);
			}
		});
		butStyle(leave,stay);
		HBox buttons = new HBox();
		buttons.setSpacing(20);
		buttons.setAlignment(Pos.CENTER);
		buttons.getChildren().addAll(leave,stay);
		Label sicher = new Label("Moechten Sie das Spiel wirklich beenden?");
		Label verloren = new Label("Ungespeicherte Zustaende gehen verloren..");
		labelStyle(sicher,verloren);
		VBox vbox = new VBox();
		vbox.setStyle("-fx-background-color: rgb(" + 192 + "," + 254 + ", " + 213 + "); -fx-border-color: black;");
		vbox.getChildren().addAll(sicher,verloren,buttons);
		vbox.setAlignment(Pos.CENTER);
		Scene scene = new Scene(vbox);
		exit.setScene(scene);
		exit.setAlwaysOnTop(true);
		exit.initModality(Modality.APPLICATION_MODAL);
		exit.show();
	}
	
	private void labelStyle(Label...labels) {
		for (Label x : labels) {
			x.setStyle("-fx-font-size: 2em;");
			x.setAlignment(Pos.CENTER);
		}
	}
	
	private void butStyle(Button... x) {
		for (Button but : x) {
			but.setStyle(
					"-fx-border-color: black; -fx-background-color: lightgreen; -fx-border-color: black; -fx-font-size: 2em;");
		}
	}
}
