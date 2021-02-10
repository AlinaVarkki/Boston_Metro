import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.*;

public class GraphBuilder {
    ArrayList<String[]> arrsToBeProcessed;
    ArrayList<String[]> edgesToBeProcessed;
    HashSet<String> linesToBeProcessed;
    HashMap<String,Node> stationsNodesById;
    HashMap<String,String> stationsNodesByName;



    public GraphBuilder() {}


    public Graph readInGraph (String filepath){

        arrsToBeProcessed = this.readFile(filepath);
        edgesToBeProcessed = new ArrayList<>();
        linesToBeProcessed = new HashSet<>();

        stationsNodesById = new HashMap<>();
        stationsNodesByName = new HashMap<>();


        for (int index = 0; index < arrsToBeProcessed.size(); index++) {
            String[] entry = arrsToBeProcessed.get(index);
            String idNum= entry[0];
            String name = entry[1];
            System.out.println(Arrays.toString(entry));
            for (int entryIndex = 2 ; entryIndex < entry.length; entryIndex = entryIndex + 3){
                String[] edge = new String[4];
                edge[0] = entry[0];
                edge[1] = entry[entryIndex];
                edge[2] = entry[entryIndex+1];
                edge[3] = entry[entryIndex+2];
                linesToBeProcessed.add(entry[entryIndex]);
                edgesToBeProcessed.add(edge);
                System.out.println("edge: " + Arrays.toString(edge));
            }

            Node newNode = new Station(idNum);
            newNode.setName(name);
            stationsNodesById.put(idNum,newNode);
            stationsNodesByName.put(name,idNum);

        }
        System.out.println(linesToBeProcessed.toString());
        return new Line("line");
    }
    /**
     * @param filepath String is the path to the file with graph details
     * @return An Arraylist of the file lines, each line split into an array by whitespace
     * */
    private ArrayList<String[]> readFile(String filepath){

        ArrayList<String[]> fileLines = new ArrayList<String[]>();
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
