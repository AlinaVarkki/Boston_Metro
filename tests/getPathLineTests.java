import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class getPathLineTests {

    //Used to Test Station and Connection
    Node a, b;
    Edge<Node> c;

    //User for Testing MultiGraph Methods
    List<Edge<Node>> testConnections;
    List<Node> testStations;
    MultiGraph<Node, Edge<Node>> testGraph;
    List<Edge<Node>> testPath;

    /* Creates a Connection & two new Stations and Reads in bostonmetro.txt*/
    @org.junit.jupiter.api.BeforeEach
    void setUp() {

        // Used for setStationName, setConnectionNameAndNodes
        a = new Station("1", "StationA");
        b = new Station("2", "StationB");
        c = new Connection<>(a, b, "Pink");

        FileReader reader = new FileReader("resources/bostonmetro.txt");
        testStations = reader.getStations();
        testConnections = reader.getConnections();

        testGraph = new MultiGraph<>();
        for (Node n : testStations) testGraph.addNode(n);
        for (Edge e : testConnections) testGraph.addEdge(e);

    }

    /* Tests Set and Get of Station Class implementing Node Interface */
    @org.junit.jupiter.api.Test
    void setStationName() {
        a.setName("A");
        assertEquals("A", a.getName());
    }

    /* Tests Get and Opposite of Connection Class implementing Generic Edge Interface */
    @org.junit.jupiter.api.Test
    void setConnectionNameAndNodes() {
        assertEquals("Pink", c.getLabel());
        assertEquals("StationA", c.getNode1().getName());
        assertEquals("StationB", c.getNode2().getName());
        assertEquals("StationA", c.getOppositeNode(b).getName());
    }

    /* Tests fileRead */
    @org.junit.jupiter.api.Test
    void fileReader() {
        FileReader reader = new FileReader("resources/bostonmetro.txt");
        List<Node> testStations = reader.getStations();
        List<Edge<Node>> testConnections = reader.getConnections();

        // Tests Names Correctly Read in from Data from start of the file to the end
        assertEquals("FenwoodRoad", testStations.get(0).getName());
        assertEquals("BrighamCircle", testStations.get(123).getName());
        assertEquals(124,testStations.size());


        /* Tests Connections Correctly Read in from Data (Bi-directional) */

        // Tests Basic Case of Orange line between OakGrove and Malden Read in Correctly
        Node a = testConnections.get(0).getNode1();
        Node b = testConnections.get(0).getNode2();
        assertEquals("OakGrove", a.getName());
        assertEquals("Malden", b.getName());
        assertEquals("Orange", testConnections.get(0).getLabel());

    }

    /**
     * Below Test Correct Continuity of Stations on Each Line using standard getPath() method
     * Blue, Orange, Red, RedA, Mattapan, Green, GreenB, GreenC, GreenD, GreenE
     */

    /* Tests MultiGraph getPath() of the Blue Line */
    @org.junit.jupiter.api.Test
    void multiGraphBlueLine() {

        // Tests Nodes and Edges Correctly Set Up for the Blue line
        Node source = App.getStationByName(testStations, "Bowdoin");
        Node destination = App.getStationByName(testStations, "Wonderland");
        testPath = testGraph.getPath(source, destination);

        Node blueNode = source;
        assertEquals("Bowdoin", blueNode.getName());
        String label = testPath.get(0).getLabel();
        assertEquals("Blue", label);

        blueNode = testPath.get(1).getOppositeNode(blueNode);
        assertEquals("GovernmentCenter", blueNode.getName());
        label = testPath.get(1).getLabel();
        assertEquals("Blue", label);

        blueNode = testPath.get(2).getOppositeNode(blueNode);
        assertEquals("State", blueNode.getName());
        label = testPath.get(2).getLabel();
        assertEquals("Blue", label);

        blueNode = testPath.get(3).getOppositeNode(blueNode);
        assertEquals("Aquarium", blueNode.getName());
        label = testPath.get(3).getLabel();
        assertEquals("Blue", label);

        blueNode = testPath.get(4).getOppositeNode(blueNode);
        assertEquals("Maverick", blueNode.getName());
        label = testPath.get(4).getLabel();
        assertEquals("Blue", label);

        blueNode = testPath.get(5).getOppositeNode(blueNode);
        assertEquals("Airport", blueNode.getName());
        label = testPath.get(5).getLabel();
        assertEquals("Blue", label);

        blueNode = testPath.get(6).getOppositeNode(blueNode);
        assertEquals("WoodIsland", blueNode.getName());
        label = testPath.get(6).getLabel();
        assertEquals("Blue", label);

        blueNode = testPath.get(7).getOppositeNode(blueNode);
        assertEquals("OrientHeights", blueNode.getName());
        label = testPath.get(7).getLabel();
        assertEquals("Blue", label);

        blueNode = testPath.get(8).getOppositeNode(blueNode);
        assertEquals("SuffolkDowns", blueNode.getName());
        label = testPath.get(8).getLabel();
        assertEquals("Blue", label);

        blueNode = testPath.get(9).getOppositeNode(blueNode);
        assertEquals("Beachmont", blueNode.getName());
        label = testPath.get(9).getLabel();
        assertEquals("Blue", label);

        blueNode = testPath.get(10).getOppositeNode(blueNode);
        assertEquals("RevereBeach", blueNode.getName());
        label = testPath.get(10).getLabel();
        assertEquals("Blue", label);

        blueNode = testPath.get(10).getOppositeNode(blueNode);
        assertEquals("Wonderland", blueNode.getName());

    }

    /* Tests MultiGraph getPath() of the Orange Line */
    @org.junit.jupiter.api.Test
    void multiGraphOrangeLine() {

        Station source = (Station) App.getStationByName(testStations, "OakGrove");
        // Tests Nodes and Edges Correctly Set Up for the Orange line
        testPath = testGraph.getPath(App.getStationByName(testStations, "OakGrove"), App.getStationByName(testStations, "ForestHills"));
        Node orangeNode = testPath.get(0).getNode1();
        assertEquals("OakGrove", orangeNode.getName());
        String label = testPath.get(0).getLabel();
        assertEquals("Orange", label);

        Node orangeNodes = testPath.get(1).getOppositeNode(orangeNode);
        assertEquals("Malden", orangeNodes.getName());
        label = testPath.get(1).getLabel();
        assertEquals("Orange", label);

        orangeNode = testPath.get(2).getNode1();
        assertEquals("Wellington", orangeNode.getName());
        label = testPath.get(2).getLabel();
        assertEquals("Orange", label);

        orangeNodes = testPath.get(3).getNode1();
        assertEquals("SullivanSquare", orangeNodes.getName());
        label = testPath.get(3).getLabel();
        assertEquals("Orange", label);

        orangeNodes = testPath.get(4).getNode1();
        assertEquals("CommunityCollege", orangeNodes.getName());
        label = testPath.get(4).getLabel();
        assertEquals("Orange", label);

        orangeNodes = testPath.get(5).getNode1();
        assertEquals("NorthStation", orangeNodes.getName());
        label = testPath.get(5).getLabel();
        assertEquals("Orange", label);

        orangeNodes = testPath.get(6).getNode1();
        assertEquals("Haymarket", orangeNodes.getName());
        label = testPath.get(6).getLabel();
        assertEquals("Orange", label);

        orangeNodes = testPath.get(7).getNode1();
        assertEquals("State", orangeNodes.getName());
        label = testPath.get(7).getLabel();
        assertEquals("Orange", label);

        orangeNodes = testPath.get(8).getNode1();
        assertEquals("DowntownCrossing", orangeNodes.getName());
        label = testPath.get(8).getLabel();
        assertEquals("Orange", label);

        orangeNodes = testPath.get(9).getNode1();
        assertEquals("Chinatown", orangeNodes.getName());
        label = testPath.get(9).getLabel();
        assertEquals("Orange", label);

        orangeNodes = testPath.get(10).getNode1();
        assertEquals("NewEnglandMedicalCenter", orangeNodes.getName());
        label = testPath.get(10).getLabel();
        assertEquals("Orange", label);

        orangeNodes = testPath.get(11).getNode1();
        assertEquals("BackBay/SouthEnd", orangeNodes.getName());
        label = testPath.get(11).getLabel();
        assertEquals("Orange", label);

        orangeNodes = testPath.get(12).getNode1();
        assertEquals("MassachusettsAvenue", orangeNodes.getName());
        label = testPath.get(12).getLabel();
        assertEquals("Orange", label);

        orangeNodes = testPath.get(13).getNode1();
        assertEquals("Ruggles", orangeNodes.getName());
        label = testPath.get(13).getLabel();
        assertEquals("Orange", label);

        orangeNodes = testPath.get(14).getNode1();
        assertEquals("RoxburyCrossing", orangeNodes.getName());
        label = testPath.get(14).getLabel();
        assertEquals("Orange", label);

        orangeNodes = testPath.get(15).getNode1();
        assertEquals("JacksonSquare", orangeNodes.getName());
        label = testPath.get(15).getLabel();
        assertEquals("Orange", label);

        orangeNodes = testPath.get(16).getNode1();
        assertEquals("StonyBrook", orangeNodes.getName());
        label = testPath.get(16).getLabel();
        assertEquals("Orange", label);

        orangeNodes = testPath.get(17).getNode1();
        assertEquals("GreenStreet", orangeNodes.getName());
        label = testPath.get(17).getLabel();
        assertEquals("Orange", label);

        orangeNodes = testPath.get(17).getNode2();
        assertEquals("ForestHills", orangeNodes.getName());
    }

    /* Tests MultiGraph getPath() of the Red Line */
    @org.junit.jupiter.api.Test
    void multiGraphRedLine() {

        Node source = App.getStationByName(testStations, "Alewife");
        Node destination = App.getStationByName(testStations, "JFK/UMass");
        // Tests Nodes and Edges Correctly Set Up for the Red line
        testPath = testGraph.getPath(source, destination);
        Node redNode = source;
        assertEquals("Alewife", redNode.getName());
        String label = testPath.get(0).getLabel();
        assertEquals("Red", label);

        redNode = testPath.get(0).getOppositeNode(redNode);
        assertEquals("Davis", redNode.getName());
        label = testPath.get(1).getLabel();
        assertEquals("Red", label);

        redNode = testPath.get(1).getOppositeNode(redNode);
        assertEquals("Porter", redNode.getName());
        label = testPath.get(2).getLabel();
        assertEquals("Red", label);

        redNode = testPath.get(2).getOppositeNode(redNode);
        assertEquals("Harvard", redNode.getName());
        label = testPath.get(3).getLabel();
        assertEquals("Red", label);

        redNode = testPath.get(3).getOppositeNode(redNode);
        assertEquals("Central", redNode.getName());
        label = testPath.get(4).getLabel();
        assertEquals("Red", label);

        redNode = testPath.get(4).getOppositeNode(redNode);
        assertEquals("Kendall", redNode.getName());
        label = testPath.get(5).getLabel();
        assertEquals("Red", label);

        redNode = testPath.get(5).getOppositeNode(redNode);
        assertEquals("Charles/MGH", redNode.getName());
        label = testPath.get(6).getLabel();
        assertEquals("Red", label);

        redNode = testPath.get(6).getOppositeNode(redNode);
        assertEquals("ParkStreet", redNode.getName());
        label = testPath.get(7).getLabel();
        assertEquals("Red", label);

        redNode = testPath.get(7).getOppositeNode(redNode);
        assertEquals("DowntownCrossing", redNode.getName());
        label = testPath.get(8).getLabel();
        assertEquals("Red", label);

        redNode = testPath.get(8).getOppositeNode(redNode);
        assertEquals("SouthStation", redNode.getName());
        label = testPath.get(9).getLabel();
        assertEquals("Red", label);

        redNode = testPath.get(9).getOppositeNode(redNode);
        assertEquals("Broadway", redNode.getName());
        label = testPath.get(10).getLabel();
        assertEquals("Red", label);

        redNode = testPath.get(10).getOppositeNode(redNode);
        assertEquals("Andrew", redNode.getName());
        label = testPath.get(11).getLabel();
        assertEquals("Red", label);

        redNode = testPath.get(11).getOppositeNode(redNode);
        assertEquals("JFK/UMass", redNode.getName());

    }

    /* Tests MultiGraph getPath() of the RedB Line */
    @org.junit.jupiter.api.Test
    void multiGraphRedBLine() {

        // Tests Nodes and Edges Correctly Set Up for the RedB line
        testPath = testGraph.getPath(App.getStationByName(testStations, "JFK/UMass"), App.getStationByName(testStations, "Braintree"));
        Node redANode = testPath.get(0).getNode1();
        assertEquals("JFK/UMass", redANode.getName());
        String label = testPath.get(0).getLabel();
        assertEquals("RedB", label);

        Node redANodes = testPath.get(1).getOppositeNode(redANode);
        assertEquals("NorthQuincy", redANodes.getName());
        label = testPath.get(1).getLabel();
        assertEquals("RedB", label);

        redANode = testPath.get(2).getNode1();
        assertEquals("Wollaston", redANode.getName());
        label = testPath.get(2).getLabel();
        assertEquals("RedB", label);

        redANode = testPath.get(3).getNode1();
        assertEquals("QuincyCenter", redANode.getName());
        label = testPath.get(3).getLabel();
        assertEquals("RedB", label);

        redANode = testPath.get(4).getNode1();
        assertEquals("QuincyAdams", redANode.getName());
        label = testPath.get(4).getLabel();
        assertEquals("RedB", label);

        redANode = testPath.get(4).getNode2();
        assertEquals("Braintree", redANode.getName());
    }

    /* Tests MultiGraph getPath() of the RedA Line */
    @org.junit.jupiter.api.Test
    void multiGraphRedALine() {

        // Tests Nodes and Edges Correctly Set Up for the RedA line
        testPath = testGraph.getPath(App.getStationByName(testStations, "JFK/UMass"), App.getStationByName(testStations, "Ashmont"));
        Node redBNode = testPath.get(0).getNode1();
        assertEquals("JFK/UMass", redBNode.getName());
        String label = testPath.get(0).getLabel();
        assertEquals("RedA", label);

        Node redANodes = testPath.get(0).getOppositeNode(redBNode);
        assertEquals("SavinHill", redANodes.getName());
        label = testPath.get(1).getLabel();
        assertEquals("RedA", label);

        redBNode = testPath.get(1).getNode2();
        assertEquals("FieldsCorner", redBNode.getName());
        label = testPath.get(2).getLabel();
        assertEquals("RedA", label);

        redBNode = testPath.get(2).getOppositeNode(redBNode);
        assertEquals("Shawmut", redBNode.getName());
        label = testPath.get(3).getLabel();
        assertEquals("RedA", label);

        redBNode = testPath.get(3).getOppositeNode(redBNode);
        assertEquals("Ashmont", redBNode.getName());

    }

    /* Tests MultiGraph getPath() of the Mattapan Line */
    @org.junit.jupiter.api.Test
    void multiGraphMattapanLine() {

        // Tests Nodes and Edges Correctly Set Up for the Mattapan line
        testPath = testGraph.getPath(App.getStationByName(testStations, "Ashmont"), App.getStationByName(testStations, "Mattapan"));
        Node mattapanNode = testPath.get(0).getNode1();
        assertEquals("Ashmont", mattapanNode.getName());
        String label = testPath.get(0).getLabel();
        assertEquals("Mattapan", label);

        Node mattapanNodes = testPath.get(1).getOppositeNode(mattapanNode);
        assertEquals("CedarGrove", mattapanNodes.getName());
        label = testPath.get(1).getLabel();
        assertEquals("Mattapan", label);

        mattapanNode = testPath.get(2).getNode1();
        assertEquals("ButlerStreet", mattapanNode.getName());
        label = testPath.get(2).getLabel();
        assertEquals("Mattapan", label);

        mattapanNode = testPath.get(3).getNode1();
        assertEquals("Milton", mattapanNode.getName());
        label = testPath.get(3).getLabel();
        assertEquals("Mattapan", label);

        mattapanNode = testPath.get(4).getNode1();
        assertEquals("CentralAvenue", mattapanNode.getName());
        label = testPath.get(4).getLabel();
        assertEquals("Mattapan", label);

        mattapanNode = testPath.get(5).getNode1();
        assertEquals("ValleyRoad", mattapanNode.getName());
        label = testPath.get(5).getLabel();
        assertEquals("Mattapan", label);

        mattapanNode = testPath.get(6).getNode1();
        assertEquals("CapenStreet", mattapanNode.getName());
        label = testPath.get(6).getLabel();
        assertEquals("Mattapan", label);

        mattapanNode = testPath.get(6).getNode2();
        assertEquals("Mattapan", mattapanNode.getName());
    }

    /* Tests MultiGraph getPath() of the Green Line */
    @org.junit.jupiter.api.Test
    void multiGraphGreenLine() {

        // Tests Nodes and Edges Correctly Set Up for the Green line
        testPath = testGraph.getPath(App.getStationByName(testStations, "Lechmere"), App.getStationByName(testStations, "Copley"));
        Node greenNode = testPath.get(0).getNode1();
        assertEquals("Lechmere", greenNode.getName());
        String label = testPath.get(0).getLabel();
        assertEquals("Green", label);

        Node greenNodes = testPath.get(1).getOppositeNode(greenNode);
        assertEquals("SciencePark", greenNodes.getName());
        label = testPath.get(1).getLabel();
        assertEquals("Green", label);

        greenNode = testPath.get(2).getNode1();
        assertEquals("NorthStation", greenNode.getName());
        label = testPath.get(2).getLabel();
        assertEquals("Green", label);

        greenNode = testPath.get(3).getNode1();
        assertEquals("Haymarket", greenNode.getName());
        label = testPath.get(3).getLabel();
        assertEquals("Green", label);

        greenNode = testPath.get(4).getNode1();
        assertEquals("GovernmentCenter", greenNode.getName());
        label = testPath.get(4).getLabel();
        assertEquals("Green", label);

        greenNode = testPath.get(5).getNode1();
        assertEquals("ParkStreet", greenNode.getName());
        label = testPath.get(5).getLabel();
        assertEquals("Green", label);

        greenNode = testPath.get(6).getNode1();
        assertEquals("Boylston", greenNode.getName());
        label = testPath.get(6).getLabel();
        assertEquals("Green", label);

        greenNode = testPath.get(7).getNode1();
        assertEquals("Arlington", greenNode.getName());
        label = testPath.get(7).getLabel();
        assertEquals("Green", label);

        greenNode = testPath.get(7).getNode2();
        assertEquals("Copley", greenNode.getName());
    }

    /* Tests MultiGraph getPath() of the GreenB Line */
    @org.junit.jupiter.api.Test
    void multiGraphGreenBLine() {

        // Tests Nodes and Edges Correctly Set Up for the GreenB line
        Node source = App.getStationByName(testStations, "Copley");
        Node destination = App.getStationByName(testStations, "BostonCollege");
        testPath = testGraph.getPath(source, destination);
        Node greenBNode = source;
        assertEquals("Copley", greenBNode.getName());
        String label = testPath.get(0).getLabel();
        assertEquals("GreenB", label);

        greenBNode = testPath.get(0).getOppositeNode(greenBNode);
        assertEquals("Hynes/ICA", greenBNode.getName());
        label = testPath.get(1).getLabel();
        assertEquals("GreenB", label);

        greenBNode = testPath.get(1).getOppositeNode(greenBNode);
        assertEquals("Kenmore", greenBNode.getName());
        label = testPath.get(2).getLabel();
        assertEquals("GreenB", label);

        greenBNode = testPath.get(2).getOppositeNode(greenBNode);
        assertEquals("BlandfordStreet", greenBNode.getName());
        label = testPath.get(3).getLabel();
        assertEquals("GreenB", label);

        greenBNode = testPath.get(3).getOppositeNode(greenBNode);
        assertEquals("BostonUniversityEast", greenBNode.getName());
        label = testPath.get(4).getLabel();
        assertEquals("GreenB", label);

        greenBNode = testPath.get(4).getOppositeNode(greenBNode);
        assertEquals("BostonUniversityCentral", greenBNode.getName());
        label = testPath.get(5).getLabel();
        assertEquals("GreenB", label);

        greenBNode = testPath.get(5).getOppositeNode(greenBNode);
        assertEquals("BostonUniversityWest", greenBNode.getName());
        label = testPath.get(6).getLabel();
        assertEquals("GreenB", label);

        greenBNode = testPath.get(6).getOppositeNode(greenBNode);
        assertEquals("St.PaulStreet", greenBNode.getName());
        label = testPath.get(7).getLabel();
        assertEquals("GreenB", label);

        greenBNode = testPath.get(7).getOppositeNode(greenBNode);
        assertEquals("PleasantStreet", greenBNode.getName());
        label = testPath.get(8).getLabel();
        assertEquals("GreenB", label);

        greenBNode = testPath.get(8).getOppositeNode(greenBNode);
        assertEquals("BabcockStreet", greenBNode.getName());
        label = testPath.get(9).getLabel();
        assertEquals("GreenB", label);

        greenBNode = testPath.get(9).getOppositeNode(greenBNode);
        assertEquals("BrightonAvenue", greenBNode.getName());
        label = testPath.get(10).getLabel();
        assertEquals("GreenB", label);

        greenBNode = testPath.get(10).getOppositeNode(greenBNode);
        assertEquals("FordhamRoad", greenBNode.getName());
        label = testPath.get(11).getLabel();
        assertEquals("GreenB", label);

        greenBNode = testPath.get(11).getOppositeNode(greenBNode);
        assertEquals("HarvardAvenue", greenBNode.getName());
        label = testPath.get(12).getLabel();
        assertEquals("GreenB", label);

        greenBNode = testPath.get(12).getOppositeNode(greenBNode);
        assertEquals("GriggsStreet/LongwoodAvenue", greenBNode.getName());
        label = testPath.get(13).getLabel();
        assertEquals("GreenB", label);

        greenBNode = testPath.get(13).getOppositeNode(greenBNode);
        assertEquals("AllstonStreet", greenBNode.getName());
        label = testPath.get(14).getLabel();
        assertEquals("GreenB", label);

        greenBNode = testPath.get(14).getOppositeNode(greenBNode);
        assertEquals("WarrenStreet", greenBNode.getName());
        label = testPath.get(15).getLabel();
        assertEquals("GreenB", label);

        greenBNode = testPath.get(15).getOppositeNode(greenBNode);
        assertEquals("SummitAvenue", greenBNode.getName());
        label = testPath.get(16).getLabel();
        assertEquals("GreenB", label);

        greenBNode = testPath.get(16).getOppositeNode(greenBNode);
        assertEquals("WashingtonStreet", greenBNode.getName());
        label = testPath.get(17).getLabel();
        assertEquals("GreenB", label);

        greenBNode = testPath.get(17).getOppositeNode(greenBNode);
        assertEquals("MountHoodRoad", greenBNode.getName());
        label = testPath.get(18).getLabel();
        assertEquals("GreenB", label);

        greenBNode = testPath.get(18).getOppositeNode(greenBNode);
        assertEquals("SutherlandRoad", greenBNode.getName());
        label = testPath.get(19).getLabel();
        assertEquals("GreenB", label);

        greenBNode = testPath.get(19).getOppositeNode(greenBNode);
        assertEquals("ChiswickRoad", greenBNode.getName());
        label = testPath.get(20).getLabel();
        assertEquals("GreenB", label);

        greenBNode = testPath.get(20).getOppositeNode(greenBNode);
        assertEquals("ChestnutHillAvenue", greenBNode.getName());
        label = testPath.get(21).getLabel();
        assertEquals("GreenB", label);

        greenBNode = testPath.get(21).getOppositeNode(greenBNode);
        assertEquals("SouthStreet", greenBNode.getName());
        label = testPath.get(22).getLabel();
        assertEquals("GreenB", label);

        greenBNode = testPath.get(22).getOppositeNode(greenBNode);
        assertEquals("GreycliffRoad", greenBNode.getName());
        label = testPath.get(23).getLabel();
        assertEquals("GreenB", label);

        greenBNode = testPath.get(23).getOppositeNode(greenBNode);
        assertEquals("BostonCollege", greenBNode.getName());
    }

    /* Tests MultiGraph getPath() of the GreenC Line */
    @org.junit.jupiter.api.Test
    void multiGraphGreenCLine() {

        // Tests Nodes and Edges Correctly Set Up for the GreenC line
        testPath = testGraph.getPath(App.getStationByName(testStations, "Kenmore"), App.getStationByName(testStations, "ClevelandCircle"));
        Node greenCNode = testPath.get(0).getNode1();
        assertEquals("Kenmore", greenCNode.getName());
        String label = testPath.get(0).getLabel();
        assertEquals("GreenC", label);

        Node greenCNodes = testPath.get(0).getOppositeNode(greenCNode);
        assertEquals("St.Mary'sStreet", greenCNodes.getName());
        label = testPath.get(1).getLabel();
        assertEquals("GreenC", label);

        greenCNode = testPath.get(1).getNode2();
        assertEquals("HawesStreet", greenCNode.getName());
        label = testPath.get(2).getLabel();
        assertEquals("GreenC", label);

        greenCNode = testPath.get(2).getNode2();
        assertEquals("KentStreet", greenCNode.getName());
        label = testPath.get(3).getLabel();
        assertEquals("GreenC", label);

        greenCNode = testPath.get(3).getNode2();
        assertEquals("St.PaulStreet", greenCNode.getName());
        label = testPath.get(4).getLabel();
        assertEquals("GreenC", label);

        greenCNode = testPath.get(4).getNode2();
        assertEquals("CoolidgeCorner", greenCNode.getName());
        label = testPath.get(5).getLabel();
        assertEquals("GreenC", label);

        greenCNode = testPath.get(5).getNode2();
        assertEquals("WinchesterStreet/SummitAv.", greenCNode.getName());
        label = testPath.get(6).getLabel();
        assertEquals("GreenC", label);

        greenCNode = testPath.get(6).getNode2();
        assertEquals("BrandonHall", greenCNode.getName());
        label = testPath.get(7).getLabel();
        assertEquals("GreenC", label);

        greenCNode = testPath.get(7).getNode2();
        assertEquals("FairbanksStreet", greenCNode.getName());
        label = testPath.get(8).getLabel();
        assertEquals("GreenC", label);

        greenCNode = testPath.get(8).getNode2();
        assertEquals("WashingtonSquare", greenCNode.getName());
        label = testPath.get(9).getLabel();
        assertEquals("GreenC", label);

        greenCNode = testPath.get(9).getNode2();
        assertEquals("TappanStreet", greenCNode.getName());
        label = testPath.get(10).getLabel();
        assertEquals("GreenC", label);

        greenCNode = testPath.get(10).getNode2();
        assertEquals("DeanRoad", greenCNode.getName());
        label = testPath.get(11).getLabel();
        assertEquals("GreenC", label);

        greenCNode = testPath.get(11).getNode2();
        assertEquals("EnglewoodAvenue", greenCNode.getName());
        label = testPath.get(12).getLabel();
        assertEquals("GreenC", label);

        greenCNode = testPath.get(12).getNode2();
        assertEquals("ClevelandCircle", greenCNode.getName());
    }

    /* Tests MultiGraph getPath() of the GreenD Line */
    @org.junit.jupiter.api.Test
    void multiGraphGreenDLine() {

        // Tests Nodes and Edges Correctly Set Up for the GreenD line
        Node source = App.getStationByName(testStations, "Kenmore");
        Node destination = App.getStationByName(testStations, "Riverside");
        testPath = testGraph.getPath(source, destination);
        Node greenDNode = source;
        assertEquals("Kenmore", greenDNode.getName());
        String label = testPath.get(0).getLabel();
        assertEquals("GreenD", label);

        greenDNode = testPath.get(0).getOppositeNode(greenDNode);
        assertEquals("Fenway", greenDNode.getName());
        label = testPath.get(1).getLabel();
        assertEquals("GreenD", label);

        greenDNode = testPath.get(1).getOppositeNode(greenDNode);
        assertEquals("Longwood", greenDNode.getName());
        label = testPath.get(2).getLabel();
        assertEquals("GreenD", label);

        greenDNode = testPath.get(2).getOppositeNode(greenDNode);
        assertEquals("BrooklineVillage", greenDNode.getName());
        label = testPath.get(3).getLabel();
        assertEquals("GreenD", label);

        greenDNode = testPath.get(3).getOppositeNode(greenDNode);
        assertEquals("BrooklineHills", greenDNode.getName());
        label = testPath.get(4).getLabel();
        assertEquals("GreenD", label);

        greenDNode = testPath.get(4).getOppositeNode(greenDNode);
        assertEquals("Beaconsfield", greenDNode.getName());
        label = testPath.get(5).getLabel();
        assertEquals("GreenD", label);

        greenDNode = testPath.get(5).getOppositeNode(greenDNode);
        assertEquals("Reservoir", greenDNode.getName());
        label = testPath.get(6).getLabel();
        assertEquals("GreenD", label);

        greenDNode = testPath.get(6).getOppositeNode(greenDNode);
        assertEquals("ChesnutHill", greenDNode.getName());
        label = testPath.get(7).getLabel();
        assertEquals("GreenD", label);

        greenDNode = testPath.get(7).getOppositeNode(greenDNode);
        assertEquals("NewtonCenter", greenDNode.getName());
        label = testPath.get(8).getLabel();
        assertEquals("GreenD", label);

        greenDNode = testPath.get(8).getOppositeNode(greenDNode);
        assertEquals("NewtonHighlands", greenDNode.getName());
        label = testPath.get(9).getLabel();
        assertEquals("GreenD", label);

        greenDNode = testPath.get(9).getOppositeNode(greenDNode);
        assertEquals("Eliot", greenDNode.getName());
        label = testPath.get(10).getLabel();
        assertEquals("GreenD", label);

        greenDNode = testPath.get(10).getOppositeNode(greenDNode);
        assertEquals("Waban", greenDNode.getName());
        label = testPath.get(11).getLabel();
        assertEquals("GreenD", label);

        greenDNode = testPath.get(11).getOppositeNode(greenDNode);
        assertEquals("Woodland", greenDNode.getName());
        label = testPath.get(12).getLabel();
        assertEquals("GreenD", label);

        greenDNode = testPath.get(12).getOppositeNode(greenDNode);
        assertEquals("Riverside", greenDNode.getName());
    }

    /* Tests MultiGraph getPath() of the GreenE Line */
    @org.junit.jupiter.api.Test
    void multiGraphGreenELine() {

        // Tests Nodes and Edges Correctly Set Up for the GreenE line
        testPath = testGraph.getPath(App.getStationByName(testStations, "Copley"), App.getStationByName(testStations, "HeathStreet"));
        Node greenENode = testPath.get(0).getNode1();
        assertEquals("Copley", greenENode.getName());
        String label = testPath.get(0).getLabel();
        assertEquals("GreenE", label);

        Node greenENodes = testPath.get(0).getOppositeNode(greenENode);
        assertEquals("Prudential", greenENodes.getName());
        label = testPath.get(1).getLabel();
        assertEquals("GreenE", label);

        greenENode = testPath.get(1).getNode2();
        assertEquals("Symphony", greenENode.getName());
        label = testPath.get(2).getLabel();
        assertEquals("GreenE", label);

        greenENode = testPath.get(2).getNode2();
        assertEquals("NortheasternUniversity", greenENode.getName());
        label = testPath.get(3).getLabel();
        assertEquals("GreenE", label);

        greenENode = testPath.get(3).getNode2();
        assertEquals("MuseumofFineArts", greenENode.getName());
        label = testPath.get(4).getLabel();
        assertEquals("GreenE", label);

        greenENode = testPath.get(4).getNode2();
        assertEquals("LongwoodMedicalArea", greenENode.getName());
        label = testPath.get(5).getLabel();
        assertEquals("GreenE", label);

        greenENode = testPath.get(5).getNode2();
        assertEquals("BrighamCircle", greenENode.getName());
        label = testPath.get(6).getLabel();
        assertEquals("GreenE", label);

        greenENode = testPath.get(6).getNode2();
        assertEquals("FenwoodRoad", greenENode.getName());
        label = testPath.get(7).getLabel();
        assertEquals("GreenE", label);

        greenENode = testPath.get(7).getNode2();
        assertEquals("MissionPark", greenENode.getName());
        label = testPath.get(8).getLabel();
        assertEquals("GreenE", label);

        greenENode = testPath.get(8).getNode2();
        assertEquals("Riverway", greenENode.getName());
        label = testPath.get(9).getLabel();
        assertEquals("GreenE", label);

        greenENode = testPath.get(9).getNode2();
        assertEquals("BackOfTheHill", greenENode.getName());
        label = testPath.get(10).getLabel();
        assertEquals("GreenE", label);

        greenENode = testPath.get(10).getNode2();
        assertEquals("HeathStreet", greenENode.getName());

    }

}
