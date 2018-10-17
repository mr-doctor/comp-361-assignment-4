
import java.util.*;

class MST {

	public static ArrayList<Edge> prims(List<List<Edge>> G) {
		ArrayList<Edge> tree = new ArrayList<>();
		PriorityQueue<Edge> queue = new PriorityQueue<>((Object o1, Object o2) -> {
			Edge first = (Edge) o1;
			Edge second = (Edge) o2;

			return Double.compare(first.weight, second.weight);
		});

		queue.addAll(G.get(0));

		boolean[] visited = new boolean[G.size()];
		visited[0] = true;

		while (!queue.isEmpty()) {
			Edge e = queue.peek();

			queue.poll();
			if (visited[e.origin] && visited[e.destination]) {
				continue;
			}
			visited[e.origin] = true;
			for (Edge edge : G.get(e.destination)) {
				if (!visited[edge.destination]) {
					queue.add(edge);
				}
			}

			visited[e.destination] = true;
			tree.add(e);
		}
		return tree;
	}

	public static List<List<Edge>> createGraph(List<Edge> edges) {
		List<List<Edge>> graph = new ArrayList<>();
		int length = edges.size() * 2;
		for (int i = 0; i < length; i++) {
			graph.add(new ArrayList<>());
		}

		for (Edge e : edges) {
			Edge other = new Edge(e.destination, e.origin, e.weight);
			graph.get(e.origin).add(e);
			graph.get(e.destination).add(other);
		}

		return graph;
	}
}

class Edge {
	final int origin;
	final int destination;
	final int weight;

	Edge(int o, int d, int w) {
		this.origin = o;
		this.destination = d;
		this.weight = w;
	}
}