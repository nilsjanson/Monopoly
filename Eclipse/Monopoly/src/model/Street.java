package model;

import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class Street extends Field {
	ImageView img;

	public Street(int location, String name, Color color) {
		super(location, name, true);
		img= new ImageView(getClass().getResource(name).toExternalForm());
	}

}
