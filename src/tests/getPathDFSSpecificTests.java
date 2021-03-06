import Graph.Edge;
import Graph.MultiGraph;
import Metro.Station;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class getPathDFSSpecificTests {

    //User for Testing Graph.MultiGraph Methods
    List<Edge<Station>> testConnections;
    List<Station> testStations;
    MultiGraph<Station, Edge<Station>> testGraph;
    List<Edge<Station>> testPath;

    /* Creates a Metro.Connection & two new Stations and Reads in bostonMetroStations.txt*/
    @org.junit.jupiter.api.BeforeEach
    void setUp() {

        FileReader reader = new FileReader("src/resources/bostonMetroStations.txt");
        testStations = reader.getStations();
        testConnections = reader.getConnections();

        testGraph = new MultiGraph<Station, Edge<Station>>();
        for (Station n : testStations) testGraph.addNode(n);
        for (Edge e : testConnections) testGraph.addEdge(e);

    }

    Station getStationByName(String stationName){
        Map<Station, List<Edge<Station>>> adjMap = testGraph.getAdjMap();
        for(Station station: adjMap.keySet()){
            if(station.getName().equals(stationName)) return station;
        }
        return null;
    }

    /**
     * Below Test Optimal routes from a station to another using the getPathDFS() method
     * This method takes the route with the fewest transitions on select instances
     * The tests will also name the standard getPath() method favouring fewest stops from above for comparison
     */

      /* Tests Graph.MultiGraph getPathDFS() optimal route with fewest transitions
       from Orange Chinatown to Green Boylston swng at Haymarket */
    @org.junit.jupiter.api.Test
    void multiGraphOrangeToGreenWithOnly1Transition() {

        Station source = getStationByName("Chinatown");
        Station destination = getStationByName("Boylston");

        testPath = testGraph.getPathDFS(source, destination);
        String label = testPath.get(0).getLabel();
        assertEquals("Orange", label);

        Station orangeNode = source;
        assertEquals("Chinatown", orangeNode.getName());
        orangeNode = testPath.get(0).getOppositeNode(orangeNode);
        assertEquals("DowntownCrossing", orangeNode.getName());
        orangeNode = testPath.get(1).getOppositeNode(orangeNode);
        assertEquals("State", orangeNode.getName());
        orangeNode = testPath.get(2).getOppositeNode(orangeNode);
        assertEquals("Haymarket", orangeNode.getName());

        label = testPath.get(3).getLabel();
        assertEquals("Green", label);

        Station greenNode = testPath.get(3).getOppositeNode(orangeNode);
        assertEquals("GovernmentCenter", greenNode.getName());
        greenNode = testPath.get(4).getOppositeNode(greenNode);
        assertEquals("ParkStreet", greenNode.getName());
        greenNode = testPath.get(5).getOppositeNode(greenNode);
        assertEquals("Boylston", greenNode.getName());
    }

    /* Tests Graph.MultiGraph getPathDFS() optimal route with fewest transitions
       from Orange ChinaTown to Blue Bowdoin swng at State */
    @org.junit.jupiter.api.Test
    void multiGraphOrangeToBlueWithOnly1Transition() {

        // Previous test getPath() for route called - multiGraphOrangeToBlue
        Station source = getStationByName("Chinatown");
        Station destination = getStationByName("Bowdoin");

        testPath = testGraph.getPathDFS(source, destination);
        String label = testPath.get(0).getLabel();
        assertEquals("Orange", label);

        Station orangeNode = source;
        assertEquals("Chinatown", orangeNode.getName());
        orangeNode = testPath.get(0).getOppositeNode(orangeNode);
        assertEquals("DowntownCrossing", orangeNode.getName());
        orangeNode = testPath.get(1).getOppositeNode(orangeNode);
        assertEquals("State", orangeNode.getName());

        label = testPath.get(2).getLabel();
        assertEquals("Blue", label);

        Station blueNode = testPath.get(2).getOppositeNode(orangeNode);
        assertEquals("GovernmentCenter", blueNode.getName());
        blueNode = testPath.get(3).getOppositeNode(blueNode);
        assertEquals("Bowdoin", blueNode.getName());


    }

    /* Tests Graph.MultiGraph getPathDFS() optimal route with fewest transitions
       of the Green Junction Line from GreenE to GreenD */
    @org.junit.jupiter.api.Test
    void multiGraphGreenEtoGreenDJunctionLineWithOnly2Transitions() {

        // Previous test getPath() for route called - multiGraphGreenEtoGreenD
        Station source = getStationByName("Prudential");
        Station destination = getStationByName("Fenway");
        testPath = testGraph.getPathDFS(source, destination);
        String label = testPath.get(0).getLabel();
        assertEquals("GreenE", label);

        Station greenJNode = source;
        assertEquals("Prudential", greenJNode.getName());
        greenJNode = testPath.get(0).getOppositeNode(greenJNode);
        assertEquals("Copley", greenJNode.getName());

        // B would be optimal at this point in the junction
        label = testPath.get(1).getLabel();
        assertTrue(label.equals("GreenB") );

        greenJNode = testPath.get(1).getOppositeNode(greenJNode);
        assertEquals("Hynes/ICA", greenJNode.getName());

        label = testPath.get(2).getLabel();
        assertTrue(label.equals("GreenB") );

        greenJNode = testPath.get(2).getOppositeNode(greenJNode);
        assertEquals("Kenmore", greenJNode.getName());

        label = testPath.get(3).getLabel();
        assertTrue(label.equals("GreenD") );

        greenJNode = testPath.get(3).getOppositeNode(greenJNode);
        assertEquals("Fenway", greenJNode.getName());
    }

}
