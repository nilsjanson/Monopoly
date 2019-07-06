package gui;

import java.util.ArrayList;
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
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.Server;

public class WelcomeStage {

	Stage prime;
	Board board;
	TextField portField;
	TextField servip;

	WelcomeStage(Stage prime, Board board) {
		this.board = board;
		board.actionSeamphore = new Semaphore(0);
		this.prime = prime;
		welcome();
	}

	private void welcome() {
		VBox vbox = new VBox();
		ImageView logo = new ImageView(getClass().getResource("/icons/TH-Poly-Logo.jpg").toExternalForm());
		vbox.setAlignment(Pos.CENTER);
		vbox.setStyle("-fx-background-color: lightgreen");
		HBox buttonBox = new HBox();
		Button zwei = new Button("2 Spieler");
		Button drei = new Button("3 Spieler");
		Button vier = new Button("4 Spieler");

		butStyle(zwei, drei, vier);
		buttonBox.getChildren().addAll(zwei, drei, vier);
		buttonBox.setSpacing(10);
		buttonBox.setStyle("-fx-padding:1em");

		HBox playerNames = new HBox();
		TextField name = new TextField();
		Label playerName = new Label("Ihr Spielername:");
		labelStyle(playerName);
		textFieldStyle(name);
		playerNames.getChildren().addAll(playerName, name);

		zwei.setOnAction(e -> startGame(2, name.getText()));
		drei.setOnAction(e -> startGame(3, name.getText()));
		vier.setOnAction(e -> startGame(4, name.getText()));

		HBox getServerIp = new HBox();
		Label labelIp = new Label("Server IP:");
		labelStyle(labelIp);
		servip = new TextField();
		servip.setText("localhost");
		textFieldStyle(servip);
		getServerIp.getChildren().addAll(labelIp, servip);

		HBox getPort = new HBox();
		Label portLabel = new Label("Port: ");
		labelStyle(portLabel);
		portField = new TextField();
		portField.setText("1099");
		textFieldStyle(portField);

		getPort.getChildren().addAll(portLabel, portField);
		Button startServer = new Button("Start Server");
		serverButStyle(startServer);
		setAligmentCenter(getPort, playerNames, getServerIp, buttonBox);
		vbox.getChildren().addAll(logo, buttonBox, playerNames, getServerIp, getPort, startServer);
		Scene scene = new Scene(vbox);
		vbox.autosize();
		controlWelcome(scene);
		prime.setScene(scene);
		prime.initStyle(StageStyle.UNDECORATED);
		prime.show();
		prime.centerOnScreen();
	}

	private void setAligmentCenter(HBox... boxes) {
		for (HBox x : boxes) {
			x.setAlignment(Pos.CENTER);
		}
	}

	private void labelStyle(Label label) {
		label.setStyle("-fx-background-color:lightgreen; -fx-padding:0.5em; -fx-text-fill: black; -fx-font-size:20px;");
	}

	private void startGame(int player, String playername) {
		board.spieler = player;
		board.setIp(servip.getText());
		board.setPort(portField.getText());
		if (playername == null || playername.equals("")) {
			board.playerName = "Player " + (int) (Math.random() * 10000);
		} else {
			board.playerName = playername;
		}
		board.playerArr = new ImageView[player];
		board.actionSeamphore.release(1);
		board.createBoard();
		board.setLobby(new Lobby(board, playername));
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

	private void serverButStyle(Button x) {
		x.setStyle(
				"-fx-border-color: black; -fx-background-color: red; -fx-border-color: black; -fx-font-size: 2em; -fx-text-fill: white");
		String[] arr = new String[] { portField.getText() };
		x.setOnAction(e -> startServer(arr));
	}

	private void startServer(String... arr) {
		Thread thread = new Thread() {
			public void run() {
				Server.main(arr);
			}
		};
		thread.start();
	}

	private void controlWelcome(Scene scene) {
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				switch (event.getCode()) {
				case ESCAPE:
					System.exit(0);
					break;
				case F1:
					helpMe();
					break;
				default:
					System.out.println(event.getCode() + " erkannt!");
					break;
				}
			}
		});
	}

	protected void helpMe() {
		Stage help = new Stage();
		VBox vbox = new VBox();
		ArrayList<Label> helpfull = new ArrayList<Label>();
		helpfull.add(new Label("F1 = Hilfe"));
		helpfull.add(new Label("Enter = Wuerfeln bestaetigen"));
		helpfull.add(new Label("F11 = Musik an"));
		helpfull.add(new Label("F12 = Musik aus"));
		helpfull.add(new Label("ESC = Beendet das Programm"));
		helpLabelStyle(helpfull);
		vbox.getChildren().addAll(helpfull);
		vbox.setStyle("-fx-background-color: rgb(" + 192 + "," + 254 + ", " + 213 + "); -fx-border-color: black;");
		Scene scene = new Scene(vbox);
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				switch (event.getCode()) {
				case ESCAPE:
					help.close();
					break;
				case ENTER:
					help.close();
					break;
				default:
					break;
				}
			}
		});
		help.setScene(scene);
		help.initModality(Modality.APPLICATION_MODAL);
		help.initStyle(StageStyle.UNDECORATED);
		help.show();
	}

	private void helpLabelStyle(ArrayList<Label> help) {
		for (Label x : help) {
			x.setStyle("-fx-font-size: 2em;");
		}
	}

}
