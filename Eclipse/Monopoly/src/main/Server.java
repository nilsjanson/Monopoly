package main;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Klasse mit der Main Methode des Servers.
 * 
 * @author lucastheiss
 *
 */
public class Server {

	/**
	 * 
	 * @param args optional kann der Port als Argument angegeben werden.<br>
	 *             Es darf dafuer nur 1 Argument angegeben werden, welches ein
	 *             Positiver Integer < 49152 sein muss. <br>
	 *             Sonst wird der Defaultwert 1337 genommen.
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/**
		 * Port auf dem der Server hoert.
		 */
		int port = 1337;
		/**
		 * Liste der Queues fuer Spieler
		 */
		List<Queue<Socket>> listOfQueues = new ArrayList<Queue<Socket>>();
		/**
		 * Queue fuer 2 Spieler
		 */
		listOfQueues.add(new LinkedList<Socket>());
		/**
		 * Queue fuer 3 Spieler
		 */
		listOfQueues.add(new LinkedList<Socket>());
		/**
		 * Queue fuer 4 Spieler
		 */
		listOfQueues.add(new LinkedList<Socket>());
		// Falls Anforderungen erfuellt, Port von args[0] nehmen
		if (args.length == 1 && args[0].matches("[0-9]{1,5}") && !args[0].matches("[0]")) {
			int arg = Integer.parseInt(args[0]);
			// Ab 49152 fuer Dynamische Port-Adressen reserviert.
			if (arg < 49152) {
				port = arg;
			}
		}
		System.out.println("Port:\t" + port);
		try (ServerSocket server = new ServerSocket(port)) {
			while (true) {
				System.out.println("Waiting for client ...");
				Socket client = server.accept();
				System.out.println("Client" + client.getInetAddress() + "connected");

				sortClientInQueue(client, listOfQueues);
				int i = isStartPossible(listOfQueues);
				if (i != -1) {
					startGame(listOfQueues.get(i));
					// Gestartete Queue ersetzen
					listOfQueues.set(i, new LinkedList<Socket>());
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * Clients werden in eine Wartequeue gepackt. <br>
	 * Client antwortet mit einem Integer[2-4] um passend mit anderen Clients
	 * sortiert zu werden.
	 * 
	 * @param client der wartende Client.
	 * @param list   Liste der Client Queues.
	 * @throws IOException
	 */
	private static void sortClientInQueue(Socket client, List<Queue<Socket>> list) throws IOException {
		DataOutputStream out = new DataOutputStream(new BufferedOutputStream(client.getOutputStream()));
		DataInputStream in = new DataInputStream(new BufferedInputStream(client.getInputStream()));
		out.writeUTF("select number of Players [2-4]; expects {" + Integer.class.toString() + "}");
		int i = in.readInt();
		while (!Integer.valueOf(i).toString().matches("[2-4]")) {
			out.writeUTF("select number of Players; expects {" + Integer.class.toString() + "}");
			i = in.readInt();
		}
		list.get(i - 2).add(client);
	}
	/**
	 * Prueft ob ein Spiel gestartet werden kann.
	 * 
	 * @param list die Liste der Queues die ueberprueft werden soll.
	 * @return -1 wenn kein Spielstart moeglich ist, <br>
	 *         sonst den Index der Queue die aus der Liste gestartet werden kann.
	 */
	private static int isStartPossible(List<Queue<Socket>> list) {
		for (int i = 0; i < 3; i++) {
			if (list.get(i).size() == i + 2) {
				return i;
			}
		}
		return -1;
	}
	/**
	 * Erstellt einen neuen Thread und startet das Spiel
	 * 
	 * @param q Die wartenden Clients
	 */
	private static void startGame(Queue<Socket> q) {
		new Server().new GameThread(q);
	}
	/**
	 * Ein laufendes Spiel
	 * 
	 * @author lucastheiss
	 *
	 */
	public class GameThread extends Thread {
		/**
		 * laenge des Spielfelds
		 */
		private final int fieldLength = 40;
		/**
		 * Spielfeld.<br>
		 * Feld der Grundstuecke.
		 */
		private Grundstueck[] feld = new Grundstueck[40];
		/**
		 * Liste der Spieler
		 */
		private List<Client> list = new ArrayList<Client>();
		private String leaveMessage = "\nPlayer has left because of IOException.\n";
		/**
		 * Verbund aus Socket und Player
		 * 
		 * @author lucastheiss
		 *
		 */
		protected class Grundstueck {
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
			 * Initialisierung des Grundstuecks.
			 * 
			 * @param miete Mietkosten je nach Bebauung.
			 * @param preis Kosten des Grundstuecks.
			 * @param hausKosten Kosten pro neues Haus.
			 */
			public Grundstueck(int[] miete, int preis, int hausKosten, int stelle) {
				this.miete = miete;
				this.preis = preis;
				this.hausKosten = hausKosten;
				this.stelle = stelle;
			}
			public boolean equals(Object o) {
				if(o == null) {
					return false;
				}
				if(!o.getClass().equals(this.getClass())) {
					return false;
				}
				if(((Grundstueck)o).getStelle() != this.getStelle()) {
					return false;
				}
				return true;
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
			 */
			public void setBesitzer(Client c) {
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
		protected class Client {
			/**
			 * Geld des Spielers.
			 */
			private int geld = 0;
			/**
			 * Position auf dem Spielfeld.
			 */
			private int position = 0;
			private Socket s;
			private DataInputStream in;
			private DataOutputStream out;
			private boolean imGefaengnis = false;
			/**
			 * Versuche einen Pasch zu wuerfeln wenn man im Gefaengnis ist.
			 */
			private int versuche = 0;
			/**
			 * Anzahl der Augen des 1. Wuerfels
			 */
			private int w1;
			/**
			 * Anzahl der Augen des 2. Wuerfels
			 */
			private int w2;
			public Client(Socket s) throws IOException {
				this.s = s;
				this.in = new DataInputStream(new BufferedInputStream(s.getInputStream()));
				this.out = new DataOutputStream(new BufferedOutputStream(s.getOutputStream()));
			}
			public int getVersuche() {
				return versuche;
			}
			public boolean getImGefaengnis() {
				return imGefaengnis;
			}
			public int getGeld() {
				return geld;
			}
			/**
			 * 
			 * @param change Aenderung des Geldes, Positiv oder Negativ.
			 */
			public void addGeld(int change) {
				geld += change;
			}
			public int getPos() {
				return position;
			}
			/**
			 * Bewegt den Client und sendet dem Client die Anzahl.
			 * @param count Anzahl Augen auf dem Wuerfel.
			 * @throws IOException Keine Verbindung zum Client.
			 */
			public void walk(int count) throws IOException {
				position += count;
				position %= fieldLength;
				out.writeUTF("move sending {" + Integer.class.toString() + "}");
				out.writeInt(count);
			}
			public Socket getSocket() {
				return s;
			}
			public DataInputStream getIn() {
				return in;
			}
			public DataOutputStream getOut() {
				return out;
			}
			public int getW1() {
				return w1;
			}
			public int getW2() {
				return w2;
			}
			public int getW() {
				return w1 + w2;
			}
			public void wuerfeln() throws IOException {
				out.writeUTF("rolling the dice Sending {" + Integer.class.toString() + ", " + Integer.class.toString()
						+ "}");
				w1 = model.Board.wuerfeln();
				w2 = model.Board.wuerfeln();
				out.writeInt(w1);
				out.writeInt(w2);
			}
		}
		public GameThread(Queue<Socket> q) {
			init(q);
			this.start();
		}
		@Override
		public void run() {
			System.out.println("Start game with " + list.size() + " players");
			// auswuerfeln wer anfaengt
			for (Client c : list) {
				try {
					c.wuerfeln();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			int nextPlayer = 0;
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).getW() > list.get(nextPlayer).getW()) {
					nextPlayer = i;
				}
			}
			// Main Loop
			// solange mehr als ein Spieler uebrig ist
			while (list.size() > 1) {
				try {
					Client client = list.get(nextPlayer);
					client.wuerfeln();
					client.walk(client.getW());
					//Feld pruefen
					
					
					
					
					
					// naechster Spieler
					nextPlayer++;
					nextPlayer %= list.size();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					//Spieler entfernen wegen IOException
					list.remove(nextPlayer);
					nextPlayer %= list.size();
					System.out.println(leaveMessage);
				}
			}
			try {
				list.get(0).getOut().writeUTF("you have won");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		/**
		 * Initialsetup der Spieler<br>
		 * Clients in der Liste speichern und allen Spielern das Startgeld geben
		 * 
		 * @param q Queue der Sockets
		 */
		private void init(Queue<Socket> q) {
			for (Socket s : q) {
				try {
					list.add(new Client(s));
				} catch (IOException e) {
					//Client entfernen der eine IOException geworfen hat.
					list.remove(list.size()-1);
					//Karten versteigern...
					System.out.println(leaveMessage);
				}
			}
			for(int i = 0; i < list.size(); i++) {
				Client c = list.get(i);
				c.addGeld(1500);
				try {
					c.getOut().writeUTF("Geld add value Sending {" + Integer.class.toString() + "}");
					c.getOut().writeInt(1500);
				} catch (IOException e) {
					//Client entfernen der eine IOException geworfen hat.
					list.remove(i);
					//Karten versteigern
					System.out.println(leaveMessage);
				}
			}
			//Felder initialisieren
			feld[0] = null; //Los
			feld[1] = new Grundstueck(new int[] {2, 10, 30, 90, 160, 250}, 60, 50, 1);
			feld[2] = null; //Kartenstapel 1
			feld[3] = new Grundstueck(new int[] {4, 20, 60, 180, 320, 450}, 60, 50, 3);
			feld[4] = null; //Steuern 200
			feld[5] = new Grundstueck(new int[] {25, 50, 100, 200}, 200, -1, 5); //Bahnhof
			feld[6] = new Grundstueck(new int[] {6, 30, 90, 270, 400, 550}, 100, 50, 6);
			feld[7] = null; //Kartenstapel 2
			feld[8] = new Grundstueck(new int[] {6, 30, 90, 270, 400, 550}, 100, 50, 8);
			feld[9] = new Grundstueck(new int[] {8, 40, 100, 300, 450, 600}, 120, 50, 9);
			feld[10] = null; //Gefaengnis
			feld[11] = new Grundstueck(new int[] {10, 50, 150, 450, 625, 750}, 140, 100, 11);
			feld[12] = null; //Rechenzentrum, evtl als Grundstueck behandeln
			feld[13] = new Grundstueck(new int[] {10, 50, 150, 450, 625, 750}, 140, 100, 13);
			feld[14] = new Grundstueck(new int[] {12, 60, 180, 500, 700, 900}, 160, 100, 14);
			feld[15] = new Grundstueck(new int[] {25, 50, 100, 200}, 200, -1, 15); //Bahnhof
			feld[16] = new Grundstueck(new int[] {14, 70, 200, 550, 750, 950}, 180, 100, 16);
			feld[17] = null; //Kartenstapel 1
			feld[18] = new Grundstueck(new int[] {14, 70, 200, 550, 750, 950}, 180, 100, 18);
			feld[19] = new Grundstueck(new int[] {16, 80, 220, 600, 800, 1000}, 200, 100, 19);
			feld[20] = null; //Frei parken
			feld[21] = new Grundstueck(new int[] {18, 90, 250, 700, 875, 1050}, 220, 150, 21);
			feld[22] = null; //Kartenstapel 2
			feld[23] = new Grundstueck(new int[] {18, 90, 250, 700, 875, 1050}, 220, 150, 23);
			feld[24] = new Grundstueck(new int[] {20, 100, 300, 750, 925, 1100}, 240, 150, 24);
			feld[25] = new Grundstueck(new int[] {25, 50, 100, 200}, 200, -1, 25); //Bahnhof
			feld[26] = new Grundstueck(new int[] {22, 110, 330, 800, 975, 1150}, 260, 150, 26);
			feld[27] = new Grundstueck(new int[] {22, 110, 330, 800, 975, 1150}, 260, 150, 27);
			feld[28] = null; //Zollamt
			feld[29] = new Grundstueck(new int[] {24, 120, 360, 850, 1025, 1200}, 280, 150, 29);
			feld[30] = null; //gehe in das Gefaengnis
			feld[31] = new Grundstueck(new int[] {26, 130, 390, 900, 1100, 1275}, 300, 200, 31);
			feld[32] = new Grundstueck(new int[] {26, 130, 390, 900, 1100, 1275}, 300, 200, 32);
			feld[33] = null; //Kartenstapel 1
			feld[34] = new Grundstueck(new int[] {28, 150, 450, 1000, 1200, 1400}, 320, 200, 34);
			feld[35] = new Grundstueck(new int[] {25, 50, 100, 200}, 200, -1, 35); //Bahnhof
			feld[36] = null; //Kartenstapel 2
			feld[37] = new Grundstueck(new int[] {35, 175, 500, 1100, 1300, 1500}, 350, 200, 37);
			feld[38] = null; //Steuern 100
			feld[39] = new Grundstueck(new int[] {50, 200, 600, 1400, 1700, 2000}, 400, 200, 39);
		}
	}
}
