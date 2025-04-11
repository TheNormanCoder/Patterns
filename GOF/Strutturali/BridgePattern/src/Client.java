// Esempio di utilizzo del pattern Bridge
public class Client {
    public static void main(String[] args) {
        Implementor implementorA = new ConcreteImplementorA();
        Abstraction abstraction = new RefinedAbstraction(implementorA);
        abstraction.operation();

        Implementor implementorB = new ConcreteImplementorB();
        abstraction = new RefinedAbstraction(implementorB);
        abstraction.operation();
    }
}