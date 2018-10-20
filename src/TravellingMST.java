import java.util.ArrayList;
import java.util.List;

public class TravellingMST {

	public TravellingMST() {
		TSPHandler t = new TSPHandler("brazil58.tsp");

		List<List<TSPNode>> map = t.getMap();
		List<Edge> edges = new ArrayList<>();

		for (List<TSPNode> row : map) {
			for (TSPNode node : row) {
				edges.add(new Edge(node.getX(), node.getY(), node.getWeight()));
			}
		}

		List<List<Edge>> graph = MST.createGraph(edges);
		List<Edge> mst = MST.prims(graph);

		List<Edge> traversal = new Traverser(mst).traverse();

		System.out.println("Traversal {");
		for (Edge edge : traversal) {
			System.out.print("	");
			System.out.println(mst.indexOf(edge));
		}
		System.out.println("}");

		/*System.out.println("MST {");
		for (Edge e : mst) {
			System.out.println("	" + e);
		}
		System.out.println("}");*/
	}

	public static void main(String[] args) {
		TravellingMST t = new TravellingMST();
	}
}