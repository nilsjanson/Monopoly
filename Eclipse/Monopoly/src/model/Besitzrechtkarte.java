package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Besitzrechtkarte {
	
	private Player owner;
	private int hausCounter;
	private String color;
	private String name;
	private int position;
	private boolean hypothek;
	
	public static List<Besitzrechtkarte> liste = new ArrayList<Besitzrechtkarte>(Arrays.asList(
			new Besitzrechtkarte("Kesselhaus", "purple",1),
			new Besitzrechtkarte("AstaBuero", "purple",3),

			new Besitzrechtkarte("PflanzenLabor", "lightblue",6),
			new Besitzrechtkarte("GewaechsHaus", "lightblue",8),
			new Besitzrechtkarte("DemonstrationsFeld", "lightblue",9),
			
			new Besitzrechtkarte("MotorenPruefstand", "magenta",11),
			new Besitzrechtkarte("FahrzeugLabor", "magenta",13),
			new Besitzrechtkarte("SolarTankstelle", "magenta",14),
			
			new Besitzrechtkarte("TrvRhenania", "ORANGERED",16),
			new Besitzrechtkarte("BingerBeasts", "ORANGERED",18),
			new Besitzrechtkarte("BingenImpulse", "ORANGERED",19),
			
			new Besitzrechtkarte("NetzwerkLabor", "red",21),
			new Besitzrechtkarte("PcPool236", "red",23),	
			new Besitzrechtkarte("PcPool237", "red",24),
			
			new Besitzrechtkarte("StudienBeratung", "yellow",26),
			new Besitzrechtkarte("StudienSekretariat", "yellow",27),
			new Besitzrechtkarte("DekanBuero", "yellow",29),
			
			
			new Besitzrechtkarte("RhenoTeutonia", "lime",31),
			new Besitzrechtkarte("Holsatia", "lime",32),
			new Besitzrechtkarte("Markomannia", "lime",34),
			
			new Besitzrechtkarte("Mensa", "mediumblue",37),
			new Besitzrechtkarte("Bibliothek", "mediumblue",39),
			
			
			new Besitzrechtkarte("BingenBahnhof", "black",5),
			new Besitzrechtkarte("KreuznachBahnhof", "black",15),
			new Besitzrechtkarte("MainzBahnhof", "black",25),
			new Besitzrechtkarte("WormsBahnhof", "black",35),
			
			new Besitzrechtkarte("Rechenzentrum", "snow",12),
			new Besitzrechtkarte("Zollamt", "snow",28)
			
			
			));
	
	Besitzrechtkarte(String name, String color, int position) {
		owner = Player.nullPlayer;
		hausCounter = 0 ;
		this.name = name;
		this.color = color ;
		this.position = position;
		hypothek= false;
	
	}
	

	public boolean isHypothek() {
		return hypothek;
	}


	public void setHypothek(boolean hypothek) {
		this.hypothek = hypothek;
	}


	public Player getOwner() {
		return owner;
	}

	public void setOwner(Player owner) {
		this.owner = owner;
	}

	public int getHausCounter() {
		return hausCounter;
	}

	public void setHausCounter(int hausCounter) {
		this.hausCounter = hausCounter;
	}

	public String getColor() {
		return color;
	}

	public String getName() {
		return name;
	}

	public int getPosition() {
		return position;
	}
	
	public static Besitzrechtkarte findByName(String name) {
		for(Besitzrechtkarte x : liste) {
			if(x.getName().equals(name)) {
				return x;
			}
			
		}
		return null;
	}
	
	
	public static Besitzrechtkarte findByPosition(int position) {
		for(Besitzrechtkarte x : liste) {
			if(x.getPosition()==position) {
				return x;
			}
			
		}
		return null;
	}
	
	
	
	
	
	
	
	

}
