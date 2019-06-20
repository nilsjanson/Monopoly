package model;

import java.util.ArrayList;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class Player {

	private ImageView img; 
	private int position;
	private int bilanz;
	private int id ;
	private String name;
	private boolean[] streets = new boolean [28];
	private ArrayList<Button> streetBut;
	
	public void setButtons(ArrayList<Button> buttons) {
		this.streetBut=buttons;
	}
	
	public ArrayList<Button> getButtons() {
		return streetBut;
	}
	public boolean [] getStreets () {
		return streets;
	}
	
	public void setStreet(int y, boolean x) {
		streets[y]=x;
	}
	
	public void init () {
		for (int i=0;i<streets.length;i++) {
			streets[i]=false;
		}
	}
	
	public String getName () {
		return name;
	}
	
	public int getBilanz() {
		return bilanz;
	}
	
	Player() {
		init();
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
