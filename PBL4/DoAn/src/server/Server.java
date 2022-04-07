package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Vector;

public class Server {
	Vector<Xuly> clients = new Vector<Xuly>();
	private static int source;
	private static int destination;
	private static ServerSocket server = null;
	private static Thread runServer = null;
	private static Scanner sc = new Scanner(System.in);
	Map<String, String> list = new HashMap<String, String>();

	public static void main(String[] args) {
		new Server();
	}

	public Server() {
		new Thread() {
			@Override
			public void run() {
				while (true) {
					System.out.print("Server >> ");
					String s = sc.nextLine().trim();
					String[] gr = s.trim().split("\\s+");
					if (s.equals("start"))
						StartServer();
					else if (s.equals("stop"))
						StopServer();
					else if (s.equals("IP"))
						PrintIP();
					else if (gr.length == 2 && gr[0].trim().equals("IP") && gr[1].trim().matches("\\d+"))
						PrintIPWithCount(gr[1]);
					else if (s.equals("Data"))
						PrintArray();
					else if (gr.length == 4 && gr[0].trim().equals("Data")) {
						PrintArrayWithIP(s);

					}

				}
			}
		}.start();

	}

	public void PrintIPWithCount(String s) {
		condition();
		int count = Integer.parseInt(s);
		if (count > clients.size()) {
			System.out.println("Không có đủ IP yêu cầu");
			return;
		}
		int n = 0;
		for (var item : clients) {
			if (n > count)
				break;
			System.out.println(item.soc);
			n++;
		}
	}

	// Socket[addr=/127.0.0.1,port=51332,localport=5000]
	public void PrintArrayWithIP(String s) {
		condition();
		String[] gr = s.trim().split("\\s+");
		String check = "Socket[addr=/" + gr[1].trim() + ",port=" + gr[2].trim() + ",localport=" + gr[3].trim() + "]";
		for (var item : list.entrySet()) {
			if (item.getKey().equals(check))
				System.out.println(item.getValue());
		}
	}

	public void PrintArray() {
		condition();
		for (var item : list.entrySet()) {
			System.out.println(item.getKey() + ":\n" + item.getValue());
		}
	}

	public void condition() {
		if (runServer == null || !runServer.isAlive() || server.isClosed()) {
			System.out.println("Server hiện đang đóng");
			return;
		}
		if (clients.isEmpty()) {
			System.out.println("Không có client đang đăng nhập");
			return;
		}
	}

	public void PrintIP() {
		condition();
		for (var item : clients) {
			System.out.println(item.soc);
		}

	}

	public void StartServer() {
		StopServer();
		try {
			server = new ServerSocket(5000);
			var sv = this;

			runServer = new Thread() {
				public void run() {
					while (true) {
						try {
							Socket soc = server.accept();
							Xuly x = new Xuly(soc, sv);
							clients.add(x);
							x.start();

						} catch (SocketException e2) {
							return;
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			};
			runServer.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void StopServer() {
		if (server == null)
			return;
		try {
			if (runServer != null) {
				runServer.stop();
			}

			if (!server.isClosed()) {

				server.close();
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		for (var item : clients) {
			try {
				item.stop();

			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			try {
				item.soc.close();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		clients.clear();

	}

	class Xuly extends Thread {
		Server cs;
		Socket soc;
		String name;

		public Xuly(Socket soc, Server cs) {
			this.soc = soc;
			this.cs = cs;
		}

		public double[][] handleData(String textArray) {
			String[] gr = textArray.split("\n");
			int length = gr.length - 1;
			for (var item : gr) {
				if (item.length() < 1)
					length -= 1;
			}
			double[][] arr = new double[length][length];
			int n = 0;
			for (String item : gr) {
				if (item.length() < 1)
					continue;
				String[] gr2 = item.trim().split("\\s+");
				if (gr2.length == 2) {
					source = Integer.parseInt(gr2[0]);
					destination = Integer.parseInt(gr2[1]);
					continue;
				}
				for (int i = 0; i < length; i++) {
					arr[n][i] = Double.parseDouble(gr2[i].trim());
				}
				n++;
			}
			return arr;
		}

		public void run() {
			try {
				DataOutputStream dos = new DataOutputStream(soc.getOutputStream());
				DataInputStream dis = new DataInputStream(soc.getInputStream());
				while (true) {
					String mgs = dis.readUTF();
					list.put(soc.toString(), mgs);
					double[][] data = handleData(mgs);
					String rs = algo.handle(data, source, destination);
					dos.writeUTF(rs);
				}
			} catch (Exception e) {
			}
		}
	}
}
