package client;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;

import javax.swing.JPanel;

import model.Edge;
import model.Graph;
import model.Node;

public class GraphPanel extends JPanel implements MouseListener, MouseMotionListener {
	private DrawUtils drawUtils;
	private Graph graph;
	private Node selectedNode = null;
	private Node hoveredNode = null;
	private Edge hoveredEdge = null;
	private Point cursor;
	private List<Node> path = null;

	public GraphPanel(Graph graph) {
		this.graph = graph;
		addMouseListener(this);
		addMouseMotionListener(this);
	}

	public void setPath(List<Node> Path) {
		this.path = Path;
		hoveredEdge = null;
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D graphics2d = (Graphics2D) g;
		graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		graphics2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		drawUtils = new DrawUtils(graphics2d);

		if (graph.isSolved()) {

			drawUtils.drawPath(path);
		}
		for (Edge edge : graph.getEdges()) {
			if (edge == hoveredEdge)
				drawUtils.drawHaloEdge(edge);
			drawUtils.drawEdge(edge);
		}
		for (Node node : graph.getNodes()) {
			if (node == selectedNode || node == hoveredNode)
				drawUtils.drawHaloNode(node);
			if (graph.isSource(node))
				drawUtils.drawSourceNode(node);
			else if (graph.isDestination(node))
				drawUtils.drawDestinationNode(node);
			else
				drawUtils.drawNode(node);
		}
	}

	public void reset() {
		graph.clear();
		selectedNode = null;
		hoveredNode = null;
		hoveredEdge = null;
		repaint();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		hoveredNode = null;
		for (Node node : graph.getNodes()) {
			if (selectedNode == null && DrawUtils.choseNode(e, node.getCoord()))
				selectedNode = node;
			else if (DrawUtils.choseNode(e, node.getCoord()))
				hoveredNode = node;
		}
		if (selectedNode != null) {
			if (e.isControlDown()) {
				selectedNode.setCoord(e.getX(), e.getY());
				cursor = null;
				repaint();
				return;
			}
			cursor = new Point(e.getX(), e.getY());
			repaint();

		}

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		selectedNode = null;
		hoveredNode = null;
		repaint();

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
