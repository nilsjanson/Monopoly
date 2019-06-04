package gui;

import java.net.URL;
import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Board {
	
	Board me = this;
	final URL resource = getClass().getResource("/musik/AnnoDominiBeatsStillStanding.mp3");
	final Media media = new Media(resource.toString());
	final MediaPlayer mediaPlayer = new MediaPlayer(media);
	private Stage prime;
	private Stage welcome;
	int spieler = 0;
	Pane parent;

	public Board(Stage prime) {
		mediaPlayer.play();
		this.prime = prime;
		welcome();
	}

//	private void createPlayer1(double width, double height) {
//		Player1 one = new Player1();
//		ImageView icon = one.getIcon();
//		height = height / 20;
//		width = width / 20;
//		icon.setFitHeight(height);
//		icon.setFitWidth(width);
//		parent.getChildren().add(icon);
//		icon.setX(prime.getWidth() - width);
//		icon.setY(prime.getHeight() - height);
//	}
//
//	private void createPlayer2(double width, double height) {
//
//		height = height / 20;
//		width = width / 20;
//		icon.setFitHeight(height);
//		icon.setFitWidth(width);
//		parent.getChildren().add(icon);
//		icon.setX(prime.getWidth() - width);
//		icon.setY(prime.getHeight() - (2 * height));
//	}

	private void createBoard() {
		welcome.close();
		Rectangle2D screen = Screen.getPrimary().getVisualBounds();
		double width = screen.getMaxX();
		double height = screen.getMaxY();
		double max = Math.min(width, height);
		Pane pane = new Pane();
		parent = pane;
		pane.setStyle("-fx-background-color: lightgreen;" + "-fx-background-image: url(\"/icons/TH-Poly.jpg\");"
				+ "    -fx-background-size: " + max + " " + max + ";");
		pane.autosize();
		Scene scene = new Scene(pane);
		prime.initStyle(StageStyle.UNDECORATED);
		controlBoard(scene);
		prime.setScene(scene);
		prime.setWidth(max);
		prime.setHeight(max);
		prime.show();
		 System.out.println("Maximale Groeße: "+max);
	}

	void startMusik() {
		final URL resource = getClass().getResource("/musik/AnnoDominiBeatsStillStanding.mp3");
		final Media media = new Media(resource.toString());
		final MediaPlayer mediaPlayer = new MediaPlayer(media);
		mediaPlayer.play();
	}

	private void welcome() {
		welcome = new Stage();
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
		hbox.setAlignment(Pos.CENTER);
		vbox.getChildren().addAll(logo, hbox);
		Scene scene = new Scene(vbox);
		vbox.autosize();
		controlWelcome(scene);
		welcome.setScene(scene);
		welcome.initStyle(StageStyle.UNDECORATED);
		welcome.initModality(Modality.APPLICATION_MODAL);
		welcome.show();
		welcome.centerOnScreen();
	}

	private void butStyle(Button... x) {
		for (Button but : x) {
			but.setStyle("-fx-border-color: black; -fx-background-color: lightgreen; -fx-font-size: 2em;");
			but.setOnAction(e -> createBoard());
		}
	}
	
	protected void helpMe() {
		Stage help = new Stage();
		VBox vbox = new VBox();
		ArrayList<Label> helpfull = new ArrayList<Label>();
		helpfull.add(new Label("F1 = Hilfe"));
		helpfull.add(new Label("F11 = Musik an"));
		helpfull.add(new Label("F12 = Musik aus"));
		helpfull.add(new Label("ESC = Beendet das Programm"));
		vbox.getChildren().addAll(helpfull);
		vbox.setStyle("-fx-background-color: honeydew");
		Scene scene = new Scene(vbox);
		help.setScene(scene);
		help.show();
	}

	private void controlBoard(Scene scene) {
		scene.setOnMousePressed(new EventHandler<javafx.scene.input.MouseEvent>() {
			@Override
			public void handle(javafx.scene.input.MouseEvent event) {
				System.out.println("X: "+event.getSceneX()+" Y: "+event.getSceneY());
			}
		});
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				switch (event.getCode()) {
				case F1:
					helpMe();
					break;
				case SPACE:
					new WuerfelStage(me,1,1);
					break;
				case CONTROL:
					parent.setRotate(parent.getRotate() - 90);
					break;
				case ESCAPE:
					System.exit(0);
					break;
				case F11:
					mediaPlayer.play();
					break;
				case F12:
					mediaPlayer.stop();
				case WINDOWS:
					break;
				default:
					System.out.println(event.getCode() + " erkannt!");
					break;
				}
			}
		});
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
