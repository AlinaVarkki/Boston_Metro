import java.util.List;

public class App {
    public static void main(String[] args){
        FileReader g = new FileReader();
        g.readInGraph("src/bostonmetro.txt");
        List<Node> stations = g.getStations();
        List<Edge> connections = g.getConnections();

        MultiGraph graph = new MultiGraph<>();
        for(Node n: stations) graph.addNode(n);
        for(Edge e: connections) graph.addEdge(e);

        Station source = (Station) getStationByName(stations, "Alewife");
        List<Connection> path = graph.getPath(getStationByName(stations, "Alewife"), getStationByName(stations, "JFK/UMass"));

        printPath(path,source);

    }

    public static Node getStationByName(List<Node> stations, String name){
        for(Node s: stations){
            if(s.getName().equals(name)) return s;
        }
        return null;
    }

    public static void printPath(List<Connection> path, Station source){
        String label = path.get(0).getLabel();
        Station fromStation = source;
        System.out.println(fromStation.getName());
        for(Connection c: path){
            if(!c.getLabel().equals(label)){
                System.out.println("SWITCH LINE TO: " + c.getLabel());
                label = c.getLabel();
            }
            Station s = (Station) c.getOppositeNode(fromStation);
            System.out.println(s.getName());
            fromStation = s;
        }
    }

}
