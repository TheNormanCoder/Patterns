
// Concrete Observer class
public class ConcreteObserver implements Observer {
    private Subject subject;

    public ConcreteObserver(Subject subject) {
        this.subject = subject;
        subject.attach(this);
    }

    public void update() {
        if (subject instanceof ConcreteSubject) {
            ConcreteSubject concreteSubject = (ConcreteSubject) subject;
            System.out.println("ConcreteObserver updated with state " + concreteSubject.getState());
        }
    }
}