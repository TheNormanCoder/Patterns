public class ConcreteClass extends AbstractClass {
    @Override
    protected void operation1() {
        System.out.println("ConcreteClass.operation1() called.");
    }

    @Override
    protected void operation2() {
        System.out.println("ConcreteClass.operation2() called.");
    }
}