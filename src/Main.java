public class Main {
    public static void main(String[] args) {
        Dijkstra dijkstra = new Dijkstra();
        dijkstra.init();
        //dijkstra.readFromUser();
        dijkstra.loadTemplateNetwork();
        dijkstra.printNetwork();
        dijkstra.setStartNode("C");

    }
}
