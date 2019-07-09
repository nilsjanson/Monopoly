package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.Reflection;
import javafx.scene.image.Image;
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
import javafx.util.Duration;
import model.Besitzrechtkarte;
import model.Player;

public class Board {

	Board me = this;
	final URL resource = getClass().getResource("/musik/StillStanding.mp3");
	final Media media = new Media(resource.toString());
	final MediaPlayer mediaPlayer = new MediaPlayer(media);
	public int ownPlayerNumber;
	protected Stage prime;
	public int spieler = 0;
	public String playerName;
	BorderPane parent;
	Lobby lobby;
	private double max;
	private double width;
	private double height;
	public boolean yourTurn = false; // Ob der Spieler am Zug gerade ist und handeln kann
	public List<Integer> actionQueue = new ArrayList<Integer>();
	public Semaphore aktionZugGemachtSem = new Semaphore(0);
	private Scene scene;
	int buttonCount = 0;
	double playerStartPositionX;
	double playerStartPositionY;
	public Semaphore actionSeamphore;
	public WuerfelStage wuerfelStage;
	public InfoStage infoStage;
	public Semaphore boardReady = new Semaphore(0);
	public StreetStage streetStageOpen;
	public Semaphore streetStageOpenSemaphore = new Semaphore(0);
	public Auktion auktionStageOpen;
	public Semaphore auktionStageOpenSemaphore = new Semaphore(0);
	public Semaphore gebotAbgegeben = new Semaphore(0);
	public Semaphore infoStageSemaphore = new Semaphore(0);
	private ArrayList<Button> streetButs = new ArrayList<Button>();
	
	private String ip;
	private String port;

	public ImageView[] playerArr;

	public Board(Stage prime) {
		// mediaPlayer.play();
		actionSeamphore = new Semaphore(0);
		this.prime = prime;
		new WelcomeStage(prime, this);
	}
	

	protected double getMax() {
		return max;
	}

	private ImageView createPlayer(double width, double height, String source) {
		ImageView icon = new ImageView(getClass().getResource(source).toExternalForm());
		icon.setFitHeight(height);
		icon.setFitWidth(width);
		parent.getChildren().add(icon);
		icon.setX(prime.getWidth() - width * 1.76);
		icon.setY(prime.getHeight() - height);
		playerStartPositionX = icon.getX();
		playerStartPositionY = icon.getY();
		return icon;
	}

	public void move(ImageView player) {

		Timeline timeline = new Timeline();

		double xValue = player.getX();
		double yValue = player.getY();

		double x = prime.getHeight() / 12.3;

		if (player.getX() - x < 0) {
			if (player.getY() + x > max) {
				x = x * 1.7;
				xValue -= (x * 0.4);
				yValue -= x;
				player.setRotate(90);
			} else if (player.getY() - x < 0) {
				x = x * 1.7;
				yValue -= (x * 0.4);
				player.setRotate(180);
				xValue += x;
			} else {
				yValue -= x;
			}
		} else if (player.getY() + x > max) {
			xValue -= x;

		} else if (player.getY() - x < 0) {
			if (player.getX() + (x * 1.8) > max) {
				x = x * 1.7;
				xValue += (x * 0.4);
				yValue += x;
				player.setRotate(270);

			} else {
				xValue += x;
			}
		} else {
			if (player.getY() + (x * 3) > max) {
				player.setRotate(0);
				xValue = playerStartPositionX;
				yValue = playerStartPositionY;
			} else {
				yValue += x;
			}

		}

		KeyFrame end = new KeyFrame(Duration.millis(500), new KeyValue(player.xProperty(), xValue),
				new KeyValue(player.yProperty(), yValue));
		timeline.getKeyFrames().add(end);
		timeline.play();
	}
	
	protected void createBoard() {
		Thread thread = new Thread() {
			public void start() {
				createBoard(true);
			}
		};
		thread.start();
	}

