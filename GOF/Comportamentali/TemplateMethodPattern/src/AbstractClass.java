public abstract class AbstractClass {
    // Metodo template
    public final void templateMethod() {
        operation1();
        operation2();
        System.out.println("Template method complete.");
    }

    protected abstract void operation1();
    protected abstract void operation2();
}