// ConcreteVisitor
public class ConcreteVisitor implements Visitor {
    @Override
    public void visitConcreteElementA(ConcreteElementA element) {
        element.operationA();
    }

    @Override
    public void visitConcreteElementB(ConcreteElementB element) {
        element.operationB();
    }
}