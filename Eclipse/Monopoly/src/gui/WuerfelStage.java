package gui;

import java.io.File;
import java.util.ArrayList;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class WuerfelStage extends Thread {

	public void run() {
		Stage stage = new Stage();
		ArrayList<Label> views = new ArrayList<Label>();
		File dir = new File("/wuerfel");
		for (File x: dir.listFiles()) {
			ImageView icon = new ImageView(new Image(x));
		}
	}
}
