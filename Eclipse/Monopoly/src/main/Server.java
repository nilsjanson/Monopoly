package main;

import java.awt.Button;
import java.awt.Color;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Klasse mit der Main Methode des Servers.
 *
 * @author lucastheiss
 * @version 0.1
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
		/**
		 * Port auf dem der Server hoert.
		 */
		int port = 1337;
		/**
		 * Liste der Queues fuer Spieler
		 */
		List<List<Socket>> listOfLists = new ArrayList<List<Socket>>();
		/**
		 * Queue fuer 2 Spieler
		 */
		listOfLists.add(new ArrayList<Socket>());
		/**
		 * Queue fuer 3 Spieler
		 */
		listOfLists.add(new ArrayList<Socket>());
		/**
		 * Queue fuer 4 Spieler
		 */
		listOfLists.add(new ArrayList<Socket>());
		// Falls Anforderungen erfuellt, Port von args[0] nehmen

//		Frame frame = createServerGui(listOfLists);
//		frame.setSize(200, 200);					AWT GUI? Nur ein Gedanke
//		frame.setVisible(true);

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
				sortClientInQueue(client, listOfLists);
				int i = isStartPossible(listOfLists);
				if (i != -1) {
					startGame(listOfLists.get(i));
					// Gestartete Queue ersetzen
					listOfLists.set(i, new LinkedList<Socket>());
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

//	private static Frame createServerGui(List<List<Socket>> listOfLists) {		Mal nur eine Idee und da AWT fuer sowas perfekt ist.. mal ein Gedanke..?
//		Frame frame = new Frame("TH-Poly Server");
//		TextField portTx = new TextField();
//		Button start = new Button("Start Server");
//		start.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent arg0) {
//				String args = portTx.getText();
//				int port = 1337;
//				if (args.matches("[0-9]{1,5}") && !args.matches("[0]") && args.length() != 0) {
//					int arg = Integer.parseInt(args);
//					// Ab 49152 fuer Dynamische Port-Adressen reserviert.
//					if (arg < 49152) {
//						port = arg;
//						System.out.println("Port:\t" + port);
//					}
//				}
//				boolean started = false;
//				if (!started) {
//					start.setBackground(Color.GREEN);
//					try (ServerSocket server = new ServerSocket(port)) {
//						while (true) {
//							System.out.println("Waiting for client ...");
//							Socket client = server.accept();
//							System.out.println("Client" + client.getInetAddress() + "connected");
//							sortClientInQueue(client, listOfLists);
//							int i = isStartPossible(listOfLists);
//							if (i != -1) {
//								startGame(listOfLists.get(i));
//								// Gestartete Queue ersetzen
//								listOfLists.set(i, new LinkedList<Socket>());
//							}
//						}
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
//				}
//			}
//		});
//		frame.setLayout(new GridLayout(2, 1));
//		frame.add(portTx);
//		frame.add(start);
//		frame.addWindowListener(new WindowListener() {
//
//			@Override
//			public void windowOpened(WindowEvent arg0) {
//
//			}
//
//			@Override
//			public void windowIconified(WindowEvent arg0) {
//
//			}
//
//			@Override
//			public void windowDeiconified(WindowEvent arg0) {
//
//			}
//
//			@Override
//			public void windowDeactivated(WindowEvent arg0) {
//
//			}
//
//			@Override
//			public void windowClosing(WindowEvent arg0) {
//				System.exit(0);
//			}
//
//			@Override
//			public void windowClosed(WindowEvent arg0) {
//
//			}
//
//			@Override
//			public void windowActivated(WindowEvent arg0) {
//
//			}
//		});
//		return frame;
//	}

	/**
	 * Clients werden in eine Wartequeue gepackt. <br>
	 * Client antwortet mit einem Integer[2-4] um passend mit anderen Clients
	 * sortiert zu werden.
	 *
	 * @param client der wartende Client.
	 * @param list   Liste der Client Queues.
	 * @throws IOException
	 */
	private static void sortClientInQueue(Socket client, List<List<Socket>> list) throws IOException {
		DataOutputStream out = new DataOutputStream(new BufferedOutputStream(client.getOutputStream()));
		DataInputStream in = new DataInputStream(new BufferedInputStream(client.getInputStream()));
		// out.writeUTF("select number of Players [2-4]; expects {" +
		// Integer.class.toString() + "}");
		int i = in.readInt();
//		while (!Integer.valueOf(i).toString().matches("[2-4]")) { //nicht umsetzbar wegen GUI
//			out.writeUTF("select number of Players; expects {" + Integer.class.toString() + "}");
//			i = in.readInt();
//		}

		list.get(i - 2).add(client);
	}

	/**
	 * Prueft ob ein Spiel gestartet werden kann.
	 *
	 * @param list die Liste der Queues die ueberprueft werden soll.
	 * @return -1 wenn kein Spielstart moeglich ist, <br>
	 *         sonst den Index der Queue die aus der Liste gestartet werden kann.
	 */
	private static int isStartPossible(List<List<Socket>> list) {
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
	private static void startGame(List<Socket> q) {
		System.out.println("Neuer Server wird gestartet");
		new Server().new GameThread(q);
	}

	/**
	 * Ein laufendes Spiel
	 *
	 * @author lucastheiss
	 * @version 0.1
	 *
	 */
	public class GameThread extends Thread {
		/**
		 * laenge des Spielfelds.
		 */
		private final int fieldLength = 40;
		/**
		 * Spielfeld.<br>
		 * Feld der Grundstuecke.
		 */
		private Grundstueck[] feld = new Grundstueck[fieldLength];
		/**
		 * Liste der Spieler
		 */
		private List<Client> list = new ArrayList<Client>();
		/**
		 * Message bei Disconnect wegen IOException.
		 */
		private String leaveMessage = "\nPlayer has left because of IOException.\n";


		/**
		 * Der Client.<br>
		 * Sammlung von Werten und Methoden die einen Spieler beschreiben.
		 *
		 * @author lucastheiss
		 * @version 0.1
		 *
		 */
		protected class Client {
			/**
			 * Geld des Spielers.
			 */
			private int geld = 0;
			/**
			 * Position auf dem Spielfeld.
			 */
			private int position = 0;
			private String name;
			private int ID;
			private Socket s;
			private DataInputStream in;
			private DataOutputStream out;
			/**
			 * Gefaengnisfreikarte vom ersten Stapel
			 */
			private boolean hasFreeCard1 = false;
			/**
			 * Gefaengnisfreikarte vom zweiten Stapel
			 */
			private boolean hasFreeCard2 = false;
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

			/**
			 * Client initialisieren.
			 *
			 * @param s    Socket des Spielers.
			 * @param name Name des Spielers.
			 * @param ID   eine <b>eindeutige</b> Nummer des Spielers.
			 * @throws IOException wenn Spieler nichtmehr antwortet.
			 */
			public Client(Socket s, String name, int ID) throws IOException {
				this.s = s;
				this.in = new DataInputStream(new BufferedInputStream(s.getInputStream()));
				this.out = new DataOutputStream(new BufferedOutputStream(s.getOutputStream()));
				this.name = name;
				this.ID = ID;
				System.out.println("Client initialisiert");
			}

			/**
			 *
			 * @return <b>true</b><br>
			 *         wenn er eine Gefaengnisfreikarte aus dem ersten Stapel hat.
			 *         <b>false</b> wenn er keine Gefaengnisfreikarte aus dem ersten Stapel
			 *         hat.
			 */
			public boolean hasFree1() {
				return hasFreeCard1;
			}

			/**
			 *
			 * @param hasFreeCard1 besitz der Gefaengnisfreikarte aus dem ersten Stapel.
			 */
			public void setFree1(boolean hasFreeCard1) {
				this.hasFreeCard1 = hasFreeCard1;
			}

			/**
			 *
			 * @return <b>true</b><br>
			 *         wenn er eine Gefaengnisfreikarte aus dem zweiten Stapel hat.
			 *         <b>false</b> wenn er keine Gefaengnisfreikarte aus dem zweiten Stapel
			 *         hat.
			 */
			public boolean hasFree2() {
				return hasFreeCard2;
			}

			/**
			 *
			 * @param hasFreeCard2 besitz der Gefaengnisfreikarte aus dem zweiten Stapel.
			 */
			public void setFree2(boolean hasFreeCard2) {
				this.hasFreeCard2 = hasFreeCard2;
			}

			/**
			 *
			 * @return die ID des Clients.
			 */
			public int getID() {
				return ID;
			}

			/**
			 *
			 * @return den Namen des Clients.
			 */
			public String getName() {
				return name;
			}

			/**
			 *
			 * @return die bisherigen Versuche einen Pasch zu wuerfeln, wenn ein Spieler im
			 *         Gefaengis ist.
			 */
			public int getVersuche() {
				return versuche;
			}

			/**
			 *
			 * @return <b>true</b> wenn der Spieler im Gefaengis ist.<br>
			 *         <b>false</b> wenn der Spieler nicht im Gefaengnis ist.
			 */
			public boolean getImGefaengnis() {
				return imGefaengnis;
			}

			/**
			 *
			 * @return den aktuellen Stand des Geldes des Clients.
			 */
			public int getGeld() {
				return geld;
			}

			/**
			 *
			 * @param change Aenderung des Geldes, Positiv oder Negativ.
			 * @throws IOException Client antwortet nicht.
			 */
			public void addGeld(int change) throws IOException {
				geld += change;
				broadcastInt(3);
				broadcastInt(this.ID);
				broadcastInt(this.geld);
			}

			/**
			 *
			 * @return die aktuelle Position auf dem Spielfeld des Clients.
			 */
			public int getPos() {
				return position;
			}

			/**
			 * Bewegt den Client und sendet dem Client die Anzahl.
			 *
			 * @param count Anzahl Augen auf dem Wuerfel.
			 * @throws IOException Keine Verbindung zum Client.
			 */
			public void walk(int count) throws IOException {
				position += count;
				if (position >= 40) {
					// ueber Los gekommen
					this.addGeld(200);
					position %= fieldLength;
				}
			}

			/**
			 * Kann der Client Karten verkaufen?
			 *
			 * @return <b>true</b> wenn Client noch Gefaengnisfreikarten hat oder
			 *         Grundstuecke verkaufen kann.<br>
			 *         <b>false</b> wenn er nichts mehr hat.
			 */
			public boolean canMakeMoney() {
				if (hasFreeCard1 || hasFreeCard2) { // Gefaengnisfreikarte zum verkaufen gefunden.
					return true;
				}
				for (Grundstueck g : feld) {
					if (g != null && g.getBesitzer().equals(this)) { // Grundstueck zum verkaufen gefunden.
						return true;
					}
				}
				// Kein Grundstueck zum verkaufen gefunden.
				return false;
			}

			/**
			 *
			 * @return der Socket des Spielers.
			 */
			public Socket getSocket() {
				return s;
			}

			/**
			 *
			 * @return der DataInputStream des Spielers.
			 */
			public DataInputStream getIn() {
				return in;
			}

			/**
			 *
			 * @return der DataOutputStream des Spielers.
			 */
			public DataOutputStream getOut() {
				return out;
			}

			/**
			 *
			 * @return die Augenzahl des ersten Wuerfels.
			 */
			public int getW1() {
				return w1;
			}

			/**
			 *
			 * @return die Augenzahl des zweiten Wuerfels.
			 */
			public int getW2() {
				return w2;
			}

			/**
			 *
			 * @return die Summe der Augenzahlen der beiden Wuerfel.
			 */
			public int getW() {
				return w1 + w2;
			}

			/**
			 * Spieler wuerfelt. Sendet die beiden Integer Werte an den Client.
			 *
			 * @throws IOException wenn der Client nicht erreichbar ist.
			 */
			public void wuerfeln(int playerNumber) throws IOException {
				broadcastInt(2);
				broadcastInt(playerNumber);
				list.get(playerNumber).in.readBoolean();
				w1 = model.Board.wuerfeln();
				w2 = model.Board.wuerfeln();
				broadcastInt(w1);
				broadcastInt(w2);
			}
		}

		/**
		 * Initialisierung eines neuen Spiels.
		 *
		 * @param sList Liste der Sockets der Mitspieler
		 */
		public GameThread(List<Socket> sList) {
			init(sList);
			this.start();
		}

		@Override
		public void run() {
			// try {
			System.out.println("Start game with " + list.size() + " players");

			/*
			 * // auswuerfeln wer anfaengt for (Client c : list) { c.wuerfeln(); }} catch
			 * (IOException e) { e.printStackTrace(); }
			 * 
			 * int nextPlayer = setBeginner(); for (int i = 0; i < list.size(); i++) { if
			 * (list.get(i).getW() > list.get(nextPlayer).getW()) { nextPlayer = i; } }
			 */
			int nextPlayer = 0;
			// Main Loop
			// solange mehr als ein Spieler uebrig ist
			while (list.size() > 1) {
				try {
					Client client = list.get(nextPlayer);
					// client im gefaengnis?
					if (client.getImGefaengnis()) {
						if (client.hasFree1()) {
							// Freikarte 1 aktivieren.
						} else if (client.hasFree2()) {
							// Freikarte 2 aktivieren.
						} else if (client.getVersuche() < 3) {
							// Fragen ob bezahlen oder Pasch versuchen.
						} else {
							// bezahlen.
						}
					}
					client.wuerfeln(nextPlayer);
					client.walk(client.getW());
					checkField(client);

					// naechster Spieler
					nextPlayer++;
					nextPlayer %= list.size();
				} catch (IOException e) {
					// Client entfernen wegen IOException
					list.remove(nextPlayer);
					nextPlayer %= list.size();
					System.out.println(leaveMessage);
				}
			}
			try {
				list.get(0).getOut().writeUTF("you have won");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		/**
		 * Prueft das Feld auf dem der Spieler ist.
		 *
		 * @param c der Client der an der reihe ist.
		 * @throws IOException Client antwortet nicht.
		 */
		private void checkField(Client c) throws IOException {
			System.out.println("Standort: " + c.getPos());
			boolean waitForAction = false;
			broadcastInt(4); // modus 4 starten
			broadcastInt(c.ID); // aktuellen Spieler miteilen
			Grundstueck grundstueck = feld[c.getPos()];
			// broadcastInt(c.getPos()); // Position miteilen
			if (grundstueck != null) {
				broadcastBoolean(true);
				System.out.println("Standort: " + c.getPos() + grundstueck.getName());
				broadcastUTF(grundstueck.getName()); // Position miteilen
				if (grundstueck.getBesitzer() == null) {
					broadcastBoolean(true);
					broadcastBoolean(c.getGeld() >= grundstueck.getPreis());
					waitForAction = true;

				} else {
					broadcastBoolean(false);

					Client besitzer = grundstueck.getBesitzer();
					broadcastInt(besitzer.getID());
					if (!besitzer.equals(c)) { // Feld gehoert einem anderen Spieler -> Spieler zahlt Miete an anderen
												// Spieler
						int miete = grundstueck.getMiete();
						if (c.getGeld() >= grundstueck.getMiete()) { // Miete zahlen
							besitzer.addGeld(miete);
							c.addGeld(-miete);
						} else { // Nicht genug Geld -> Verkaufen bis: genug Geld oder nichts mehr zu verkaufen.
							boolean lauf = true;
							while (lauf) {
								if (c.getGeld() >= miete) {
									lauf = false;
								} else {
									if (c.canMakeMoney()) {
										// Verkaufen...
										// zuerst die Gefaengnisfreikarten (falls vorhanden).
										if (c.hasFree1()) { // erste Gefaengnisfreikarte verkaufen.
											c.getOut().writeUTF("Sells freeCard1");
											c.addGeld(50);
											c.setFree1(false);
										} else if (c.hasFree2()) { // zweite Gefaengnisfreikarte verkaufen.
											c.getOut().writeUTF("Sells freeCard2");
											c.addGeld(50);
											c.setFree2(false);
										} else { // Grundstuecke oder Haeuser verkaufen.

										}
									} else {
										lauf = false;
									}
								}
							}
							if (c.getGeld() >= miete) { // genug Geld -> Miete zahlen.
								besitzer.addGeld(miete);
								c.addGeld(-miete);
							} else { // nicht genug Geld -> Verlieren.
								c.getOut().writeUTF("Nicht genug Geld, du hast verloren.");
								list.remove(c.getID()); // Stimmt die Reihenfolge der Spieler (naechsterSpieler) noch?
							}
						}
					} else {
						// Haus kauf moeglich?
					}
					// Feld gehoert dem Spieler -> nichts
				}
			} else { // Feld == null -> Sonderfelder
				broadcastBoolean(false);
				int steuern = 0;
				switch (c.getPos()) {
				case 0:
					broadcastInt(0);
					c.addGeld(400);
					break;
				// Fall through beabsichtigt -> Karte von Stapel 1 ziehen.
				case 2:
				case 17:
				case 33:
					broadcastInt(1);
					break;
				// Fall through beabsichtigt -> Karte von Stapel 2 ziehen.
				case 7:
				case 22:
				case 36:
					broadcastInt(2);
					// Karte von Stapel 2 ziehen.
					break;
				// Fall through beabsichtigt -> Steuern zahlen.
				case 4:
					steuern += 100;
				case 38:
					steuern += 100;
					broadcastInt(0);
					c.addGeld(-steuern);
					break;
				case 10: // Gefaengnis
					broadcastInt(0);
					break;
				case 20: // frei parken
					broadcastInt(0);
					break;
				case 30: // gehe in das Gefaengnis
					broadcastInt(3);
					break;
				default: // falls was uebersehen wurde.
					broadcastInt(0);
					break;
				}
			}

			if (waitForAction) {
				int erg = c.in.readInt();
				switch (erg) {
				case 1:
					broadcastInt(5);
					broadcastUTF(c.getName() + " hat die Strasse " + grundstueck.getName() + " gekauft");
					grundstueck.setBesitzer(c);
					c.addGeld(-grundstueck.getPreis());
					changeBesitzer(grundstueck, c);
					break;
				case 2:
					versteigerung(grundstueck, c);
					break;
				}
			}
		}
		
		private void changeBesitzer(Grundstueck g , Client neuerOwner) throws IOException {
			broadcastInt(7);
			broadcastUTF(g.getName());
			if(g.getBesitzer()==null) {
				broadcastInt(-1);
			}
			else {
				broadcastInt(g.getBesitzer().getID());
			}
			g.setBesitzer(neuerOwner);
			broadcastInt(neuerOwner.getID());
			
			
		}

		private void versteigerung(Grundstueck g, Client c) throws IOException {
			System.out.println("Versteigerung starten");
			int beginner = list.lastIndexOf(c);
			ArrayList<Client> auctionList = new ArrayList<Client>();
			auctionList.addAll(list);
			broadcastInt(6); //Auktion starten
			broadcastUTF(g.name);
			Client hoechstbieter = c;
			int aktuellesGebot = 0;
			while (auctionList.size() > 1) {
				beginner = beginner % auctionList.size();
				Client actual = auctionList.get(beginner);
				broadcastInt(actual.getID());
				broadcastInt(aktuellesGebot);
				int gebotNeu = actual.in.readInt();
				System.out.println(gebotNeu);
				if(gebotNeu ==-1) {
					auctionList.remove(actual);
				} else {
					aktuellesGebot = gebotNeu;
					hoechstbieter = actual;
				}
				beginner++;
			}
			broadcastInt(-1);
			broadcastInt(hoechstbieter.getID());
			hoechstbieter.addGeld(-aktuellesGebot);
			g.setBesitzer(hoechstbieter);

		}

		/**
		 * Entscheidet welcher Spieler beginnt.
		 *
		 * @return den Index des Spielers der Liste list der die höechste Augenzahl
		 *         geworfen hat.
		 */
		private int setBeginner() {
			for (int i = 0; i < list.size(); i++) {
				Client c = list.get(i);
				try {
					c.wuerfeln(i);
				} catch (IOException e) {
					// Client entfernt wegen IOException
					list.remove(i);
					System.out.println(leaveMessage);
				}
			}
			int erg = 0;
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).getW() > list.get(erg).getW()) {
					erg = i;
				}
			}
			return erg;
		}

		private void broadcastInt(int x) throws IOException {
			for (Client s : list) {
				s.out.writeInt(x);
				s.out.flush();
			}
		}

		private void broadcastBoolean(boolean x) throws IOException {
			for (Client s : list) {
				s.out.writeBoolean(x);
				s.out.flush();
			}
		}

		private void broadcastUTF(String x) throws IOException {
			for (Client s : list) {
				s.out.writeUTF(x);
				s.out.flush();
			}
		}

		/**
		 * Initialsetup der Spieler<br>
		 * Clients in der Liste speichern und allen Spielern das Startgeld geben
		 *
		 * @param sList Liste der Sockets
		 */
		private void init(List<Socket> sList) {
			for (int i = 0; i < sList.size(); i++) {
				Socket s = sList.get(i);
				try {
					DataOutputStream out = new DataOutputStream(new BufferedOutputStream(s.getOutputStream()));
					DataInputStream in = new DataInputStream(new BufferedInputStream(s.getInputStream()));
					String name = "";
					// Spielername benoetigt
					// String needPlayerName = "need Playername expects {" + String.class.toString()
					// + "}";
					// out.writeUTF(needPlayerName);
					// Spielername gelesen
					System.out.println("Spieler " + i + " erzeugt");
					out.writeInt(i);
					out.flush();
					name = in.readUTF();

					// while (name == null || name.equals("")) {
					// out.writeUTF(needPlayerName);
					// name = in.readUTF();
					// }
					list.add(new Client(s, name, i));
				} catch (IOException e) {
					// Client entfernen der eine IOException geworfen hat.
					list.remove(list.size() - 1);
					System.out.println(leaveMessage);
				}
			}
			for (Client other : list) {
				try {
					broadcastUTF(other.name);
					broadcastInt(other.ID);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			for (int i = 0; i < list.size(); i++) {
				Client c = list.get(i);
				try {
			
					c.addGeld(1500);
				} catch (Exception e) {
					// Client entfernen der eine IOException geworfen hat.
					list.remove(i);
					// Karten versteigern
					System.out.println(leaveMessage);
				}
			}
			// Felder initialisieren
			feld[0] = null; // Los
			feld[1] = new Grundstueck("Kesselhaus", new int[] { 2, 10, 30, 90, 160, 250 }, 60, 1, new int[] {1, 3});
			feld[2] = null; // Kartenstapel 1
			feld[3] = new Grundstueck("AstaBuero", new int[] { 4, 20, 60, 180, 320, 450 }, 60, 3, new int[] {1, 3});
			feld[4] = null; // Steuern 200
			feld[5] = new Grundstueck("BingenBahnhof", new int[] { 25, 50, 100, 200 }, 200, 5, null); // Bahnhof
			feld[6] = new Grundstueck("PflanzenLabor", new int[] { 6, 30, 90, 270, 400, 550 }, 100, 6, new int[] {6, 8, 9});
			feld[7] = null; // Kartenstapel 2
			feld[8] = new Grundstueck("GewaechsHaus", new int[] { 6, 30, 90, 270, 400, 550 }, 100, 8, new int[] {6, 8, 9});
			feld[9] = new Grundstueck("DemonstrationsFeld", new int[] { 8, 40, 100, 300, 450, 600 }, 120, 9, new int[] {6, 8, 9});
			feld[10] = null; // Gefaengnis
			feld[11] = new Grundstueck("MotorenPruefstand", new int[] { 10, 50, 150, 450, 625, 750 }, 140, 11, new int[] {11, 13, 14});
			feld[12] = null; // Rechenzentrum, evtl als Grundstueck behandeln
			feld[13] = new Grundstueck("FahrzeugLabor", new int[] { 10, 50, 150, 450, 625, 750 }, 140, 13, new int[] {11, 13, 14});
			feld[14] = new Grundstueck("SolarTankstelle", new int[] { 12, 60, 180, 500, 700, 900 }, 160, 14, new int[] {11, 13, 14});
			feld[15] = new Grundstueck("KreuznachBahnhof", new int[] { 25, 50, 100, 200 }, 200, 15, null); // Bahnhof
			feld[16] = new Grundstueck("TrvRhenania", new int[] { 14, 70, 200, 550, 750, 950 }, 180, 16, new int[] {16, 18, 19});
			feld[17] = null; // Kartenstapel 1
			feld[18] = new Grundstueck("BingerBeasts", new int[] { 14, 70, 200, 550, 750, 950 }, 180, 18, new int[] {16, 18, 19});
			feld[19] = new Grundstueck("BingenImpulse", new int[] { 16, 80, 220, 600, 800, 1000 }, 200, 19, new int[] {16, 18, 19});
			feld[20] = null; // Frei parken
			feld[21] = new Grundstueck("NetzwerkLabor", new int[] { 18, 90, 250, 700, 875, 1050 }, 220, 21, new int[] {21, 23, 24});
			feld[22] = null; // Kartenstapel 2
			feld[23] = new Grundstueck("PcPool236", new int[] { 18, 90, 250, 700, 875, 1050 }, 220, 23, new int[] {21, 23, 24});
			feld[24] = new Grundstueck("PcPool237", new int[] { 20, 100, 300, 750, 925, 1100 }, 240, 24, new int[] {21, 23, 24});
			feld[25] = new Grundstueck("MainzBahnhof", new int[] { 25, 50, 100, 200 }, 200, 25, null); // Bahnhof
			feld[26] = new Grundstueck("StudienBeratung", new int[] { 22, 110, 330, 800, 975, 1150 }, 260, 26, new int[] {26, 27, 29});
			feld[27] = new Grundstueck("StudienSekretariat", new int[] { 22, 110, 330, 800, 975, 1150 }, 260, 27, new int[] {26, 27, 29});
			feld[28] = null; // Zollamt
			feld[29] = new Grundstueck("DekanBuero", new int[] { 24, 120, 360, 850, 1025, 1200 }, 280, 29, new int[] {26, 27, 29});
			feld[30] = null; // gehe in das Gefaengnis
			feld[31] = new Grundstueck("RhenoTeutonia", new int[] { 26, 130, 390, 900, 1100, 1275 }, 300, 31, new int[] {31, 32, 34});
			feld[32] = new Grundstueck("Holsatia", new int[] { 26, 130, 390, 900, 1100, 1275 }, 300, 32, new int[] {31, 32, 34});
			feld[33] = null; // Kartenstapel 1
			feld[34] = new Grundstueck("Markomannia", new int[] { 28, 150, 450, 1000, 1200, 1400 }, 320, 34, new int[] {31, 32, 34});
			feld[35] = new Grundstueck("WormsBahnhof", new int[] { 25, 50, 100, 200 }, 200, 35, null); // Bahnhof
			feld[36] = null; // Kartenstapel 2
			feld[37] = new Grundstueck("Mensa", new int[] { 35, 175, 500, 1100, 1300, 1500 }, 350, 37, new int[] {37, 39});
			feld[38] = null; // Steuern 100
			feld[39] = new Grundstueck("Bibliothek", new int[] { 50, 200, 600, 1400, 1700, 2000 }, 400, 39, new int[] {37, 39});
		}
	}
}
