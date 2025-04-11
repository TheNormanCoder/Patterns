// Definizione della classe ConcreteCreator1 che estende Creator
public class ConcreteCreator1 extends Creator {
    public Product factoryMethod() {
        return new ConcreteProduct1();
    }
}
