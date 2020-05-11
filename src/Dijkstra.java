import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * The Dijkstra Algorithm class
 *
 * @author fabioanzola
 */
public class Dijkstra {

    /**
     * The network-graph
     */
    private Map<String, Node> graph;

    /**
     * The Node for the Algorithm to start from
     */
    private Node startNode;

    /**
     * Initializes the network
     */
    public void init() {
        this.graph = new HashMap<>();
    }

    /**
     * Gets the StartNode
     *
     * @return StartingNode
     */
    public Node getStartNode() {
        return startNode;
    }

    /**
     * Prompts the user to enter data for the network
     */
    public void readFromUser() {
        System.out.println("Number of nodes");
        Scanner sc = new Scanner(System.in);
        int nrNodes = sc.nextInt();
        for (int i = 0; i < nrNodes; i++) {
            System.out.println("Node name");
            String ndName = sc.next();
            this.graph.put(ndName, new Node(ndName));
        }

        for (int i = 0; i < this.graph.size(); i++) {
            System.out.println("How many connections " + this.graph.keySet().toArray()[i]);
            int conn = sc.nextInt();
            for (int j = 0; j < conn; j++) {
                System.out.println("Connection distance");
                int dist = sc.nextInt();
                System.out.println("Connected to");
                String name = sc.next();
                this.graph.get(this.graph.keySet().toArray()[i]).links.put(dist, this.graph.get(name));
            }
        }

    }

    /**
     * Loads prefabricated Network form the given link
     * (for Debugging)
     */
    public void loadTemplateNetwork() {
        //Loads this Network https://www.codingame.com/servlet/fileservlet?id=14497257275137
        String[] nodes = {"A", "B", "C", "D", "E"};
        for (String node : nodes) {
            this.graph.put(node, new Node(node));
        }
        this.graph.get("A").links.put(3, this.graph.get("B"));
        this.graph.get("A").links.put(1, this.graph.get("C"));

        this.graph.get("B").links.put(3, this.graph.get("A"));
        this.graph.get("B").links.put(7, this.graph.get("C"));
        this.graph.get("B").links.put(5, this.graph.get("D"));
        this.graph.get("B").links.put(1, this.graph.get("E"));

        this.graph.get("C").links.put(1, this.graph.get("A"));
        this.graph.get("C").links.put(7, this.graph.get("B"));
        this.graph.get("C").links.put(2, this.graph.get("D"));

        this.graph.get("D").links.put(2, this.graph.get("C"));
        this.graph.get("D").links.put(5, this.graph.get("B"));
        this.graph.get("D").links.put(7, this.graph.get("E"));

        this.graph.get("E").links.put(1, this.graph.get("B"));
        this.graph.get("E").links.put(7, this.graph.get("D"));
    }

    /**
     * Prints out the Network "diagram"
     * (for Debugging)
     */
    public void printNetwork() {
        for (int i = 0; i < this.graph.size(); i++) {
            System.out.println("Node " + this.graph.keySet().toArray()[i]);
            for (int j = 0; j < this.graph.get(this.graph.keySet().toArray()[i]).links.size(); j++) {
                System.out.println(this.graph.get(this.graph.keySet().toArray()[i]).links.keySet().toArray()[j] + "="
                        + this.graph.get(this.graph.keySet().toArray()[i]).links
                        .get(this.graph.get(this.graph.keySet().toArray()[i]).links.keySet().toArray()[j]).id);
            }
            System.out.println(this.graph.get(this.graph.keySet().toArray()[i]).bestDistance);
        }
    }

    /**
     * Sets the start-node from the Methods parameter
     *
     * @param startNode The id of the start-node
     */
    public void setStartNode(String startNode) {
        this.startNode = this.graph.get(startNode);
        this.startNode.bestDistance = 0;
        this.startNode.parent = new Node("/");
    }

    /**
     * User can set the start-node from prompt
     */
    public void setStartNodeFromUser() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Specify the start Node");
        this.startNode = this.graph.get(sc.next());
        this.startNode.bestDistance = 0;
        this.startNode.parent = new Node("/");
    }

    /**
     * The Dijkstra Algorithm calculations
     *
     * @param node The node to start from
     */
    public void calculateBestNodeDistances(Node node) {
        for (int i = 0; i < node.links.size(); i++) {
            Node nextNode = node.links.get(node.links.keySet().toArray()[i]);
            int calcDistance = node.bestDistance + Integer.parseInt(node.links.keySet().toArray()[i].toString());
            if (calcDistance < nextNode.bestDistance) {
                nextNode.bestDistance = calcDistance;
            }
        }
        node.checked = true;

        Node newNode = new Node();
        Node toCalculateNext = newNode;
        for (int i = 0; i < node.links.size(); i++) {
            if (node.links.get(node.links.keySet().toArray()[i]).bestDistance < toCalculateNext.bestDistance) {
                if (!node.links.get(node.links.keySet().toArray()[i]).checked) {
                    toCalculateNext = node.links.get(node.links.keySet().toArray()[i]);
                    toCalculateNext.parent = node;
                }
            }
        }
        if (!toCalculateNext.equals(newNode)) {
            calculateBestNodeDistances(toCalculateNext);
        }
    }

    /**
     * Prints the routing table for the network
     */
    public void printRoutingTable() {
        String parentNodeID;
        System.out.format("%11s%10s%11s%10s", "Node ID", "Visited", "Distance", "Parent ID");
        System.out.println();
        for (int i = 0; i < this.graph.size(); i++) {
            Node currentNode = this.graph.get(this.graph.keySet().toArray()[i]);
            try {
                parentNodeID = currentNode.parent.id;
            }
            catch (NullPointerException e) {
                parentNodeID = "404";
            }
            System.out.format("%10s%8s%10d%10s", "Node " + currentNode.id, currentNode.checked,
                    currentNode.bestDistance, parentNodeID);
            System.out.println();
        }
    }

    /**
     * Loads the Network from a file
     *
     * @param path The path to the Network-File
     * @throws IOException If an IO Error occurs
     */
    public void readFromFile(String path) throws IOException {
        List<String> readFile = Files.readAllLines(Paths.get(path));
        for (String s : readFile) {
            if (!s.startsWith("#")) {
                if (!s.startsWith("-")) {
                    String ndName = s.split(" ")[0];
                    this.graph.put(ndName, new Node(ndName));
                }
            }
        }
        Node currentNode = new Node();
        for (String s : readFile) {
            if (!s.startsWith("#")) {
                if (s.startsWith("-")) {

                    int dist = Integer.parseInt(s.split(" ")[1]);
                    Node goingTo = this.graph.get(s.split(" ")[2]);
                    currentNode.links.put(dist, goingTo);
                } else {
                    currentNode = this.graph.get(s);
                }
            }
        }
    }

}
