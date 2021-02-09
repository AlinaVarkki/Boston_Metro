import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner; // Import the Scanner class to read text files

public class GraphBuilder {
    ArrayList<String[]> stations;
    ArrayList<String[]> edgesToBeProcessed;

    public GraphBuilder() {}

    public Graph readInGraph (String filepath){

        stations = this.readFile(filepath);
        HashMap<String,Node> stationsNodes = new HashMap<>();
        HashMap<String,Graph> lines = new HashMap<>();
        edgesToBeProcessed = new ArrayList<>();

        for (int index = 0; index < stations.size(); index++) {
            String[] entry = stations.get(index);
            String idNum =entry[0];
            String name = entry[1];
            System.out.println(Arrays.toString(entry));
            for (int entryIndex = 2 ; entryIndex < entry.length; entryIndex = entryIndex + 3){
                String[] edge = new String[4];
                edge[0] = entry[0];
                edge[1] = entry[entryIndex];
                edge[2] = entry[entryIndex+1];
                edge[3] = entry[entryIndex+2];
                edgesToBeProcessed.add(edge);
                System.out.println("edge: " + Arrays.toString(edge));

            }
            Node newNode = new Station(idNum);
            newNode.setName(name);
            stationsNodes.put(idNum,newNode);
        }
        return new Line();
    }
    /**
     * @return An Arraylist of the file, each line split into an array
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

    public void printLineEdges(int i,String value){
        for (int index = 0; index < edgesToBeProcessed.size(); index++){
            String[] node = edgesToBeProcessed.get(index);
            if (value.equals(node[i])) {
                System.out.println(Arrays.toString(edgesToBeProcessed.get(index)));
            }
        }

    }

}
