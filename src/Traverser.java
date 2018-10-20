import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

class Traverser {

	private final List<Edge> mst;
	private List<Edge> edges = new ArrayList<>();

	public Traverser(List<Edge> mst) {
		this.mst = mst;
	}

	List<Edge> traverse() {

		addNextEdge(0);

		return edges;
	}

	private void addNextEdge(int node) {

		if (node >= mst.size()) {
			return;
		}

		edges.add(mst.get(node));

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

		for (Edge e : mst) {
			if (e.origin == parent) {
				children.add(e);
			}
		}
		return children;
	}

}
