import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TSPHandler {

	private List<List<TSPEdge>> table = new ArrayList<>();
	private int dimension;

	private boolean isEdgeWeight = false;



	private List<TSPNode> nodes = new ArrayList<>();

	public TSPHandler(String filename) {
		loadFile(filename);
	}

	private void loadFile(String filename) {
		File file = new File(getClass().getResource("/" + filename).getFile());
		if (!getFileExtension(file).equals(".tsp")) {
			throw new IllegalArgumentException("Requires a file in .tsp format");
		}

		try {
			parseFile(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void parseFile(File file) throws FileNotFoundException {
		Scanner s = new Scanner(file);
		String line = "";
		String textSoFar = "";
		while (s.hasNextLine()) {
			line = s.nextLine();
			if (!Character.isLetter(line.charAt(0))) {
				break;
			}
			if (line.equals("DIMENSION")) {
				this.dimension = s.nextInt();
			}
			textSoFar += line;
		}

		if (textSoFar.contains("EDGE_WEIGHT_SECTION")) {
			isEdgeWeight = true;
		} else if (textSoFar.contains("NODE_COORD_SECTION")) {
			isEdgeWeight = false;
		} else {
			throw new RuntimeException("Unsupported node data type");
		}

		int x;
		int y = 0;
		while(!line.equals("EOF")) {
			if (isEdgeWeight) {
				x = 0;
				table.add(new ArrayList<>());
				for (String value : line.split(" ")) {
					table.get(table.size() - 1).add(new TSPEdge(Integer.parseInt(value), x, y));
					x++;
				}
				y++;
			} else {
				String[] split = line.split(" ");
				split = clean(split);
				nodes.add(new TSPNode(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2])));
			}
			line = s.nextLine();
		}

		if (!isEdgeWeight) {
			for(TSPNode node1 : nodes) {
				x = 0;
				table.add(new ArrayList<>());
				for(TSPNode node2 : nodes) {
					if (node1.equals(node2)) {
						continue;
					}
					table.get(table.size() - 1).add(new TSPEdge(node1.weightTo(node2), x, y));
					x++;
				}
				y++;
			}
		}
	}
	public List<TSPNode> getNodes() {
		return nodes;
	}
	private static String[] clean(String[] split) {
		List<String> cleaned = new ArrayList<>();
		for (String s : split) {
			if (isNumeric(s)) {
				cleaned.add(s);
			}
		}
		return cleaned.toArray(new String[0]);
	}

	public static boolean isNumeric(String str) {
		return str.matches("-?\\d+(\\.\\d+)?");
	}

	private static String getFileExtension(File file) {
		String name = file.getName();
		int lastIndexOf = name.lastIndexOf(".");
		if (lastIndexOf == -1) {
			return "";
		}
		return name.substring(lastIndexOf);
	}

	public List<List<TSPEdge>> getTable() {
		return table;
	}

	public int distanceBetween(int x, int y) {
		return table.get(y).get(x).getWeight();
	}

	public int getDimension() {
		return dimension;
	}

	static void print(String[] arr) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("[");
		for(String s : arr) {
			stringBuilder.append(s);
			stringBuilder.append(", ");
		}
		stringBuilder.append("]");
		System.out.println(stringBuilder.toString());
	}
}

class TSPEdge {
	private final int x;
	private final int y;
	private final int weight;

	public TSPEdge(int weight, int x, int y) {
		this.x = x;
		this.y = y;
		this.weight = weight;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWeight() {
		return weight;
	}

	@Override
	public String toString() {
		return "[" + getX() + "," + getY() + "]";
	}
}

class TSPNode {
	private final int x;
	private final int y;
	private final int id;

	public TSPNode(int id, int x, int y) {
		this.x = x;
		this.y = y;
		this.id = id;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getID() {
		return id;
	}

	@Override
	public String toString() {
		return "[" + getX() + "," + getY() + "]";
	}

	public int weightTo(TSPNode node2) {
		return (int) Math.sqrt(Math.pow(this.x - node2.x, 2) + Math.pow(this.y - node2.y, 2));
	}
}