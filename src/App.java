public class App {
    public static void main(String[] args){
        GraphBuilder g = new GraphBuilder();
        g.readInGraph("src/bostonmetro.txt");
        System.out.println("Orange line:");
        g.printLineEdges(1,"Orange");
    }

}
