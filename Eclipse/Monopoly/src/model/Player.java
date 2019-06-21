package model;

import javafx.scene.image.ImageView;

public class Player {

	private ImageView img; 
	private int position;
	private int bilanz;
	private int id ;
	private String name;
	
	
	public String getName () {
		return name;
	}
	
	public int getBilanz() {
		return bilanz;
	}
	
	Player() {
		img = new ImageView(getClass().getResource("").toExternalForm());
	}
	
	public Player(String name, String iconName,int money) {
		this.name=name;
		img = new ImageView(getClass().getResource("").toExternalForm());
		bilanz=money;
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
	
	
	protected void buyHouseOption(Card card) {
		
	}
	public int getPosition() {
		return position;
	}
	
	
}
