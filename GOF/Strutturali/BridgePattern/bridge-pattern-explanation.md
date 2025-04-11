# Pattern Bridge e Principi SOLID

## Spiegazione del Pattern Bridge

Il Bridge è un pattern strutturale che separa l'astrazione dall'implementazione, permettendo a entrambe di variare indipendentemente. Questo pattern è utile quando vuoi evitare un legame permanente tra un'astrazione e la sua implementazione.

### Struttura del Pattern
- **Abstraction**: classe astratta che definisce l'interfaccia dell'astrazione e mantiene un riferimento all'implementazione
- **RefinedAbstraction**: estende l'astrazione con funzionalità specifiche
- **Implementor**: interfaccia che definisce l'API per le implementazioni concrete
- **ConcreteImplementorA** e **ConcreteImplementorB**: implementazioni concrete

### Componenti Principali
- L'`Abstraction` contiene un riferimento a un `Implementor`
- Le operazioni su `Abstraction` delegano il lavoro all'oggetto `Implementor`
- `RefinedAbstraction` può estendere l'interfaccia definita da `Abstraction`

### Flusso di Esecuzione
- Il `Client` crea un'istanza di `RefinedAbstraction` passando un'implementazione concreta
- Quando viene chiamato `operation()` sull'astrazione, questa a sua volta chiama `operationImpl()` sull'implementazione

### Vantaggi
- Disaccoppia l'interfaccia dall'implementazione
- Permette di cambiare l'implementazione a runtime
- Consente di estendere sia le astrazioni che le implementazioni indipendentemente

## Analisi SOLID del Codice Client

```java
public class Client {
    public static void main(String[] args) {
        Implementor implementorA = new ConcreteImplementorA();
        Abstraction abstraction = new RefinedAbstraction(implementorA);
        abstraction.operation();

        Implementor implementorB = new ConcreteImplementorB();
        abstraction = new RefinedAbstraction(implementorB);
        abstraction.operation();
    }
}
```

### Punti Positivi
- **Single Responsibility Principle**: il Client si occupa solo di utilizzare il pattern Bridge
- **Open/Closed Principle**: puoi aggiungere nuove implementazioni senza modificare il codice esistente
- **Liskov Substitution Principle**: le implementazioni concrete possono essere sostituite liberamente
- **Dependency Inversion Principle**: il Client dipende da astrazioni (Implementor, Abstraction) e non da implementazioni concrete

### Possibili Miglioramenti
- Il Client crea direttamente istanze concrete, il che introduce un lieve accoppiamento. In un'applicazione più complessa, potresti usare una factory o dependency injection
- La riga `abstraction = new RefinedAbstraction(implementorB)` crea un nuovo oggetto invece di cambiare l'implementazione dell'oggetto esistente

### Versione Migliorata
```java
// Nella classe Abstraction
public abstract class Abstraction {
    protected Implementor implementor;

    public Abstraction(Implementor implementor) {
        this.implementor = implementor;
    }

    public void setImplementor(Implementor implementor) {
        this.implementor = implementor;
    }

    public abstract void operation();
}

// Nel Client
public class Client {
    public static void main(String[] args) {
        Implementor implementorA = new ConcreteImplementorA();
        Abstraction abstraction = new RefinedAbstraction(implementorA);
        abstraction.operation();

        // Cambio l'implementazione senza creare una nuova astrazione
        Implementor implementorB = new ConcreteImplementorB();
        abstraction.setImplementor(implementorB);
        abstraction.operation();
    }
}
```

## Interface Segregation Principle

L'Interface Segregation Principle (ISP) stabilisce che "i client non dovrebbero essere forzati a dipendere da interfacce che non utilizzano".

Nel codice fornito, l'interfaccia `Implementor` è molto snella:

```java
public interface Implementor {
    void operationImpl();
}
```

Questo è positivo dal punto di vista dell'ISP perché:
- È un'interfaccia minima, che richiede solo ciò che è necessario
- I client dell'interfaccia devono implementare solo un metodo
- Non forza le implementazioni a fornire metodi che non utilizzerebbero

L'esempio rispetta bene l'Interface Segregation Principle grazie alla sua interfaccia già ben focalizzata.
