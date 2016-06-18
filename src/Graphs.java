import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class Graphs {
	private HashMap<Integer, ArrayList<Integer>> adjListMap;
	private boolean[][] reachabilityMatrix;
	private int maxNumVertices;

	/**
	 * Constructor to initialize the size of the graph and private variables.
	 * 
	 * @param maxNumVertices
	 *            - Maximum number of vertices in the graph.
	 */
	public Graphs(int maxNumVertices) {
		this.maxNumVertices = maxNumVertices + 1;
		this.adjListMap = new HashMap<Integer, ArrayList<Integer>>();
		for (int i = 0; i < this.maxNumVertices; i++) {
			adjListMap.put(i, new ArrayList<Integer>());
		}
		// Reachability matrix so that isLinked() look ups are O(1)
		this.reachabilityMatrix = new boolean[this.maxNumVertices + 1][this.maxNumVertices + 1];
	}

	/**
	 * Adds a bi-directional link from vertexA to vertexB
	 * 
	 * @param vertexA
	 *            - Graph vertex A
	 * @param vertexB
	 *            - Graph vertex B
	 */
	public void addLink(int vertexA, int vertexB) {
		try {
			// Link from A to B
			ArrayList<Integer> neighbors = this.adjListMap.get(vertexA);
			neighbors.add(vertexB);
			this.adjListMap.put(vertexA, neighbors);

			// Link from B to A
			neighbors = this.adjListMap.get(vertexB);
			neighbors.add(vertexA);
			this.adjListMap.put(vertexB, neighbors);

			// Update reachability matrix on every add link
			buildReachabilityMatrix(this.adjListMap);

		} catch (NullPointerException e) {
			System.out.println("Vertices not in range!. It should be <" + this.maxNumVertices);
		}

	}

	/**
	 * Remove a bi-directional link between vertex A and B
	 * 
	 * @param vertexA
	 *            - Graph vertex A
	 * @param vertexB
	 *            - Graph vertex b
	 */
	public void removeLink(int vertexA, int vertexB) {
		try {
			// Remove link from A to B
			ArrayList<Integer> neighbors = this.adjListMap.get(vertexA);
			neighbors.remove((Integer) vertexB);
			this.adjListMap.put(vertexA, neighbors);

			// Remove a link from B to A
			neighbors = this.adjListMap.get(vertexB);
			neighbors.remove((Integer) vertexA);
			this.adjListMap.put(vertexB, neighbors);

			// Update reachability matrix on every link removal
			buildReachabilityMatrix(this.adjListMap);
		} catch (NullPointerException e) {
			System.out.println("Vertices are out of range. It should be < " + this.maxNumVertices);
		}

	}

	/**
	 * Return whether there is a path between vertex A and vertex B.
	 * 
	 * @param vertexA
	 *            - Graph vertex A
	 * @param vertexB
	 *            - Graph vertex B
	 * @return true if there is a path between A and B
	 */
	public boolean isLinked(int vertexA, int vertexB) {
		boolean result = false;
		try {
			result = this.reachabilityMatrix[vertexA][vertexB];
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Vertices are out of range. It should be < " + this.maxNumVertices);
		}
		return result;
	}

	/**
	 * This method builds the reachability matrix by performing breadth first
	 * search for every pair of nodes
	 * 
	 * @param adjMapList
	 *            - Adjacency list for the graphs.
	 */
	public void buildReachabilityMatrix(HashMap<Integer, ArrayList<Integer>> adjMapList) {
		for (int i = 0; i < this.maxNumVertices; i++) {
			for (int j = 0; j < this.maxNumVertices; j++) {
				this.reachabilityMatrix[i][j] = breadthFirstSearch(i, j, adjMapList);
			}
		}
	}

	/**
	 * This method performs breadth first search from vertex A to vertex B.
	 * 
	 * @param vertexA
	 *            - Graph vertex A
	 * @param vertexB
	 *            - Graph vertex B
	 * @param adjMapList
	 *            - Adjacency List for the graph
	 * @return true if there is a path, false otherwise
	 */
	public boolean breadthFirstSearch(int vertexA, int vertexB, HashMap<Integer, ArrayList<Integer>> adjMapList) {
		Queue<Integer> queue = new LinkedList<Integer>();
		HashSet<Integer> visited = new HashSet<Integer>();
		queue.add(vertexA);
		visited.add(vertexA);
		while (!queue.isEmpty()) {
			Integer curr = queue.remove();
			if (curr == vertexB) {
				return true;
			}
			ArrayList<Integer> neighbors = adjMapList.get(curr);
			for (Integer vertex : neighbors) {
				if (!visited.contains(vertex)) {
					visited.add(vertex);
					queue.add(vertex);
				}
			}
		}
		return false;
	}
    
	/**
	 * Overridden toString method for debugging purposes. We use this to examine the contents of the reachability matrix.
	 */
	@Override
	public String toString() {
		String out = new String();
		for (int i = 0; i < this.maxNumVertices; i++) {
			for (int j = 0; j < this.maxNumVertices; j++) {
				out = out + this.reachabilityMatrix[i][j] + " ";
			}
			out = out + "\n";
		}
		return out;
	}

}