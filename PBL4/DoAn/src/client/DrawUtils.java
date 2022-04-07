package client;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import model.Edge;
import model.Node;

public class DrawUtils {
	private Graphics2D g;
	private static int radius = 20;

	public DrawUtils(Graphics2D graphics2d) {
		this.g = graphics2d;
	}

	public static Color parseColor(String colorStr) {
		return new Color(Integer.valueOf(colorStr.substring(1, 3), 16), Integer.valueOf(colorStr.substring(3, 5), 16),
				Integer.valueOf(colorStr.substring(5, 7), 16));
	}

	public void drawNode(Node node) {
		g.setColor(parseColor("#9C27B0"));
		g.fillOval(node.getX() - radius, node.getY() - radius, 2 * radius, 2 * radius);

		radius -= 5;
		g.setColor(parseColor("#E1BEE7"));
		g.fillOval(node.getX() - radius, node.getY() - radius, 2 * radius, 2 * radius);

		radius += 5;
		g.setColor(parseColor("#9C27B0"));
		g.drawString("" + node.getId(), node.getX() - 4, node.getY() + 4);
	}

	public void drawSourceNode(Node node) {
		g.setColor(parseColor("#00BCD4"));
		g.fillOval(node.getX() - radius, node.getY() - radius, 2 * radius, 2 * radius);

		radius -= 5;
		g.setColor(parseColor("#B2EBF2"));
		g.fillOval(node.getX() - radius, node.getY() - radius, 2 * radius, 2 * radius);

		radius += 5;
		g.setColor(parseColor("#00BCD4"));
		g.drawString("" + node.getId(), node.getX() - 4, node.getY() + 4);
	}

	public void drawDestinationNode(Node node) {
		g.setColor(parseColor("#F44336"));
		g.fillOval(node.getX() - radius, node.getY() - radius, 2 * radius, 2 * radius);

		radius -= 5;
		g.setColor(parseColor("#FFCDD2"));
		g.fillOval(node.getX() - radius, node.getY() - radius, 2 * radius, 2 * radius);

		radius += 5;
		g.setColor(parseColor("#F44336"));
		g.drawString("" + node.getId(), node.getX() - 4, node.getY() + 4);
	}

	public void drawHaloNode(Node node) {
		g.setColor(parseColor("#E91E63"));
		radius += 5;
		g.fillOval(node.getX() - radius, node.getY() - radius, 2 * radius, 2 * radius);
		radius -= 5;
	}

	public void drawEdge(Edge edge) {
		g.setColor(Color.GRAY);
		drawLineEdge(edge);
		drawNodeEdge(edge);
	}

	public void drawLineEdge(Edge edge) {
		Point from = edge.getStart().getCoord();
		Point to = edge.getEnd().getCoord();
		g.setStroke(new BasicStroke(3));
		g.drawLine((int) from.getX(), (int) from.getY(), (int) to.getX(), (int) to.getY());

	}

	public void drawNodeEdge(Edge edge) {
		int x = (int) (edge.getStart().getCoord().getX() + edge.getEnd().getCoord().getX()) / 2;
		int y = (int) (edge.getStart().getCoord().getY() + edge.getEnd().getCoord().getY()) / 2;
		int rad = radius / 2;
		g.fillOval(x - rad, y - rad, 2 * rad, 2 * rad);
		g.setColor(Color.WHITE);
		g.drawString("" + (int) edge.getDistance(), x - 4, y + 5);
	}

	public void drawHaloEdge(Edge edge) {
		g.setColor(Color.GREEN);
		Point from = edge.getStart().getCoord();
		Point to = edge.getEnd().getCoord();
		g.setStroke(new BasicStroke(8));
		g.drawLine(from.x, from.y, to.x, to.y);
	}

	public static boolean choseNode(MouseEvent e, Point point) {
		int x = (int) e.getX();
		int y = (int) e.getY();

		int NodeX = (int) point.getX();
		int NodeY = (int) point.getY();
		return (x <= NodeX + radius && x >= NodeX - radius) && (y <= NodeY + radius && y >= NodeY - radius);

	}	

	public void drawPath(List<Node> path) {
		List<Edge> edges = new ArrayList<>();
		for (int i = 0; i < path.size() - 1; i++) {
			edges.add(new Edge(path.get(i), path.get(i + 1)));
		}
		for (Edge item : edges) {
			g.setColor(parseColor("#E1BEE7"));
			drawHaloEdge(item);
		}

	}
}
