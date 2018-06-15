package run;

import java.awt.HeadlessException;
import java.io.IOException;

import javax.swing.JOptionPane;

import connection.ServerKingdomBattle;

public class RunnerServer {
	
	public static void main(String[] args) {
		try {
			new ServerKingdomBattle(Integer.parseInt(JOptionPane.showInputDialog("IP Port")));
		} catch (NumberFormatException | HeadlessException | IOException e) {
			JOptionPane.showMessageDialog(null, "Wrong IP Port / Cannot Start The Server", "Wrong IP Port", JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
		}
	}
	
}