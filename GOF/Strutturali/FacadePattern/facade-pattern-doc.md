# Il Design Pattern Facade - Documentazione Completa

## Introduzione al Pattern Facade

Il pattern Facade è un design pattern strutturale che fornisce un'interfaccia semplificata ad un insieme complesso di classi, una libreria o un framework. Come suggerisce il nome stesso, "facade" (facciata in italiano) crea una facciata che nasconde la complessità del sistema sottostante.

## Struttura del Pattern Facade

1. **Facade**: 
   - Fornisce un'interfaccia unificata e semplificata per un insieme di interfacce in un sottosistema
   - Delega le richieste del client ai componenti appropriati del sottosistema

2. **Subsystem**: 
   - Contiene la logica complessa o le funzionalità che vogliamo semplificare
   - Può essere composto da una o più classi con operazioni distinte

3. **Client**: 
   - Interagisce con il sistema attraverso la Facade
   - Non deve conoscere i dettagli implementativi del sottosistema

## Implementazione Base

```java
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

// Classe Subsystem
class Subsystem {
    public void operation1() {
        System.out.println("Operazione 1 eseguita.");
    }

    public void operation2() {
        System.out.println("Operazione 2 eseguita.");
    }

    public void operation3() {
        System.out.println("Operazione 3 eseguita.");
    }
}

// Classe Main (Client)
public class Main {
    public static void main(String[] args) {
        Facade facade = new Facade();
        facade.operation();
    }
}
```

## Vantaggi del Pattern Facade

- **Semplificazione**: Riduce la complessità del sistema dal punto di vista del client
- **Disaccoppiamento**: Riduce le dipendenze tra il client e i componenti del sottosistema
- **Incapsulamento**: Nasconde i dettagli implementativi del sottosistema
- **Miglior leggibilità del codice**: Il client ha bisogno di conoscere solo l'interfaccia della Facade

## Quando usare il Pattern Facade

- Quando hai bisogno di fornire un'interfaccia semplice a un sistema complesso
- Quando vuoi strutturare un sistema in livelli
- Quando hai molte dipendenze tra client e classi di implementazione

## Esempio Concreto: Sistema di e-commerce

Ecco un esempio concreto dell'uso del pattern Facade in un contesto di e-commerce:

