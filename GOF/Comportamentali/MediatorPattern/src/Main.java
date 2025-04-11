public class Main {
    public static void main(String[] args) {
        // Creazione del Mediator
        ConcreteMediator mediator = new ConcreteMediator();

        // Creazione dei Colleague
        ConcreteColleague colleague1 = new ConcreteColleague(mediator, "Colleague 1");
        ConcreteColleague colleague2 = new ConcreteColleague(mediator, "Colleague 2");

        // Associazione dei Colleague al Mediator
        mediator.setColleague1(colleague1);
        mediator.setColleague2(colleague2);

        // Invio di un messaggio dal primo Colleague al secondo Colleague
        colleague1.sendMessage("Hello from Colleague 1!");
    }
}