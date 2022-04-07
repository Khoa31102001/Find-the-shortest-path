package model;

public class Edge {

	private Node start;
	private Node end;
	private double distance = 1.0;

	public Edge(Node start, Node end, Double distance) {
		super();
		this.start = start;
		this.end = end;
		this.distance = distance;
	}

	public Node getStart() {
		return start;
	}

	public void setStart(Node start) {
		this.start = start;
	}

	public Node getEnd() {
		return end;
	}

	public Edge(Node start, Node end) {
		super();
		this.start = start;
		this.end = end;
	}

	public void setEnd(Node end) {
		this.end = end;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public boolean hasNode(Node node) {
		return start == node || end == node;
	}

	public Node contactNode(Node node) {
		return node == start ? end : start;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Edge))
			return false;
		Edge that = (Edge) obj;
		return (start == that.start && end == that.end) || (start == that.end && end == that.start);
	}

	@Override
	public String toString() {
		return start.toString() + " đến " + end.toString();
	}
}
