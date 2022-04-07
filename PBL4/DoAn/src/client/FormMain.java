package client;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.UIManager;

public class FormMain {
	public static void main(String[] args) {
		FormMain formMain =new FormMain("localhost","5000");
	}
	
	public FormMain(String ip, String port) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}
		JFrame j = new JFrame();
		j.setTitle("Dijkstra Algorithm");
		j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		j.setSize(new Dimension(2000, 900));
		j.add(new MainWindow(ip,port));
		j.setVisible(true);
	}
}
