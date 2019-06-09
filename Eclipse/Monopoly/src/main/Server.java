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
			/**
			 * Einrichten eines neuen Sockets
			 */

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
	 * @param q Die wartenden Clients
	 */
	private static void startGame(Queue<Socket> q) {
		new Server().new GameThread(q);
	}
	/**
	 * Ein laufendes Spiel
	 * @author lucastheiss
	 *
	 */
	public class GameThread extends Thread {
		/**
		 * Liste der Spieler
		 */
		private List<Client> list = new ArrayList<Client>();
		/**
		 * Verbund aus Socket und Player
		 * @author lucastheiss
		 *
		 */
		protected class Client {
			private Socket s;
			private DataInputStream in;
			private DataOutputStream out;
			private model.Player p;
			int w1;
			int w2;
			public Client(Socket s, model.Player p) throws IOException {
				this.s = s;
				this.in = new DataInputStream(new BufferedInputStream(s.getInputStream()));
				this.out = new DataOutputStream(new BufferedOutputStream(s.getOutputStream()));
				this.p = p;
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
			public model.Player getPlayer() {
				return p;
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
				out.writeUTF("rolling the dice Sending {" + Integer.class.toString() + ", " + Integer.class.toString() + "}");
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
			//auswuerfeln wer anfaengt
			for(Client c : list) {
				try {
					c.wuerfeln();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			int nextPlayer = 0;
			for(int i = 0; i < list.size(); i++) {
				if(list.get(i).getW() > list.get(nextPlayer).getW()) {
					nextPlayer = i;
				}
			}
			//Main Loop
			//solange mehr als ein Spieler uebrig ist
			while(list.size() > 1) {
				try {
					Client client = list.get(nextPlayer);
					client.wuerfeln();
					//Methode private
					//client.getPlayer().move(client.getW());
					
					
					
					//naechster Spieler
					nextPlayer++;
					nextPlayer %= list.size();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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
		 * @param q Queue der Sockets
		 */
		private void init(Queue<Socket> q) {
			for(Socket s: q) {
				//Konstruktor nicht public
				//list.add(new Client(s, new model.Player()));
			}
			for(Client c : list) {
				c.getPlayer().earnRental(1500);
			}
		}
	}
}
