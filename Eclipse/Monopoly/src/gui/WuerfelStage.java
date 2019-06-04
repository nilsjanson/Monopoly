package gui;

import java.util.ArrayList;
import java.util.Collections;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class WuerfelStage extends Application {
	Board game;
	int x;
	int y;
	ArrayList<ImageView> views = new ArrayList<ImageView>();
	WuerfelStage(Board board,int x,int y) {
		this.x=x;
		this.y=y;
		game=board;
		try {
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
			this.start(new Stage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void start(Stage primaryStage) throws Exception {
	HBox hbox = new HBox();
	hbox.getChildren().add(views.get(x-1));
	hbox.getChildren().add(views.get((y+6)-1));
	Scene scene = new Scene(hbox);
	scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
		@Override
		public void handle(KeyEvent event) {
			switch (event.getCode()) {
			case F1:
				game.helpMe();
			case ESCAPE:
				System.exit(0);
				break;
			default:
				break;
			}
		}
	});
	Stage stage = new Stage();
	stage.setScene(scene);
	stage.show();
	}
}
