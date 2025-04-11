# Factory Method Pattern - Guida Completa

## Cos'è il Pattern Factory Method

Il Factory Method è un pattern di progettazione creazionale che fornisce un'interfaccia per creare oggetti in una superclasse, ma consente alle sottoclassi di alterare il tipo di oggetti che verranno creati.

## Struttura del pattern

La struttura del Factory Method comprende i seguenti componenti chiave:

1. **Product**: un'interfaccia che definisce le operazioni che tutti i prodotti concreti devono implementare.
2. **ConcreteProduct**: classi che implementano l'interfaccia Product.
3. **Creator**: una classe astratta che dichiara il factory method, che restituisce un oggetto di tipo Product.
4. **ConcreteCreator**: sottoclassi che implementano il factory method per creare e restituire un ConcreteProduct specifico.

## Come funziona

Nel codice esempio:

- `Creator` è una classe astratta con un metodo astratto `factoryMethod()` e un metodo concreto `operation()`.
- `ConcreteCreator1` e `ConcreteCreator2` estendono `Creator` e implementano `factoryMethod()` per restituire rispettivamente `ConcreteProduct1` e `ConcreteProduct2`.
- `Product` è un'interfaccia con un metodo `operation()`.
- `ConcreteProduct1` e `ConcreteProduct2` implementano l'interfaccia `Product`.

Quando il client chiama `operation()` su un oggetto `Creator`, questo a sua volta:
1. Utilizza il suo `factoryMethod()` per creare un oggetto `Product`
2. Invoca il metodo `operation()` sull'oggetto `Product` creato

## Vantaggi

1. **Incapsulamento della creazione degli oggetti**: il client non ha bisogno di conoscere le classi concrete dei prodotti.
2. **Estensibilità**: puoi introdurre nuovi tipi di prodotti senza modificare il codice esistente.
3. **Separazione delle responsabilità**: il codice che utilizza gli oggetti è separato dal codice che li crea.

## Quando usarlo

- Quando non si conosce in anticipo il tipo esatto degli oggetti che il codice deve creare.
- Quando si vuole fornire un modo per estendere i componenti di un sistema.
- Quando si desidera risparmiare risorse di sistema riutilizzando oggetti esistenti anziché ricrearli ogni volta.

## Utilizzo nei contesti moderni

Il Factory Method è ancora ampiamente utilizzato nella programmazione moderna, nonostante sia uno dei pattern "classici" descritti nel libro "Design Patterns" di Gamma et al. nel 1994.

### Uso nei framework e librerie moderne

1. **Spring Framework**: Utilizza factory methods estensivamente per la creazione di bean e componenti.

2. **React.js**: Il pattern factory è presente nella creazione di componenti React, specialmente con l'uso dei "Higher-Order Components".

3. **Java e .NET**: Le API di queste piattaforme continuano a utilizzare factory methods in molte delle loro librerie standard.

4. **Flutter**: Utilizza factory constructors, che sono un'implementazione del pattern Factory Method.

### Integrazioni in pattern e architetture moderne

1. **Dependency Injection**: Il Factory Method è spesso utilizzato all'interno dei container DI per creare istanze di classi.

2. **Microservizi**: Viene utilizzato per creare client di servizi o adattatori per diversi sistemi esterni.

3. **Domain-Driven Design (DDD)**: Factory Method è utile nei "bounded contexts" per creare entità di dominio complesse.

4. **Architetture Clean/Hexagonal**: Utilizzato nelle implementazioni di adapter e port.

### Vantaggi nell'uso moderno

- **Testabilità**: Facilita il testing permettendo di sostituire implementazioni reali con mock durante i test.
- **Evoluzione dell'API**: Consente di cambiare le implementazioni concrete mantenendo stabile l'interfaccia.
- **Resilienza**: Permette strategie di fallback quando la creazione di un oggetto specifico fallisce.

## Esempio concreto: Sistema di Notifiche Multi-Canale

Ecco un esempio pratico di come il pattern Factory Method può essere utilizzato per implementare un sistema di notifiche che supporta diversi canali di comunicazione:

