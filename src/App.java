import java.util.List;

public class App {
    public static void main(String[] args){
        FileReader g = new FileReader();
        g.readInGraph("src/bostonmetro.txt");
        List<Node> stations = g.getStations();
        List<Edge<Node>> connections = g.getConnections();

        MultiGraph<Node, Edge<Node>> graph = new MultiGraph<>();
        for(Node n: stations) graph.addNode(n);
        for(Edge e: connections) graph.addEdge(e);

        Node source = getStationByName(stations, "CommunityCollege");
        Node destination = getStationByName(stations, "Chinatown");
        List<Edge<Node>> path = graph.getPath(source, destination);

        printPath(path,source);

        System.out.println("with Tuples");
        List<Edge<Node>> path2 = graph.getPathTuple(source, destination);
        printPath(path2,source);


    }

    public static Node getStationByName(List<Node> stations, String name){
        for(Node s: stations){
            if(s.getName().equals(name)) return s;
        }
        return null;
    }

    public static void printPath(List<Edge<Node>> path, Node source){
        String label = path.get(0).getLabel();
        Node fromStation = source;
        System.out.println(fromStation.getName());
        for(Edge<Node> c: path){
            if(!c.getLabel().equals(label)){
                System.out.println("SWITCH LINE TO: " + c.getLabel());
                label = c.getLabel();
            }
            Node s = c.getOppositeNode(fromStation);
            System.out.println(s.getName());
            fromStation = s;
        }
    }

}
