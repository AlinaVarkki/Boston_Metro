public class Connection implements Edge {
    String line;
    Node   prev;
    Node   next;

    public Connection(String line){
        this.line = line;
    }

    public Connection(String line,Node nxt, Node prv){
        this.line = line;
        this.next = nxt;
        this.prev = prv;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    public void setPrev(Node prev) {
        this.prev = prev;
    }

    public Node getNext() {
        return next;
    }

    public Node getPrev() {
        return prev;
    }

    public String getLine() {
        return line;
    }
}
