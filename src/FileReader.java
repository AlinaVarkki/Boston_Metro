import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.*;

public class FileReader {
    ArrayList<String[]> arrsToBeProcessed;
    ArrayList<String[]> edgesToBeProcessed;
    HashSet<String> linesToBeProcessed;

    HashMap<String,Node> stationsNodesById;
    HashMap<String,Station> stationsById;
    HashMap<String,Line> linesByName;
    ArrayList<Graph> lines;
    List<Edge> connections;


    public FileReader() {}

    public List<Edge> getConnections(){
        return connections;
    }

    public List<Node> getStations(){
        List<Node> stations = new ArrayList<>();
        for (String s: stationsNodesById.keySet()) stations.add(stationsById.get(s));
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


        for (int index = 0; index < arrsToBeProcessed.size(); index++) {
            String[] entry = arrsToBeProcessed.get(index);
            String idNum= entry[0];
            String name = entry[1];
     //       System.out.println(Arrays.toString(entry));
            for (int entryIndex = 2 ; entryIndex < entry.length; entryIndex = entryIndex + 3){
                String[] edge = new String[4];
                edge[0] = entry[0];
                edge[1] = entry[entryIndex];
                edge[2] = entry[entryIndex+1];
                edge[3] = entry[entryIndex+2];
                linesToBeProcessed.add(entry[entryIndex]);
                edgesToBeProcessed.add(edge);
             //   System.out.println("edge: " + Arrays.toString(edge));
            }

            Node newNode = new Station(idNum);
            newNode.setName(name);
            stationsNodesById.put(idNum,newNode);
//            stationsNodesByName.put(name,idNum);
            stationsById.put(idNum, new Station(idNum, name));

        }

        //was throwing ann error, not relevant atm so commented
//        for (String lineName : linesToBeProcessed){
//            Line line = new Line(lineName);
//            lines.add(line);
//            linesByName.putIfAbsent(lineName,line);
//        }
//        putEdgesInLines();

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

    private void putEdgesInLines (){
        for(String[] edge : edgesToBeProcessed){
            Node thisStation = stationsNodesById.get(edge[0]);
            Node prev = stationsNodesById.get(edge[2]);
            Node next = stationsNodesById.get(edge[3]);
            Edge edge1 = new Connection(prev,thisStation);
            Edge edge2 = new Connection(thisStation,next);
            Line line = linesByName.get(edge[1]);
            line.addEdge(edge1);
            line.addEdge(edge2);
        }
    }
    /**
     * TESTING method, remove later
     */
    public void printLineEdges(int i,String value){
        for (int index = 0; index < edgesToBeProcessed.size(); index++){
            String[] node = edgesToBeProcessed.get(index);
            if (value.equals(node[i])) {
                System.out.println(Arrays.toString(edgesToBeProcessed.get(index)));
            }
        }
    }

}
