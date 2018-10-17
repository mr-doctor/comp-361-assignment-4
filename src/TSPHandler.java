import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TSPHandler {

	private List<List<TSPNode>> map = new ArrayList<>();
	private int dimension;

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
		while (s.hasNextLine()) {
			line = s.nextLine();
			if (line.equals("EDGE_WEIGHT_SECTION")) {
				break;
			}
			if (line.equals("DIMENSION")) {
				this.dimension = s.nextInt();
			}
		}
		line = s.nextLine();
		int x;
		int y = 0;
		while(!line.equals("EOF")) {
			x = 0;
			map.add(new ArrayList<>());
			for (String value : line.split(" ")) {
				map.get(map.size() - 1).add(new TSPNode(Integer.parseInt(value), x, y));
				x++;
			}
			y++;
			line = s.nextLine();
		}
	}

	private static String getFileExtension(File file) {
		String name = file.getName();
		int lastIndexOf = name.lastIndexOf(".");
		if (lastIndexOf == -1) {
			return "";
		}
		return name.substring(lastIndexOf);
	}

	public List<List<TSPNode>> getMap() {
		return map;
	}

	public int distanceBetween(int x, int y) {
		return map.get(y).get(x).getWeight();
	}

	public int getDimension() {
		return dimension;
	}
}

class TSPNode {
	private final int x;
	private final int y;
	private final int weight;

	public TSPNode(int weight, int x, int y) {
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