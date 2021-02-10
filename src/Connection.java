public class Connection implements Edge {
    Node   prev;
    Node   next;

    public Connection(){
    }

    public Connection(Node prv,Node nxt){
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
