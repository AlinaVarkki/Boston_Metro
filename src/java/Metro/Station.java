package Metro;

public class Station{

    String name;
    String idNumber;

    public Station(String name) {
        this.name = name;
    }

    public Station(String id, String name) {
        this.name = name;
        this.idNumber = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        String name = this.idNumber + " " + this.name;
        StringBuilder newName = new StringBuilder();
        for (int i = 0; i < name.length() - 1; i++) {
            char a = name.charAt(i);
            char b = name.charAt(i + 1);
            newName.append(a);
            if (Character.isLowerCase(a) && Character.isUpperCase(b)) {
                newName.append(" ");
            }
        }
        newName.append(name.charAt(name.length() - 1));
        return newName.toString();
    }

}
