import java.util.ArrayList;

public abstract class Node {

    String name;
    ArrayList<Edge> edges;
    String idNumber;

    public String getName() {
        return name;
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setName(String name) {
        this.name = name;
    }
}
