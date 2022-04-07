package server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class algo {
	public static String handle(double graph[][], int source, int destination) {

		for (int i = 0; i < graph.length; i++) {
			for (int j = 0; j < graph.length; j++) {
				if (i != j && graph[i][j] == 0)
					graph[i][j] = Double.POSITIVE_INFINITY;
			}
		}
		Integer Top[] = new Integer[graph.length];
		boolean visited[] = new boolean[graph.length];
		Top[source] = source;
		while (true) {

			Integer choose = null;
			for (int i = 0; i < Top.length; i++) {
				if (visited[i] == true || Top[i] == null)
					continue;
				if (choose == null) {
					choose = i;
				} else if (graph[choose][source] > graph[i][source]) {
					choose = i;
				}
			}

			if (choose == null)
				break;
			visited[choose] = true;
			for (int i = 0; i < Top.length; i++) {
				if (visited[i] == true)
					continue;
				if (Top[i] == null && graph[i][choose] != Double.POSITIVE_INFINITY) {
					Top[i] = choose;
					graph[i][source] = graph[source][i] = graph[choose][source] + graph[i][choose];
				} else if (Top[i] != null && graph[choose][source] + graph[choose][i] < graph[i][source]) {
					Top[i] = choose;
					graph[i][source] = graph[source][i] = graph[choose][source] + graph[choose][i];
				}
			}
		}
		List<List<Integer>> list = new ArrayList<List<Integer>>();
		for (int i = 0; i < Top.length; i++) {
			List<Integer> l = new ArrayList<Integer>();
			Integer end = i;

			do {
				l.add(end);
				end = Top[end];
			} while (end != null && end != source);
			if (end == null)
				continue;
			l.add(source);
			Collections.reverse(l);
			list.add(l);
		}
		String rs = "";
		for (var item : list) {
			if (item.get(item.size() - 1).equals(destination)) {
				for (var itemm : item) {
					rs += " " + itemm;
				}
			}
		}

		for (var item : list) {
			rs += "\n";
			for (var itemm : item) {
				rs += " " + itemm;
			}
		}
		return rs;

	}
}
