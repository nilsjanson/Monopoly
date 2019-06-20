package gui;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

import javafx.application.Application;
import javafx.application.Platform;
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

public class Auktion extends Application {

	Board board;
	TextField gebot = new TextField();
	Label aktGebot;
	Button bestaetigen;
	Button aussteigen;
	Stage stage;
	ImageView icon;
	String[] playerNames;
	
	public int meinGebot;
	
	

	public Auktion( Board board, String strassenName, String... playerNames) {
		this.board= board;
		icon = new ImageView(
				getClass().getResource("/besitzkarten/" + strassenName + ".jpg").toExternalForm());
		this.playerNames = playerNames;
		board.auktionStageOpen = this;
		
		try {
			start(board.prime);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
	}

	private void bieten() {
		
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				meinGebot = Integer.parseInt(gebot.getText());
				board.gebotAbgegeben.release(1);
				System.out.println("Eingegebenes Gebot " + meinGebot + "€");
			
				
			}
		});
	}
	
	private void aussteigen() {
		System.out.println("Spieler: "+ "" +"ist aus der Auktion ausgestiegen.");
	}

	public void neuesGebot(int x) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				aktGebot.setText("" + x);
			}
		});
	}
	
	public void disableButtons() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				aussteigen.setVisible(false);
				bestaetigen.setVisible(false);
			}
		});
	
		
	}
	
	public void yourTurn() {
		
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				board.gebotAbgegeben = new Semaphore(0); 
				aussteigen.setVisible(true);
				bestaetigen.setVisible(true);
				
			}
		});
		
		
	}
	
	public int getGebot() {
		try {

		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return Integer.parseInt(aktGebot.getText());
	}

	private void butStyle(Button... buttons) {
		for (Button but : buttons) {
			but.setStyle(
					"-fx-border-color: black; -fx-background-color: lightgreen; -fx-border-color: black; -fx-font-size: 2em;");
		}
	}

	private void labelStyle(Label... labels) {
		for (Label lab : labels) {
			lab.setStyle("-fx-font-size:30px; -fx-font-weight: bold;");
		}
	}

	private void helpMe() {
		Stage help = new Stage();
		VBox vbox = new VBox();
		ArrayList<Label> helpfull = new ArrayList<Label>();
		helpfull.add(new Label("F1 = Hilfe"));
		helpfull.add(new Label("Enter = Gebot bestaetigen"));
		helpfull.add(new Label("F11 = Musik an"));
		helpfull.add(new Label("F12 = Musik aus"));
		helpLabelStyle(helpfull);
		vbox.getChildren().addAll(helpfull);
		vbox.setStyle("-fx-background-color: rgb(" + 192 + "," + 254 + ", " + 213 + ");");
		Scene scene = new Scene(vbox);
		help.setScene(scene);
		help.initModality(Modality.APPLICATION_MODAL);
		help.show();
	}

	private void helpLabelStyle(ArrayList<Label> help) {
		for (Label x : help) {
			x.setStyle("-fx-font-size: 2em;");
		}
	}

	@Override
	public void start(Stage prime) throws Exception {
		VBox vbox = new VBox();
		vbox.setAlignment(Pos.CENTER);
		vbox.setStyle("-fx-background-color:rgb(" + 192 + "," + 254 + ", " + 213 + ");");
		icon.setFitHeight(prime.getHeight() / 2);
		icon.setFitWidth(prime.getWidth() / 2);
		vbox.getChildren().add(icon);
		HBox hbox = new HBox();
		for (String x : playerNames) {
			Label name = new Label(x);
			name.setStyle("-fx-font-size:20px; -fx-text-fill: black; -fx-padding:0.5em;");
			hbox.getChildren().add(name);
		}
		VBox bieten = new VBox();
		gebot.setStyle("-fx-font-size:10px;");
		gebot.setMaxWidth(prime.getWidth() / 10);
		Label akt = new Label("Aktuelles Gebot");
		aktGebot = new Label("0€");
		Label ihrGebot = new Label("Ihr Gebot?");
		labelStyle(akt, ihrGebot);
		aktGebot.setStyle("-fx-font-size:30px; -fx-text-fill: red;");
		hbox.setAlignment(Pos.CENTER);
		HBox buttons = new HBox();
		bestaetigen = new Button("Bestaetigen");
		bestaetigen.setOnAction(e-> bieten());
		bestaetigen.setVisible(false);
		aussteigen = new Button("Aussteigen");
		aussteigen.setOnAction(e -> aussteigen());
		aussteigen.setVisible(false);
		buttons.setAlignment(Pos.CENTER);
		butStyle(bestaetigen, aussteigen);
		buttons.setSpacing(20);
		buttons.getChildren().addAll(bestaetigen, aussteigen);

		vbox.getChildren().addAll(hbox, akt, aktGebot, ihrGebot, bieten, gebot, buttons);
		vbox.setSpacing(10);
		Scene scene = new Scene(vbox);
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				switch (event.getCode()) {
				case F1:
					helpMe();
					break;
				case ENTER:
					bieten();
					break;
				case F11:
					board.mediaPlayer.play();
					break;
				case F12:
					board.mediaPlayer.stop();
					break;
				case ESCAPE:
					prime.setScene(board.getScene());
					break;
				default:
					System.out.println(event.getCode() + " erkannt!");
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
		board.auktionStageOpenSemaphore.release();
		
	}
}
