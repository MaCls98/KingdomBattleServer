package connection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;

public class ThreadSocket extends Thread {

	private Socket connection;
	private DataInputStream inputStream;
	private DataOutputStream outputStream;
	private boolean stop;
	// private ArrayList<String> players;
	private ArrayList<String> players;
	private ServerKingdomBattle server;
	private ArrayList<ThreadSocket> sockets;

	public ThreadSocket(Socket socket, ServerKingdomBattle server) throws IOException {
		players = new ArrayList<>();
		this.connection = socket;
		this.server = server;
		inputStream = new DataInputStream(socket.getInputStream());
		outputStream = new DataOutputStream(socket.getOutputStream());
		start();
	}

	@Override
	public void run() {
		while (!stop) {
			try {
				String request = inputStream.readUTF();
				if (request != null) {
					manageRequest(request);
				}
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null,
						"Connection lost with " + connection.getInetAddress().getHostAddress(), "Connection Ended",
						JOptionPane.WARNING_MESSAGE);
				stop = true;
			}
		}
	}

	public void manageRequest(String request) throws IOException {
		if (request.equals(REQUEST.SEND_PLAYERS.toString())) {
			updatePlayer();
		}
	}

	public void updatePlayer() throws IOException {
		String player = inputStream.readUTF();
		String tmpP[] = player.split(",");
		if (players.size() == 0) {
			players.add(player);
		} else {
			for (String string : players) {
				String tmpL[] = string.split(",");
				if (!tmpP[0].equals(tmpL[0])) {
					players.add(player);
				}
			}
		}
		sendPlayers();
	}

	public void sendPlayers() throws IOException {
		outputStream.writeUTF(REQUEST.SEND_PLAYERS.toString());
		outputStream.writeInt(players.size());
		for (String string : players) {
			System.out.println(string);
			outputStream.writeUTF(string);
		}
	}

	public boolean isStop() {
		return stop;
	}
}
