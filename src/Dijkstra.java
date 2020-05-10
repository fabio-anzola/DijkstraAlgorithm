import java.util.HashMap;
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
    Map<String, Node> graph;

    /**
     * The Node for the Algorithm to start from
     */
    Node startNode;

    /**
     * Initializes the network
     */
    void init() {
        this.graph = new HashMap<>();
    }

    /**
     * Prompts the user to enter data for the network
     */
    void readFromUser() {
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
    void loadTemplateNetwork() {
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
    void printNetwork() {
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
    void setStartNode(String startNode) {
        this.startNode = this.graph.get(startNode);
        this.startNode.bestDistance = 0;
    }

    /**
     * User can set the start-node from prompt
     */
    void setStartNodeFromUser() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Specify the start Node");
        this.startNode = this.graph.get(sc.next());
        this.startNode.bestDistance = 0;
    }

    /**
     * The Dijkstra Algorithm calculations
     *
     * @param node The node to start from
     */
    void calculateBestNodeDistances(Node node) {
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
                }
            }
        }
        if (!toCalculateNext.equals(newNode)) {
            calculateBestNodeDistances(toCalculateNext);
        } else {
            return;
        }
    }

    /**
     * Prints the routing table for the network
     */
    void printRoutingTable() {
        for (int i = 0; i < this.graph.size(); i++) {
            Node currentNode = this.graph.get(this.graph.keySet().toArray()[i]);
            System.out.format("%10s%8s%10d", "Node " + currentNode.id, currentNode.checked, currentNode.bestDistance);
            System.out.println();
        }
    }

}
