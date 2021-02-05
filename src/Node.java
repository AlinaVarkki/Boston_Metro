import java.util.ArrayList;

public abstract class Node {

    String name;
    ArrayList<Edge> edges;
    int idNumber;

    public String getName() {
        return name;
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public int getIdNumber() {
        return idNumber;
    }
}
