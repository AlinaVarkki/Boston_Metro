public class Station implements Node{

    String name;
    String idNumber;

    public Station(String name){
        this.name = name;
    }

    public Station(String id, String name){
        this.name = name;
        this.idNumber = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }
}
