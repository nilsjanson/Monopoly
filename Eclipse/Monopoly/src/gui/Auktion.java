package gui;

import java.util.ArrayList;
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
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Auktion {

	Board board;
	TextField gebot = new TextField();
	Label aktGebot;

	Auktion(Stage prime, Board board, String strassenName, String... playerNames) {
		VBox vbox = new VBox();
		vbox.setAlignment(Pos.CENTER);
		vbox.setStyle("-fx-background-color:rgb(" + 192 + "," + 254 + ", " + 213 + ");");
		ImageView icon = new ImageView(
				getClass().getResource("/besitzkarten/" + strassenName + ".jpg").toExternalForm());
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
		gebot.setMaxHeight(prime.getHeight() / 10);
		gebot.setMaxWidth(prime.getWidth() / 10);
		Label akt = new Label("Aktuelles Gebot");
		aktGebot = new Label("0€");
		Label ihrGebot = new Label("Ihr Gebot?");
		labelStyle(akt, ihrGebot);
		aktGebot.setTextFill(Color.RED);
		aktGebot.setStyle("-fx-text-size:40px;");
		hbox.setAlignment(Pos.CENTER);
		HBox buttons = new HBox();
		Button bestaetigen = new Button("Bestaetigen");
		Button aussteigen = new Button("Aussteigen");
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
		prime.setScene(scene);
	}

	private void bieten() {
		System.out.println("Hier koennte man ein Gebot abgeben. Eingegebenes Gebot " + gebot.getText() + "€");
	}

	public void neuesGebot(int x) {
		this.aktGebot.setText("" + x);
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
}
