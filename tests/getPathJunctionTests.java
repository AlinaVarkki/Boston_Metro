import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class getPathJunctionTests {

    //User for Testing MultiGraph Methods
    List<Edge<Node>> testConnections;
    List<Node> testStations;
    MultiGraph<Node, Edge<Node>> testGraph;
    List<Edge<Node>> testPath;

    /* Creates a Connection & two new Stations and Reads in bostonmetro.txt*/
    @org.junit.jupiter.api.BeforeEach
    void setUp() {

        FileReader reader = new FileReader("resources/bostonmetro.txt");
        testStations = reader.getStations();
        testConnections = reader.getConnections();

        testGraph = new MultiGraph<>();
        for (Node n : testStations) testGraph.addNode(n);
        for (Edge e : testConnections) testGraph.addEdge(e);

    }

    /**
     * Below Test Correct Continuity between Key Junction Stations on Red and Green Line
     * Using standard getPath() method
     * RedB to RedA, RedA to Mattapan,
     */

    /* Tests MultiGraph getPath() of the Red Junction Line between RedA and RedB */
    @org.junit.jupiter.api.Test
    void multiGraphRedJunctionLine() {

        // Tests Obtained Route Correct for the Red Junction line from RedB to RedA
        Node source = App.getStationByName(testStations, "Wollaston");
        Node destination = App.getStationByName(testStations, "FieldsCorner");
        testPath = testGraph.getPath(source, destination);
        String label = testPath.get(0).getLabel();
        assertEquals("RedB", label);

        Node redJNode = source;
        assertEquals("Wollaston", redJNode.getName());
        Node redJNodes = testPath.get(0).getOppositeNode(redJNode);
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

    /* Tests MultiGraph getPath() of the Mattapan Junction Line from RedA */
    @org.junit.jupiter.api.Test
    void multiGraphMattapanJunctionLine() {

        // Tests Obtained Route Correct for the Red Junction line from RedA to Mattapan
        testPath = testGraph.getPath(App.getStationByName(testStations, "Shawmut"), App.getStationByName(testStations, "CedarGrove"));
        String label = testPath.get(0).getLabel();
        assertEquals("RedA", label);

        Node redJNode = testPath.get(0).getNode1();
        assertEquals("Shawmut", redJNode.getName());
        Node redJNodes = testPath.get(0).getOppositeNode(redJNode);
        assertEquals("Ashmont", redJNodes.getName());

        label = testPath.get(1).getLabel();
        assertEquals("Mattapan", label);

        redJNode = testPath.get(1).getNode2();
        assertEquals("CedarGrove", redJNode.getName());
    }

    /* Tests MultiGraph getPath() of the Green Junction Line from GreenE to GreenB */
    @org.junit.jupiter.api.Test
    void multiGraphGreenEtoGreenBJunctionLine() {

        // Tests Obtained Route Correct for the Green Junction line from GreenE to GreenB
        Node source = App.getStationByName(testStations, "Prudential");
        Node destination = App.getStationByName(testStations, "Hynes/ICA");
        testPath = testGraph.getPath(source, destination);
        String label = testPath.get(0).getLabel();
        assertEquals("GreenE", label);

        Node greenJNode = source;
        assertEquals("Prudential", greenJNode.getName());
        greenJNode = testPath.get(0).getOppositeNode(greenJNode);
        assertEquals("Copley", greenJNode.getName());

        label = testPath.get(1).getLabel();
        assertTrue(label.equals("GreenB")||label.equals("GreenC")||label.equals("GreenD") );

        greenJNode = testPath.get(1).getOppositeNode(greenJNode);
        assertEquals("Hynes/ICA", greenJNode.getName());
    }

    /* Tests MultiGraph getPath() of the Green Junction Line from GreenB to GreenC */
    @org.junit.jupiter.api.Test
    void multiGraphGreenBtoGreenCJunctionLine() {

        // Tests Obtained Route Correct for the Green Junction line from GreenB to GreenC
        testPath = testGraph.getPath(App.getStationByName(testStations, "BlandfordStreet"), App.getStationByName(testStations, "St.Mary'sStreet"));
        String label = testPath.get(0).getLabel();
        assertEquals("GreenB", label);

        Node greenJNode = testPath.get(0).getNode1();
        assertEquals("BlandfordStreet", greenJNode.getName());
        Node greenJNodes = testPath.get(0).getOppositeNode(greenJNode);
        assertEquals("Kenmore", greenJNodes.getName());

        label = testPath.get(1).getLabel();
        assertEquals("GreenC", label);

        greenJNode = testPath.get(1).getNode2();
        assertEquals("St.Mary'sStreet", greenJNode.getName());
    }

    /* Tests MultiGraph getPath() of the Green Junction Line from GreenD to GreenB */
    @org.junit.jupiter.api.Test
    void multiGraphGreenDtoGreenBJunctionLine() {

        Node source = App.getStationByName(testStations, "Fenway");
        Node destination = App.getStationByName(testStations, "Hynes/ICA");

        // Tests Obtained Route Correct for the Green Junction line from GreenD to GreenB
        testPath = testGraph.getPath(source, destination);
        String label = testPath.get(0).getLabel();
        assertEquals("GreenD", label);

        Node greenJNode = source;
        assertEquals("Fenway", greenJNode.getName());
        greenJNode = testPath.get(0).getOppositeNode(greenJNode);
        assertEquals("Kenmore", greenJNode.getName());

        label = testPath.get(1).getLabel();
        assertEquals("GreenD", label);

        greenJNode = testPath.get(1).getOppositeNode(greenJNode);
        assertEquals("Hynes/ICA", greenJNode.getName());
    }

    @org.junit.jupiter.api.Test
    void multiGraphGreenEtoGreenDJunctionLine() {

        // Tests Obtained Route Correct for the Green Junction line from GreenE to GreenD
        Node source = App.getStationByName(testStations, "Prudential");
        Node destination = App.getStationByName(testStations, "Fenway");
        testPath = testGraph.getPath(source, destination);
        String label = testPath.get(0).getLabel();
        assertEquals("GreenE", label);

        Node greenJNode = source;
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

    /**
     * Below Test Optimal routes from a station on one coloured line to another
     * Using standard getPath() method
     * Orange-Red, Orange-Orange, Orange-Green, Orange-Blue, Blue-Red, Blue-Orange/Green,
     */

    /* Tests MultiGraph getPath() optimal route from Orange ChinaTown to Red Charles/MGH */
    @org.junit.jupiter.api.Test
    void multiGraphOrangeToRed() {

        Node source = App.getStationByName(testStations, "Chinatown");
        Node destination = App.getStationByName(testStations, "Charles/MGH");

        // Tests Obtained Route Correct for the Orange ChinaTown to Red Charles/MGH
        testPath = testGraph.getPath(source, destination);
        String label = testPath.get(0).getLabel();
        assertEquals("Orange", label);

        Node orangeNode = source;
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

    /* Tests MultiGraph getPath() optimal route from Orange CommunityCollege to Orange Chinatown */
    @org.junit.jupiter.api.Test
    void multiGraphOrangeRemainsOrange() {

        Node source = App.getStationByName(testStations, "CommunityCollege");

        // Tests Obtained Route Correct for the Orange CommunityCollege to Orange Chinatown without any Line swaps
        testPath = testGraph.getPath(App.getStationByName(testStations, "CommunityCollege"), App.getStationByName(testStations, "Chinatown"));
        String label = testPath.get(0).getLabel();
        assertEquals("Orange", label);

        Node orangeNode = testPath.get(0).getNode1();
        assertEquals("CommunityCollege", orangeNode.getName());
        Node orangeNodes = testPath.get(0).getOppositeNode(orangeNode);
        assertEquals("NorthStation", orangeNodes.getName());

        //currently swaps to green the back again
        label = testPath.get(1).getLabel();
        assertEquals("Orange", label);

        orangeNode = testPath.get(2).getNode1();
        assertEquals("Haymarket", orangeNode.getName());
        orangeNode = testPath.get(2).getNode2();
        assertEquals("State", orangeNode.getName());

        label = testPath.get(2).getLabel();
        assertEquals("Orange", label);

        orangeNode = testPath.get(3).getNode2();
        assertEquals("DowntownCrossing", orangeNode.getName());
        orangeNode = testPath.get(4).getNode2();
        assertEquals("Chinatown", orangeNode.getName());

        App.printPath(testPath,source);
    }

    /* Tests MultiGraph getPath() optimal route from Orange Chinatown to Green Boylston
       swapping at DowntownCrossing, ParkStreet*/
    @org.junit.jupiter.api.Test
    void multiGraphOrangeToGreen() {

        Node source = App.getStationByName(testStations, "Chinatown");
        Node destination = App.getStationByName(testStations, "Boylston");

        testPath = testGraph.getPath(source, destination);
        String label = testPath.get(0).getLabel();
        assertEquals("Orange", label);

        Node orangeNode = source;
        assertEquals("Chinatown", orangeNode.getName());
        orangeNode = testPath.get(0).getOppositeNode(orangeNode);
        assertEquals("DowntownCrossing", orangeNode.getName());

        label = testPath.get(1).getLabel();
        assertEquals("Red", label);

        Node greenNode = testPath.get(2).getOppositeNode(orangeNode);
        assertEquals("ParkStreet", greenNode.getName());

        label = testPath.get(2).getLabel();
        assertEquals("Green", label);

        greenNode = testPath.get(2).getOppositeNode(greenNode);
        assertEquals("Boylston", greenNode.getName());
    }

    /* Tests MultiGraph getPath() optimal route from Orange Chinatown to Blue Bowdoin swapping at DowntownCrossing,ParkStreet,GovernmentCenter */
    @org.junit.jupiter.api.Test
    void multiGraphOrangeToBlue() {

        Node source = App.getStationByName(testStations, "Chinatown");
        Node destination = App.getStationByName(testStations, "Bowdoin");

        // Tests Obtained Route Correct for the Orange Chinatown to Blue Bowdoin
        // swapping at DowntownCrossing,ParkStreet,GovernmentCenter
        testPath = testGraph.getPath(source, destination);
        String label = testPath.get(0).getLabel();
        assertEquals("Orange", label);

        Node orangeNode = source;
        assertEquals("Chinatown", orangeNode.getName());
        orangeNode = testPath.get(0).getOppositeNode(orangeNode);
        assertEquals("DowntownCrossing", orangeNode.getName());

        label = testPath.get(1).getLabel();
        assertEquals("Orange", label);

        orangeNode = testPath.get(1).getOppositeNode(orangeNode);
        assertEquals("State", orangeNode.getName());

        label = testPath.get(2).getLabel();
        assertEquals("Blue", label);

        Node blueNode = testPath.get(2).getOppositeNode(orangeNode);
        assertEquals("GovernmentCenter", blueNode.getName());

        label = testPath.get(3).getLabel();
        assertEquals("Blue", label);

        blueNode = testPath.get(3).getOppositeNode(blueNode);
        assertEquals("Bowdoin", blueNode.getName());

    }

    /* Tests MultiGraph getPath() optimal route from Blue Aquarium to Red Charles/MGH via GovernmentCenter and ParkStreet */
    @org.junit.jupiter.api.Test
    void multiGraphBlueToRed() {

        Node source = App.getStationByName(testStations, "Aquarium");
        Node destination = App.getStationByName(testStations, "Charles/MGH");

        // Tests Obtained Route Correct for the Blue Aquarium to Red Charles/MGH
        testPath = testGraph.getPath(source, destination);
        String label = testPath.get(0).getLabel();
        assertEquals("Blue", label);

        Node blueNode = source;
        assertEquals("Aquarium", blueNode.getName());
        blueNode = testPath.get(0).getOppositeNode(blueNode);
        assertEquals("State", blueNode.getName());
        blueNode = testPath.get(1).getOppositeNode(blueNode);
        assertEquals("GovernmentCenter", blueNode.getName());

        label = testPath.get(2).getLabel();
        assertEquals("Green", label);

        Node greenNode = testPath.get(2).getOppositeNode(blueNode);
        assertEquals("ParkStreet", greenNode.getName());

        label = testPath.get(3).getLabel();
        assertEquals("Red", label);

        Node redNode = testPath.get(3).getOppositeNode(greenNode);
        assertEquals("Charles/MGH", redNode.getName());
    }

    /* Tests MultiGraph getPath() optimal route from Blue Aquarium to Orange/Green Haymarket via State */
    @org.junit.jupiter.api.Test
    void multiGraphBlueToOrangeGreen() {

        // Tests Obtained Route Correct for the Blue Aquarium to Orange/Green Haymarket via State
        Node source = App.getStationByName(testStations, "Aquarium");
        Node destination = App.getStationByName(testStations, "Haymarket");

        testPath = testGraph.getPath(source, destination);

        String label = testPath.get(0).getLabel();
        assertEquals("Blue", label);

        Node blueNode = source;
        assertEquals("Aquarium", blueNode.getName());
        blueNode = testPath.get(0).getOppositeNode(blueNode);
        assertEquals("State", blueNode.getName());

        label = testPath.get(1).getLabel();
        assertEquals("Orange", label);

        Node orangeNode = testPath.get(1).getOppositeNode(blueNode);
        assertEquals("Haymarket", orangeNode.getName());
    }
}
