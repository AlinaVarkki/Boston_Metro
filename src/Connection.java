public class Connection implements Edge {
    Node   prev;
    Node   next;

    public Connection(){
    }

    public Connection(Node nxt, Node prv){
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
}
