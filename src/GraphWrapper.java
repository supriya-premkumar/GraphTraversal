import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author supriya This class implements a wrapper function to perform graph
 *         traversals.
 */
public class GraphWrapper {
	private Graphs graph;
	private File commandFile;
	private int maxNumVertices;
	private Pattern pattern;
	private static final String commandPattern = "^(.+) +(\\d+) +(\\d+)$";

	/**
	 * Initialize private member variables.
	 * 
	 * @param maxNumVertices
	 *            - Maximum number of vertices in the graph
	 * @param filePath
	 *            - File Path for input command file.
	 */
	public GraphWrapper(int maxNumVertices, String filePath) {
		this.maxNumVertices = maxNumVertices;
		this.commandFile = Paths.get(filePath).toFile();
		this.pattern = Pattern.compile(this.commandPattern);
		this.graph = new Graphs(this.maxNumVertices);
	}

	/**
	 * This method reads the input command file. Parses them for valid commands
	 * and executes them. This also ignores illegally formatted commands in the
	 * input file.
	 */
	public void run() {
		try (BufferedReader in = new BufferedReader(new FileReader(this.commandFile))) {
			String cmd;
			while ((cmd = in.readLine()) != null) {
				Matcher match = pattern.matcher(cmd);
				if (match.find()) {
					String command = match.group(1).trim();
					int vertexA = Integer.parseInt(match.group(2).trim());
					int vertexB = Integer.parseInt(match.group(3).trim());
					switch (command) {
					case "add":
						this.graph.addLink(vertexA, vertexB);
						break;
					case "remove":
						this.graph.removeLink(vertexA, vertexB);
						break;
					case "is linked":
						System.out.println(this.graph.isLinked(vertexA, vertexB));
						break;
					default:
						System.out.println("Illegal command. Continuing to the next command ...");
						continue;
					}
				}

			}
		} catch (FileNotFoundException e) {
			System.out.println("Cound not find the file");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Something went wrong!");
			e.printStackTrace();
		}
	}

}