package client;

import javax.swing.JButton;
import javax.swing.JFrame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.JTextField;

public class Client extends JFrame implements ActionListener {
	
	
	public static void main(String[] args) {
		Client client = new Client();
	}
	
	
	JTextField ipInput, portInput;
	JButton connectButton, closeButton;
	
	public Client() {
		// Connect Sever Form
				JLabel ipLabel = new JLabel("IP: ");
				ipInput = new JTextField();

				ipLabel.setForeground(parseColor("#191970"));
				ipLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
				ipLabel.setBounds(50, 30, 150, 25);
				ipInput.setBounds(100, 30, 200, 25);

				this.add(ipLabel);
				this.add(ipInput);
				JLabel portLabel = new JLabel("PORT: ");
				portInput = new JTextField();

				portLabel.setForeground(parseColor("#191970"));
				portLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
				portLabel.setBounds(50, 80, 150, 25);
				portInput.setBounds(100, 80, 200, 25);

				this.add(portLabel);
				this.add(portInput);
				connectButton = new JButton("Connect");
				closeButton = new JButton("Exit");
				connectButton.setBounds(110, 130, 85, 25);
				closeButton.setBounds(215,130, 85, 25);
				
				this.add(connectButton);
				this.add(closeButton);

			
				// Add event
				connectButton.addActionListener(this);
				closeButton.addActionListener(this);
				
				

				this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//				this.setTitle("Client Frame");
				this.setLocationRelativeTo(null);
				this.setLayout(null);
				this.setBounds(600, 280, 380, 220);
				this.getContentPane().setBackground(parseColor("#B0C4DE"));
	
				this.setVisible(true);
	}
	private void setupIcon(JButton button, String img) {
		try {
			Image icon = ImageIO.read(getClass().getResource("/resources/" + img + ".png"));
			ImageIcon imageIcon = new ImageIcon(icon);
			button.setIcon(imageIcon);
			button.setBorderPainted(false);
			button.setFocusPainted(false);
			button.setContentAreaFilled(false);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Color parseColor(String colorStr) {
		return new Color(Integer.valueOf(colorStr.substring(1, 3), 16), Integer.valueOf(colorStr.substring(3, 5), 16),
				Integer.valueOf(colorStr.substring(5, 7), 16));
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == connectButton) {
			String ip = ipInput.getText();
			String port = portInput.getText();
			try {
				Socket soc = new Socket(ip,Integer.parseInt(port));
			}catch (Exception e1) {
				JOptionPane.showMessageDialog(this, "Can't connect to Server","ERROR",JOptionPane.ERROR_MESSAGE);
				return;
			}
			JOptionPane.showMessageDialog(this, "Connect to Server","INFOR",JOptionPane.INFORMATION_MESSAGE);
			
			FormMain formMain = new FormMain(ip, port);
			this.dispose();
		}
		if(e.getSource()==closeButton)
		{
			this.dispose();
		}
		
	}
}