```java
// Sottosistemi complessi
class InventarioSystem {
    public boolean verificaDisponibilità(String prodottoId, int quantità) {
        // Logica complessa per verificare la disponibilità nel magazzino
        System.out.println("Verifica disponibilità prodotto: " + prodottoId);
        return true;
    }
    
    public void aggiornaInventario(String prodottoId, int quantità) {
        // Logica per aggiornare l'inventario
        System.out.println("Inventario aggiornato per: " + prodottoId);
    }
}

class PagamentoSystem {
    public boolean processaPagamento(String clienteId, double importo) {
        // Logica complessa per elaborare un pagamento
        System.out.println("Pagamento di €" + importo + " processato per cliente: " + clienteId);
        return true;
    }
    
    public String generaRicevuta(String ordineId) {
        // Generazione ricevuta
        System.out.println("Ricevuta generata per ordine: " + ordineId);
        return "RIC-" + ordineId;
    }
}

class SpedizioneSystem {
    public String creaSpedizione(String indirizzo, String[] prodotti) {
        // Logica per creare una spedizione
        System.out.println("Spedizione creata per indirizzo: " + indirizzo);
        return "SPD-" + System.currentTimeMillis();
    }
    
    public void notificaCorriere(String spedizioneId) {
        // Notifica al corriere
        System.out.println("Corriere notificato per spedizione: " + spedizioneId);
    }
}

class NotificheSystem {
    public void inviaEmail(String email, String messaggio) {
        // Invio email
        System.out.println("Email inviata a: " + email);
    }
    
    public void inviaSMS(String telefono, String messaggio) {
        // Invio SMS
        System.out.println("SMS inviato a: " + telefono);
    }
}

// Facade che semplifica l'intero processo di acquisto
class OrdineFacade {
    private InventarioSystem inventario;
    private PagamentoSystem pagamento;
    private SpedizioneSystem spedizione;
    private NotificheSystem notifiche;
    
    public OrdineFacade() {
        this.inventario = new InventarioSystem();
        this.pagamento = new PagamentoSystem();
        this.spedizione = new SpedizioneSystem();
        this.notifiche = new NotificheSystem();
    }
    
    // Metodo semplificato che orchestrata tutto il processo di acquisto
    public boolean effettuaOrdine(String clienteId, String email, String telefono, 
                               String indirizzo, String prodottoId, int quantità, double prezzo) {
        
        // 1. Verifica disponibilità
        if (!inventario.verificaDisponibilità(prodottoId, quantità)) {
            notifiche.inviaEmail(email, "Prodotto non disponibile");
            return false;
        }
        
        // 2. Processa pagamento
        boolean pagamentoOk = pagamento.processaPagamento(clienteId, prezzo * quantità);
        if (!pagamentoOk) {
            notifiche.inviaEmail(email, "Problema con il pagamento");
            return false;
        }
        
        // 3. Aggiorna inventario
        inventario.aggiornaInventario(prodottoId, quantità);
        
        // 4. Crea spedizione
        String[] prodotti = {prodottoId};
        String spedizioneId = spedizione.creaSpedizione(indirizzo, prodotti);
        spedizione.notificaCorriere(spedizioneId);
        
        // 5. Genera ricevuta e invia notifiche
        String ordineId = "ORD-" + System.currentTimeMillis();
        String ricevutaId = pagamento.generaRicevuta(ordineId);
        notifiche.inviaEmail(email, "Ordine confermato! Ricevuta: " + ricevutaId);
        notifiche.inviaSMS(telefono, "Il tuo ordine è in preparazione");
        
        return true;
    }
}

// Client che utilizza la Facade
public class ECommerceClient {
    public static void main(String[] args) {
        // Il client interagisce solo con la Facade, non con i sottosistemi
        OrdineFacade ordineManager = new OrdineFacade();
        
        // Effettua un ordine con un'unica chiamata
        ordineManager.effettuaOrdine(
            "CLI123", 
            "cliente@example.com", 
            "+39123456789", 
            "Via Roma 123, Milano", 
            "PROD-567", 
            2, 
            29.99
        );
        
        // Il client non ha bisogno di conoscere tutti i passaggi interni
        // e come interagire con i vari sottosistemi
    }
}
```

## Pattern Facade nei Contesti di Sviluppo Moderni

Il pattern Facade è ampiamente utilizzato in molti contesti di sviluppo moderni:

### 1. Framework e Librerie
- **Spring Framework**: Utilizza facade per semplificare operazioni complesse come la gestione delle transazioni o l'accesso ai dati
- **jQuery**: È essenzialmente una facade per le API DOM del browser, semplificando notevolmente la manipolazione del DOM

### 2. Architetture a Microservizi
- **API Gateway**: Agisce come una facade che nasconde la complessità dell'architettura a microservizi sottostante, offrendo un punto di ingresso unificato

### 3. Sviluppo Mobile
- **SDK per sviluppo app**: Spesso forniscono facade per l'accesso alle funzionalità hardware complesse del dispositivo

### 4. Cloud Computing
- **Servizi AWS**: La AWS SDK è un esempio di facade che semplifica l'interazione con servizi cloud complessi

### 5. Backend as a Service (BaaS)
- **Firebase**: Offre una facade semplificata per interagire con database, autenticazione e altri servizi

### 6. Pattern Architetturali Moderni
- **MVC/MVVM**: I controller in MVC o i view-model in MVVM spesso agiscono come facade tra la view e il model sottostante
- **Clean Architecture**: L'application layer spesso implementa il pattern facade per esporre casi d'uso ai layer esterni

### 7. DevOps e Infrastruttura
- **Docker Compose / Kubernetes**: Offrono facade per gestire ambienti complessi multi-container

### 8. Sviluppo Frontend
- **Componenti React/Vue/Angular**: Spesso fungono da facade, nascondendo complessità interne e offrendo API semplici

