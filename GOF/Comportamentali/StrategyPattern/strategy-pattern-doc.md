# Design Pattern Strategy - Guida Completa

## Introduzione
Il pattern Strategy è un design pattern comportamentale che permette di definire una famiglia di algoritmi, incapsularli e renderli intercambiabili. Questo pattern consente di modificare l'algoritmo in uso indipendentemente dai client che lo utilizzano.

## Componenti chiave

1. **Strategy**: un'interfaccia che definisce il comportamento comune a tutti gli algoritmi.
2. **ConcreteStrategy**: implementazioni concrete dell'interfaccia Strategy. Ogni classe rappresenta un algoritmo specifico.
3. **Context**: mantiene un riferimento a un oggetto Strategy e delega a esso l'esecuzione dell'algoritmo.
4. **Client**: crea un'istanza specifica di ConcreteStrategy e la passa al Context.

## Implementazione di base

Ecco una semplice implementazione del pattern Strategy:

```java
// Strategy interface
public interface Strategy {
    void execute();
}

// Concrete strategies
public class ConcreteStrategyA implements Strategy {
    public void execute() {
        System.out.println("Esecuzione dell'algoritmo A");
    }
}

public class ConcreteStrategyB implements Strategy {
    public void execute() {
        System.out.println("Esecuzione dell'algoritmo B");
    }
}

// Context
public class Context {
    private Strategy strategy;

    public Context(Strategy strategy) {
        this.strategy = strategy;
    }

    public void executeStrategy() {
        strategy.execute();
    }
}

// Client code
public class Main {
    public static void main(String[] args) {
        Strategy strategyA = new ConcreteStrategyA();
        Context context = new Context(strategyA);
        context.executeStrategy();

        Strategy strategyB = new ConcreteStrategyB();
        context = new Context(strategyB);
        context.executeStrategy();
    }
}
```

## Come funziona il pattern

Nel file `Main.java` si può vedere il pattern in azione:

1. Si crea una strategia concreta (`ConcreteStrategyA`)
2. Si crea un contesto passando la strategia
3. Si esegue l'algoritmo attraverso il contesto
4. Si può cambiare la strategia (passando a `ConcreteStrategyB`) e il contesto eseguirà un algoritmo diverso

## Vantaggi del pattern Strategy

1. **Incapsulamento**: isola l'implementazione di un algoritmo dalle classi che lo utilizzano
2. **Sostituibilità a runtime**: permette di cambiare l'algoritmo in uso durante l'esecuzione
3. **Eliminazione di istruzioni condizionali**: invece di usare switch o if-else per selezionare il comportamento, si usa il polimorfismo
4. **Riutilizzo del codice**: evita duplicazioni implementando varianti dello stesso algoritmo in classi separate
5. **Principio Open/Closed**: si possono aggiungere nuove strategie senza modificare le classi esistenti

## Utilizzo attuale del pattern Strategy

Il pattern Strategy è ancora ampiamente utilizzato nello sviluppo software moderno. Non è tanto un precursore di pattern più recenti quanto una base fondamentale che continua a essere rilevante anche in architetture e framework contemporanei.

### Esempi in framework e librerie moderne:

1. **Spring Framework**
   - Spring utilizza il pattern Strategy in numerosi contesti, per esempio:
   - `HandlerMapping` per determinare quale controller gestirà una richiesta HTTP
   - `ViewResolver` per selezionare quale view renderizzare
   - `ResourceLoader` per caricare risorse da diverse fonti

2. **React.js e il concetto di "Hooks"**
   - I React Hooks rappresentano un'evoluzione del pattern, permettendo di modificare comportamenti (strategie) all'interno dei componenti funzionali
   - Ad esempio, `useState`, `useEffect`, `useContext` sono strategie intercambiabili per gestire lo stato e gli effetti collaterali

3. **Java Stream API**
   - I metodi come `sorted()` e `collect()` accettano diverse strategie (Comparator, Collector) per personalizzare il comportamento

4. **Dependency Injection**
   - Molti framework DI (come Spring, Guice) usano il pattern Strategy per iniettare implementazioni diverse di un'interfaccia

## Esempio pratico attuale

Ecco un esempio pratico in un contesto moderno di come il pattern Strategy viene utilizzato in un servizio di pagamento:

```java
// Interfaccia Strategy
public interface PaymentStrategy {
    boolean pay(double amount);
}

// Concrete Strategies
public class CreditCardStrategy implements PaymentStrategy {
    private String name;
    private String cardNumber;
    private String cvv;
    private String dateOfExpiry;
    
    public CreditCardStrategy(String name, String cardNumber, String cvv, String dateOfExpiry) {
        this.name = name;
        this.cardNumber = cardNumber;
        this.cvv = cvv;
        this.dateOfExpiry = dateOfExpiry;
    }
    
    @Override
    public boolean pay(double amount) {
        // Logica per processare pagamento con carta di credito
        System.out.println(amount + " pagati con carta di credito");
        return true;
    }
}

public class PayPalStrategy implements PaymentStrategy {
    private String email;
    private String password;
    
    public PayPalStrategy(String email, String password) {
        this.email = email;
        this.password = password;
    }
    
    @Override
    public boolean pay(double amount) {
        // Logica per processare pagamento con PayPal
        System.out.println(amount + " pagati con PayPal");
        return true;
    }
}

public class CryptoStrategy implements PaymentStrategy {
    private String walletAddress;
    
    public CryptoStrategy(String walletAddress) {
        this.walletAddress = walletAddress;
    }
    
    @Override
    public boolean pay(double amount) {
        // Logica per processare pagamento con criptovalute
        System.out.println(amount + " pagati con criptovaluta");
        return true;
    }
}

// Context
public class ShoppingCart {
    private List<Item> items;
    
    public ShoppingCart() {
        this.items = new ArrayList<>();
    }
    
    public void addItem(Item item) {
        this.items.add(item);
    }
    
    public double calculateTotal() {
        return items.stream().mapToDouble(Item::getPrice).sum();
    }
    
    public boolean pay(PaymentStrategy paymentStrategy) {
        double amount = calculateTotal();
        return paymentStrategy.pay(amount);
    }
}

// Client code
public static void main(String[] args) {
    ShoppingCart cart = new ShoppingCart();
    cart.addItem(new Item("123", 100));
    cart.addItem(new Item("456", 200));
    
    // Il cliente sceglie come pagare
    cart.pay(new PayPalStrategy("myemail@example.com", "password"));
    // Oppure
    cart.pay(new CreditCardStrategy("John Doe", "1234567890123456", "786", "12/2025"));
    // O anche
    cart.pay(new CryptoStrategy("0x71C7656EC7ab88b098defB751B7401B5f6d8976F"));
}
```

## Esempi pratici di utilizzo

- Strategie di validazione diverse per tipi diversi di dati
- Algoritmi di ordinamento diversi a seconda del tipo di dati
- Strategie di compressione diverse per vari formati di file
- Strategie di pagamento diverse in un'applicazione di e-commerce (come mostrato nell'esempio sopra)
- Strategie diverse di calcolo delle tasse in base alla località
- Diversi algoritmi di autenticazione (password, biometria, token, ecc.)
- Varie strategie di notifica (email, SMS, push notification)

## Conclusione

Il pattern Strategy rimane rilevante anche nell'era del cloud computing e dei microservizi, dove la flessibilità e l'adattabilità degli algoritmi sono ancora più importanti. La sua capacità di separare l'algoritmo dal suo contesto di utilizzo lo rende uno strumento potente per costruire sistemi flessibili e facilmente estendibili.