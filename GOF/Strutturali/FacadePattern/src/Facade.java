// Classe Facade
class Facade {
    private Subsystem subsystem;

    public Facade() {
        this.subsystem = new Subsystem();
    }

    public void operation() {
        subsystem.operation1();
        subsystem.operation2();
        subsystem.operation3();
    }
}