public class Main {
    public static void main(String[] args) {
        Strategy strategyA = new ConcreteStrategyA();
        Context context = new Context(strategyA);
        context.executeStrategy();

        Strategy strategyB = new ConcreteStrategyB();
        context = new Context(strategyB);
        context.executeStrategy();
    }
}