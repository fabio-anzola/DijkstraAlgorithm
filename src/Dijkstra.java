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
        }
    }

}
