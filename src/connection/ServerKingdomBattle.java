package connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Logger;

public class ServerKingdomBattle extends Thread{
	
	private ServerSocket server;	
	private int port;
	private boolean stop;
	public final static Logger LOGGER = Logger.getGlobal();
	private ArrayList<ThreadSocket> connections;
	
	public ServerKingdomBattle(int port) throws IOException {
		connections = new ArrayList<>();
		this.port = port;
		server = new ServerSocket(port);
		System.out.println("Servidor Iniciado");
		start();
	}
	
	@Override
	public void run() {
		while (!stop) {
			Socket connection;
			try {
				connection = server.accept();
				connections.add(new ThreadSocket(connection, this));
				System.out.println("Conexion Aceptada: " + connection.getInetAddress().getHostAddress());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public ArrayList<ThreadSocket> getConnections() {
		return connections;
	}
	
	public int getPort() {
		return port;
	}
	
}
