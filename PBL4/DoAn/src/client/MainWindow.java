package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.Socket;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import model.Graph;

public class MainWindow extends JPanel implements ActionListener {
	private Graph graph;
	private GraphPanel graphPanel;
	private DataOutputStream dos;
	private DataInputStream dis;
	private Socket soc = null;

	private JButton run, reset, info,close;
	private JTextField textFieldStart, textFieldEnd;
	private JTextArea nhap, textArea;

	private String ip;
	private Integer port;
	
	public MainWindow(String ip,String port) {
		MainPanel();
		this.ip=ip;
		this.port = Integer.parseInt(port);
	}

	public void MainPanel() {
		super.setLayout(null);
		this.setBackground(parseColor("#B0C4DE"));
		setTopPanel();
		SetMainPanel();
		SetLeftPanel();
		SetBotPanel();
	}

	public void setTopPanel() {
		JLabel label = new JLabel("XÂY DỰNG ĐƯỜNG ĐI TẬP TRUNG");
		label.setForeground(parseColor("#191970"));
		label.setFont(new Font("arial", Font.BOLD, 30));
		label.setBounds(600, 20, 2000, 50);
		add(label);
	}

	public void SetMainPanel() {
		graph = new Graph();
		graphPanel = new GraphPanel(graph);
		graphPanel.setPreferredSize(new Dimension(10000, 10000));
		graphPanel.setBackground(parseColor("#F0F8FF"));
		JScrollPane scroll = new JScrollPane();
		scroll.setViewportView(graphPanel);
		scroll.setPreferredSize(new Dimension(750, 500));
		scroll.getViewport().setViewPosition(new Point(3900, 900));
		scroll.setBounds(400, 100, 1100, 600);
		
		
		
		add(scroll);
	}

	public void SetLeftPanel() {

		JLabel input = new JLabel("Nhập ma trận");
		input.setForeground(parseColor("#191970"));
		input.setFont(new Font("arial", Font.PLAIN, 18));
		input.setBounds(35, 83, 130, 50);

		nhap = new JTextArea();
		nhap.setPreferredSize(new Dimension(900, 900));
		JScrollPane scrollNhap = new JScrollPane(nhap);
		scrollNhap.setPreferredSize(new Dimension(300, 300));
		scrollNhap.setBounds(30, 130, 330, 240);
		nhap.setBackground(parseColor("#F0F8FF"));

		JLabel rs = new JLabel("Kết quả");
		rs.setForeground(parseColor("#191970"));
		rs.setFont(new Font("arial", Font.PLAIN, 18));
		rs.setBounds(35, 375, 100, 50);

		textArea = new JTextArea();
		textArea.setPreferredSize(new Dimension(900, 900));
		JScrollPane scrollRs = new JScrollPane(textArea);
		scrollRs.setPreferredSize(new Dimension(300, 300));
		scrollRs.setBounds(30, 420, 330, 278);
		textArea.setBackground(parseColor("#F0F8FF"));
		add(scrollNhap);
		add(input);
		add(rs);
		add(scrollRs);

	}

	public void SetBotPanel() {
		JLabel start = new JLabel("Đỉnh:");
		textFieldStart = new JTextField();
		start.setBounds(400, 715, 50, 50);
		textFieldStart.setBounds(480, 715, 60, 50);

		JLabel end = new JLabel("Đích:");
		textFieldEnd = new JTextField();
		end.setBounds(570, 715, 50, 50);
		textFieldEnd.setBounds(640, 715, 60, 50);

		start.setForeground(parseColor("#191970"));
		start.setFont(new Font("arial", Font.PLAIN, 18));

		end.setForeground(parseColor("#191970"));
		end.setFont(new Font("arial", Font.PLAIN, 18));

		run = new JButton();
		setupIcon(run, "run");
		run.setBounds(920, 710, 50, 50);

		reset = new JButton();
		setupIcon(reset, "reset");
		reset.setBounds(1020, 710, 50, 50);

		info = new JButton();
		setupIcon(info, "info");
		info.setBounds(820, 710, 50, 50);
		
		close = new JButton();
		setupIcon(close, "close");
		close.setBounds(1445,710,50,50);
		
		add(run);
		add(reset);
		add(info);
		add(start);
		add(end);
		add(close);
		add(textFieldEnd);
		add(textFieldStart);
		run.addActionListener(this);
		info.addActionListener(this);
		reset.addActionListener(this);
		close.addActionListener(this);
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
	public void out() throws Exception
	{
		throw new Exception("out");
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == reset) {
			graphPanel.reset();
			nhap.setText("");
			textArea.setText("");
			textFieldEnd.setText("");
			textFieldStart.setText("");
		}
		if (e.getSource() == info) {
			JFileChooser chooser = new JFileChooser();
			chooser.showOpenDialog(null);
			File file = chooser.getSelectedFile();
			String filename = file.getAbsolutePath();
			try {
				FileReader reader = new FileReader(filename);
				BufferedReader br = new BufferedReader(reader);
				nhap.read(br, null);
				br.close();
				nhap.requestFocus();
			} catch (Exception e2) {
				JOptionPane.showMessageDialog(null, e);
			}
			textArea.setText("");
			textFieldStart.setText("");
			textFieldEnd.setText("");
			graphPanel.reset();
		}
		if (e.getSource() == run) {
			graphPanel.reset();
			textArea.setText("");
			graph.setTextArray(nhap.getText());
			graph.setTextDestination(textFieldEnd.getText());
			graph.setTextSource(textFieldStart.getText());
			try {
				try {
					graph.CheckCondition();
					graph.AddData();
					try {
						if (soc == null || soc.isClosed()) {
							soc = new Socket(ip, port);
							dos = new DataOutputStream(soc.getOutputStream());
							dis = new DataInputStream(soc.getInputStream());
						}
					} catch (Exception e2) {
						soc = null;
						int input = JOptionPane.showConfirmDialog(null,
								"Không thể kết nối server, Có muốn reconnect");
						if (input == 0) {
							actionPerformed(e);
						}
						return;
					}
					dos.writeUTF(nhap.getText() + "\n" + graph.getTextSource() + " " + graph.getTextDestination());
					String s = dis.readUTF();
					graphPanel.setPath(graph.setPathFromString(s));
					textArea.setText(graph.Result(s));
				} catch (IOException e1) {
					soc = null;
					int input = JOptionPane.showConfirmDialog(null, "Không thể kết nối server, Có muốn reconnect");
					if (input == 0) {
						actionPerformed(e);
					}
					return;

				}
			} catch (IllegalStateException ise) {
				JOptionPane.showMessageDialog(null, ise.getMessage());
			}

		}
		if(e.getSource()==close)
		{
			 Window win = SwingUtilities.getWindowAncestor(this);
			 win.dispose();
			 Client client = new Client();
			 client.setVisible(true);
		}
		

	}
}
