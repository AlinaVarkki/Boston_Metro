import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Model {

    MultiGraph<Node, Edge<Node>> multiGraph;
    HashMap<String,Node> stationsHashMap;
    List<Node> stations;
    Map<Node, List<Edge<Node>>> adjMap;
    private Map<String, String> stationColorMap;

    public Model(String filepath){
        this.setMultiGraph(filepath);
        this.setStationsHashMap();
        stationColorMap = new HashMap<>();
        getAdjMap();
        fillStationColorMap(adjMap);
    }

    /**
     * @param filepath passed to FileReader
     * MultiGraph initialised with Nodes and Edges
     * */
    private void setMultiGraph(String filepath){
        FileReader reader = new FileReader(filepath);

        multiGraph = new MultiGraph<>();
        stations = reader.getStations();
        List<Edge<Node>> connections = reader.getConnections();

        for(Node n: stations) multiGraph.addNode(n);
        for(Edge<Node> e: connections) multiGraph.addEdge(e);

    }

    public void getAdjMap(){
        adjMap = multiGraph.getAdjMap();
    }

    private void setStationsHashMap(){
        stationsHashMap = new HashMap<>();
        for(Node station : stations){
            this.stationsHashMap.put(station.toString(),station);
        }
    }

    public ArrayList<String> getListOfStations(){
        return new ArrayList<>(stationsHashMap.keySet());

    }
    /**
     * @param start,destination stored as Nodes and passed to MultiGraph
     * Tuple used to store Line-Colour and Station's
     * @return List of Tuples for optimal Route
     * */
    public List<Tuple<String,List<String>>> runSearch(String start,String destination,String algorithm){
        Node from = this.find(start);
        Node to = this.find(destination);

        List<Tuple<String,List<String>>> processedForView = new ArrayList<>();
        List<Edge<Node>> path;
        if (algorithm.equals("Transitions")) {
            path = multiGraph.getPath(from, to);
        }

        else{
            path = multiGraph.getPathDFS(from, to);
        }

        Node station = from;

        if (path.size() != 0) {
            String lineColour = path.get(0).getLabel();
            Tuple<String, List<String>> line = new Tuple<>(lineColour, new ArrayList<>());
            line.second.add(station.toString());

            for (Edge<Node> edge : path) {
                System.out.println(edge.toString());
                lineColour = edge.getLabel();
                if (!lineColour.equals(line.first)) {
                    processedForView.add(line);
                    line = new Tuple<>(lineColour, new ArrayList<>());
                }
                station = edge.getOppositeNode(station);
                line.second.add(station.toString());
            }

            processedForView.add(line);
        }

        return processedForView;
    }

    private Node find(String name){
        return stationsHashMap.get(name);
    }

    private void fillStationColorMap(Map<Node, List<Edge<Node>>> adjMap){
        List<Edge<Node>> adjEdges;
        boolean sameEdges;
        for(Node n: adjMap.keySet()){
            sameEdges = true;
            adjEdges = adjMap.get(n);

            String label = toBaseColor(adjEdges.get(0).getLabel());
            for(Edge<Node> e: adjEdges){
                String currLabel = toBaseColor(e.getLabel());
                if(!currLabel.equals(label)){
                    sameEdges = false;
                }
            }

            if(sameEdges) stationColorMap.put(n.toString(), label);
            else stationColorMap.put(n.toString(), "Black");
        }
    }

    public String toBaseColor(String label){
        if(label.equals("GreenB") || label.equals("GreenC") || label.equals("GreenD") || label.equals("GreenE")) return "Green";
        if(label.equals("RedA") || label.equals("RedB")) return "Red";
        return label;
    }

    public Map<String, String> getStationColorMap(){
        return stationColorMap;
    }
}
