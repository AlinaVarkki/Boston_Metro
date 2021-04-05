import Graph.Edge;
import Metro.Connection;
import Metro.Station;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class FileReader {

    ArrayList<String[]> edgesToBeProcessed;
    HashSet<String> linesToBeProcessed;

    HashMap<String, Station> stationsNodesById;
    HashMap<String, Station> stationsById;
    List<Edge<Station>> connections;
    int idNumCheck;


    public FileReader(String filepath) {
        readInGraph(filepath);
    }

    /**
     * @param filepath saved to ArrayList of strings
     *                 Each String broken into components and added to Fields
     *                 Calls createEdge once ArrayList has been sorted through
     */
    public void readInGraph(String filepath) {

        ArrayList<String[]> arrsToBeProcessed = this.readFile(filepath);
        edgesToBeProcessed = new ArrayList<>();
        linesToBeProcessed = new HashSet<>();

        stationsNodesById = new HashMap<>();
        stationsById = new HashMap<>();

        try {
            for (String[] entry : arrsToBeProcessed) {
                String idNum = entry[0];
                String name = entry[1];
                for (int entryIndex = 2; entryIndex < entry.length; entryIndex = entryIndex + 3) {
                    String[] edge = new String[4];
                    edge[0] = entry[0];
                    edge[1] = entry[entryIndex];
                    edge[2] = entry[entryIndex + 1];
                    edge[3] = entry[entryIndex + 2];

                    idNumCheck = Integer.parseInt(idNum);
                    idNumCheck = Integer.parseInt(edge[2]);
                    idNumCheck = Integer.parseInt(edge[3]);

                    linesToBeProcessed.add(entry[entryIndex]);
                    edgesToBeProcessed.add(edge);
                }

                Station newNode = new Station(idNum);
                newNode.setName(name);
                stationsNodesById.put(idNum, newNode);
                stationsById.put(idNum, new Station(idNum, name));
            }
        } catch (NumberFormatException e) {
            System.out.println("The File Provided Has Invalid Characters");
            e.printStackTrace();
        }

        createEdges();
    }

    /**
     * @param filepath String is the path to the file with metro details
     * @return An Arraylist of the file lines, each line split into an array by whitespace
     */
    private ArrayList<String[]> readFile(String filepath) {

        ArrayList<String[]> fileLines = new ArrayList<>();
        try {
            File file = new File(filepath);
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                line = line.trim();
                fileLines.add(line.split("\\s+"));
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("A File Has Not Been Found");
            e.printStackTrace();
        }

        return fileLines;
    }

    /**
     * Creates Connections between Nodes considering End Cases
     * Saves Connections to ArrayList
     */
    public void createEdges() {
        connections = new ArrayList<>();
        for (String[] edgeArr : edgesToBeProcessed) {
            if (!edgeArr[2].equals("0")) {
                Connection<Station> new_connection = new Connection<>(stationsById.get(edgeArr[0]), stationsById.get(edgeArr[2]), edgeArr[1]);
                connections.add(new_connection);
            }
            if (!edgeArr[3].equals("0")) {
                Connection<Station> new_connection = new Connection<>(stationsById.get(edgeArr[0]), stationsById.get(edgeArr[3]), edgeArr[1]);
                connections.add(new_connection);
            }

        }
    }

    /**
     * Returns the Edges loaded from the file
     * @return Edges loaded from the file
     */
    public List<Edge<Station>> getConnections() {
        return connections;
    }

    /**
     * Return the list of Nodes loaded from the file
     * @return list of nodes loaded from the file
     */
    public List<Station> getStations() {
        List<Station> stations = new ArrayList<>();
        for (String s : stationsNodesById.keySet()) stations.add(stationsById.get(s));
        System.out.println("Num of Stations (exp: 124):" + stations.size());
        return stations;
    }

}