## Facade Pattern come Ponte tra Architetture

Il Pattern Facade viene spesso utilizzato come "ponte" o "adattatore" per facilitare la transizione tra diverse architetture.

### Scenari di Utilizzo tra Architetture

#### 1. Migrazione da Sistemi Legacy a Moderni

Quando un'azienda deve migrare da un sistema legacy a uno moderno, può creare una Facade che:
- Espone un'interfaccia moderna e pulita
- Internamente chiama il sistema legacy
- Permette una migrazione graduale, componente per componente

```java
// Sistema legacy con API obsolete
class LegacyCustomerSystem {
    public void addCustomerRecord(String id, String firstName, String lastName, String address) {
        System.out.println("Legacy system: Adding customer record to COBOL database");
        // Logica legacy per inserire un cliente nel database
    }
    
    public String[] getCustomerData(String customerId) {
        System.out.println("Legacy system: Retrieving customer from COBOL database");
        // Restituisce un array di stringhe con i dati del cliente
        return new String[] {"John", "Doe", "123 Main St"};
    }
}

// Nuova interfaccia moderna desiderata
interface ModernCustomerService {
    void createCustomer(Customer customer);
    Customer getCustomer(String id);
}

// Modello dati moderno
class Customer {
    private String id;
    private String firstName;
    private String lastName;
    private Address address;
    
    // Costruttori, getters e setters
    public Customer(String id, String firstName, String lastName, Address address) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
    }
    
    // Getters
    public String getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public Address getAddress() { return address; }
}

class Address {
    private String street;
    private String city;
    private String zipCode;
    
    // Costruttore
    public Address(String street, String city, String zipCode) {
        this.street = street;
        this.city = city;
        this.zipCode = zipCode;
    }
    
    // Getters
    public String getStreet() { return street; }
    public String getCity() { return city; }
    public String getZipCode() { return zipCode; }
}

// FACADE che fa da ponte tra il nuovo e il vecchio sistema
class CustomerServiceFacade implements ModernCustomerService {
    private LegacyCustomerSystem legacySystem;
    
    public CustomerServiceFacade() {
        this.legacySystem = new LegacyCustomerSystem();
    }
    
    @Override
    public void createCustomer(Customer customer) {
        // Adatta la chiamata moderna al sistema legacy
        legacySystem.addCustomerRecord(
            customer.getId(),
            customer.getFirstName(), 
            customer.getLastName(),
            customer.getAddress().getStreet() // Il sistema legacy gestisce solo l'indirizzo come stringa
        );
    }
    
    @Override
    public Customer getCustomer(String id) {
        // Adatta la risposta del sistema legacy al nuovo formato
        String[] legacyData = legacySystem.getCustomerData(id);
        
        // Converti i dati legacy nel nuovo formato
        Address address = new Address(legacyData[2], "Unknown City", "Unknown ZIP");
        return new Customer(id, legacyData[0], legacyData[1], address);
    }
}

// Client moderno che utilizza la nuova API
class ModernClient {
    public static void main(String[] args) {
        // Il client interagisce con l'interfaccia moderna
        ModernCustomerService customerService = new CustomerServiceFacade();
        
        // Crea un cliente usando l'API moderna
        Address address = new Address("456 New Street", "New York", "10001");
        Customer newCustomer = new Customer("CUST-001", "Alice", "Smith", address);
        customerService.createCustomer(newCustomer);
        
        // Ottieni un cliente usando l'API moderna
        Customer customer = customerService.getCustomer("CUST-002");
        System.out.println("Retrieved customer: " + customer.getFirstName() + " " + customer.getLastName());
    }
}
```

#### 2. Integrazione di Architetture Eterogenee

Il pattern Facade è fondamentale quando si devono integrare sistemi con architetture diverse:

- **Microservizi e Monoliti**: Una Facade può esporre microservizi come se fossero parte di un monolite (o viceversa)
- **Sistemi Cloud e On-Premises**: Permette di presentare un'interfaccia unificata
- **Differenti Tecnologie**: Per esempio, integrare un sistema Java con uno Python

#### 3. Evoluzione Architetturale

