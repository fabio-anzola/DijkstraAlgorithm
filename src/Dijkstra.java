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
        System.out.print("Number of nodes: ");
        Scanner sc = new Scanner(System.in);
        int nrNodes = sc.nextInt();
        for (int i = 0; i < nrNodes; i++) {
            System.out.print("Name for Node #" + (i + 1) + ": ");
            String ndName = sc.next();
            this.graph.put(ndName, new Node(ndName));
        }

        for (int i = 0; i < this.graph.size(); i++) {
            System.out.print("How many connections does Node " + this.graph.keySet().toArray()[i] + " have? ");
            int conn = sc.nextInt();
            for (int j = 0; j < conn; j++) {
                System.out.print("Connection distance on #" + (j + 1) + " on Node " + this.graph.keySet().toArray()[i] + ": ");
                int dist = sc.nextInt();
                System.out.print("Connection #" + (j + 1) + " on Node " + this.graph.keySet().toArray()[i] + " connected to: ");
                String name = sc.next();
                this.graph.get(this.graph.keySet().toArray()[i]).links.put(this.graph.get(name), dist);
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
        this.graph.get("A").links.put(this.graph.get("B"), 3);
        this.graph.get("A").links.put(this.graph.get("C"), 1);

        this.graph.get("B").links.put(this.graph.get("A"), 3);
        this.graph.get("B").links.put(this.graph.get("C"), 7);
        this.graph.get("B").links.put(this.graph.get("D"), 5);
        this.graph.get("B").links.put(this.graph.get("E"), 1);

        this.graph.get("C").links.put(this.graph.get("A"), 1);
        this.graph.get("C").links.put(this.graph.get("B"), 7);
        this.graph.get("C").links.put(this.graph.get("D"), 2);

        this.graph.get("D").links.put(this.graph.get("C"), 2);
        this.graph.get("C").links.put(this.graph.get("B"), 5);
        this.graph.get("C").links.put(this.graph.get("E"), 7);

        this.graph.get("E").links.put(this.graph.get("B"), 1);
        this.graph.get("E").links.put(this.graph.get("D"), 7);
    }

    /**
     * Prints out the Network "diagram"
     * (for Debugging)
     */
    public void printNetwork() {
        for (int i = 0; i < this.graph.size(); i++) {
            System.out.println("Node " + this.graph.keySet().toArray()[i]);
            Node thisNode = this.graph.get(this.graph.keySet().toArray()[i]);
            for (int j = 0; j < thisNode.links.size(); j++) {
                Node selectedNode = (Node) (thisNode.links.keySet().toArray()[j]);
                String thisID = selectedNode.id;
                System.out.println(thisID + " = " + thisNode.links.get(selectedNode));
            }
            System.out.println(thisNode.bestDistance);
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
            Node nextNode = (Node) node.links.keySet().toArray()[i];
            int dist = node.bestDistance + node.links.get(nextNode);
            if (dist < nextNode.bestDistance) {
                nextNode.parent = node;
                nextNode.bestDistance = dist;
            }
        }
        node.checked = true;

        Node newNode = new Node();
        int x = Integer.MAX_VALUE;
        Node toCalculateNext = newNode;
        for (int i = 0; i < node.links.size(); i++) {
            Node nodeTmp = (Node) node.links.keySet().toArray()[i];
            if (nodeTmp.bestDistance < x) {
                if (!nodeTmp.checked) {
                    x = nodeTmp.bestDistance;
                    toCalculateNext = nodeTmp;
                    //toCalculateNext.parent = node;
                }
            }
        }
        if (toCalculateNext.bestDistance != (newNode.bestDistance)) {
            calculateBestNodeDistances(toCalculateNext);
        }

        for (int i = 0; i < this.graph.size(); i++) {
            this.graph.get(this.graph.keySet().toArray()[i]).checked = true;
        }
    }

    /**
     * Prints the routing table for the network
     */
    public void printRoutingTable() {
        System.out.println();
        String parentNodeID;
        System.out.format("%11s%10s%11s%10s", "Node ID", "Visited", "Distance", "Parent ID");
        System.out.println();
        for (int i = 0; i < this.graph.size(); i++) {
            Node currentNode = this.graph.get(this.graph.keySet().toArray()[i]);
            parentNodeID = currentNode.parent.id;
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
                    currentNode.links.put(goingTo, dist);
                } else {
                    currentNode = this.graph.get(s);
                }
            }
        }
    }

}
