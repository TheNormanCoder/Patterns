public class Main {
    public static void main(String[] args) {
        // Create a ConcreteSubject object
        ConcreteSubject subject = new ConcreteSubject();

        // Create two ConcreteObserver objects and attach them to the ConcreteSubject
        ConcreteObserver observer1 = new ConcreteObserver(subject);
        ConcreteObserver observer2 = new ConcreteObserver(subject);

        // Set the state of the ConcreteSubject to 1
        subject.setState(1);

        // Detach observer1 from the ConcreteSubject
        subject.detach(observer1);

        // Set the state of the ConcreteSubject to 2
        subject.setState(2);
    }
}
