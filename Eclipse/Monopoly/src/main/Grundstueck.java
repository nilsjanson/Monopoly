package main;

import java.io.IOException;

import main.Server.GameThread.Client;

/**
 * Ein Grundstueck.
 *
 * @author lucastheiss
 * @version 0.2
 *
 */
public class Grundstueck {
	/**
	 * Mietkosten je nach Bebauung.
	 */
	private int[] miete;
	/**
	 * Kosten des Grundstueckes.
	 */
	private int preis;
	/**
	 * Grad der Bebauung.
	 */
	private int haeuser = 0;
	/**
	 * Der aktuelle Besitzer des Grundstuecks.
	 */
	private Client besitzer = null;
	/**
	 * Index des Feldes an dem dieses Grundstueck zu finden ist.
	 */
	private int stelle;
	/**
	 * Name des Grundstuecks
	 */
	String name;
	/**
	 * Index der anderen Grundstuecke die zu dieser Strasse gehoeren.
	 */
	private int[] street;

	/**
	 * Initialisierung des Grundstuecks.
	 *
	 * @param name   Name des Grundstuecks.
	 * @param miete  Mietkosten je nach Bebauung.
	 * @param preis  Kosten des Grundstuecks.
	 * @param stelle Stelle auf dem Spielfeld.
	 * @param street Index der anderen Grundstuecke die zu dieser Strasse gehoeren.
	 */
	public Grundstueck(String name, int[] miete, int preis, int stelle, int[] street) {
		this.name = name;
		this.miete = miete;
		this.preis = preis;
		this.stelle = stelle;
		this.street = street;
	}

	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}
		if (!o.getClass().equals(this.getClass())) {
			return false;
		}
		if (((Grundstueck) o).getStelle() != this.getStelle()) {
			return false;
		}
		return true;
	}

	public String getName() {
		return name;
	}

	/**
	 * 
	 * @return der Index des Grundstuecks auf dem Feld.
	 */
	public int getStelle() {
		return stelle;
	}

	/**
	 * 
	 * @param feld Das Feld der Grundstuecke mit dem gerade gespielt wird.
	 * @return <b>true</b>, wenn ein Spieler alle Grundstuecke einer Strasse
	 *         besitzt<br>
	 *         <b>false</b>, sonst.
	 */
	public boolean kannBebautWerden(Grundstueck[] feld) {
		Client c = this.getBesitzer();
		for (int i : this.street) {
			if (!feld[i].getBesitzer().equals(c)) {
				return false;
			}
		}
		return true;
	}
	
	public int getHaeuser() {
		return haeuser;
	}

	/**
	 *
	 * @return Kosten pro neues Haus.<br>
	 *         im Falle eines Bahnhofs wird <b>-1</b> zurueckgegeben.
	 */
	public int getHausKosten() {
		if (this.stelle == 5 || this.stelle == 15 || this.stelle == 25 || this.stelle == 35) {
			return -1;
		}
		if (this.stelle < 10) {
			return 50;
		}
		if (this.stelle < 20) {
			return 100;
		}
		if (this.stelle < 30) {
			return 150;
		}
		return 200;
	}

	/**
	 * Aendern des Besitzers.
	 *
	 * @param c der neue Besitzer.
	 * @throws IOException keine Antwort vom Client.
	 */
	public void setBesitzer(Client c) throws IOException {
		this.besitzer = c;
	}

	/**
	 *
	 * @return den aktuellen Besitzer des Grundstuecks.
	 */
	public Client getBesitzer() {
		return besitzer;
	}

	/**
	 *
	 * @return die aktuelle Miete.
	 */
	public int getMiete(Client c,Grundstueck[] feld) {
		if(miete.length==2) {
			if(kannBebautWerden(feld)) {
				return c.getW() *miete[1];
			}else {
				return c.getW() *miete[0];
			}
		}else {
			if(haeuser==0) {
				if(kannBebautWerden(feld)) {
					return miete[0]*2;
				}else {
					return miete[0];
				}
			}else {
				return miete[haeuser];
			}
		
		}
		
	}

	/**
	 *
	 * @return Kaufpreis des Grundstuecks.
	 */
	public int getPreis() {
		return preis;
	}

	/**
	 *
	 * @param haeuser neue Anzahl an Haeusern.
	 */
	public void setHaeuser(int haeuser) {
		if (haeuser < 0 || haeuser > 5) {
			throw new IllegalArgumentException();
		} else {
			this.haeuser = haeuser;
		}
	}
	
	
}