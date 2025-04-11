// Definizione della classe Creator
public abstract class Creator {
    public abstract Product factoryMethod();

    public void operation() {
        Product product = factoryMethod();
        product.operation();
    }
}