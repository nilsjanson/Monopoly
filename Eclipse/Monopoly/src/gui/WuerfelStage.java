package gui;

import java.io.File;
import java.util.ArrayList;

import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class WuerfelStage extends Thread {
	
	public void run() {
		Stage stage = new Stage();
		ArrayList<ImageView> views = new ArrayList<ImageView>();
		File dir = new File("/wuerfel");
		for (File x: dir.listFiles()) {
			views.add(new ImageView(getClass().getResource(x.toString()).toExternalForm()));
		}
		VBox vbox = new VBox();
		vbox.getChildren().addAll(views);
		Scene scene = new Scene(vbox);
		stage.setScene(scene);
		stage.show();
	}
}