Durante l'evoluzione dell'architettura di un sistema:

- **Refactoring Graduale**: Permette di refactorizzare internamente il sistema senza modificare le API esposte
- **Strangler Pattern**: Tecnica per gradualmente sostituire componenti di un sistema esistente

#### 4. Adozione di Nuovi Pattern Architetturali

Quando si passa a nuovi pattern architetturali:

- **Da procedure a oggetti**: Facade può incapsulare codice procedurale in un'interfaccia orientata agli oggetti
- **Da blocking a reactive**: Può nascondere la complessità di una migrazione verso programming reattivo

### Esempio Pratico: Transizione da Monolite a Microservizi

```java
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

// ----- MONOLITE ORIGINALE -----
class MonolithOrderSystem {
    public void processOrder(int orderId, int customerId, String[] productIds, double totalAmount) {
        System.out.println("Monolith processing order: " + orderId);
        verifyInventory(productIds);
        chargeCustomer(customerId, totalAmount);
        updateInventory(productIds);
        scheduleShipment(orderId, customerId);
        sendConfirmationEmail(customerId, orderId);
    }
    
    private void verifyInventory(String[] productIds) {
        System.out.println("Monolith verifying inventory");
    }
    
    private void chargeCustomer(int customerId, double amount) {
        System.out.println("Monolith charging customer: " + customerId);
    }
    
    private void updateInventory(String[] productIds) {
        System.out.println("Monolith updating inventory");
    }
    
    private void scheduleShipment(int orderId, int customerId) {
        System.out.println("Monolith scheduling shipment");
    }
    
    private void sendConfirmationEmail(int customerId, int orderId) {
        System.out.println("Monolith sending confirmation email");
    }
}

// ----- NUOVI MICROSERVIZI -----
// Questi servizi funzionerebbero tramite REST o messaggistica in un'architettura reale

class InventoryService {
    public boolean checkAvailability(String[] productIds) {
        System.out.println("Inventory Microservice: checking availability");
        return true;
    }
    
    public void updateStock(String[] productIds) {
        System.out.println("Inventory Microservice: updating stock");
    }
}

class PaymentService {
    public String processPayment(int customerId, double amount) {
        System.out.println("Payment Microservice: processing payment for $" + amount);
        return "PAY-" + System.currentTimeMillis();
    }
}

class ShippingService {
    public String createShipment(int orderId, int customerId, String[] items) {
        System.out.println("Shipping Microservice: creating shipment");
        return "SHIP-" + System.currentTimeMillis();
    }
}

class NotificationService {
    public void sendEmail(int customerId, String subject, String body) {
        System.out.println("Notification Microservice: sending email to customer " + customerId);
    }
}

// ----- FACADE CHE ORCHESTRA I MICROSERVIZI -----
class OrderProcessingFacade {
    private final InventoryService inventoryService;
    private final PaymentService paymentService;
    private final ShippingService shippingService;
    private final NotificationService notificationService;
    
    // Mappa per tenere traccia dello stato di migrazione degli ordini
    // In uno scenario reale, questo sarebbe in un database
    private final Map<Integer, Boolean> ordersMigratedToMicroservices = new HashMap<>();
    
    // Riferimento al monolite originale per gli ordini non ancora migrati
    private final MonolithOrderSystem legacySystem;
    
    public OrderProcessingFacade() {
        // Inizializza i servizi
        this.inventoryService = new InventoryService();
        this.paymentService = new PaymentService();
        this.shippingService = new ShippingService();
        this.notificationService = new NotificationService();
        this.legacySystem = new MonolithOrderSystem();
        
        // Simula alcuni ordini già migrati
        ordersMigratedToMicroservices.put(1001, true);
        ordersMigratedToMicroservices.put(1002, true);
    }
    
    // Metodo principale esposto ai client, mantiene la stessa interfaccia
    public void processOrder(int orderId, int customerId, String[] productIds, double totalAmount) {
        // Decisione se usare il nuovo o il vecchio sistema
        if (shouldUseNewArchitecture(orderId)) {
            processOrderWithMicroservices(orderId, customerId, productIds, totalAmount);
        } else {
            // Fallback al monolite per ordini non ancora migrati
            legacySystem.processOrder(orderId, customerId, productIds, totalAmount);
        }
    }
    
    private boolean shouldUseNewArchitecture(int orderId) {
        // Decisione se l'ordine dovrebbe usare la nuova architettura
        // Potrebbe essere basato su vari fattori come età dell'ordine, tipo, ecc.
        return ordersMigratedToMicroservices.getOrDefault(orderId, false);
    }
    
    private void processOrderWithMicroservices(int orderId, int customerId, String[] productIds, double totalAmount) {
        System.out.println("\nProcessing order " + orderId + " with microservices architecture");
        
        // Verifica disponibilità (sincrona)
        boolean available = inventoryService.checkAvailability(productIds);
        if (!available) {
            throw new RuntimeException("Products not available");
        }
        
        // Processa pagamento
        String paymentId = paymentService.processPayment(customerId, totalAmount);
        
        // In un sistema reale, queste potrebbero essere operazioni asincrone
        CompletableFuture.runAsync(() -> inventoryService.updateStock(productIds));
        
        // Crea spedizione
        String shipmentId = shippingService.createShipment(orderId, customerId, productIds);
        
        // Invia notifica
        String subject = "Order Confirmation #" + orderId;
        String body = "Your order has been processed. Payment ID: " + paymentId + 
                      ", Shipment ID: " + shipmentId;
        notificationService.sendEmail(customerId, subject, body);
        
        System.out.println("Order " + orderId + " successfully processed with microservices\n");
    }
}

// ----- CLIENT CHE USA IL SISTEMA -----
public class OrderSystemClient {
    public static void main(String[] args) {
        // Il client interagisce sempre con la stessa interfaccia
        OrderProcessingFacade orderSystem = new OrderProcessingFacade();
        
        // Ordini di esempio
        String[] products1 = {"PROD-1", "PROD-2"};
        String[] products2 = {"PROD-3"};
        
        // Ordine migrato alla nuova architettura
        System.out.println("PROCESSING ORDER 1001 (migrated to microservices):");
        orderSystem.processOrder(1001, 5001, products1, 129.99);
        
        // Ordine ancora gestito dal monolite
        System.out.println("\nPROCESSING ORDER 1003 (still on monolith):");
        orderSystem.processOrder(1003, 5002, products2, 59.99);
    }
}
```

