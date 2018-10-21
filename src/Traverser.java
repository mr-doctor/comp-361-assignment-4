import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

class Traverser {

	private final List<Edge> graph;
	private List<Edge> edges = new ArrayList<>();

	public Traverser(List<Edge> graph) {
		this.graph = graph;
	}

	List<Edge> traverse() {

		addNextEdge(0);

		return edges;
	}

	private void addNextEdge(int node) {

		if (node >= graph.size()) {
			return;
		}

		edges.add(graph.get(node));

		List<Edge> children = getChildren(node);

		if (children.size() == 0) {
			return;
		}

		for (Edge child : children) {
			addNextEdge(child.destination);
		}
	}

	private List<Edge> getChildren(int parent) {
		List<Edge> children = new ArrayList<>();

		for (Edge e : graph) {
			if (e.origin == parent) {
				children.add(e);
			}
		}
		return children;
	}

	Edge getEdge(int origin, int destination) {
		for (Edge e : graph) {
			if (e.origin == origin && e.destination == destination) {
				return e;
			}
		}
		return null;
	}

}
