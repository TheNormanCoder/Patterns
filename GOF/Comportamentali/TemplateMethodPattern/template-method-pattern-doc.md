# Template Method Pattern

## Spiegazione del Pattern

Il Template Method è un pattern di progettazione comportamentale che definisce la struttura di un algoritmo in una classe base (astratta), delegando alcuni passaggi specifici alle sottoclassi. Consente alle sottoclassi di ridefinire certi passaggi dell'algoritmo senza cambiarne la struttura generale.

### Componenti principali

1. **AbstractClass (Classe Astratta)**: Definisce il metodo template (`templateMethod()`) che contiene la struttura dell'algoritmo e dichiara i metodi astratti che le sottoclassi dovranno implementare.

2. **ConcreteClass (Classe Concreta)**: Estende AbstractClass e implementa i metodi astratti (`operation1()` e `operation2()`) con comportamenti specifici.

3. **Client**: Usa il metodo template attraverso un riferimento alla classe astratta.

### Come funziona

1. Il metodo `templateMethod()` nella classe astratta è dichiarato come `final` per impedire che le sottoclassi ne alterino la struttura.

2. Dentro `templateMethod()`, vengono chiamati in sequenza i metodi `operation1()` e `operation2()`.

3. Essendo questi metodi dichiarati `abstract`, ogni sottoclassa deve fornire la propria implementazione.

4. Quando il client esegue `templateMethod()`, l'algoritmo segue sempre la stessa struttura, ma i passaggi specifici dipendono dall'implementazione nella sottoclasse concreta.

### Vantaggi del Pattern

- **Riuso del codice**: La struttura dell'algoritmo è definita una sola volta nella classe base.
- **Estensibilità**: Facile aggiungere nuove varianti dell'algoritmo creando nuove sottoclassi.
- **Inversione del controllo**: È la classe padre che chiama i metodi delle sottoclassi (non viceversa), seguendo il principio di "Hollywood" - "non chiamarci, ti chiameremo noi".

### Esempio di esecuzione

Eseguendo il programma, vedrai questo output:
```
ConcreteClass.operation1() called.
ConcreteClass.operation2() called.
Template method complete.
```

## Rilevanza attuale del Template Method

Il pattern è ancora molto rilevante perché risolve un problema fondamentale: definire la struttura di un algoritmo permettendo variazioni in alcune sue parti. Questo concetto di "scheletro fisso con parti intercambiabili" è alla base di molti framework moderni.

## Evoluzione e relazioni con altri pattern

Il Template Method può essere visto come precursore di diversi concetti moderni:

1. **Inversione di Controllo (IoC)** e **Dependency Injection**: Il principio "don't call us, we'll call you" del Template Method è alla base dell'IoC, fondamentale nei framework moderni.

2. **Hook Methods**: L'evoluzione moderna del Template Method spesso include "hook methods" che sono opzionali (non astratti) e permettono alle sottoclassi di inserirsi in punti specifici dell'algoritmo senza doverlo reimplementare completamente.

3. **Functional Programming**: Nei linguaggi moderni, spesso si preferisce passare funzioni/lambda invece di creare sottoclassi, ottenendo lo stesso risultato con meno codice.

## Esempi in framework e librerie moderne

### Spring Framework (Java)
Spring utilizza ampiamente il Template Method, ad esempio:
- `JdbcTemplate`: Gestisce la connessione al database, mentre le query specifiche sono delegate al codice client
- La classe `AbstractController` definisce il flusso di elaborazione HTTP, mentre i metodi specifici sono implementati dalle sottoclassi

### React (JavaScript)
Il ciclo di vita dei componenti React segue il Template Method:
```javascript
class Component extends React.Component {
  componentDidMount() {
    // Implementazione specifica
  }
  
  render() {
    // Implementazione specifica
  }
}
```
React chiama questi metodi in un ordine predefinito, ma le implementazioni sono fornite dalle sottoclassi.

### ASP.NET Core (C#)
Il middleware pipeline di ASP.NET Core segue un principio simile, definendo una sequenza di elaborazione ma permettendo ai singoli middleware di implementare comportamenti specifici.

## Esempio pratico moderno

Ecco un esempio di come il Template Method potrebbe essere usato in un'applicazione moderna che elabora pagamenti:

```java
// Classe astratta che definisce il processo di pagamento
public abstract class PaymentProcessor {
    // Il metodo template che definisce il flusso del pagamento
    public final boolean processPayment(double amount) {
        if (!validateAmount(amount)) {
            return false;
        }
        
        boolean authorizationResult = authorizePayment(amount);
        if (!authorizationResult) {
            logFailure("Authorization failed");
            return false;
        }
        
        boolean processResult = processTransaction(amount);
        if (!processResult) {
            reverseAuthorization(amount);
            logFailure("Processing failed");
            return false;
        }
        
        notifySuccess(amount);
        return true;
    }
    
    // Metodi comuni per tutti i processori di pagamento
    private boolean validateAmount(double amount) {
        return amount > 0;
    }
    
    protected void logFailure(String reason) {
        System.out.println("Payment failed: " + reason);
    }
    
    // Metodi astratti che devono essere implementati dai processori specifici
    protected abstract boolean authorizePayment(double amount);
    protected abstract boolean processTransaction(double amount);
    protected abstract void reverseAuthorization(double amount);
    protected abstract void notifySuccess(double amount);
}

// Implementazione concreta per PayPal
public class PayPalProcessor extends PaymentProcessor {
    @Override
    protected boolean authorizePayment(double amount) {
        // Logica specifica per l'autorizzazione PayPal
        System.out.println("Authorizing " + amount + " via PayPal");
        return true;
    }
    
    @Override
    protected boolean processTransaction(double amount) {
        // Logica specifica per processare un pagamento PayPal
        System.out.println("Processing " + amount + " via PayPal");
        return true;
    }
    
    @Override
    protected void reverseAuthorization(double amount) {
        System.out.println("Reversing authorization for " + amount + " via PayPal");
    }
    
    @Override
    protected void notifySuccess(double amount) {
        System.out.println("Payment of " + amount + " successful via PayPal");
    }
}
```

Questo esempio mostra come il Template Method rimanga rilevante nelle applicazioni moderne, specialmente in sistemi che richiedono processi standardizzati con variazioni specifiche dell'implementazione.
