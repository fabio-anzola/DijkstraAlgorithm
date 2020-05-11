import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Dijkstra dijkstra = new Dijkstra();
        dijkstra.init();
        //dijkstra.readFromUser();
        dijkstra.loadTemplateNetwork();
        //dijkstra.readFromFile("resources/Network01.txt");
        //dijkstra.printNetwork();
        dijkstra.setStartNode("C");
        dijkstra.calculateBestNodeDistances(dijkstra.getStartNode());
        //dijkstra.printNetwork();
        dijkstra.printRoutingTable();

    }
}
