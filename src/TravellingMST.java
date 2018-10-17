import java.util.ArrayList;
import java.util.List;

public class TravellingMST {

	public TravellingMST() {
		TSPHandler t = new TSPHandler("brazil58.tsp");

		int vertices = 0;
		List<List<TSPNode>> map = t.getMap();
		for (List<TSPNode> row : map) {
			vertices += row.size();
		}

		List<Edge> edges = new ArrayList<>();

		for (List<TSPNode> row : map) {
			for (TSPNode node : row) {
				edges.add(new Edge(node.getX(), node.getY(), node.getWeight()));
			}
		}

		List<List<Edge>> graph = MST.createGraph(edges);
		ArrayList<Edge> mst = MST.prims(graph);

		System.out.println("MST {");
		for (Edge e : mst) {
			System.out.println("	" + e.origin + "	-	" + e.destination + ": 	" + e.weight);
		}
		System.out.println("}");
	}

	public static void main(String[] args) {
		TravellingMST t = new TravellingMST();
	}
}