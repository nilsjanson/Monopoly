package gui;

import java.net.URL;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class EmailStage extends Application {

	Board board;
	String betreff;
	String von;
	String inhalt;

	final URL resource = getClass().getResource("/sounds/mailSound.wav");
	final Media media = new Media(resource.toString());
	final MediaPlayer mediaPlayer = new MediaPlayer(media);
	
	
	EmailStage(Board board, String betreff, String von, String inhalt) {
		this.betreff = betreff;
		this.von = von;
		this.inhalt = inhalt;
		this.board = board;
		start(board.prime);
	}

	@Override
	public void start(Stage primaryStage) {
		mediaPlayer.play();
		Stage email = new Stage();
		VBox vbox = new VBox();
		vbox.setAlignment(Pos.CENTER);
		Scene scene = new Scene(vbox);
		Label head = new Label("Sie haben Post!");
		head.setStyle("-fx-font-size: 6em; -fx-font-color: white;");
		Label betref = new Label("Betreff: " + betreff);
		Label vo = new Label("Von: " + von);
		Label absatz = new Label("");
		Label inhal = new Label(inhalt);
		labelStyle(betref, vo, inhal);
		vboxStyle(vbox);
		vbox.autosize();
		vbox.getChildren().addAll(head,betref, vo, absatz, inhal);
		email.setScene(scene);
		email.initStyle(StageStyle.UNDECORATED);
		email.initModality(Modality.APPLICATION_MODAL);
		email.show();

		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				switch (event.getCode()) {
				case F1:
					board.helpMe();
					break;
				case ESCAPE:
					email.close();
					break;
				default:
					System.out.println(event.getCode() + " erkannt!");
					break;
				}
			}
		});
	}

	void labelStyle(Label... labels) {
		for (Label x : labels) {
			x.setStyle("-fx-font-size: 2em;");
		}
	}

	void vboxStyle(VBox vbox) {
		vbox.setStyle("-fx-background-color: rgb( 192,254,213); -fx-border-color: black;");
	}
}
