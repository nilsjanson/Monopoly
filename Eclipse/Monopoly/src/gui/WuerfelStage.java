package gui;

import java.net.URL;
import java.util.ArrayList;
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
	ArrayList<ImageView> views;
	//private Semaphore leertaste;
	HBox hbox;
	double min;
	double max;


	public WuerfelStage(Board board, double min, double max) {
		this.min = min;
		this.max = max;
		this.board = board;
		try {
			views = new ArrayList<ImageView>();
			views.add(new ImageView(getClass().getResource("/wuerfel/1.png").toExternalForm()));
			views.add(new ImageView(getClass().getResource("/wuerfel/2.png").toExternalForm()));
			views.add(new ImageView(getClass().getResource("/wuerfel/3.png").toExternalForm()));
			views.add(new ImageView(getClass().getResource("/wuerfel/4.png").toExternalForm()));
			views.add(new ImageView(getClass().getResource("/wuerfel/5.png").toExternalForm()));
			views.add(new ImageView(getClass().getResource("/wuerfel/6.png").toExternalForm()));

			views.add(new ImageView(getClass().getResource("/wuerfel/1.png").toExternalForm()));
			views.add(new ImageView(getClass().getResource("/wuerfel/2.png").toExternalForm()));
			views.add(new ImageView(getClass().getResource("/wuerfel/3.png").toExternalForm()));
			views.add(new ImageView(getClass().getResource("/wuerfel/4.png").toExternalForm()));
			views.add(new ImageView(getClass().getResource("/wuerfel/5.png").toExternalForm()));
			views.add(new ImageView(getClass().getResource("/wuerfel/6.png").toExternalForm()));

			views.add(new ImageView(getClass().getResource("/wuerfel/0.png").toExternalForm()));
			views.add(new ImageView(getClass().getResource("/wuerfel/0.png").toExternalForm()));
			Thread thread = new Thread() {
				public void start() {
					startWuerfelStage();
				}
			};
			thread.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	

	public void startWuerfelStage() {
		hbox = new HBox();
		hbox.setStyle("-fx-background-color: red;");
		ImageView eins = views.get(views.size() - 1);
		ImageView zwei = views.get(views.size() - 2);
		hbox.getChildren().add(eins);
		hbox.getChildren().add(zwei);
		Scene scene = new Scene(hbox);
		stage = new Stage();
		stage.getIcons().add(new Image("/icons/dice.png"));
		keyHandler(scene);
		stage.setScene(scene);
		stage.initStyle(StageStyle.UNDECORATED);
		stage.show();
		stage.setWidth(((max - min) / 2) - 5);
		stage.setHeight(min * .15);
		eins.setFitHeight(stage.getHeight());
		zwei.setFitHeight(stage.getHeight());
		eins.setFitWidth(stage.getWidth() / 2);
		zwei.setFitWidth(stage.getWidth() / 2);
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
		stage.setOnCloseRequest(e->System.exit(0));
	}

	public void wuerfeln(int x, int y) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				final URL resource = getClass().getResource("/sounds/wuerfeln.mp3");
				final Media media = new Media(resource.toString());
				final MediaPlayer mediaPlayer = new MediaPlayer(media);
				mediaPlayer.play();
				hbox.getChildren().remove(0);
				hbox.getChildren().remove(0);
				ImageView eins = views.get(x - 1);
				ImageView zwei = views.get((y + 6) - 1);
				eins.setFitHeight(min * .15);
				zwei.setFitHeight(min * .15);
				eins.setFitWidth(((max - min) / 2) / 2);
				zwei.setFitWidth(((max - min) / 2) / 2);
				hbox.getChildren().add(eins);
				hbox.getChildren().add(zwei);
			}
		});
	}

	void keyHandler(Scene scene) {
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				switch (event.getCode()) {
				case F1:
					board.helpMe();
					break;
				case F2:
					new WuerfelAnimation(views, stage);
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
		new WuerfelAnimation(views,stage);
	}

}

class WuerfelAnimation extends Thread {
	ArrayList<ImageView> imgs;
	Stage stage;

	WuerfelAnimation(ArrayList<ImageView> imgs, Stage stage) {
		this.imgs = imgs;
		this.stage = stage;
		this.setDaemon(true);
	}

	public void run() {
		wuerfeln();
	}

	void wuerfeln() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				ArrayList<Image> img = new ArrayList<Image>();
				img.add(new Image(getClass().getResource("/wuerfel/1.png").toExternalForm()));
				img.add(new Image(getClass().getResource("/wuerfel/2.png").toExternalForm()));
				img.add(new Image(getClass().getResource("/wuerfel/3.png").toExternalForm()));
				img.add(new Image(getClass().getResource("/wuerfel/4.png").toExternalForm()));
				img.add(new Image(getClass().getResource("/wuerfel/5.png").toExternalForm()));
				img.add(new Image(getClass().getResource("/wuerfel/6.png").toExternalForm()));
				long time = System.currentTimeMillis();
				long till = time+30000;
				while(System.currentTimeMillis()<till) {
				HBox hbox = new HBox();
				hbox.setStyle("-fx-background-color: red");
				ImageView img1 = new ImageView();
				img1.setFitHeight(stage.getHeight());
				img1.setFitWidth(stage.getWidth() / 2);
				hbox.getChildren().add(img1);
				ImageView img2 = new ImageView();
				img2.setFitHeight(stage.getHeight());
				img2.setFitWidth(stage.getWidth() / 2);
				hbox.getChildren().add(img2);
				for (Image x: img) {
					img1.setImage(x);
					img2.setImage(x);
				}
				Scene scene = new Scene(hbox);
				stage.setScene(scene);
				}
				
			}
		});
	}

}
