import java.util.ArrayList;

public abstract class Node {

    String name;
    String idNumber;

    public String getName() {
        return name;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public abstract boolean isTransferStation();

}
