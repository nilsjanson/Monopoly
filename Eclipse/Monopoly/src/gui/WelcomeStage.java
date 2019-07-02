package gui;

import java.util.concurrent.Semaphore;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class WelcomeStage {
	
	Stage prime;
	public int spieler = 0;
	public String playerName;
	public ImageView[] playerArr;
	public Semaphore actionSeamphore;
	
	WelcomeStage(Stage prime) {

		actionSeamphore = new Semaphore(0);
		this.prime=prime;
		welcome();
	}

	private void welcome() {
		Stage welcome = new Stage();
		VBox vbox = new VBox();
		ImageView logo = new ImageView(getClass().getResource("/icons/TH-Poly-Logo.jpg").toExternalForm());
		vbox.setAlignment(Pos.CENTER);
		vbox.setStyle("-fx-background-color: lightgreen");
		HBox hbox = new HBox();
		Button zwei = new Button("2 Spieler");
		Button drei = new Button("3 Spieler");
		Button vier = new Button("4 Spieler");

		butStyle(zwei, drei, vier);
		hbox.getChildren().addAll(zwei, drei, vier);
		hbox.setSpacing(10);
		hbox.setStyle("-fx-padding:1em");
		hbox.setAlignment(Pos.CENTER);

		HBox playerNames = new HBox();
		TextField name = new TextField();
		Label playerName = new Label("Ihr Spielername:");
		playerName.setStyle(
				"-fx-background-color:lightgreen; -fx-padding:0.5em; -fx-text-fill: black; -fx-font-size:20px;");
		textFieldStyle(name);
		playerNames.getChildren().addAll(playerName, name);
		playerNames.setAlignment(Pos.CENTER);

		zwei.setOnAction(e -> startGame(2, name.getText()));
		drei.setOnAction(e -> startGame(3, name.getText()));
		vier.setOnAction(e -> startGame(4, name.getText()));

		vbox.getChildren().addAll(logo, hbox, playerNames);
		Scene scene = new Scene(vbox);
		vbox.autosize();
		controlWelcome(scene);
		welcome.setScene(scene);
		welcome.initStyle(StageStyle.UNDECORATED);
		welcome.initModality(Modality.APPLICATION_MODAL);
		welcome.show();
		welcome.centerOnScreen();
	}
	


	private void startGame(int player, String playername) {
		spieler = player;
		if (playername == null || playername.equals("")) {
			this.playerName = "Player " + (int) (Math.random() * 10000);
		} else {
			this.playerName = playername;
		}
		playerArr = new ImageView[player];
		actionSeamphore.release(1);
		new Board(prime);
	}
	
	private void textFieldStyle(TextField... x) {
		for (TextField field : x) {
			field.setStyle("-fx-border-color: black; -fx-control-inner-background: lightgreen; -fx-font-size: 2em;");
		}
		// vbox.setStyle("-fx-background-color: rgb(" + 192 + "," + 254 + ", " + 213 +
		// ");");
	}
	
	private void butStyle(Button... x) {
		for (Button but : x) {
			but.setStyle(
					"-fx-border-color: black; -fx-background-color: lightgreen; -fx-border-color: black; -fx-font-size: 2em;");
		}
	}
	
	private void controlWelcome(Scene scene) {
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				switch (event.getCode()) {
				case ESCAPE:
					System.exit(0);
					break;
				default:
					System.out.println(event.getCode() + " erkannt!");
					break;
				}
			}
		});
	}
	
}
