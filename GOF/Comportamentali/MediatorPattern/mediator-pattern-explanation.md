# Pattern Mediator - Spiegazione

## Cos'è il Pattern Mediator

Il pattern Mediator è un design pattern comportamentale che promuove l'accoppiamento debole (loose coupling) tra oggetti, facendo sì che essi comunichino tra loro indirettamente attraverso un oggetto mediatore.

## Funzionamento

Nel pattern Mediator:

1. Gli oggetti (detti "colleghi" o "colleagues") non comunicano direttamente tra loro
2. Tutte le comunicazioni passano attraverso un oggetto mediatore centrale
3. Il mediatore controlla e coordina le interazioni tra i colleghi

## Componenti del Pattern

- **Mediator** (interface): definisce l'interfaccia per la comunicazione tra i colleghi
- **ConcreteMediator**: implementa l'interfaccia Mediator e coordina i colleghi
- **Colleague** (interface): definisce l'interfaccia per gli oggetti che devono comunicare
- **ConcreteColleague**: implementa l'interfaccia Colleague e comunica con altri colleghi attraverso il mediatore

## Codice di Esempio

### Mediator.java
```java
public interface Mediator {
    void sendMessage(String message, Colleague colleague);
}
```

### ConcreteMediator.java
```java
// Implementazione del Mediator
public class ConcreteMediator implements Mediator {
    private Colleague colleague1;
    private Colleague colleague2;

    public void setColleague1(Colleague colleague1) {
        this.colleague1 = colleague1;
    }

    public void setColleague2(Colleague colleague2) {
        this.colleague2 = colleague2;
    }

    public void sendMessage(String message, Colleague colleague) {
        if (colleague == colleague1) {
            colleague2.receiveMessage(message);
        } else if (colleague == colleague2) {
            colleague1.receiveMessage(message);
        }
    }
}
```

### Colleague.java
```java
public interface Colleague {
    void sendMessage(String message);
    void receiveMessage(String message);
}
```

### ConcreteColleague.java
```java
// Implementazione di Colleague
public class ConcreteColleague implements Colleague {
    private Mediator mediator;
    private String name;

    public ConcreteColleague(Mediator mediator, String name) {
        this.mediator = mediator;
        this.name = name;
    }

    public void sendMessage(String message) {
        mediator.sendMessage(message, this);
    }

    public void receiveMessage(String message) {
        System.out.println(name + " received message: " + message);
    }
}
```

### Main.java
```java
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
```

## Analisi dell'Implementazione

L'implementazione mostrata è corretta ma presenta alcune limitazioni:

1. **Scalabilità limitata**: Il `ConcreteMediator` è progettato per gestire esattamente due colleghi. Per aggiungere più colleghi, sarebbe necessario modificare significativamente l'implementazione.

2. **Accoppiamento ancora presente**: Il mediatore conosce esplicitamente i tipi specifici dei colleghi. Idealmente, dovrebbe poter lavorare con qualsiasi oggetto che implementi l'interfaccia `Colleague`.

3. **Routing rigido**: La logica di routing dei messaggi è rigida - i messaggi da `colleague1` vanno sempre a `colleague2` e viceversa. In un'implementazione più flessibile, il mediatore potrebbe decidere a chi inoltrare il messaggio in base al contenuto o ad altri criteri.

## Implementazione Migliorata

Una implementazione più flessibile potrebbe utilizzare una collezione di colleghi:

```java
public class BetterMediator implements Mediator {
    private List<Colleague> colleagues = new ArrayList<>();
    
    public void addColleague(Colleague colleague) {
        colleagues.add(colleague);
    }
    
    public void sendMessage(String message, Colleague sender) {
        for (Colleague colleague : colleagues) {
            // Non inviare il messaggio a chi l'ha mandato
            if (colleague != sender) {
                colleague.receiveMessage(message);
            }
        }
    }
}
```

## Vantaggi del Pattern Mediator

- **Riduce l'accoppiamento**: i colleghi non hanno bisogno di conoscersi reciprocamente
- **Centralizza il controllo**: la logica di comunicazione è centralizzata nel mediatore
- **Semplifica le interazioni**: i colleghi devono solo conoscere come comunicare con il mediatore, non con tutti gli altri colleghi
- **Facilita la manutenzione**: modificare o aggiungere comportamenti diventa più semplice

## Quando usarlo

Il pattern Mediator è utile quando:
- Hai un insieme di oggetti che comunicano tra loro in modo complesso
- Vuoi evitare che gli oggetti siano strettamente accoppiati
- Vuoi poter riutilizzare componenti indipendentemente dagli altri
- Vuoi centralizzare comportamenti complessi tra più oggetti