```java
// L'interfaccia Product
public interface NotificationService {
    void sendNotification(String recipient, String message);
}

// ConcreteProduct 1
public class EmailNotification implements NotificationService {
    @Override
    public void sendNotification(String recipient, String message) {
        // Logica per inviare email
        System.out.println("Invio email a " + recipient + ": " + message);
    }
}

// ConcreteProduct 2
public class SMSNotification implements NotificationService {
    @Override
    public void sendNotification(String recipient, String message) {
        // Logica per inviare SMS
        System.out.println("Invio SMS a " + recipient + ": " + message);
    }
}

// ConcreteProduct 3
public class PushNotification implements NotificationService {
    @Override
    public void sendNotification(String recipient, String message) {
        // Logica per inviare push notification
        System.out.println("Invio push notification a " + recipient + ": " + message);
    }
}

// La classe Creator astratta
public abstract class NotificationFactory {
    public abstract NotificationService createNotificationService();
    
    public void sendNotification(String recipient, String message) {
        NotificationService service = createNotificationService();
        service.sendNotification(recipient, message);
    }
}

// ConcreteCreator 1
public class EmailNotificationFactory extends NotificationFactory {
    @Override
    public NotificationService createNotificationService() {
        return new EmailNotification();
    }
}

// ConcreteCreator 2
public class SMSNotificationFactory extends NotificationFactory {
    @Override
    public NotificationService createNotificationService() {
        return new SMSNotification();
    }
}

// ConcreteCreator 3
public class PushNotificationFactory extends NotificationFactory {
    @Override
    public NotificationService createNotificationService() {
        return new PushNotification();
    }
}

// Client code
public class NotificationApp {
    public static void main(String[] args) {
        // Configurabile da file di configurazione o condizioni di runtime
        String notificationType = getUserPreference(); // "EMAIL", "SMS", o "PUSH"
        
        NotificationFactory factory = createFactory(notificationType);
        factory.sendNotification("user@example.com", "Questo è un messaggio importante!");
    }
    
    private static String getUserPreference() {
        // In un'app reale, potrebbe essere letto da un database o preferenze utente
        return "EMAIL"; 
    }
    
    private static NotificationFactory createFactory(String type) {
        switch (type) {
            case "EMAIL":
                return new EmailNotificationFactory();
            case "SMS":
                return new SMSNotificationFactory();
            case "PUSH":
                return new PushNotificationFactory();
            default:
                throw new IllegalArgumentException("Tipo di notifica non supportato: " + type);
        }
    }
}
```

## Implementazione con Spring Boot

In un'applicazione Spring Boot moderna, questo pattern potrebbe essere implementato con componenti di framework:

```java
@Service
public interface NotificationService {
    void sendNotification(String recipient, String message);
}

@Service("email")
public class EmailNotificationService implements NotificationService {
    // implementazione
}

@Service("sms")
public class SMSNotificationService implements NotificationService {
    // implementazione
}

@Service
public class NotificationFactory {
    private final Map<String, NotificationService> serviceMap;
    
    @Autowired
    public NotificationFactory(List<NotificationService> services) {
        serviceMap = services.stream()
            .collect(Collectors.toMap(
                s -> s.getClass().getAnnotation(Service.class).value(),
                Function.identity()
            ));
    }
    
    public NotificationService getNotificationService(String type) {
        return serviceMap.get(type);
    }
}

@RestController
public class NotificationController {
    private final NotificationFactory factory;
    
    @Autowired
    public NotificationController(NotificationFactory factory) {
        this.factory = factory;
    }
    
    @PostMapping("/notify")
    public ResponseEntity<?> sendNotification(@RequestBody NotificationRequest request) {
        NotificationService service = factory.getNotificationService(request.getType());
        service.sendNotification(request.getRecipient(), request.getMessage());
        return ResponseEntity.ok().build();
    }
}
```

## Conclusioni

Il pattern Factory Method rimane una soluzione elegante ai problemi di creazione di oggetti, mantenendo il codice aperto all'estensione ma chiuso alla modifica, uno dei principi SOLID fondamentali nello sviluppo software moderno.

Questo pattern offre:

- Un modo per delegare la responsabilità di creazione degli oggetti alle sottoclassi
- Una separazione chiara tra il codice che utilizza gli oggetti e il codice che li crea
- Facilità di estensione del sistema con nuovi tipi di prodotti
- Maggiore flessibilità nel gestire le dipendenze tra le classi

Anche nei contesti di sviluppo moderni, dove esistono framework e strumenti avanzati, il Factory Method continua a dimostrare il suo valore fornendo un approccio strutturato alla creazione di oggetti che promuove il riuso del codice e mantiene le applicazioni mantenibili e scalabili.