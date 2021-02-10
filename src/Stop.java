import java.util.ArrayList;

public class Stop extends Node {
    // Standard station on the line
    public Stop(String idNum){
        this.idNumber = idNum;
    }

    @Override
    public boolean isTransferStation() {
        return false;
    }
}
