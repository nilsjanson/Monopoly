package model;

import javafx.scene.image.ImageView;

public class Player {

	ImageView img; 
	
	Player() {
		img = new ImageView(getClass().getResource("").toExternalForm());
	}
	
	
	private void Zug() {
		int augen = wuerfeln() +wuerfeln();
	}
	
	
	private int wuerfeln() {
		int erg = 7;
		while(erg==7) {
			erg = (int)(Math.random()*6)+1;
		}
		return erg;
	}
}
