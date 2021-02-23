import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class FileReader {
    ArrayList<String[]> arrsToBeProcessed;
    ArrayList<String[]> edgesToBeProcessed;
    HashSet<String> linesToBeProcessed;

    HashMap<String, Node> stationsNodesById;
    HashMap<String,Station> stationsById;
    List<Edge<Node>> connections;


    public FileReader() {}

    public List<Edge<Node>> getConnections(){
        return connections;
    }

    public List<Node> getStations(){
        List<Node> stations = new ArrayList<>();
        for (String s: stationsNodesById.keySet()) stations.add(stationsById.get(s));
        System.out.println("Num of Stations (exp: 124):"+stations.size());
        return stations;
    }

    public void createEdges(){
        connections = new ArrayList<>();
        for(String[] edgeArr: edgesToBeProcessed){
            if(!edgeArr[2].equals("0")) {
                Connection c1 = new Connection(stationsById.get(edgeArr[0]), stationsById.get(edgeArr[2]), edgeArr[1]);
                connections.add(c1);
            }
            if(!edgeArr[3].equals("0")) {
                Connection c2 = new Connection(stationsById.get(edgeArr[0]), stationsById.get(edgeArr[3]), edgeArr[1]);
                connections.add(c2);
            }
        }
    }

    public void readInGraph (String filepath){

        arrsToBeProcessed = this.readFile(filepath);
        edgesToBeProcessed = new ArrayList<>();
        linesToBeProcessed = new HashSet<>();

        stationsNodesById = new HashMap<>();
        stationsById = new HashMap<>();


        for (String[] entry : arrsToBeProcessed) {
            String idNum = entry[0];
            String name = entry[1];
            for (int entryIndex = 2; entryIndex < entry.length; entryIndex = entryIndex + 3) {
                String[] edge = new String[4];
                edge[0] = entry[0];
                edge[1] = entry[entryIndex];
                edge[2] = entry[entryIndex + 1];
                edge[3] = entry[entryIndex + 2];
                linesToBeProcessed.add(entry[entryIndex]);
                edgesToBeProcessed.add(edge);
            }

            Node newNode = new Station(idNum);
            ((Station) newNode).setName(name);
            stationsNodesById.put(idNum, newNode);
            stationsById.put(idNum, new Station(idNum, name));

        }
        createEdges();

    }
    /**
     * @param filepath String is the path to the file with graph details
     * @return An Arraylist of the file lines, each line split into an array by whitespace
     * */
    private ArrayList<String[]> readFile(String filepath){

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
            System.out.println("An error has occurred");
            e.printStackTrace();
        }

        return fileLines;
    }
}
