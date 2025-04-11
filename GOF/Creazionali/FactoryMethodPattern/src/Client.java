// Esempio di utilizzo del Factory pattern
public class Client {
    public static void main(String[] args) {
        Creator creator1 = new ConcreteCreator1();
        creator1.operation(); // stampa "Sono ConcreteProduct1 e sto eseguendo un'operazione."

        Creator creator2 = new ConcreteCreator2();
        creator2.operation(); // stampa "Sono ConcreteProduct2 e sto eseguendo un'operazione."
    }
}