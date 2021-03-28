public class App2 {

    public static void main(String[] args){
        Model model = new Model("src/bostonmetro.txt");
        View view = new View();
        Controller controller = new Controller(model,view);
        controller.run();
    }
}
