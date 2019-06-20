package model;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class Player {

	private ImageView img; 
	private int position;
	private int bilanz;
	private int id ;
	private String name;
	private ArrayList<Button> streetBut;
	private ArrayList<String> streetName;
	private HashMap<String, Button> streets;
	
	public void printStreetNames() {
		for (String x: streetName) {
			System.out.println(x);
		}
	}
	
	public void printHash() {
		for (int i=0;i<streetName.size();i++) {
			Object x=streets.get(streetName.get(i));
			System.out.println(x.toString());
		}
	}
	
	public void setStreetNames(ArrayList<String> streetName) {
		this.streetName=streetName;
	}
	
	public ArrayList<String> getStreetNames() {
		return streetName;
	}
	
	public void setButtons(ArrayList<Button> buttons) {
		this.streetBut=buttons;
	}
	
	public ArrayList<Button> getButtons() {
		return streetBut;
	}
	public HashMap<String,Button> getStreets () {
		return streets;
	}
	
	public Button getStreetsButton (String name) {
		return streets.get(name);
	}
	
	public String getStreetName(Button x) {
		for (int i=0;i<streetName.size();i++) {
			if (streets.get(streetName.get(i)).equals(x)) {
				return streetName.get(i);
			}
		}
		return "";
	}
	
	public void setStreet(HashMap<String,Button> streets) {
		this.streets=streets;
	}
	

	public String getName () {
		return name;
	}
	
	public int getBilanz() {
		return bilanz;
	}
	
	Player() {
		img = new ImageView(getClass().getResource("").toExternalForm());
		streetName=getNames();
	}
	
	public Player(String name, String iconName,int money) {
		this.name=name;
		img = new ImageView(getClass().getResource("/playerIcons/"+iconName).toExternalForm());
		bilanz=money;
		streetName=getNames();
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
	
	private ArrayList<String> getNames() {
		ArrayList<String> names = new ArrayList<String>();
		names.add("KesselHaus");
		names.add("AstaBuero");
		names.add("PflanzenLabor");
		names.add("GewaechsHaus");
		names.add("DemonstrationsFeld");
		names.add("MotorenPruefstand");
		names.add("FahrzeugLabors");
		names.add("SolarTankstelle");
		names.add("TrvRhenania");
		names.add("BingerBeasts");
		names.add("BingenImpulse");
		names.add("NetzwerkLabor");
		names.add("PcPool236");
		names.add("PcPool237");
		names.add("StudienBeratung");
		names.add("StudienSekretariat");
		names.add("DekanBuero");
		names.add("RhenoTeutonia");
		names.add("Holsatia");
		names.add("Markomannia");
		names.add("Mensa");
		names.add("Bibliothek");
		names.add("BingenBahnhof");
		names.add("KreuznachBahnhof");
		names.add("WormsBahnhof");
		names.add("MainzBahnhof");
		names.add("Rechenzentrum");
		names.add("Zollamt");
		return names;
	}
	
	
}
