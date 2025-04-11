// Definizione della classe ConcreteCreator2 che estende Creator
public class ConcreteCreator2 extends Creator {
    public Product factoryMethod() {
        return new ConcreteProduct2();
    }
}