package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class MainWindow extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextArea console;
	
	public MainWindow() {
		setTitle("B-K Server Console");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setExtendedState(MAXIMIZED_BOTH);
		setLayout(new BorderLayout());
		setIconImage(new ImageIcon(getClass().getResource("/img/icon.png")).getImage());
		
		console = new JTextArea();
		console.setEditable(false);
		console.setBackground(Color.BLACK);
		console.setForeground(Color.decode("#00FF00"));
		console.setLineWrap(true);
		console.setFont(new Font("Arial", Font.BOLD, 25));
		add(new JScrollPane(console), BorderLayout.CENTER);
		
		setVisible(true);
	}
	
	public void setConsole(ArrayList<String> consoleLog){
		console.setText("");
		for (String string : consoleLog) {
			console.append(string + "\n");
		}
	}
}
