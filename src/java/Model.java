import Graph.Edge;
import Graph.MultiGraph;
import Graph.Node;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Model {

    private MultiGraph<Node, Edge<Node>> multiGraph;
    private HashMap<String, Node> stationsHashMap;
    private List<Node> stations;
    private List<Edge<Node>> connections;
    private Map<Node, List<Edge<Node>>> adjMap;
    private Map<String, List<String>> stationColorMap;

    public Model(List<Node> stations, List<Edge<Node>> connections) {
        this.stations = stations;
        this.connections = connections;
        this.setMultiGraph();
        this.setStationsHashMap();
        stationColorMap = new HashMap<>();
        getAdjMap();
        fillStationColorMap(adjMap);
    }

    /**
     * Called in Model Constructor
     * MultiGraph initialised with Nodes and Edges
     *
     *
     * Graph.MultiGraph initialised with Nodes and Edges
     * Nodes representing Stations, Edges representing Connecting Lines
     */
    private void setMultiGraph() {

        multiGraph = new MultiGraph<Node, Edge<Node>>();

        for (Node n : stations) multiGraph.addNode(n);
        for (Edge<Node> e : connections) multiGraph.addEdge(e);

    }

    /**
     * stores multigraph's ajdMap in a local variable
     */
    public void getAdjMap() {
        adjMap = multiGraph.getAdjMap();
    }

    /**
     * @return list of all stations
     */
    public ArrayList<String> getListOfStations() {
        return new ArrayList<>(stationsHashMap.keySet());
    }

    /**
     * @return map of color and corresponding stations
     */
    public Map<String, List<String>> getStationColorMap() {
        return stationColorMap;
    }

    /**
     * Called in Model Constructor
     * Fills Hashmap with Station which can be easily Searched through
     */
    private void setStationsHashMap() {
        stationsHashMap = new HashMap<>();
        for (Node station : stations) {
            this.stationsHashMap.put(station.toString(), station);
        }
    }

    /**
     * @param start,destination stored as Nodes and passed to Graph.MultiGraph
     *                          Pair used to store Line-Colour and Station's
     * @param algorithm chosen algorithm for search
     * @return List of Tuples for optimal Route
     */
    public List<Pair<String, List<String>>> runSearch(String start, String destination, String algorithm) {
        Node from = this.find(start);
        Node to = this.find(destination);

        List<Pair<String, List<String>>> processedForView = new ArrayList<>();
        List<Edge<Node>> path;
        if (algorithm.equals("Length")) {
            path = multiGraph.getPath(from, to);
        } else {
            path = multiGraph.getPathDFS(from, to);
        }

        Node station = from;

        if (path.size() != 0) {
            String lineColour = path.get(0).getLabel();
            Pair<String, List<String>> line = new Pair<>(lineColour, new ArrayList<>());
            line.getValue().add(station.toString());

            for (Edge<Node> edge : path) {
                lineColour = edge.getLabel();
                if (!lineColour.equals(line.getKey())) {
                    processedForView.add(line);
                    line = new Pair<>(lineColour, new ArrayList<>());
                }
                station = edge.getOppositeNode(station);
                line.getValue().add(station.toString());
            }

            processedForView.add(line);
        }

        return processedForView;
    }

    /**
     * @param name String
     * @return Node of station with name
     */
    private Node find(String name) {
        return stationsHashMap.get(name);
    }

    /**
     * @param adjMap of nodes and their corresponding edges
     * fills stationColorMap with station name and colors of lines that pass through
     */
    private void fillStationColorMap(Map<Node, List<Edge<Node>>> adjMap) {
        List<Edge<Node>> adjEdges;

        for (Node n : adjMap.keySet()) {
            adjEdges = adjMap.get(n);
            List<String> colors = new ArrayList<>();

            for (Edge<Node> e : adjEdges) {
                String color = e.getLabel();
                if (!colors.contains(color)) {
                    colors.add(color);
                }
            }
            stationColorMap.put(n.toString(), colors);

        }
    }

    /**
     * @return a map containing station ids mapped to their names
     */
    public Map<String, String> getMapIdsToStation() {
        Map<String, String> idsToStations = new HashMap<>();
        ArrayList<String> stations = new ArrayList<>(stationsHashMap.keySet());

        for (String station : stations) {
            String id = station.split(" ")[0];
            idsToStations.put(id, station);
        }

        return idsToStations;
    }

}
