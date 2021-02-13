public class Connection<N> implements Edge<N> {
    private N node1;
    private N node2;
    private String label;

    public Connection(){
    }

    public Connection(N node1,N node2, String label){
        this.node1 = node1;
        this.node2 = node2;
        this.label = label;
    }

    public Connection(N node1,N node2){
        this.node1 = node1;
        this.node2 = node2;
    }

    @Override
    public String getLabel() {
        return label;
    }

    public void setNode1(N node1) {
        this.node1 = node1;
    }

    public void setNode2(N node2) {
        this.node2 = node2;
    }

    public N getNode1() {
        return node1;
    }

    public N getNode2() {
        return node2;
    }

    @Override
    public N getOppositeNode(N node) {
        return ((node == node1) ? node2 : node1);
    }
}
