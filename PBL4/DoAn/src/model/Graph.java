package model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class Graph {
	private List<Node> nodes = new ArrayList<>();
	private List<Edge> edges = new ArrayList<>();
	private Node source;
	private Node destination;
	private boolean solved = false;
	private String textArray = null;
	private String textSource = null;
	private String textDestination = null;
	public static double[][] arr;

	public void setTextArray(String textArray) {
		this.textArray = textArray;
	}

	public String getTextSource() {
		return textSource;
	}

	public void setTextSource(String textSource) {
		this.textSource = textSource;
	}

	public String getTextDestination() {
		return textDestination;
	}

	public void setTextDestination(String textDestination) {
		this.textDestination = textDestination;
	}

	public List<Node> getNodes() {
		return nodes;
	}

	public void setNodes(List<Node> nodes) {
		this.nodes = nodes;
	}

	public List<Edge> getEdges() {
		return edges;
	}

	public void setEdges(List<Edge> edges) {
		this.edges = edges;
	}

	public Node getSource() {
		return source;
	}

	public void setSource(Node source) {
		if (nodes.contains(source))
			this.source = source;
	}

	public Node getDestination() {
		return destination;
	}

	public void setDestination(Node destination) {
		if (nodes.contains(destination))
			this.destination = destination;
	}

	public boolean isSolved() {
		return solved;
	}

	public void setSolved(boolean solved) {
		this.solved = solved;
	}

	public boolean isSource(Node node) {
		return source == node;
	}

	public boolean isDestination(Node node) {
		return destination == node;
	}

	public Node GetNodeById(int id) {
		for (Node node : nodes) {
			if (node.getId() == id)
				return node;
		}
		return null;
	}

	public List<Node> GetFriendNode(Node node) {
		List<Node> list = new ArrayList<>();
		for (Edge edge : edges) {
			if (edge.getStart() == node) {
				list.add(edge.getEnd());
			}
			if (edge.getEnd() == node) {
				list.add(edge.getStart());
			}
		}
		return list;
	}

	public void clear() {
		nodes.clear();
		edges.clear();
		solved = false;
		source = null;
		destination = null;
	}

	public String getTextArray() {
		return textArray;
	}

	public class NodeComparator implements Comparator<Node> {

		@Override
		public int compare(Node o1, Node o2) {
			return o1.getId() - o2.getId();
		}

	}

	

	public void AddData() {
		String[] gr = textArray.split("\n");
		int length = gr.length;
		for (var item : gr) {
			if (item.length() < 1)
				length -= 1;
		}
		for (int i = 0; i < length; i++) {
			Node node = new Node(i);
			nodes.add(node);
		}
		arr = new double[length][length];
		int n = 0;
		for (String item : gr) {
			if (item.length() < 1)
				continue;
			String[] gr2 = item.trim().split("\\s+");
			for (int i = 0; i < length; i++) {
				arr[n][i] = Double.parseDouble(gr2[i].trim());
			}
			n++;
		}
		for (int i = 0; i < length; i++) {
			for (int j = 0; j < length; j++) {
				if (arr[i][j] != 0) {
					Edge edge = new Edge(GetNodeById(j), GetNodeById(i), arr[i][j]);
					if (checkEdge(edge))
						edges.add(edge);
				}
			}
		}

		SetLocation();
	}

	private void SetLocation() {
		setSource(GetNodeById(Integer.parseInt(textSource)));
		setDestination(GetNodeById(Integer.parseInt(textDestination)));
		getSource().setCoord(4100, 1300);
		int x = 0;
		int y = 0;
		if (getNodes().size() < 10) {
			x = 200;
			y = 100;
		} else if (getNodes().size() < 16) {
			x = 200;
			y = 200;
		}
		PriorityQueue<Node> unchecked = new PriorityQueue<Node>(getNodes().size(), new NodeComparator());
		unchecked.add(source);
		while (!unchecked.isEmpty()) {
			Node node = unchecked.poll();
			List<Node> list = GetFriendNode(node);
			int x2 = 0;
			for (Node node2 : list) {
				if (node.getX() != 0 && node.getY() != 0 && node2.getX() != 0 && node2.getY() != 0) {
					continue;
				}
				if (node2.getX() != 0 || node2.getY() != 0) {
					node.setCoord(node2.getX() + x, node2.getY() - y + y * x2);
				} else {
					node2.setCoord(node.getX() + x, node.getY() - y + y * x2);
					x2++;
					unchecked.add(node2);
				}

			}
			unchecked.remove(node);
		}
	}

	public List<Node> setPathFromString(String text) {

		List<Node> rs = new ArrayList<>();
		String[] gr = text.split("\n");
		String[] item = gr[0].trim().split("\\s+");
		for (String s : item) {
			int tmp = Integer.parseInt(s.trim());
			Node node = GetNodeById(tmp);
			rs.add(node);
		}
		setSolved(true);
		return rs;
	}

	public boolean checkEdge(Edge edge) {
		for (Edge edge2 : edges) {
			if ((edge2.getStart() == edge.getStart() && edge2.getEnd() == edge.getEnd())
					|| (edge2.getEnd() == edge.getStart() && edge2.getStart() == edge.getEnd()))
				return false;
		}
		return true;
	}

	

	private double[][] handleData(String text) {
		String[] gr = text.trim().split("\n");
		int length = gr.length;
		double[][] arr = new double[length][length];
		int n = 0;
		for (String item : gr) {
			String[] gr2 = item.trim().split("\\s+");
			for (int i = 0; i < length; i++) {
				arr[n][i] = Double.parseDouble(gr2[i].trim());
			}
			n++;
		}
		return arr;
	}

	private boolean checkAdjacencyMatrix(double[][] arr) {
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr.length; j++) {
				if (arr[i][j] != arr[j][i]) {
					return false;
				}
				if (i == j && arr[i][j] != 0)
					return false;
			}
		}
		return true;
	}

	private boolean checkHasString(String text) {
		String[] gr = text.trim().split("\n");
		for (var s : gr) {
			String[] tmp = s.trim().split("\\s+");
			for (var item : tmp) {
				if (!item.trim().matches("\\d+"))
					return false;
			}
		}
		return true;
	}

	private boolean checkSize(String text) {
		String[] gr = text.trim().split("\n");
		for (var item : gr) {
			String[] tmp = item.trim().split("\\s+");
			if (tmp.length != gr.length)
				return false;
		}
		return true;
	}

	private boolean checkSAndD(String text) {
		String[] gr = textArray.split("\n");
		int length = gr.length;
		for (var item : gr) {
			if (item.length() < 1)
				length -= 1;
		}
		int s = Integer.parseInt(text);
		if (s < 0 || s > length - 1)
			return false;
		return true;
	}

	public void CheckCondition() {
		if (textArray.length() < 1)
			throw new IllegalStateException("Vui lòng nhập ma trận");
		if (textSource.length() < 1)
			throw new IllegalStateException("Vui lòng nhập nguồn");
		if (textDestination.length() < 1)
			throw new IllegalStateException("Vui lòng nhập đích");
		if (!textSource.matches("\\d+"))
			throw new IllegalStateException("Nguồn nhập vào không phải là số");

		if (!textDestination.matches("\\d+"))
			throw new IllegalStateException("Đích nhập vào không phải là số");

		if (!checkSAndD(textSource))
			throw new IllegalStateException("Nguồn nhập vào vượt ngưỡng");

		if (!checkSAndD(textDestination))
			throw new IllegalStateException("Đích nhập vào vượt ngưỡng");

		if (!checkSize(textArray))
			throw new IllegalStateException("Ma trận nhập vào không phải ma trận vuông");

		if (!checkHasString(textArray))
			throw new IllegalStateException("Ma trận nhập vào không phải là số");

		double[][] data = handleData(textArray);
		if (!checkAdjacencyMatrix(data))
			throw new IllegalStateException("Ma trận nhập vào không phải ma trận kề");
	}

	public String Result(String text) {
		String[] gr = text.split("\n");
		String rs = "Đường đi ngắn nhất từ " + textSource + "->" + textDestination + "\n"
				+ gr[0].trim().replaceAll("\\s+", "->");
		String tmp = "Bảng chỉ đường\n";
		for (int i = 1; i < gr.length; i++) {
			tmp += gr[i].trim().replaceAll("\\s+", "->") + "\n";
		}
		rs += "\n" + tmp;
		return rs;
	}
}
