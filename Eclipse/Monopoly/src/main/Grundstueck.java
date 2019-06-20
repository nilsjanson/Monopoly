package main;

import java.io.IOException;

import main.Server.GameThread.Client;

/**
 * Ein Grundstueck.
 *
 * @author lucastheiss
 * @version 0.1
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
	private int stelle;
	private int hausKosten;
	/**
	 * Name des Grundstuecks
	 */
	private String name;

	/**
	 * Initialisierung des Grundstuecks.
	 *
	 * @param miete      Mietkosten je nach Bebauung.
	 * @param preis      Kosten des Grundstuecks.
	 * @param hausKosten Kosten pro neues Haus.
	 * @param stelle     Stelle auf dem Spielfeld.
	 */
	public Grundstueck(String name, int[] miete, int preis, int hausKosten, int stelle) {
		this.name = name;
		this.miete = miete;
		this.preis = preis;
		this.hausKosten = hausKosten;
		this.stelle = stelle;
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

	public int getStelle() {
		return stelle;
	}

	/**
	 *
	 * @return Kosten pro neues Haus.
	 */
	public int getHausKosten() {
		return hausKosten;
	}

	/**
	 * Aendern des Besitzers.
	 *
	 * @param c der neue Besitzer.
	 * @throws IOException keine Antwort vom Client.
	 */
	public void setBesitzer(Client c) throws IOException {
		c.getOut().writeUTF(
				"Grundstueck gekauft Sending(Stelle auf dem Spielfeld) {" + Integer.class.toString() + "}");
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
	public int getMiete() {
		return miete[haeuser];
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