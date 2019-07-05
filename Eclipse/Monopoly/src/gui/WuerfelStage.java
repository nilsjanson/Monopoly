package gui;

import java.net.URL;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class WuerfelStage {
	Stage stage;
	Board board;
	// private Semaphore leertaste;
	HBox hbox;
	double min;
	double max;
	ImageView viewOne;
	ImageView viewTwo;
	Wuerfel wuerfel1 = new Wuerfel();
	Wuerfel wuerfel2 = new Wuerfel();

	public WuerfelStage(Board board, double min, double max) {
		this.min = min;
		this.max = max;
		this.board = board;
		try {
			start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void start() {
		hbox = new HBox();
		hbox.setStyle("-fx-background-color: red;");
		viewOne = new ImageView();
		viewOne = new ImageView(wuerfel1.getStartingSymbol());
		viewTwo = new ImageView(wuerfel2.getStartingSymbol());
		hbox.getChildren().add(viewOne);
		hbox.getChildren().add(viewTwo);
		Scene scene = new Scene(hbox);
		stage = new Stage();
		keyHandler(scene);
		stage.setScene(scene);
		stage.initStyle(StageStyle.UNDECORATED);
		stage.show();
		stage.setWidth(((max - min) / 2) - 5);
		stage.setHeight(min * .15);
		viewOne.setFitHeight(stage.getHeight());
		viewTwo.setFitHeight(stage.getHeight());
		viewOne.setFitWidth(stage.getWidth() / 2);
		viewTwo.setFitWidth(stage.getWidth() / 2);
		stage.setX(0);
		stage.setY((min / 2) - (stage.getHeight() / 2));
		stage.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> ov, Boolean hidden, Boolean shown) {
				if (shown) {
					stage.toFront();
					board.infoStage.info.toFront();
					board.prime.toFront();
				}
			}
		});
		stage.setOnCloseRequest(e -> System.exit(0));
	}

	public void wuerfeln(int x, int y) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				final URL resource = getClass().getResource("/sounds/wuerfeln.mp3");
				final Media media = new Media(resource.toString());
				final MediaPlayer mediaPlayer = new MediaPlayer(media);
				mediaPlayer.play();
				wuerfel1 = new Wuerfel() {
					public void run() {
						rollDice(wuerfel1, viewOne, x);
						rollDice(wuerfel2, viewTwo, y);
					}
				};
			}
		});
	}

	private void rollDice(Wuerfel wuerfel, ImageView iv, int x) {
		wuerfel.views = wuerfel.spin();
		long t = System.currentTimeMillis();
		long end = t + 15000;
		while (System.currentTimeMillis() < end) {
			for (Image view : wuerfel.views) {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						iv.setImage(view);
					}
				});
				try {
					Wuerfel.sleep(80);
				} catch (InterruptedException e) {
				}
			}
		}
		iv.setImage(wuerfel.getDice(x));
	}

	void keyHandler(Scene scene) {
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				switch (event.getCode()) {
				case F1:
					board.helpMe();
					break;
				case ESCAPE:
					new ExitStage(board.prime);
					break;
				default:
					board.prime.toFront();
					break;
				}

			}
		});
	}

	public void yourTurn() {
		wuerfeln(3,2);
	}

}
