package model;

import java.util.ArrayList;
import java.util.Collections;

public class EmailKarte {
	private String text;
	private int aktion;
	private int parameter;
	private static ArrayList<EmailKarte> list  = new ArrayList<EmailKarte>();
	private static EmailKarte a = new EmailKarte("Ihr Bausparvertrag wird faellig. Sie erhalten 200 Euro",1, 200);
	private static EmailKarte b = new EmailKarte("Du hast am Mathepruefungstag zufaellig mal wieder ''Durchfall'' ",2,0);
	private static EmailKarte c = new EmailKarte("Du erhaelst EU-Foerderungsmittel. Ziehe 20 Euro ein",1,20);
	private static EmailKarte d = new EmailKarte("Winkel hat dich an die Tafel gerufen. Gehe in die Mathepruefung! Gehe nicht ueber los. Ziehe nicht 200 Euro ein",3,0);
	private static EmailKarte e = new EmailKarte("Winkel hat dein Schieben satt. Gehe in die Mathepruefung! Gehe nicht ueber los. Ziehe nicht 200 Euro ein",3,0);
	private static EmailKarte f = new EmailKarte("Gehe vor bis zum Kesselhaus. Ziehe 200 Euro ein, wenn du ueber Los gehst!",4,1);
	private static EmailKarte g = new EmailKarte("Die Unweltaktivisten jagen dich ueber den Campus. Verstecke dich im Fahrzeuglabor",4,13);
	private static EmailKarte h = new EmailKarte("Du hast vergessen, dich fuer eine Pruefung anzumelden. Gehe zum Studiensekreteriat",4,27);
	private static EmailKarte i = new EmailKarte("Du hast vergessen, dich fuer das neue Semester zurueckzumelden. Zahle 100",1,250);
	private static EmailKarte j = new EmailKarte("Du hast bei der Essensumfrage positiv abgestimmt. Ziehe vor in die Mensa",4,37);
	private static EmailKarte k = new EmailKarte("Du faehrst gerne Zug. Ziehe vor zum Bingener Bahnhof",4,5);
	private static EmailKarte l = new EmailKarte("Verspaetungen sind genau dein Ding. Ziehe vor bis zum Mainzer Bahnhof",4,25);
	private static EmailKarte m = new EmailKarte("Professor Doktor Luckas hat einen schlechten Tag. Ziehe vor zum PC-Pool R1-236 zur C++ Abnahme",4,23);
	private static EmailKarte n = new EmailKarte("Nullpointerexception. Zahle 100",1,-100);
	private static EmailKarte o = new EmailKarte("In einer Gruppenarbeit machst du alles alleine. Ziehe 50 von jedem Mitspieler ein!",5,50);
	private static EmailKarte p = new EmailKarte("Du verteilst Altklausuren. Ziehe 20 von jedem Mitspieler ein!",5,20);
	private static EmailKarte q = new EmailKarte("Du erhaelst durch einen Fehler einen Freiversuch fuer die naechste Mathepruefung",2,0);
	private static EmailKarte r = new EmailKarte("Du faehrst jemandem gegen das Auto. Zahle 80 Euro",1,-80);
	private static EmailKarte s = new EmailKarte("Du gewinnst im Casino 120 Euro",1,120);
	private static EmailKarte t = new EmailKarte("Du verlierst im Casino 70 Euro",1,-70);
	private static EmailKarte u = new EmailKarte("Du bist betrunken Auto gefahren. Zahle 50",1,-50);
	private static EmailKarte v = new EmailKarte("Du kippst Energy ueber dein Laptop. Die Reperatur kostet dich 100",1,100);
	private static EmailKarte w = new EmailKarte("Shisha geht auf deinen Nacken. Zahle 50",1,50);
	private static EmailKarte x = new EmailKarte("Du faehrst in die Drosselgasse und wachst mit einer Alkoholvergiftung auf. Dir fehlen 100 Euro",1, -100);
	
	
	
	
	
	
	
	
	
	public EmailKarte(String text , int aktion, int parameter) {
		this.text = text;
		this.aktion = aktion;
		this.parameter = parameter;
		list.add(this);
	}
	
	public static EmailKarte getEmail() {
		Collections.shuffle(list);
		return list.get(0);
	}

	public String getText() {
		return text;
	}


	public int getAktion() {
		return aktion;
	}


	public int getParameter() {
		return parameter;
	}

	
}
