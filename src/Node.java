import java.util.HashMap;
import java.util.Map;

/**
 * The Node class for the Dijkstra Algorithm
 * @author fabioanzola
 */
public class Node {

    /**
     * The parent node of this Node
     */
    Node parent;

    /**
     * The id for the Node (Name)
     */
    String id;

    /**
     * Stores the distance (current best distance) for the Node
     */
    int bestDistance = Integer.MAX_VALUE;

    /**
     * Stores to whom the Node is connected to
     * (Connected to which other Nodes)
     */
    Map<Integer, Node> links = new HashMap<>();

    /**
     * Checked and done (finished) Node
     */
    boolean checked;

    /**
     * Constructor for all parameters of the Node
     *
     * @param id The id for the Node
     * @param bestDistance The best distance for the Node
     * @param links The Map of other nodes which this Node is connected to
     * @param checked If this node is already checked (finished)
     */
    public Node(String id, int bestDistance, Map<Integer, Node> links, boolean checked) {
        this.id = id;
        this.bestDistance = bestDistance;
        this.links = links;
        this.checked = checked;
    }

    /**
     * Constructor only to set the id
     *
     * @param id The id for the Node
     */
    public Node(String id) {
        this.id = id;
    }

    /**
     * Constructor for empty Node
     */
    public Node() {
    }
}
