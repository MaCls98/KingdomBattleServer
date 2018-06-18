package connection;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Logger;

import model.Player;
import persistence.JSonPlayer;

public class ServerKingdomBattle extends Thread implements IObserver{
	
	private ServerSocket server;	
	private int port;
	private boolean stop;
	public final static Logger LOGGER = Logger.getGlobal();
	private ArrayList<ThreadSocket> connections;
	private JSonPlayer jSonPlayer;
	
	public ServerKingdomBattle(int port) throws IOException, InterruptedException {
		connections = new ArrayList<>();
		jSonPlayer = new JSonPlayer();
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
	
	
	
	private void updatePlayers() throws IOException {
		ArrayList<Player> players = new ArrayList<>();
		for (ThreadSocket threadSocket : connections) {
			if (threadSocket.getClientPlayer() != null) {
				players.add(threadSocket.getClientPlayer());				
			}
		}
		jSonPlayer.setPlayers(players);
		jSonPlayer.writePlayersJSon();
		File jsonPlayers = new File("./players.json");
		for (ThreadSocket threadSocket : connections) {
			if (threadSocket != null) {
				threadSocket.sendPlayers(jsonPlayers);
			}
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
			updatePlayers();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
