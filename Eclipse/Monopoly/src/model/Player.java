package model;

import javafx.scene.image.ImageView;

public class Player {
	private ImageView img; 
	
	Player() {
		
	}
	
	public ImageView getIcon () {
		return img;
	}
	
	public void setIcon(String x) {
		img=new ImageView(getClass().getResource("/playerIcons/"+x+".png").toExternalForm());
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
