package connection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import model.Player;
import model.Shoot;

public class ThreadSocket extends Thread implements IObservable {

	private Socket connection;
	private DataInputStream inputStream;
	private DataOutputStream outputStream;
	private boolean stop;
	// private ArrayList<String> players;
	private ServerKingdomBattle server;
	private Player clientPlayer;
	private Shoot shoot;
	private IObserver iObserver;

	public ThreadSocket(Socket socket, ServerKingdomBattle server) throws IOException, InterruptedException {
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
					try {
						manageRequest(request);
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null,
								"Connection lost with " + connection.getInetAddress().getHostAddress(),
								"Connection Finished", JOptionPane.WARNING_MESSAGE);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
				stop = true;
			}
		}
	}

	public void manageRequest(String request) throws IOException {
		if (request.equals(REQUEST.SEND_PLAYERS.toString())) {
			updatePlayer();
		}else if (request.equals(REQUEST.SEND_SHOOTS.toString())) {
			updateShoots();
		}
	}

	private void updateShoots() throws IOException {
		String strShoot = inputStream.readUTF();
		String[] tmpShoot = strShoot.split(",");
		shoot = new Shoot(Integer.parseInt(tmpShoot[0]), Integer.parseInt(tmpShoot[1]), Integer.parseInt(tmpShoot[2]), 
				Integer.parseInt(tmpShoot[3]), new Boolean(tmpShoot[4]));
		server.update();
		try {
			shoot = null;
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private void updatePlayer() throws IOException {
		String strPlayer = inputStream.readUTF();
		String[] tmpPlayer = strPlayer.split(",");
		clientPlayer = new Player(tmpPlayer[0], Integer.parseInt(tmpPlayer[1]), Integer.parseInt(tmpPlayer[2]),
				Integer.parseInt(tmpPlayer[3]), Integer.parseInt(tmpPlayer[4]), Integer.parseInt(tmpPlayer[5]));
		server.update();
	}

	public void sendPlayers(ArrayList<Player> players) throws IOException {
		// outputStream.writeUTF(REQUEST.SEND_PLAYERS.toString());
		// outputStream.writeUTF(String.valueOf(players.length()));
		// FileInputStream inputStream = new FileInputStream(players);
		// byte[] buffer = new byte[4096];
		// while (inputStream.read(buffer) > 0) {
		// outputStream.write(buffer);
		// }
		// inputStream.close();
		try {
			String playersStr = "";
			for (Player player : players) {
				playersStr = playersStr + player.toString() + ":";
			}
			outputStream.writeUTF(REQUEST.UPDATE_PLAYER.toString());
			outputStream.writeUTF(playersStr);
			outputStream.flush();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void sendWaitRequest() throws IOException {
		outputStream.writeUTF(REQUEST.WAIT_PLAYERS.toString());
		outputStream.flush();
	}

	public Player getClientPlayer() {
		return clientPlayer;
	}
	
	public Shoot getShoot() {
		return shoot;
	}

	public boolean isStop() {
		return stop;
	}

	@Override
	public void addObserver(IObserver iObserver) {
		this.iObserver = iObserver;
	}

	@Override
	public void removeObserver() {
		iObserver = null;
	}
}
