import java.util.ArrayList;
import java.util.List;

public class TravellingMST {

	public TravellingMST(String filename) {
		TSPHandler t = new TSPHandler(filename);

		List<List<TSPEdge>> map = t.getTable();
		List<Edge> edges = new ArrayList<>();

		for (List<TSPEdge> row : map) {
			for (TSPEdge node : row) {
				edges.add(new Edge(node.getX(), node.getY(), node.getWeight()));
			}
		}

		List<List<Edge>> graph = MST.createGraph(edges);
		List<Edge> mst = MST.prims(graph);

		Traverser traverser = new Traverser(mst);

		List<Edge> traversal = traverser.traverse();

		int net = 0;
		System.out.println("Traversal {");
		for (Edge edge : traversal) {
			System.out.print("	");
			System.out.println(mst.indexOf(edge));
			net+=edge.weight;
		}
		System.out.println("	" + 0);
		System.out.println("	Total Weight: " + net);
		System.out.println("}");
	}

	public static void main(String[] args) {
		TravellingMST t = new TravellingMST("st70.tsp");
		t = new TravellingMST("brazil58.tsp");
		t = new TravellingMST("a280.tsp");
	}
}