	protected void createBoard(boolean boost) {
		Rectangle2D screen = Screen.getPrimary().getVisualBounds();
		width = screen.getMaxX();
		height = screen.getMaxY();
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

		scene = new Scene(pane);
		controlBoard(scene);
		prime.setScene(scene);
		prime.setWidth(max);
		prime.setHeight(max);
		prime.centerOnScreen();
		prime.getIcons().add(new Image("./icons/zylinder.png"));
		prime.show();

		wuerfelStage = new WuerfelStage(me, Math.min(width, height), Math.max(width, height));
		// infoStage = new InfoStage(me,Math.min(width, height), Math.max(width,
		// height));
		playerArr[0] = createPlayer(max * 0.075, max * 0.075, "/playerIcons/bike.png");
		playerArr[1] = createPlayer(max * 0.075, max * 0.075, "/playerIcons/dog.png");
		if (spieler > 2) {
			playerArr[2] = createPlayer(max * 0.075, max * 0.075, "/playerIcons/tank.png");
		}
		if (spieler > 3) {
			playerArr[3] = createPlayer(max * 0.075, max * 0.075, "/playerIcons/misslex.png");
		}

		System.out.println("Maximale Groesse: " + width + " " + max);
		boardReady.release();
		prime.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> ov, Boolean hidden, Boolean shown) {
				if (shown) {
					wuerfelStage.stage.toFront();
					infoStage.info.toFront();
					prime.toFront();
				}
			}
		});
	}

	public void startInfoStage(Player... players) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				infoStage = new InfoStage(me, Math.min(width, height), Math.max(width, height), players);
				lobby.lobby.close();
			}
		});
	}
	
	protected void setLobby(Lobby lobby) {
		this.lobby=lobby;
	}

	ArrayList<Button> getAllButtons() {
		return streetButs;
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
		streetButs.addAll(buttons);
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
		streetButs.addAll(buttons);
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
		streetButs.addAll(buttons);
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
		streetButs.addAll(buttons);
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
			streetButtonControl(x);
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
			streetButtonControl(x);
		}
	}

	private void streetButtonControl(Button button) {
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("Street " + button.getText());
				streetStageOpen = new StreetStage(me,
						Besitzrechtkarte.findByPosition(Integer.parseInt(button.getText())), false, false, false,
						false);
			}
		});

	}

	void startMusik() {
		final URL resource = getClass().getResource("/musik/AnnoDominiBeatsStillStanding.mp3");
		final Media media = new Media(resource.toString());
		final MediaPlayer mediaPlayer = new MediaPlayer(media);
		mediaPlayer.play();
	}

	private void helpLabelStyle(ArrayList<Label> help) {
		for (Label x : help) {
			x.setStyle("-fx-font-size: 2em;");
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

				case W:
					if (yourTurn) {
						actionQueue.add(2);
						yourTurn = false;
						aktionZugGemachtSem.release();

					}

					break;
				case B:
					// new StreetStage(me,"AstaBuero");
					break;
				case A:
					// new Auktion(prime,me,"Mensa","Lars","Nils","Florian");
					// Auktion auk = new Auktion(prime,me,"Mensa","Nils");
					break;
				case X:
					System.exit(0);
					break;
				case H:
					// new ConstructionClass(streetButs.get(39),
					// Besitzrechtkarte.findByPosition(39), true);
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
				case ESCAPE:
					new ExitStage(prime);
					break;
				default:
					System.out.println(event.getCode() + " erkannt!");
					break;
				}
			}
		});
	}

	// public void eventFilter(Scene scene) {
	// scene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
	// public void handle(KeyEvent.) {
	//
	// };
	// });
	// }

	public Scene getScene() {
		return scene;
	}


	public String getIp() {
		return ip;
	}


	public void setIp(String ip) {
		this.ip = ip;
	}


	public String getPort() {
		return port;
	}


	public void setPort(String port) {
		this.port = port;
	}
	
	public void youLost() {

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				winOrLoose(false);
				

			}
		});
	}
	
	public void youWon() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				winOrLoose(true);
				

			}
		});
		
	}
	
	public void winOrLoose(boolean won) {
		VBox vbox = new VBox();
		vbox.setAlignment(Pos.CENTER);
		vbox.setStyle("-fx-background-color: rgb(" + 192 + "," + 254 + ", " + 213 + ");");
		Label waiting;
		if (won) {
			waiting = new Label("You won!");
		} else {
			waiting = new Label("You Lost!");	
		}
		waiting.setStyle("-fx-font-size: 30pt;");
		ImageView thp = new ImageView("/icons/TH-Poly-Logo.jpg");
		
		vbox.getChildren().addAll(thp, waiting);
		Scene scene = new Scene(vbox);
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				switch (event.getCode()) {
				default:
					System.exit(0);
					break;
				}
			}
		});
		prime.setScene(scene);
		prime.sizeToScene();
		prime.centerOnScreen();
	}

	

}
