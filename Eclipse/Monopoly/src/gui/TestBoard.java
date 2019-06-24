package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Semaphore;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Besitzrechtkarte;

public class TestBoard {

	TestBoard me = this;
	final URL resource = getClass().getResource("/musik/StillStanding.mp3");
	final Media media = new Media(resource.toString());
	final MediaPlayer mediaPlayer = new MediaPlayer(media);
	protected Stage prime;
	public int spieler = 0;
	public String playerName;
	BorderPane parent;
	private double max;
	private double width;
	private double height;
	private Scene scene;
	int buttonCount = 0;
	double playerStartPositionX;
	double playerStartPositionY;
	public Semaphore TestBoardReady = new Semaphore(0);
	public StreetStage streetStageOpen;
	HashMap<Pane, Integer> identity = new HashMap<Pane, Integer>();
	int boxCounter = 0;

	public ImageView[] playerArr;

	public TestBoard(Stage prime) {
		// mediaPlayer.play(); Musik ausgeschaltet, da wenn ich nocheinmal dieses Lied
		// hoere meine rechte Halsschlagader platzt sollte man auf die Idee kommen das
		// ganze mal mit drei Clients zu testen.
		this.prime = prime;
		createTestBoard();
	}

	protected double getMax() {
		return max;
	}

	private void createTestBoard() {
		Rectangle2D screen = Screen.getPrimary().getVisualBounds();
		width = screen.getMaxX();
		height = screen.getMaxY();
		max = Math.min(width, height);
		BorderPane pane = new BorderPane();

		parent = pane;
		pane.setStyle("-fx-background-color: lightgreen;" + "-fx-background-image: url(\"/icons/TH-Poly.jpg\");"
				+ "    -fx-background-size: " + max + " " + max + ";");

		HBox buttom = botBoxes(); // invertieren
		int bot=identity.size();
		System.out.println("Bot: "+bot);
		VBox left = leftBoxes(); // invertieren
		int lev=identity.size()-bot;
		System.out.println("Left: "+lev);
		HBox top = topBoxes();
		int tp=identity.size()-lev;
		System.out.println("Top: "+tp);
		VBox right = rightBoxes();
		System.out.println("Fields: "+identity.size());
		pane.setTop(top);
		pane.setBottom(buttom);
		pane.setLeft(left);
		pane.setRight(right);
		pane.setCenter(createCardFields());

		BorderPane.setAlignment(left, Pos.CENTER);
		BorderPane.setAlignment(right, Pos.CENTER);

		scene = new Scene(pane);

		prime.initStyle(StageStyle.UNDECORATED);
		controlTestBoard(scene);
		prime.setScene(scene);
		prime.setWidth(max);
		prime.setHeight(max);
		prime.show();

		System.out.println("Maximale Groesse: " + width + " " + max);
	}

	private Pane createCardFields() {
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

	private HBox topBoxes() {
		HBox hbox = new HBox();
		ArrayList<VBox> boxes = new ArrayList<VBox>();
		for (int i = 0; i <= 10; i++) {
			boxes.add(new VBox(buttonCount++));
			identity.put(boxes.get(i), boxCounter++);
		}
		streetHBoxs(boxes, true);
		hbox.getChildren().addAll(boxes);
		return hbox;
	}

	private HBox botBoxes() {
		HBox hbox = new HBox();
		ArrayList<VBox> boxes = new ArrayList<VBox>();
		int j = buttonCount + 10;
		for (int i = 0; i <= 10; i++) {
			boxes.add(new VBox(j--));
			buttonCount++;
		}
		for (int i=boxes.size()-1;i!=0;i--) {
			identity.put(boxes.get(i), boxCounter++);
		}
		streetHBoxs(boxes, false);
		hbox.getChildren().addAll(boxes);
		return hbox;
	}

	private VBox rightBoxes() {
		VBox vbox = new VBox();
		ArrayList<HBox> boxes = new ArrayList<HBox>();
		for (int i = 0; i <= 8; i++) {
			boxes.add(new HBox(buttonCount++));
			identity.put(boxes.get(i), boxCounter++);
		}
		
		leftAndRight(boxes, false);
		vbox.getChildren().addAll(boxes);
		return vbox;
	}

	private VBox leftBoxes() {
		VBox vbox = new VBox();
		ArrayList<HBox> boxes = new ArrayList<HBox>();
		int j = buttonCount + 8;
		for (int i = 0; i <= 8; i++) {
			boxes.add(new HBox(j--));
			buttonCount++;
		}
		for (int i=boxes.size()-1;i!=0;i--) {
			identity.put(boxes.get(i), boxCounter++);
		}
		leftAndRight(boxes, true);
		vbox.getChildren().addAll(boxes);
		return vbox;
	}

	private void streetHBoxs(ArrayList<VBox> boxes, boolean rotation) {
		for (VBox x : boxes) {
			x.setPrefWidth(max * 0.134);
			x.setPrefHeight(max * 0.0814);
			x.setStyle("-fx-border-color: red; -fx-background-color: transparent");
			if (rotation) {
				streetVBoxTop(x);
			} else {
				streetVBoxBot(x);
			}
		}
		boxes.get(0).setPrefWidth(max * 0.19);
		boxes.get(boxes.size() - 1).setPrefWidth(max * 0.19);
		

		boxes.get(0).setPrefHeight(max * 0.136);
		boxes.get(boxes.size() - 1).setPrefHeight(max * 0.136);
	}

	private void leftAndRight(ArrayList<HBox> boxes, boolean rotation) {
		for (HBox x : boxes) {
			x.setPrefWidth(max * 0.134);
			x.setPrefHeight(max * 0.0814);
			x.setStyle("-fx-border-color: red; -fx-background-color: transparent");
			if (rotation) {
				streetVBoxLeft(x);
			} else {
				streetVBoxRight(x);
			}
		}
	}

	private void streetVBoxBot(VBox box) {
		box.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				boolean buy = true;
				new ConstructionClass(box, Besitzrechtkarte.findByPosition(identity.get(box)), buy, 0);
				getIdentity(box);
			}
		});

	}

	private void streetVBoxTop(VBox box) {
		box.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				boolean buy = true;
				box.setAlignment(Pos.BOTTOM_RIGHT);
				new ConstructionClass(box, Besitzrechtkarte.findByPosition(identity.get(box)), buy, 180);
				getIdentity(box);
			}
		});

	}

	private void streetVBoxLeft(HBox box) {
		box.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				boolean buy = true;
				box.setAlignment(Pos.TOP_RIGHT);
				new ConstructionClass(box, Besitzrechtkarte.findByPosition(identity.get(box)), buy, 90);
				getIdentity(box);
			}
		});
	}



	private void streetVBoxRight(HBox box) {
		box.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				boolean buy = true;
				new ConstructionClass(box, Besitzrechtkarte.findByPosition(identity.get(box)), buy, -90);
				getIdentity(box);
			}
		});
	}
	
	private void getIdentity(Pane pane) {
		System.out.println(identity.get(pane));
	}

	void startMusik() {
		final URL resource = getClass().getResource("/musik/AnnoDominiBeatsStillStanding.mp3");
		final Media media = new Media(resource.toString());
		final MediaPlayer mediaPlayer = new MediaPlayer(media);
		mediaPlayer.play();
	}

	private void controlTestBoard(Scene scene) {
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

	public Scene getScene() {
		return scene;
	}

}