### Vantaggi nell'Uso del Facade per Transizioni Architetturali

1. **Migrazione Incrementale**: Permette di migrare il sistema gradualmente senza interrompere il servizio
2. **Riduzione del Rischio**: Il cambiamento può essere testato con un impatto limitato
3. **Compatibilità Retroattiva**: I client esistenti continuano a funzionare senza modifiche
4. **Isolamento dei Dettagli Implementativi**: I client non devono sapere quale architettura stanno utilizzando
5. **Separazione delle Responsabilità**: La Facade si occupa della traduzione e orchestrazione

### Approcci Comuni nella Pratica

- **Feature Toggles**: Integrati nella Facade per permettere di attivare/disattivare l'uso della nuova architettura
- **Canary Release**: La Facade può indirizzare gradualmente più traffico verso la nuova architettura
- **Circuit Breaker**: Può implementare pattern di resilienza per gestire fallimenti nella nuova architettura, tornando al sistema precedente

## Conclusione

Il pattern Facade è uno strumento essenziale nel toolkit di ogni sviluppatore software. Offre un modo elegante per semplificare interfacce complesse, migliorare la manutenibilità del codice e facilitare le transizioni tra diverse architetture. 

Utilizzato correttamente, il pattern Facade può significativamente migliorare la qualità del software, riducendo l'accoppiamento tra componenti e fornendo una chiara separazione delle responsabilità.

Nel contesto dello sviluppo software moderno, dove la complessità continua ad aumentare e l'evoluzione delle architetture è costante, il valore del pattern Facade diventa ancora più evidente, rendendolo uno dei design pattern più rilevanti e ampiamente utilizzati.
