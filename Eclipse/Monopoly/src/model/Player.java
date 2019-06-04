package model;

import javafx.scene.image.ImageView;

public class Player {

	private ImageView img; 
	private int position;
	private int bilanz;
	private int id ;
	
	Player() {
		img = new ImageView(getClass().getResource("").toExternalForm());
	}
	
	public ImageView getIcon () {
		return img;
	}
	
	
	
	public void setIcon(String x) {
		img=new ImageView(getClass().getResource("/playerIcons/"+x+".png").toExternalForm());
	}
	
	public void payRental(int rentalFee) {
		bilanz -= bilanz;
	}
	
	public void earnRental(int rentalFee) {
		bilanz +=bilanz;
	}
	
	public int getID() {
		return id;
	}
	public void buyCardOption(Card x) {
		//
	}
	
	
	private void move(int count) {
		position+=count;
		position = position % 40;
	}
	
	public int getPosition() {
		return position;
	}
	
	
}
