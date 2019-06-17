package gui;

import java.net.URL;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Board {

	Board me = this;
	final URL resource = getClass().getResource("/musik/StillStanding.mp3");
	final Media media = new Media(resource.toString());
	final MediaPlayer mediaPlayer = new MediaPlayer(media);
	protected Stage prime;
	private Stage welcome;
	int spieler = 0;
	BorderPane parent;
	private double max;
	int buttonCount = 0;

	public Board(Stage prime) {
		mediaPlayer.play();
		this.prime = prime;
		welcome();
	}
	
	protected double getMax() {
		return max;
	}

	// private void createPlayer1(double width, double height) {
	// Player1 one = new Player1();
	// ImageView icon = one.getIcon();
	// height = height / 20;
	// width = width / 20;
	// icon.setFitHeight(height);
	// icon.setFitWidth(width);
	// parent.getChildren().add(icon);
	// icon.setX(prime.getWidth() - width);
	// icon.setY(prime.getHeight() - height);
	// }
	//
	// private void createPlayer2(double width, double height) {
	//
	// height = height / 20;
	// width = width / 20;
	// icon.setFitHeight(height);
	// icon.setFitWidth(width);
	// parent.getChildren().add(icon);
	// icon.setX(prime.getWidth() - width);
	// icon.setY(prime.getHeight() - (2 * height));
	// }

	private void createBoard() {
		welcome.close();
		Rectangle2D screen = Screen.getPrimary().getVisualBounds();
		double width = screen.getMaxX();
		double height = screen.getMaxY();
		max = Math.min(width, height);
		BorderPane pane = new BorderPane();

		parent = pane;
		pane.setStyle("-fx-background-color: lightgreen;" + "-fx-background-image: url(\"/icons/TH-Poly.jpg\");"
				+ "    -fx-background-size: " + max + " " + max + ";");


		HBox buttom = hButtonRow(true);
		VBox left = vButtonRow(true);
		HBox top = hButtonRow();
		VBox right = vButtonRow();

		pane.setTop(top);
		pane.setBottom(buttom);
		pane.setLeft(left);
		pane.setRight(right);
		pane.setCenter(createCardFields());

		BorderPane.setAlignment(left, Pos.CENTER);
		BorderPane.setAlignment(right, Pos.CENTER);

		Scene scene = new Scene(pane);
		prime.initStyle(StageStyle.UNDECORATED);
		controlBoard(scene);
		prime.setScene(scene);
		prime.setWidth(max);
		prime.setHeight(max);
		prime.show();
		System.out.println("Maximale Groe�e: " + max);
	}

	private Pane createCardFields() {
		// parent.setRotate(45);
		Button email = new Button("EMAIL");
		Button asta = new Button("ASTA");
		Pane cards = new Pane();
		email.setStyle("-fx-border-color: transparent; -fx-background-color: transparent; -fx-text-fill: transparent;");
		asta.setStyle("-fx-border-color: transparent; -fx-background-color: transparent;  -fx-text-fill: transparent;");

		email.setPrefHeight(max * 0.1414);
		asta.setPrefHeight(max * 0.1414);
		asta.setPrefWidth(max * 0.1986);
		email.setPrefWidth(max * 0.1986);

		email.setRotate(-45);
		email.setLayoutX(max * 0.47);
		email.setLayoutY(max * 0.51);
		asta.setRotate(135);
		asta.setLayoutX(max * 0.065);
		asta.setLayoutY(max * 0.085);

		email.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				System.out.println("Hier koennte eine E-Mail stehen.");
			}
		});

		asta.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				System.out.println("Hier koennte eine Asta Karte stehen.");
			}
		});

		cards.getChildren().addAll(email, asta);
		return cards;
	}

	private HBox hButtonRow() {
		HBox hbox = new HBox();
		ArrayList<Button> buttons = new ArrayList<Button>();
		for (int i = 0; i <= 10; i++) {
			buttons.add(new Button("" + buttonCount++));
		}
		streetHButs(buttons);
		hbox.getChildren().addAll(buttons);
		return hbox;
	}

	private HBox hButtonRow(boolean invers) {
		HBox hbox = new HBox();
		ArrayList<Button> buttons = new ArrayList<Button>();
		int j = buttonCount + 10;
		for (int i = 0; i <= 10; i++) {
			buttons.add(new Button("" + j--));
			buttonCount++;
		}
		streetHButs(buttons);
		hbox.getChildren().addAll(buttons);
		return hbox;
	}

	private VBox vButtonRow() {
		VBox vbox = new VBox();
		ArrayList<Button> buttons = new ArrayList<Button>();
		for (int i = 0; i <= 8; i++) {
			buttons.add(new Button("" + buttonCount++));
		}
		streetVButs(buttons);
		vbox.getChildren().addAll(buttons);
		return vbox;
	}

	private VBox vButtonRow(boolean invers) {
		VBox vbox = new VBox();
		ArrayList<Button> buttons = new ArrayList<Button>();
		int j = buttonCount + 8;
		for (int i = 0; i <= 8; i++) {
			buttons.add(new Button("" + j--));
			buttonCount++;
		}
		streetVButs(buttons);
		vbox.getChildren().addAll(buttons);
		return vbox;
	}

	private void streetHButs(ArrayList<Button> buttons) {
		for (Button x : buttons) {
			x.setPrefHeight(max * 0.134);
			x.setPrefWidth(max * 0.0843);
			;
			x.setStyle("-fx-border-color: transparent; -fx-background-color: transparent");
			x.setTextFill(Color.TRANSPARENT);
			x.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					System.out.println("Street " + x.getText());
				}
			});
		}
		buttons.get(0).setPrefWidth(max * 0.136);
		buttons.get(buttons.size() - 1).setPrefWidth(max * 0.136);

		buttons.get(0).setPrefHeight(max * 0.136);
		buttons.get(buttons.size() - 1).setPrefHeight(max * 0.136);
	}

	private void streetVButs(ArrayList<Button> buttons) {
		for (Button x : buttons) {
			x.setPrefWidth(max * 0.134);
			x.setPrefHeight(max * 0.0814);
			;
			x.setStyle("-fx-border-color: transparent; -fx-background-color: transparent");
			x.setTextFill(Color.TRANSPARENT);
			x.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					System.out.println("Street " + x.getText());
				}
			});
		}
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
		helpfull.add(new Label("Enter = Wuerfeln bestaetigen"));
		helpfull.add(new Label("F11 = Musik an"));
		helpfull.add(new Label("F12 = Musik aus"));
		helpfull.add(new Label("ESC = Beendet das Programm"));
		vbox.getChildren().addAll(helpfull);
		vbox.setStyle("-fx-background-color: rgb(" + 192 + "," + 254 + ", " + 213 + ");");
		Scene scene = new Scene(vbox);
		help.setScene(scene);
		help.initModality(Modality.APPLICATION_MODAL);
		help.show();
	}

	private void controlBoard(Scene scene) {
		scene.setOnMousePressed(new EventHandler<javafx.scene.input.MouseEvent>() {
			@Override
			public void handle(javafx.scene.input.MouseEvent event) {
				System.out.println("X: " + event.getSceneX() + " Y: " + event.getSceneY());
			}
		});
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				switch (event.getCode()) {
				case F1:
					helpMe();
					break;
				case E:
					try {
				//		new EmailStage(me,"Stupa Seminar","Studiensekreteriat","Wir laden Sie herzlichst zu unserem Stupa Seminar am 15.07 ein.").start(prime);
					} catch (Exception e) {
						e.printStackTrace();
					}
					break;
				case B: 
		//			new StreetStage(me,"AstaBuero"); 
					break;
				case CONTROL:
					parent.setRotate(parent.getRotate() - 90);
					break;
				case F11:
					mediaPlayer.play();
					break;
				case F12:
					mediaPlayer.stop();
					break;
				case WINDOWS:
					break;
				case SPACE:
					new WuerfelStage(me);
					break;
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
	
//	public void eventFilter(Scene scene) {
//		scene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
//	                public void handle(KeyEvent.) { 
//	                	
//	                };
//	            });
//	}

	private void controlWelcome(Scene scene) {
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				switch (event.getCode()) {
				case F12:
					mediaPlayer.stop();
					break;
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
