import Graph.Edge;
import Graph.MultiGraph;
import Metro.Station;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class getPathJunctionTests {

    //User for Testing Graph.MultiGraph Methods
    List<Edge<Station>> testConnections;
    List<Station> testStations;
    MultiGraph<Station, Edge<Station>> testGraph;
    List<Edge<Station>> testPath;

    /* Reads in bostonMetroStations.txt*/
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
     * Below Test Correct Continuity between Key Junction Stations on Red and Green Line
     * Using standard getPath() method
     * RedB to RedA, RedA to Mattapan,
     */

    /* Tests Graph.MultiGraph getPath() of the Red Junction Line between RedA and RedB */
    @org.junit.jupiter.api.Test
    void multiGraphRedJunctionLine() {

        // Tests Obtained Route Correct for the Red Junction line from RedB to RedA
        Station source = getStationByName("Wollaston");
        Station destination = getStationByName("FieldsCorner");
        testPath = testGraph.getPath(source, destination);
        String label = testPath.get(0).getLabel();
        assertEquals("RedB", label);

        Station redJNode = source;
        assertEquals("Wollaston", redJNode.getName());
        Station redJNodes = testPath.get(0).getOppositeNode(redJNode);
        assertEquals("NorthQuincy", redJNodes.getName());
        redJNode = testPath.get(1).getOppositeNode(redJNodes);
        assertEquals("JFK/UMass", redJNode.getName());

        label = testPath.get(2).getLabel();
        assertEquals("RedA", label);

        redJNode = testPath.get(2).getOppositeNode(redJNode);
        assertEquals("SavinHill", redJNode.getName());
        redJNode = testPath.get(3).getOppositeNode(redJNode);
        assertEquals("FieldsCorner", redJNode.getName());
    }

    /* Tests Graph.MultiGraph getPath() of the Mattapan Junction Line from RedA */
    @org.junit.jupiter.api.Test
    void multiGraphMattapanJunctionLine() {

        // Tests Obtained Route Correct for the Red Junction line from RedA to Mattapan
        testPath = testGraph.getPath(getStationByName("Shawmut"), getStationByName("CedarGrove"));
        String label = testPath.get(0).getLabel();
        assertEquals("RedA", label);

        Station redJNode = testPath.get(0).getNode1();
        assertEquals("Shawmut", redJNode.getName());
        Station redJNodes = testPath.get(0).getOppositeNode(redJNode);
        assertEquals("Ashmont", redJNodes.getName());

        label = testPath.get(1).getLabel();
        assertEquals("Mattapan", label);

        redJNode = testPath.get(1).getNode2();
        assertEquals("CedarGrove", redJNode.getName());
    }

    /* Tests Graph.MultiGraph getPath() of the Green Junction Line from GreenE to GreenB */
    @org.junit.jupiter.api.Test
    void multiGraphGreenEtoGreenBJunctionLine() {

        // Tests Obtained Route Correct for the Green Junction line from GreenE to GreenB
        Station source = getStationByName("Prudential");
        Station destination = getStationByName("Hynes/ICA");
        testPath = testGraph.getPath(source, destination);
        String label = testPath.get(0).getLabel();
        assertEquals("GreenE", label);

        Station greenJNode = source;
        assertEquals("Prudential", greenJNode.getName());
        greenJNode = testPath.get(0).getOppositeNode(greenJNode);
        assertEquals("Copley", greenJNode.getName());

        label = testPath.get(1).getLabel();
        assertTrue(label.equals("GreenB")||label.equals("GreenC")||label.equals("GreenD") );

        greenJNode = testPath.get(1).getOppositeNode(greenJNode);
        assertEquals("Hynes/ICA", greenJNode.getName());
    }

    /* Tests Graph.MultiGraph getPath() of the Green Junction Line from GreenB to GreenC */
    @org.junit.jupiter.api.Test
    void multiGraphGreenBtoGreenCJunctionLine() {

        // Tests Obtained Route Correct for the Green Junction line from GreenB to GreenC
        testPath = testGraph.getPath(getStationByName("BlandfordStreet"), getStationByName("St.Mary'sStreet"));
        String label = testPath.get(0).getLabel();
        assertEquals("GreenB", label);

        Station greenJNode = testPath.get(0).getNode1();
        assertEquals("BlandfordStreet", greenJNode.getName());
        Station greenJNodes = testPath.get(0).getOppositeNode(greenJNode);
        assertEquals("Kenmore", greenJNodes.getName());

        label = testPath.get(1).getLabel();
        assertEquals("GreenC", label);

        greenJNode = testPath.get(1).getNode2();
        assertEquals("St.Mary'sStreet", greenJNode.getName());
    }

    /* Tests Graph.MultiGraph getPath() of the Green Junction Line from GreenD to GreenB */
    @org.junit.jupiter.api.Test
    void multiGraphGreenDtoGreenBJunctionLine() {

        Station source = getStationByName("Fenway");
        Station destination = getStationByName("BlandfordStreet");

        // Tests Obtained Route Correct for the Green Junction line from GreenD to GreenB
        testPath = testGraph.getPath(source, destination);
        String label = testPath.get(0).getLabel();
        assertEquals("GreenD", label);

        Station greenJNode = source;
        assertEquals("Fenway", greenJNode.getName());
        greenJNode = testPath.get(0).getOppositeNode(greenJNode);
        assertEquals("Kenmore", greenJNode.getName());

        label = testPath.get(1).getLabel();
        assertEquals("GreenB", label);

        greenJNode = testPath.get(1).getOppositeNode(greenJNode);
        assertEquals("BlandfordStreet", greenJNode.getName());
    }

    @org.junit.jupiter.api.Test
    void multiGraphGreenEtoGreenDJunctionLine() {

        // Tests Obtained Route Correct for the Green Junction line from GreenE to GreenD
        Station source = getStationByName("Prudential");
        Station destination = getStationByName("Fenway");
        testPath = testGraph.getPath(source, destination);
        String label = testPath.get(0).getLabel();
        assertEquals("GreenE", label);

        Station greenJNode = source;
        assertEquals("Prudential", greenJNode.getName());
        greenJNode = testPath.get(0).getOppositeNode(greenJNode);
        assertEquals("Copley", greenJNode.getName());

        label = testPath.get(1).getLabel();
        assertTrue(label.equals("GreenB"));

        greenJNode = testPath.get(1).getOppositeNode(greenJNode);
        assertEquals("Hynes/ICA", greenJNode.getName());

        label = testPath.get(2).getLabel();
        assertTrue(label.equals("GreenB"));

        greenJNode = testPath.get(2).getOppositeNode(greenJNode);
        assertEquals("Kenmore", greenJNode.getName());

        label = testPath.get(3).getLabel();
        assertTrue(label.equals("GreenD"));

        greenJNode = testPath.get(3).getOppositeNode(greenJNode);
        assertEquals("Fenway", greenJNode.getName());
    }

    @org.junit.jupiter.api.Test
    void multiGraphGreenDtoGreenJunctionLine() {

        // Tests Obtained Route Correct for the Green Junction line from GreenD to Green
        Station source = getStationByName("Fenway");
        Station destination = getStationByName("Boylston");
        testPath = testGraph.getPath(source, destination);
        String label = testPath.get(0).getLabel();
        assertEquals("GreenD", label);

        Station greenJNode = source;
        assertEquals("Fenway", greenJNode.getName());
        greenJNode = testPath.get(0).getOppositeNode(greenJNode);
        assertEquals("Kenmore", greenJNode.getName());

        label = testPath.get(1).getLabel();
        assertTrue(label.equals("GreenD"));

        greenJNode = testPath.get(1).getOppositeNode(greenJNode);
        assertEquals("Hynes/ICA", greenJNode.getName());

        label = testPath.get(2).getLabel();
        assertTrue(label.equals("GreenB"));

        greenJNode = testPath.get(2).getOppositeNode(greenJNode);
        assertEquals("Copley", greenJNode.getName());

        label = testPath.get(3).getLabel();
        assertTrue(label.equals("GreenB"));

        greenJNode = testPath.get(3).getOppositeNode(greenJNode);
        assertEquals("Arlington", greenJNode.getName());

        label = testPath.get(4).getLabel();
        assertTrue(label.equals("Green"));

        greenJNode = testPath.get(4).getOppositeNode(greenJNode);
        assertEquals("Boylston", greenJNode.getName());


    }

    /**
     * Below Test Optimal routes from a station on one coloured line to another
     * Using standard getPath() method
     * Orange-Red, Orange-Green, Orange-Blue, Blue-Red, Blue-Orange/Green,
     */

    /* Tests Graph.MultiGraph getPath() optimal route from Orange ChinaTown to Red Charles/MGH */
    @org.junit.jupiter.api.Test
    void multiGraphOrangeToRed() {

        Station source = getStationByName("Chinatown");
        Station destination = getStationByName("Charles/MGH");

        // Tests Obtained Route Correct for the Orange ChinaTown to Red Charles/MGH
        testPath = testGraph.getPath(source, destination);
        String label = testPath.get(0).getLabel();
        assertEquals("Orange", label);

        Station orangeNode = source;
        assertEquals("Chinatown", orangeNode.getName());
        orangeNode = testPath.get(0).getOppositeNode(orangeNode);
        assertEquals("DowntownCrossing", orangeNode.getName());

        label = testPath.get(1).getLabel();
        assertEquals("Red", label);

        orangeNode = testPath.get(2).getOppositeNode(orangeNode);
        assertEquals("ParkStreet", orangeNode.getName());
        orangeNode = testPath.get(2).getOppositeNode(orangeNode);
        assertEquals("Charles/MGH", orangeNode.getName());
    }

    /* Tests Graph.MultiGraph getPath() optimal route from Orange Chinatown to Green Boylston
       swing at DowntownCrossing, ParkStreet*/
    @org.junit.jupiter.api.Test
    void multiGraphOrangeToGreen() {

        Station source = getStationByName("Chinatown");
        Station destination = getStationByName("Boylston");

        testPath = testGraph.getPath(source, destination);
        String label = testPath.get(0).getLabel();
        assertEquals("Orange", label);

        Station orangeNode = source;
        assertEquals("Chinatown", orangeNode.getName());
        orangeNode = testPath.get(0).getOppositeNode(orangeNode);
        assertEquals("DowntownCrossing", orangeNode.getName());

        label = testPath.get(1).getLabel();
        assertEquals("Red", label);

        Station greenNode = testPath.get(2).getOppositeNode(orangeNode);
        assertEquals("ParkStreet", greenNode.getName());

        label = testPath.get(2).getLabel();
        assertEquals("Green", label);

        greenNode = testPath.get(2).getOppositeNode(greenNode);
        assertEquals("Boylston", greenNode.getName());
    }

    /* Tests Graph.MultiGraph getPath() optimal route from Orange Chinatown to Blue Bowdoin swing at DowntownCrossing,ParkStreet,GovernmentCenter */
    @org.junit.jupiter.api.Test
    void multiGraphOrangeToBlue() {

        Station source = getStationByName("Chinatown");
        Station destination = getStationByName("Bowdoin");

        // Tests Obtained Route Correct for the Orange Chinatown to Blue Bowdoin
        // swing at DowntownCrossing,ParkStreet,GovernmentCenter
        testPath = testGraph.getPath(source, destination);
        String label = testPath.get(0).getLabel();
        assertEquals("Orange", label);

        Station orangeNode = source;
        assertEquals("Chinatown", orangeNode.getName());
        orangeNode = testPath.get(0).getOppositeNode(orangeNode);
        assertEquals("DowntownCrossing", orangeNode.getName());

        label = testPath.get(1).getLabel();
        assertEquals("Orange", label);

        orangeNode = testPath.get(1).getOppositeNode(orangeNode);
        assertEquals("State", orangeNode.getName());

        label = testPath.get(2).getLabel();
        assertEquals("Blue", label);

        Station blueNode = testPath.get(2).getOppositeNode(orangeNode);
        assertEquals("GovernmentCenter", blueNode.getName());

        label = testPath.get(3).getLabel();
        assertEquals("Blue", label);

        blueNode = testPath.get(3).getOppositeNode(blueNode);
        assertEquals("Bowdoin", blueNode.getName());

    }

    /* Tests Graph.MultiGraph getPath() optimal route from Blue Aquarium to Red Charles/MGH via GovernmentCenter and ParkStreet */
    @org.junit.jupiter.api.Test
    void multiGraphBlueToRed() {

        Station source = getStationByName("Aquarium");
        Station destination = getStationByName("Charles/MGH");

        // Tests Obtained Route Correct for the Blue Aquarium to Red Charles/MGH
        testPath = testGraph.getPath(source, destination);
        String label = testPath.get(0).getLabel();
        assertEquals("Blue", label);

        Station blueNode = source;
        assertEquals("Aquarium", blueNode.getName());
        blueNode = testPath.get(0).getOppositeNode(blueNode);
        assertEquals("State", blueNode.getName());
        blueNode = testPath.get(1).getOppositeNode(blueNode);
        assertEquals("GovernmentCenter", blueNode.getName());

        label = testPath.get(2).getLabel();
        assertEquals("Green", label);

        Station greenNode = testPath.get(2).getOppositeNode(blueNode);
        assertEquals("ParkStreet", greenNode.getName());

        label = testPath.get(3).getLabel();
        assertEquals("Red", label);

        Station redNode = testPath.get(3).getOppositeNode(greenNode);
        assertEquals("Charles/MGH", redNode.getName());
    }

    /* Tests Graph.MultiGraph getPath() optimal route from Blue Aquarium to Orange/Green Haymarket via State */
    @org.junit.jupiter.api.Test
    void multiGraphBlueToOrangeGreen() {

        // Tests Obtained Route Correct for the Blue Aquarium to Orange/Green Haymarket via State
        Station source = getStationByName("Aquarium");
        Station destination = getStationByName("Haymarket");

        testPath = testGraph.getPath(source, destination);

        String label = testPath.get(0).getLabel();
        assertEquals("Blue", label);

        Station blueNode = source;
        assertEquals("Aquarium", blueNode.getName());
        blueNode = testPath.get(0).getOppositeNode(blueNode);
        assertEquals("State", blueNode.getName());

        label = testPath.get(1).getLabel();
        assertEquals("Orange", label);

        Station orangeNode = testPath.get(1).getOppositeNode(blueNode);
        assertEquals("Haymarket", orangeNode.getName());
    }
}
