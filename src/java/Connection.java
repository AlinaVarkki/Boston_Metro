public class Connection<N> implements Edge<N> {
    private final N node1;
    private final N node2;
    private final String label;


    public Connection(N node1, N node2, String label) {
        this.node1 = node1;
        this.node2 = node2;
        this.label = label;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public N getNode1() {
        return node1;
    }

    @Override
    public N getNode2() {
        return node2;
    }

    @Override
    public N getOppositeNode(N node) {
        return ((node == node1) ? node2 : node1);
    }

    @Override
    public String toString() {
        return this.label + ": " + node1.toString() + " to " + node2.toString();
    }
}
