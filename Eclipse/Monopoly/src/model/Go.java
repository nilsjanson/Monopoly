package model;

import java.io.FileNotFoundException;
import javafx.scene.image.ImageView;

public class Go extends Field {
	
public ImageView icon = new ImageView(Go.class.getResource("/icons/go.gif").toExternalForm());
	
	public ImageView getIcon() throws FileNotFoundException {
		return icon;
	}

	public Go(int location, String name) {
		super(location, name, false);
		// TODO Auto-generated constructor stub
	}

}
