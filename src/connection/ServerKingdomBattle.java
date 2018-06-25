package connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Logger;

import model.GameManager;
import model.Player;
import model.Shoot;
import persistence.JSonPlayer;

public class ServerKingdomBattle extends Thread implements IObserver {

	private ServerSocket server;
	private int port;
	private boolean stop;
	public final static Logger LOGGER = Logger.getGlobal();
	private ArrayList<ThreadSocket> connections;
	private GameManager manager;

	public ServerKingdomBattle(int port) throws IOException, InterruptedException {
		manager = new GameManager();
		connections = new ArrayList<>();
		this.port = port;
		server = new ServerSocket(port);
		start();
		System.out.println("Servidor Iniciado en puerto " + this.port);
	}

	@Override
	public void run() {
		ThreadSocket socket;
		while (!stop) {
			Socket connection;
			try {
				connection = server.accept();
				socket = new ThreadSocket(connection, this);
				connections.add(socket);
				System.out.println("Conexion Aceptada: " + connection.getInetAddress().getHostAddress());
			} catch (IOException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void updateGame() throws IOException {
		ArrayList<Player> players = updatePlayers();
		manager.setPlayers(players);

		updateShoots();

		sendPlayers(manager.getPlayers());
		sendShoots(manager.getShoots());
	}

	public void updateShoots() {
		for (ThreadSocket threadSocket : connections) {
			if (threadSocket.getShoot() != null) {
				manager.addShoot(threadSocket.getShoot());
			}
		}
		manager.validateGame();
	}

	public ArrayList<Player> updatePlayers() {
		ArrayList<Player> players = new ArrayList<>();
		for (ThreadSocket threadSocket : connections) {
			if (threadSocket.getClientPlayer() != null) {
				players.add(threadSocket.getClientPlayer());
			}
		}
		return players;
	}

	private void sendShoots(ArrayList<Shoot> shoots) {
		for (ThreadSocket threadSocket : connections) {
			threadSocket.sendShoots(shoots);
		}
	}

	private void sendPlayers(ArrayList<Player> players) throws IOException {
		for (ThreadSocket threadSocket : connections) {
			threadSocket.sendPlayers(players);
		}
	}

	public ArrayList<ThreadSocket> getConnections() {
		return connections;
	}

	public int getPort() {
		return port;
	}

	@Override
	public void update() {
		try {
			updateGame();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
