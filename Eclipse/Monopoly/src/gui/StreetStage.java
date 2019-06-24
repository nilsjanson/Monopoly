package gui;

import java.util.concurrent.Semaphore;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Besitzrechtkarte;

public class StreetStage extends Application {

	Board board;
	Stage stage;
	ImageView img;
	boolean kaufbar;
	boolean versteigerbar;
	boolean hausKaufbar;
	boolean aktionErforderlich;
	int playerNumber;
	public Semaphore readySem = new Semaphore(0);
	public Semaphore actionSem = new Semaphore(0);
	public int action;

	Besitzrechtkarte besitz;

	public StreetStage(Board board, Besitzrechtkarte x, boolean kaufbar, boolean versteigerbar, boolean hausKaufbar,
			boolean aktionErforderlich) {
		if (board.streetStageOpen != null) {
			try {
				board.streetStageOpen.close();
				board.streetStageOpen = null;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		board.streetStageOpen = this;
		this.kaufbar = kaufbar;
		besitz = x;
		this.versteigerbar = versteigerbar;
		this.hausKaufbar = hausKaufbar;
		this.playerNumber = x.getOwner().getID();
		this.aktionErforderlich = aktionErforderlich;
		img = new ImageView(getClass().getResource("/besitzkarten/" + x.getName() + ".jpg").toExternalForm());
		this.board = board;
		img.setFitWidth(board.getMax() * 0.55);
		img.setFitHeight(board.getMax() * 0.55);
		try {
			this.start(board.prime);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	StreetStage(Board board, ImageView img) {
		this.img = img;
		this.board = board;
	}

	public void start(Stage primaryStage) throws Exception {
		HBox hbox = new HBox();
		VBox vbox = new VBox();

		Button kaufen = new Button("Straﬂe Kaufen");
		kaufen.setVisible(kaufbar);

		Button versteigern = new Button("Versteigern");
		versteigern.setVisible(versteigerbar);

		Button haus = new Button("Haus kaufen");
		haus.setVisible(hausKaufbar);

		Button hypothek = new Button("Hypothek aufnehmen");
		hypothek.setVisible(false);

		Button hausVerkaufen = new Button("Haus verkaufen");
		hausVerkaufen.setVisible(false);

		Button exit = new Button("Exit");
		haus.setVisible(!aktionErforderlich);

		if (board.yourTurn) {
			versteigern.setVisible(true);
			versteigern.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {
					System.out.println("Versteigerung starten");
					board.actionQueue.add(3);
					board.actionQueue.add(besitz.getPosition());
					board.actionSeamphore.release();
					stage.close();
				}
			});

			if (besitz.getHausCounter() > 0) {
				hausVerkaufen.setVisible(true);
				hausVerkaufen.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						System.out.println("Haus verkaufen");
						board.actionQueue.add(4);
						board.actionQueue.add(besitz.getPosition());
						board.actionSeamphore.release();
						stage.close();
					}
				});

			}
			if (!besitz.isHypothek()) {
				hypothek.setVisible(true);
				hypothek.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						System.out.println("Versteigerung starten");
						board.actionQueue.add(5);
						board.actionQueue.add(besitz.getPosition());
						board.actionSeamphore.release();
						stage.close();
					}
				});
			}
		}

		Label player = new Label("Spieler " + playerNumber);
		if (aktionErforderlich) {
			kaufen.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {
					System.out.println("Sie haben die Straﬂe gekauft");
					action = 1;
					actionSem.release();
					stage.close();

				}
			});

			versteigern.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {
					System.out.println("Versteigerung starten");
					action = 2;
					actionSem.release();
					stage.close();
				}
			});

			haus.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent arg0) {
					System.out.println("Haus gekauft.");
					action = 3;
					actionSem.release();
				}
			});

		}
		player.setStyle("-fx-font-size: 2em; -fx-text-fill: red;");

		buttonStyle(kaufen, versteigern, haus, hypothek, hausVerkaufen, exit);

		vbox.getChildren().addAll(kaufen, versteigern, haus, hypothek, hausVerkaufen, exit, player);
		vbox.setAlignment(Pos.CENTER);
		vbox.setSpacing(10);
		vbox.setStyle("-fx-background-color: white;");
		hbox.setStyle("-fx-background-color: white;");
		hbox.getChildren().addAll(img, vbox);
		hbox.setSpacing(20);
		Scene scene = new Scene(hbox);
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				switch (event.getCode()) {
				case F1:
					board.helpMe();
					break;
				case ENTER:
					stage.close();
					break;
				case ESCAPE:
					close();

					break;
				case C:
					stage.centerOnScreen();
					break;
				default:
					System.out.println(event.getCode());
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
		board.streetStageOpenSemaphore.release();

	}

	public void close() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {

				stage.close();

			}
		});
		try {
			this.stop();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void buttonStyle(Button... buttons) {
		for (Button x : buttons) {
			x.setStyle(
					"-fx-background-color: black; -fx-border-color: black; -fx-font-size: 2em; -fx-text-fill: white");
			x.setPrefWidth(200);
			x.setPrefHeight(20);
		}
	}

}
