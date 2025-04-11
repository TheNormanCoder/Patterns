// Definizione di una classe concreta dell'astrazione
public class RefinedAbstraction extends Abstraction {
    public RefinedAbstraction(Implementor implementor) {
        super(implementor);
    }

    @Override
    public void operation() {
        System.out.println("Esegui operazione raffinata");
        implementor.operationImpl();
    }
}