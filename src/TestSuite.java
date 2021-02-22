import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestSuite {

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

        FileReader reader = new FileReader();
        reader.readInGraph("src/bostonmetro.txt");
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
        FileReader reader = new FileReader();
        reader.readInGraph("src/bostonmetro.txt");
        List<Node> testStations = reader.getStations();
        List<Edge<Node>> testConnections = reader.getConnections();

        // Tests Names Correctly Read in from Test Data from start of the file to the end
        assertEquals("FenwoodRoad", testStations.get(0).getName());
        assertEquals("BrighamCircle", testStations.get(123).getName());


        /* Tests Connections Correctly Read in from Test Data (Bi-directional) */

        // Tests Basic Case of Pink line between Glasgow and Stirling Read in Correctly
        Node a = testConnections.get(0).getNode1();
        Node b = testConnections.get(0).getNode2();
        assertEquals("OakGrove", a.getName());
        assertEquals("Malden", b.getName());
        assertEquals("Orange", testConnections.get(0).getLabel());

        // Tests Bi-directional Case of Pink Line Back between Stirling and Glasgow Read in Correctly
        a = testConnections.get(1).getNode1();
        b = testConnections.get(1).getNode2();
        assertEquals("Malden", a.getName());
        assertEquals("OakGrove", b.getName());
        assertEquals("Orange", testConnections.get(1).getLabel());

    }

    /**
     * Below Test Correct Continuity of Stations on Each Line
     * Blue, Orange, Red, RedA, Mattapan, Green, GreenB, GreenC, GreenD, GreenE
     */

    /* Tests MultiGraph getPath() of the Blue Line using Official Data */
    @org.junit.jupiter.api.Test
    void multiGraphBlueLine() {

        // Tests Nodes and Edges Correctly Set Up for the Blue line
        testPath = testGraph.getPath(App.getStationByName(testStations, "Bowdoin"), App.getStationByName(testStations, "Wonderland"));

        Node blueNode = testPath.get(0).getNode1();
        assertEquals("Bowdoin", blueNode.getName());
        Node blueNodes = testPath.get(1).getOppositeNode(blueNode);
        assertEquals("GovernmentCenter", blueNodes.getName());
        blueNode = testPath.get(2).getNode2();
        assertEquals("State", blueNode.getName());
        blueNodes = testPath.get(3).getNode2();
        assertEquals("Aquarium", blueNodes.getName());
        blueNodes = testPath.get(4).getNode2();
        assertEquals("Maverick", blueNodes.getName());
        blueNodes = testPath.get(5).getNode2();
        assertEquals("Airport", blueNodes.getName());
        blueNodes = testPath.get(6).getNode2();
        assertEquals("WoodIsland", blueNodes.getName());
        blueNodes = testPath.get(7).getNode2();
        assertEquals("OrientHeights", blueNodes.getName());
        blueNodes = testPath.get(8).getNode2();
        assertEquals("SuffolkDowns", blueNodes.getName());
        blueNodes = testPath.get(9).getNode2();
        assertEquals("Beachmont", blueNodes.getName());
        blueNodes = testPath.get(10).getNode2();
        assertEquals("RevereBeach", blueNodes.getName());
        blueNodes = testPath.get(10).getNode1();
        assertEquals("Wonderland", blueNodes.getName());
    }

    /* Tests MultiGraph getPath() of the Orange Line using Official Data */
    @org.junit.jupiter.api.Test
    void multiGraphOrangeLine() {

        Station source = (Station) App.getStationByName(testStations, "OakGrove");
        // Tests Nodes and Edges Correctly Set Up for the Orange line
        testPath = testGraph.getPath(App.getStationByName(testStations, "OakGrove"), App.getStationByName(testStations, "ForestHills"));
        Node orangeNode = testPath.get(0).getNode1();
        assertEquals("OakGrove", orangeNode.getName());
        Node orangeNodes = testPath.get(1).getOppositeNode(orangeNode);
        assertEquals("Malden", orangeNodes.getName());
        orangeNode = testPath.get(2).getNode1();
        assertEquals("Wellington", orangeNode.getName());
        orangeNodes = testPath.get(3).getNode1();
        assertEquals("SullivanSquare", orangeNodes.getName());
        orangeNodes = testPath.get(4).getNode1();
        assertEquals("CommunityCollege", orangeNodes.getName());
        orangeNodes = testPath.get(5).getNode1();
        assertEquals("NorthStation", orangeNodes.getName());
        orangeNodes = testPath.get(6).getNode1();
        assertEquals("Haymarket", orangeNodes.getName());
        orangeNodes = testPath.get(7).getNode1();
        assertEquals("State", orangeNodes.getName());
        orangeNodes = testPath.get(8).getNode1();
        assertEquals("DowntownCrossing", orangeNodes.getName());
        orangeNodes = testPath.get(9).getNode1();
        assertEquals("Chinatown", orangeNodes.getName());
        orangeNodes = testPath.get(10).getNode1();
        assertEquals("NewEnglandMedicalCenter", orangeNodes.getName());
        orangeNodes = testPath.get(11).getNode1();
        assertEquals("BackBay/SouthEnd", orangeNodes.getName());
        orangeNodes = testPath.get(12).getNode1();
        assertEquals("MassachusettsAvenue", orangeNodes.getName());
        orangeNodes = testPath.get(13).getNode1();
        assertEquals("Ruggles", orangeNodes.getName());
        orangeNodes = testPath.get(14).getNode1();
        assertEquals("RoxburyCrossing", orangeNodes.getName());
        orangeNodes = testPath.get(15).getNode1();
        assertEquals("JacksonSquare", orangeNodes.getName());
        orangeNodes = testPath.get(16).getNode1();
        assertEquals("StonyBrook", orangeNodes.getName());
        orangeNodes = testPath.get(17).getNode1();
        assertEquals("GreenStreet", orangeNodes.getName());
        orangeNodes = testPath.get(17).getNode2();
        assertEquals("ForestHills", orangeNodes.getName());

        App.printPath(testPath, source);
    }

    /* Tests MultiGraph getPath() of the Red Line using Official Data */
    @org.junit.jupiter.api.Test
    void multiGraphRedLine() {

        Station source = (Station) App.getStationByName(testStations, "Alewife");
        // Tests Nodes and Edges Correctly Set Up for the Red line
        testPath = testGraph.getPath(App.getStationByName(testStations, "Alewife"), App.getStationByName(testStations, "JFK/UMass"));
        Node redNode = testPath.get(0).getNode2();
        assertEquals("Alewife", redNode.getName());
        Node redNodes = testPath.get(0).getOppositeNode(redNode);
        assertEquals("Davis", redNodes.getName());
        redNode = testPath.get(1).getNode2();
        assertEquals("Porter", redNode.getName());
        redNode = testPath.get(2).getNode2();
        assertEquals("Harvard", redNode.getName());
        redNode = testPath.get(3).getNode2();
        assertEquals("Central", redNode.getName());
        redNode = testPath.get(4).getNode2();
        assertEquals("Kendall", redNode.getName());
        redNode = testPath.get(5).getNode2();
        assertEquals("Charles/MGH", redNode.getName());
        redNode = testPath.get(6).getNode2();
        assertEquals("ParkStreet", redNode.getName());
        redNode = testPath.get(7).getNode2();
        assertEquals("DowntownCrossing", redNode.getName());
        redNode = testPath.get(8).getNode2();
        assertEquals("SouthStation", redNode.getName());
        redNode = testPath.get(9).getNode2();
        assertEquals("Broadway", redNode.getName());
        redNode = testPath.get(10).getNode2();
        assertEquals("Andrew", redNode.getName());
        redNode = testPath.get(11).getNode2();
        assertEquals("JFK/UMass", redNode.getName());

        for (int i = 0; i < testStations.size(); i++) {
            if (testStations.get(i).getName().equals("Davis")) {
                System.out.println(i + " Station:" + testStations.get(i).getName());
            }
        }

        for (int i = 0; i < testConnections.size(); i++) {
            Node a = testConnections.get(i).getNode1();
            Node b = testConnections.get(i).getNode2();
            if (a.getName().equals("Davis") || b.getName().equals("Davis")) {
                System.out.println(i + " Node1:" + a.getName() + " Node2:" + b.getName() + " Line:" + testConnections.get(i).getLabel());
            }
        }

        App.printPath(testPath, source);
    }

    /* Tests MultiGraph getPath() of the RedA Line */
    @org.junit.jupiter.api.Test
    void multiGraphRedALine() {

        // Tests Nodes and Edges Correctly Set Up for the RedA line
        testPath = testGraph.getPath(App.getStationByName(testStations, "JFK/UMass"), App.getStationByName(testStations, "Braintree"));
        Node redANode = testPath.get(0).getNode1();
        assertEquals("JFK/UMass", redANode.getName());
        Node redANodes = testPath.get(1).getOppositeNode(redANode);
        assertEquals("NorthQuincy", redANodes.getName());
        redANode = testPath.get(2).getNode1();
        assertEquals("Wollaston", redANode.getName());
        redANode = testPath.get(3).getNode1();
        assertEquals("QuincyCenter", redANode.getName());
        redANode = testPath.get(4).getNode1();
        assertEquals("QuincyAdams", redANode.getName());
        redANode = testPath.get(4).getNode2();
        assertEquals("Braintree", redANode.getName());
    }

    /* Tests MultiGraph getPath() of the Mattapan Line */
    @org.junit.jupiter.api.Test
    void multiGraphMattapanLine() {

        // Tests Nodes and Edges Correctly Set Up for the Mattapan line
        testPath = testGraph.getPath(App.getStationByName(testStations, "Ashmont"), App.getStationByName(testStations, "Mattapan"));
        Node mattapanNode = testPath.get(0).getNode1();
        assertEquals("Ashmont", mattapanNode.getName());
        Node mattapanNodes = testPath.get(1).getOppositeNode(mattapanNode);
        assertEquals("CedarGrove", mattapanNodes.getName());
        mattapanNode = testPath.get(2).getNode1();
        assertEquals("ButlerStreet", mattapanNode.getName());
        mattapanNode = testPath.get(3).getNode1();
        assertEquals("Milton", mattapanNode.getName());
        mattapanNode = testPath.get(4).getNode1();
        assertEquals("CentralAvenue", mattapanNode.getName());
        mattapanNode = testPath.get(5).getNode1();
        assertEquals("ValleyRoad", mattapanNode.getName());
        mattapanNode = testPath.get(6).getNode1();
        assertEquals("CapenStreet", mattapanNode.getName());
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
        Node greenNodes = testPath.get(1).getOppositeNode(greenNode);
        assertEquals("SciencePark", greenNodes.getName());
        greenNode = testPath.get(2).getNode1();
        assertEquals("NorthStation", greenNode.getName());
        greenNode = testPath.get(3).getNode1();
        assertEquals("Haymarket", greenNode.getName());
        greenNode = testPath.get(4).getNode1();
        assertEquals("GovernmentCenter", greenNode.getName());
        greenNode = testPath.get(5).getNode1();
        assertEquals("ParkStreet", greenNode.getName());
        greenNode = testPath.get(6).getNode1();
        assertEquals("Boylston", greenNode.getName());
        greenNode = testPath.get(7).getNode1();
        assertEquals("Arlington", greenNode.getName());
        greenNode = testPath.get(7).getNode2();
        assertEquals("Copley", greenNode.getName());
    }

    /* Tests MultiGraph getPath() of the GreenB Line */
    @org.junit.jupiter.api.Test
    void multiGraphGreenBLine() {

        // Tests Nodes and Edges Correctly Set Up for the GreenB line
        testPath = testGraph.getPath(App.getStationByName(testStations, "Copley"), App.getStationByName(testStations, "BostonCollege"));
        Node greenBNode = testPath.get(0).getNode1();
        assertEquals("Copley", greenBNode.getName());
        Node greenBNodes = testPath.get(0).getOppositeNode(greenBNode);
        assertEquals("Hynes/ICA", greenBNodes.getName());
        greenBNode = testPath.get(1).getNode1();
        assertEquals("Kenmore", greenBNode.getName());
        greenBNode = testPath.get(2).getNode1();
        assertEquals("BlandfordStreet", greenBNode.getName());
        greenBNode = testPath.get(3).getNode1();
        assertEquals("BostonUniversityEast", greenBNode.getName());
        greenBNode = testPath.get(4).getNode1();
        assertEquals("BostonUniversityCentral", greenBNode.getName());
        greenBNode = testPath.get(5).getNode1();
        assertEquals("BostonUniversityWest", greenBNode.getName());
        greenBNode = testPath.get(6).getNode1();
        assertEquals("St.PaulStreet", greenBNode.getName());
        greenBNode = testPath.get(7).getNode1();
        assertEquals("PleasantStreet", greenBNode.getName());
        greenBNode = testPath.get(8).getNode1();
        assertEquals("BabcockStreet", greenBNode.getName());
        greenBNode = testPath.get(9).getNode2();
        assertEquals("BrightonAvenue", greenBNode.getName());
        greenBNode = testPath.get(10).getNode2();
        assertEquals("FordhamRoad", greenBNode.getName());
        greenBNode = testPath.get(11).getNode2();
        assertEquals("HarvardAvenue", greenBNode.getName());
        greenBNode = testPath.get(12).getNode2();
        assertEquals("GriggsStreet/LongwoodAvenue", greenBNode.getName());
        greenBNode = testPath.get(13).getNode2();
        assertEquals("AllstonStreet", greenBNode.getName());
        greenBNode = testPath.get(14).getNode1();
        assertEquals("WarrenStreet", greenBNode.getName());
        greenBNode = testPath.get(15).getNode2();
        assertEquals("SummitAvenue", greenBNode.getName());
        greenBNode = testPath.get(16).getNode2();
        assertEquals("WashingtonStreet", greenBNode.getName());
        greenBNode = testPath.get(17).getNode2();
        assertEquals("MountHoodRoad", greenBNode.getName());
        greenBNode = testPath.get(18).getNode2();
        assertEquals("SutherlandRoad", greenBNode.getName());
        greenBNode = testPath.get(19).getNode2();
        assertEquals("ChiswickRoad", greenBNode.getName());
        greenBNode = testPath.get(20).getNode2();
        assertEquals("ChestnutHillAvenue", greenBNode.getName());
        greenBNode = testPath.get(21).getNode1();
        assertEquals("SouthStreet", greenBNode.getName());
        greenBNode = testPath.get(22).getNode1();
        assertEquals("GreycliffRoad", greenBNode.getName());
        greenBNode = testPath.get(23).getNode1();
        assertEquals("BostonCollege", greenBNode.getName());
    }

    /* Tests MultiGraph getPath() of the GreenC Line */
    @org.junit.jupiter.api.Test
    void multiGraphGreenCLine() {

        // Tests Nodes and Edges Correctly Set Up for the GreenC line
        testPath = testGraph.getPath(App.getStationByName(testStations, "Kenmore"), App.getStationByName(testStations, "ClevelandCircle"));
        Node greenCNode = testPath.get(0).getNode1();
        assertEquals("Kenmore", greenCNode.getName());
        Node greenCNodes = testPath.get(0).getOppositeNode(greenCNode);
        assertEquals("St.Mary'sStreet", greenCNodes.getName());
        greenCNode = testPath.get(1).getNode2();
        assertEquals("HawesStreet", greenCNode.getName());
        greenCNode = testPath.get(2).getNode2();
        assertEquals("KentStreet", greenCNode.getName());
        greenCNode = testPath.get(3).getNode2();
        assertEquals("St.PaulStreet", greenCNode.getName());
        greenCNode = testPath.get(4).getNode2();
        assertEquals("CoolidgeCorner", greenCNode.getName());
        greenCNode = testPath.get(5).getNode2();
        assertEquals("WinchesterStreet/SummitAv.", greenCNode.getName());
        greenCNode = testPath.get(6).getNode2();
        assertEquals("BrandonHall", greenCNode.getName());
        greenCNode = testPath.get(7).getNode2();
        assertEquals("FairbanksStreet", greenCNode.getName());
        greenCNode = testPath.get(8).getNode2();
        assertEquals("WashingtonSquare", greenCNode.getName());
        greenCNode = testPath.get(9).getNode2();
        assertEquals("TappanStreet", greenCNode.getName());
        greenCNode = testPath.get(10).getNode2();
        assertEquals("DeanRoad", greenCNode.getName());
        greenCNode = testPath.get(11).getNode2();
        assertEquals("EnglewoodAvenue", greenCNode.getName());
        greenCNode = testPath.get(12).getNode2();
        assertEquals("ClevelandCircle", greenCNode.getName());
    }

    /* Tests MultiGraph getPath() of the GreenD Line */
    @org.junit.jupiter.api.Test
    void multiGraphGreenDLine() {

        // Tests Nodes and Edges Correctly Set Up for the GreenD line
        testPath = testGraph.getPath(App.getStationByName(testStations, "Kenmore"), App.getStationByName(testStations, "Riverside"));
        Node greenDNode = testPath.get(0).getNode1();
        assertEquals("Kenmore", greenDNode.getName());
        Node greenDNodes = testPath.get(0).getOppositeNode(greenDNode);
        assertEquals("Fenway", greenDNodes.getName());
        greenDNode = testPath.get(1).getNode2();
        assertEquals("Longwood", greenDNode.getName());
        greenDNode = testPath.get(2).getNode2();
        assertEquals("BrooklineVillage", greenDNode.getName());
        greenDNode = testPath.get(3).getNode2();
        assertEquals("BrooklineHills", greenDNode.getName());
        greenDNode = testPath.get(4).getNode1();
        assertEquals("Beaconsfield", greenDNode.getName());
        greenDNode = testPath.get(5).getNode2();
        assertEquals("Reservoir", greenDNode.getName());
        greenDNode = testPath.get(6).getNode2();
        assertEquals("ChesnutHill", greenDNode.getName());
        greenDNode = testPath.get(7).getNode2();
        assertEquals("NewtonCenter", greenDNode.getName());
        greenDNode = testPath.get(8).getNode2();
        assertEquals("NewtonHighlands", greenDNode.getName());
        greenDNode = testPath.get(9).getNode2();
        assertEquals("Eliot", greenDNode.getName());
        greenDNode = testPath.get(10).getNode2();
        assertEquals("Waban", greenDNode.getName());
        greenDNode = testPath.get(11).getNode2();
        assertEquals("Woodland", greenDNode.getName());
        greenDNode = testPath.get(12).getNode2();
        assertEquals("Riverside", greenDNode.getName());

    }

    /* Tests MultiGraph getPath() of the GreenE Line */
    @org.junit.jupiter.api.Test
    void multiGraphGreenELine() {

        // Tests Nodes and Edges Correctly Set Up for the GreenE line
        testPath = testGraph.getPath(App.getStationByName(testStations, "Copley"), App.getStationByName(testStations, "HeathStreet"));
        Node greenENode = testPath.get(0).getNode1();
        assertEquals("Copley", greenENode.getName());
        Node greenENodes = testPath.get(0).getOppositeNode(greenENode);
        assertEquals("Prudential", greenENodes.getName());
        greenENode = testPath.get(1).getNode2();
        assertEquals("Symphony", greenENode.getName());
        greenENode = testPath.get(2).getNode2();
        assertEquals("NortheasternUniversity", greenENode.getName());
        greenENode = testPath.get(3).getNode2();
        assertEquals("MuseumofFineArts", greenENode.getName());
        greenENode = testPath.get(4).getNode2();
        assertEquals("LongwoodMedicalArea", greenENode.getName());
        greenENode = testPath.get(5).getNode2();
        assertEquals("BrighamCircle", greenENode.getName());
        greenENode = testPath.get(6).getNode2();
        assertEquals("FenwoodRoad", greenENode.getName());
        greenENode = testPath.get(7).getNode2();
        assertEquals("MissionPark", greenENode.getName());
        greenENode = testPath.get(8).getNode2();
        assertEquals("Riverway", greenENode.getName());
        greenENode = testPath.get(9).getNode2();
        assertEquals("BackOfTheHill", greenENode.getName());
        greenENode = testPath.get(10).getNode2();
        assertEquals("HeathStreet", greenENode.getName());

    }

    /**
     * Below Test Correct Continuity between Key Junction Stations on Red and Green Line
     * RedB to RedA, RedA to Mattapan,
     */

    /* Tests MultiGraph getPath() of the Red Junction Line between RedA and RedB */
    @org.junit.jupiter.api.Test
    void multiGraphRedJunctionLine() {

        // Tests Obtained Route Correct for the Red Junction line from RedB to RedA
        testPath = testGraph.getPath(App.getStationByName(testStations, "Wollaston"), App.getStationByName(testStations, "FieldsCorner"));
        String label = testPath.get(0).getLabel();
        assertEquals("RedB", label);

        Node redJNode = testPath.get(0).getNode2();
        assertEquals("Wollaston", redJNode.getName());
        Node redJNodes = testPath.get(0).getOppositeNode(redJNode);
        assertEquals("NorthQuincy", redJNodes.getName());
        redJNode = testPath.get(1).getNode1();
        assertEquals("JFK/UMass", redJNode.getName());

        label = testPath.get(2).getLabel();
        assertEquals("RedA", label);

        redJNode = testPath.get(2).getNode2();
        assertEquals("SavinHill", redJNode.getName());
        redJNode = testPath.get(3).getNode2();
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
        testPath = testGraph.getPath(App.getStationByName(testStations, "Prudential"), App.getStationByName(testStations, "Hynes/ICA"));
        String label = testPath.get(0).getLabel();
        assertEquals("GreenE", label);

        Node greenJNode = testPath.get(0).getNode2();
        assertEquals("Prudential", greenJNode.getName());
        Node greenJNodes = testPath.get(0).getOppositeNode(greenJNode);
        assertEquals("Copley", greenJNodes.getName());

        label = testPath.get(1).getLabel();
        assertEquals("GreenB", label);

        greenJNode = testPath.get(1).getNode2();
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

        // Tests Obtained Route Correct for the Green Junction line from GreenD to GreenB
        testPath = testGraph.getPath(App.getStationByName(testStations, "Fenway"), App.getStationByName(testStations, "Hynes/ICA"));
        String label = testPath.get(0).getLabel();
        assertEquals("GreenD", label);

        Node greenJNode = testPath.get(0).getNode2();
        assertEquals("Fenway", greenJNode.getName());
        Node greenJNodes = testPath.get(0).getOppositeNode(greenJNode);
        assertEquals("Kenmore", greenJNodes.getName());

        label = testPath.get(1).getLabel();
        assertEquals("GreenB", label);

        greenJNode = testPath.get(1).getNode2();
        assertEquals("Hynes/ICA", greenJNode.getName());
    }

    /**
     * Below Test Optimal routes from a station on one coloured line to another
     * Orange-Red, Orange-Orange, Orange-Green, Orange-Blue, Blue-Red, Blue-Orange/Green,
     */

    /* Tests MultiGraph getPath() optimal route from Orange ChinaTown to Red Charles/MGH */
    @org.junit.jupiter.api.Test
    void multiGraphOrangeToRed() {

        // Tests Obtained Route Correct for the Orange ChinaTown to Red Charles/MGH
        testPath = testGraph.getPath(App.getStationByName(testStations, "Chinatown"), App.getStationByName(testStations, "Charles/MGH"));
        String label = testPath.get(0).getLabel();
        assertEquals("Orange", label);

        Node orangeNode = testPath.get(0).getNode2();
        assertEquals("Chinatown", orangeNode.getName());
        Node orangeNodes = testPath.get(0).getOppositeNode(orangeNode);
        assertEquals("DowntownCrossing", orangeNodes.getName());

        label = testPath.get(1).getLabel();
        assertEquals("Red", label);

        orangeNode = testPath.get(2).getNode2();
        assertEquals("ParkStreet", orangeNode.getName());
        orangeNode = testPath.get(2).getNode1();
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

    /* Tests MultiGraph getPath() optimal route from Orange Chinatown to Green Boylston swapping at Haymarket for minimal train Transfers */
    @org.junit.jupiter.api.Test
    void multiGraphOrangeToGreen() {

        Station source = (Station) App.getStationByName(testStations, "Chinatown");

        // Tests Obtained Route Correct for the Orange Chinatown to Green Boylston swapping at Haymarket
        testPath = testGraph.getPath(App.getStationByName(testStations, "Chinatown"), App.getStationByName(testStations, "Boylston"));
        String label = testPath.get(0).getLabel();
        assertEquals("Orange", label);

        Node orangeNode = testPath.get(0).getNode2();
        assertEquals("Chinatown", orangeNode.getName());
        Node orangeNodes = testPath.get(0).getOppositeNode(orangeNode);
        assertEquals("DowntownCrossing", orangeNodes.getName());

        label = testPath.get(1).getLabel();
        assertEquals("Red", label);

        label = testPath.get(2).getLabel();
        assertEquals("Green", label);

        Node greenNode = testPath.get(2).getNode1();
        assertEquals("ParkStreet", greenNode.getName());
        greenNode = testPath.get(2).getNode2();
        assertEquals("Boylston", greenNode.getName());
    }

    /* Tests MultiGraph getPath() optimal route from Orange Chinatown to Blue Bowdoin swapping at State for minimal train Transfers */
    @org.junit.jupiter.api.Test
    void multiGraphOrangeToBlue() {

        // Tests Obtained Route Correct for the Orange Chinatown to Blue Bowdoin swapping at State
        testPath = testGraph.getPath(App.getStationByName(testStations, "Chinatown"), App.getStationByName(testStations, "Bowdoin"));
        String label = testPath.get(0).getLabel();
        assertEquals("Orange", label);

        Node orangeNode = testPath.get(0).getNode2();
        assertEquals("Chinatown", orangeNode.getName());
        Node orangeNodes = testPath.get(0).getOppositeNode(orangeNode);
        assertEquals("DowntownCrossing", orangeNodes.getName());
        orangeNode = testPath.get(1).getNode1();
        assertEquals("State", orangeNode.getName());

        label = testPath.get(2).getLabel();
        assertEquals("Blue", label);

        Node blueNode = testPath.get(2).getNode1();
        assertEquals("GovernmentCenter", blueNode.getName());
        blueNode = testPath.get(3).getNode1();
        assertEquals("Bowdoin", blueNode.getName());
    }

    /* Tests MultiGraph getPath() optimal route from Blue Aquarium to Red Charles/MGH via GovernmentCenter and ParkStreet for minimal train Transfers */
    @org.junit.jupiter.api.Test
    void multiGraphBlueToRed() {

        // Tests Obtained Route Correct for the Blue Aquarium to Red Charles/MGH
        testPath = testGraph.getPath(App.getStationByName(testStations, "Aquarium"), App.getStationByName(testStations, "Charles/MGH"));
        String label = testPath.get(0).getLabel();
        assertEquals("Blue", label);

        Node blueNode = testPath.get(0).getNode1();
        assertEquals("Aquarium", blueNode.getName());
        Node blueNodes = testPath.get(0).getOppositeNode(blueNode);
        assertEquals("State", blueNodes.getName());
        blueNode = testPath.get(1).getNode1();
        assertEquals("GovernmentCenter", blueNode.getName());

        label = testPath.get(2).getLabel();
        assertEquals("Green", label);

        Node greenNode = testPath.get(2).getNode2();
        assertEquals("ParkStreet", greenNode.getName());

        label = testPath.get(3).getLabel();
        assertEquals("Red", label);

        Node redNode = testPath.get(3).getNode1();
        assertEquals("Charles/MGH", redNode.getName());
    }

    /* Tests MultiGraph getPath() optimal route from Blue Aquarium to Orange/Green Haymarket via State for minimal train Transfers */
    @org.junit.jupiter.api.Test
    void multiGraphBlueToOrangeGreen() {

        // Tests Obtained Route Correct for the Blue Aquarium to Orange/Green Haymarket via State
        testPath = testGraph.getPath(App.getStationByName(testStations, "Aquarium"), App.getStationByName(testStations, "Haymarket"));
        String label = testPath.get(0).getLabel();
        assertEquals("Blue", label);

        Node blueNode = testPath.get(0).getNode1();
        assertEquals("Aquarium", blueNode.getName());
        Node blueNodes = testPath.get(0).getOppositeNode(blueNode);
        assertEquals("State", blueNodes.getName());

        label = testPath.get(1).getLabel();
        assertEquals("Orange", label);

        Node orangeNode = testPath.get(1).getNode1();
        assertEquals("Haymarket", orangeNode.getName());
    }

}



