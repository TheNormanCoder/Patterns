public class Main {
    public static void main(String[] args) {
        Context context = new Context();
        context.setState(new ConcreteStateA());
        context.request();
        context.setState(new ConcreteStateB());
        context.request();
    }
}