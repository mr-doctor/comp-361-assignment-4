import java.util.ArrayList;
import java.util.Collections;

public class TSPWalk {
	private ArrayList walk;
	private int netWeight = 0;


	public TSPWalk(ArrayList walk, boolean shuffle){
		if (shuffle) {
			this.walk = walk;
			Collections.shuffle(this.walk);
		} else {
			this.walk = (ArrayList) walk.clone();
		}
	}

	public ArrayList getWalk(){
		return walk;
	}

	public int walkSize() {
		return walk.size();
	}

	public TSPNode getNode(int tourPosition) {
		return (TSPNode) walk.get(tourPosition);
	}

	public void setNode(int index, TSPNode node) {
		walk.set(index, node);
		netWeight = 0;
	}

	public int getWeight() {
		if (netWeight == 0) {
			int currentWeight = 0;
			for (int i = 0; i < walk.size(); i++) {

				TSPNode from = getNode(i);
				TSPNode to;

				if (i + 1 < walk.size()) {
					to = getNode(i + 1);
				} else {
					to = getNode(0);
				}

				currentWeight += from.weightTo(to);
			}

			netWeight = currentWeight;
		}
		return netWeight;
	}

	public void swap(int index1, int index2) {
		Collections.swap(walk, index1, index2);
	}
}
