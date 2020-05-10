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

}
