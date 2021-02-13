import java.util.ArrayList;

public class Station extends Node {

    public Station(String name){
        this.name = name;
    }

    public Station(String id, String name){
        this.name = name;
        this.idNumber = id;
    }

    @Override
    public boolean isTransferStation() {
        return true;
    }
}
