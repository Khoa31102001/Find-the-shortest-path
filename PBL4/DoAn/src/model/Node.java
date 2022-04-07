package model;

import java.awt.Point;
import java.util.List;

public class Node {
	private Point coord = new Point();
	private int id;
	private List<Node> path;

	public Node(Point coord, int id) {
		super();
		this.coord = coord;
		this.id = id;
	}

	public Node(int id) {
		this.id = id;
	}

	public Node(Point coord) {
		this.coord = coord;
	}

	public Point getCoord() {
		return coord;
	}

	public void setCoord(int x, int y) {
		this.coord.setLocation(x, y);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<Node> getPath() {
		return path;
	}

	public void setPath(List<Node> path) {
		this.path = path;
	}

	public int getX() {
		return (int) coord.getX();
	}

	public int getY() {
		return (int) coord.getY();
	}

	@Override
	public String toString() {
		return "Node " + id;
	}
}
