import java.util.ArrayList;
import java.util.List;

public class TravellingAnnealing {

	public TravellingAnnealing() {
		TSPHandler t = new TSPHandler("a280.tsp");

		List<TSPNode> nodes = t.getNodes();

		List<TSPNode> traversal = anneal(new ArrayList<>(nodes));

		/*System.out.println("Anneal {");
		for (TSPNode edge : traversal) {
			System.out.println(edge.getID());
		}
		System.out.println("	Total Weight: " + getWeight(traversal));
		System.out.println("}");*/
	}

	private int getWeight(List<TSPNode> list) {
		int weight = 0;
		for (int i = 0; i < list.size() - 1; i++) {
			weight += list.get(i).weightTo(list.get(i + 1));
		}
		return weight;
	}

	public double acceptP(int oldW, int newW, double T) {
		if (newW < oldW) {
			return 1.0;
		}
		return Math.exp(((float) oldW - (float) newW) / T);
	}

	public List<TSPNode> anneal(ArrayList<TSPNode> input) {
		double T = 10000;
		double rate = 0.002;
		TSPWalk current = new TSPWalk(input, true);
		TSPWalk best = new TSPWalk(current.getWalk(), false);

		while (T > 1) {

			TSPWalk potential = new TSPWalk(current.getWalk(), false);

			int index1 = (int) (Math.random() * potential.walkSize());
			int index2 = (int) (Math.random() * potential.walkSize());

			potential.swap(index1, index2);

			int currentWeight = current.getWeight();
			int potentialWeight = potential.getWeight();

			if (acceptP(currentWeight, potentialWeight, T) > Math.random()) {
				current = new TSPWalk(potential.getWalk(), false);
			}

			if (current.getWeight() < best.getWeight()) {
				best = new TSPWalk(current.getWalk(), false);
			}
			System.out.println(best.getWeight());
			T *= 1.0 - rate;
		}
		return best.getWalk();
	}

	public static void main(String[] args) {
		TravellingAnnealing t = new TravellingAnnealing();
	}

}
