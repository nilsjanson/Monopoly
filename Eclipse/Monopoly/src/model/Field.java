package model;

import java.io.FileNotFoundException;

import javafx.scene.image.ImageView;

public class Field {

	private int location;
	private String name;
	private boolean isStreet;

	public ImageView icon;

	public ImageView getIcon() throws FileNotFoundException {
		return icon;
	}

	public Field(int location, String name, boolean isStreet) {
		super();
		if (this instanceof Street) {
			isStreet=true;
		}
		this.location = location;
		this.name = name;
		this.isStreet = isStreet;
		icon = new ImageView(Go.class.getResource("/icons/"+name+".gif").toExternalForm());
	}
	
	public Field(int location, String name) {
		super();
		if (this instanceof Street) {
			isStreet=true;
		}
		this.location = location;
		this.name = name;
		icon = new ImageView(Go.class.getResource("/icons/"+name+".gif").toExternalForm());
	}
	
	public void action(Player player) {
		
	}

}
