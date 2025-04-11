# Il Pattern Proxy

Il Pattern Proxy è un design pattern strutturale che ti consente di fornire un sostituto o un placeholder per un altro oggetto. Un proxy controlla l'accesso all'oggetto originale, permettendoti di eseguire operazioni prima o dopo che la richiesta raggiunga l'oggetto originale.

## Struttura di Base

L'implementazione classica del pattern Proxy include:

1. **Subject (Soggetto)**: Un'interfaccia che definisce le operazioni comuni che possono essere eseguite sia dal RealSubject che dal Proxy.

2. **RealSubject (Soggetto Reale)**: La classe che implementa l'interfaccia Subject e rappresenta l'oggetto reale che il proxy rappresenta.

3. **Proxy**: Mantiene un riferimento al RealSubject e implementa la stessa interfaccia, controllando l'accesso ad esso.

4. **Client**: Utilizza il Proxy come se fosse il RealSubject.

## Esempio di Implementazione Base

```java
// Definizione dell'interfaccia Subject
public interface Subject {
    void request();
}

// Implementazione del RealSubject
public class RealSubject implements Subject {
    public void request() {
        System.out.println("RealSubject: Handling request.");
    }
}

// Implementazione del Proxy
public class Proxy implements Subject {
    private RealSubject realSubject;

    public void request() {
        if (realSubject == null) {
            realSubject = new RealSubject();
        }
        preRequest();
        realSubject.request();
        postRequest();
    }

    private void preRequest() {
        System.out.println("Proxy: Preparing request.");
    }

    private void postRequest() {
        System.out.println("Proxy: Finishing request.");
    }
}

// Utilizzo del Proxy pattern
public class Client {
    public static void main(String[] args) {
        Subject subject = new Proxy();
        subject.request();
    }
}
```

Nell'esecuzione di questo codice, quando viene chiamato `subject.request()`:
1. Viene creata un'istanza di RealSubject (se non esiste già)
2. Viene stampato "Proxy: Preparing request."
3. Viene chiamato il metodo `request()` del RealSubject, che stampa "RealSubject: Handling request."
4. Viene stampato "Proxy: Finishing request."

## Tipi di Pattern Proxy

### 1. Proxy Virtuale (Virtual Proxy)

Il Proxy Virtuale ritarda la creazione di oggetti costosi fino a quando non sono realmente necessari.

```java
// Interfaccia comune
interface Image {
    void display();
}

// RealSubject - l'oggetto reale che è costoso da creare
class HighResolutionImage implements Image {
    private String filename;
    
    public HighResolutionImage(String filename) {
        this.filename = filename;
        loadImageFromDisk();
    }
    
    private void loadImageFromDisk() {
        System.out.println("Caricamento dell'immagine ad alta risoluzione: " + filename);
        // Simuliamo un'operazione costosa
        try { Thread.sleep(3000); } catch (InterruptedException e) { e.printStackTrace(); }
    }
    
    @Override
    public void display() {
        System.out.println("Visualizzazione dell'immagine ad alta risoluzione: " + filename);
    }
}

// Proxy
class ImageProxy implements Image {
    private String filename;
    private HighResolutionImage realImage;
    
    public ImageProxy(String filename) {
        this.filename = filename;
    }
    
    @Override
    public void display() {
        // Creazione dell'oggetto reale solo quando necessario
        if (realImage == null) {
            realImage = new HighResolutionImage(filename);
        }
        realImage.display();
    }
}

// Client
class VirtualProxyExample {
    public static void main(String[] args) {
        // L'immagine non viene caricata finché non chiamiamo display()
        Image image = new ImageProxy("foto_vacanze.jpg");
        System.out.println("Immagine creata, ma non ancora caricata");
        
        // L'immagine viene effettivamente caricata solo ora
        System.out.println("Caricamento immagine...");
        image.display();
        
        // La seconda volta non deve ricaricare l'immagine
        System.out.println("Visualizzazione di nuovo...");
        image.display();
    }
}
```

### 2. Proxy di Protezione (Protection Proxy)

Il Proxy di Protezione controlla l'accesso all'oggetto originale in base ai diritti dell'utente.

```java
// Interfaccia comune
interface Document {
    void view();
    void edit();
}

// RealSubject
class RealDocument implements Document {
    private String content;
    private String name;
    
    public RealDocument(String name, String content) {
        this.name = name;
        this.content = content;
    }
    
    @Override
    public void view() {
        System.out.println("Visualizzazione del documento: " + name);
        System.out.println("Contenuto: " + content);
    }
    
    @Override
    public void edit() {
        System.out.println("Modifica del documento: " + name);
    }
}

// Proxy di Protezione
class DocumentProxy implements Document {
    private RealDocument document;
    private String userRole;
    
    public DocumentProxy(String name, String content, String userRole) {
        this.document = new RealDocument(name, content);
        this.userRole = userRole;
    }
    
    @Override
    public void view() {
        // Tutti possono visualizzare
        document.view();
    }
    
    @Override
    public void edit() {
        // Solo gli admin possono modificare
        if ("ADMIN".equals(userRole)) {
            document.edit();
        } else {
            System.out.println("Accesso negato: è richiesto il ruolo ADMIN per modificare il documento");
        }
    }
}

// Client
class ProtectionProxyExample {
    public static void main(String[] args) {
        Document documentUser = new DocumentProxy("budget.xlsx", "Dati bilancio 2024", "USER");
        Document documentAdmin = new DocumentProxy("budget.xlsx", "Dati bilancio 2024", "ADMIN");
        
        // L'utente normale può visualizzare
        System.out.println("--- Accesso utente normale ---");
        documentUser.view();
        documentUser.edit(); // Non funzionerà
        
        // L'admin può sia visualizzare che modificare
        System.out.println("\n--- Accesso admin ---");
        documentAdmin.view();
        documentAdmin.edit();
    }
}
```

### 3. Proxy Remoto (Remote Proxy)

Il Proxy Remoto rappresenta localmente un oggetto che esiste in uno spazio di indirizzamento remoto.

```java
// Interfaccia comune
interface WeatherService {
    String getWeatherData(String city);
}

// RealSubject (simuliamo un servizio remoto)
class RemoteWeatherService implements WeatherService {
    @Override
    public String getWeatherData(String city) {
        // In un caso reale, qui ci sarebbe una chiamata API a un servizio remoto
        return "Temperatura: 22°C, Umidità: 65%, Città: " + city;
    }
}

// Proxy Remoto
class WeatherServiceProxy implements WeatherService {
    private RemoteWeatherService service;
    
    public WeatherServiceProxy() {
        System.out.println("Inizializzazione connessione al servizio remoto...");
        // Simuliamo la connessione al servizio remoto
        try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }
        this.service = new RemoteWeatherService();
    }
    
    @Override
    public String getWeatherData(String city) {
        System.out.println("Proxy: Inizio chiamata remota per " + city);
        String result = service.getWeatherData(city);
        System.out.println("Proxy: Fine chiamata remota");
        return result;
    }
}

// Client
class RemoteProxyExample {
    public static void main(String[] args) {
        WeatherService weatherService = new WeatherServiceProxy();
        
        // Il client interagisce con il proxy come se fosse il servizio remoto
        System.out.println("Client: Richiedo dati meteo per Milano");
        String milanWeather = weatherService.getWeatherData("Milano");
        System.out.println(milanWeather);
        
        System.out.println("\nClient: Richiedo dati meteo per Roma");
        String romeWeather = weatherService.getWeatherData("Roma");
        System.out.println(romeWeather);
    }
}
```

### 4. Proxy Smart (Smart Proxy)

Il Proxy Smart aggiunge funzionalità quando si accede a un oggetto, come logging, caching, conteggio dei riferimenti.

```java
// Interfaccia comune
interface Database {
    String query(String sql);
}

// RealSubject
class RealDatabase implements Database {
    public RealDatabase() {
        System.out.println("Connessione al database stabilita");
    }
    
    @Override
    public String query(String sql) {
        // Simuliamo una query database
        return "Risultato della query: " + sql;
    }
}

// Smart Proxy con caching e logging
class SmartDatabaseProxy implements Database {
    private RealDatabase database;
    private Map<String, String> cache = new HashMap<>();
    private int queryCount = 0;
    
    @Override
    public String query(String sql) {
        queryCount++;
        System.out.println("Log: Esecuzione query #" + queryCount + ": " + sql);
        
        // Controllo cache
        if (cache.containsKey(sql)) {
            System.out.println("Cache hit: ritorno risultato dalla cache");
            return cache.get(sql);
        }
        
        // Lazy initialization
        if (database == null) {
            database = new RealDatabase();
        }
        
        // Esegui query e memorizza in cache
        String result = database.query(sql);
        cache.put(sql, result);
        System.out.println("Risultato memorizzato in cache");
        
        return result;
    }
}

// Client
class SmartProxyExample {
    public static void main(String[] args) {
        Database db = new SmartDatabaseProxy();
        
        // Prima query - non in cache
        System.out.println(db.query("SELECT * FROM users"));
        
        // Seconda query - non in cache
        System.out.println(db.query("SELECT * FROM products"));
        
        // Ripetizione prima query - dovrebbe essere in cache
        System.out.println(db.query("SELECT * FROM users"));
    }
}

import java.util.*;  // Aggiungi questa importazione per Map e HashMap
```

## Usi comuni del Pattern Proxy

1. **Proxy Virtuale**: Ritarda la creazione di oggetti costosi fino a quando non sono veramente necessari
2. **Proxy di Protezione**: Controlla l'accesso all'oggetto originale in base a diritti di accesso
3. **Proxy Remoto**: Rappresenta localmente un oggetto che esiste in uno spazio di indirizzamento remoto
4. **Proxy Smart**: Aggiunge funzionalità quando si accede a un oggetto (logging, caching, controllo delle referenze, ecc.)

## Vantaggi del Pattern Proxy

- Consente di aggiungere funzionalità all'accesso di un oggetto senza modificare il codice dell'oggetto stesso
- Rispetta il principio "Open/Closed" (aperto all'estensione, chiuso alla modifica)
- Permette di separare le preoccupazioni trasversali (come logging, caching, controllo degli accessi) dalla logica di business
- Migliora la sicurezza e le prestazioni delle applicazioni
- Permette la lazy initialization di oggetti costosi

## Quando Usare il Pattern Proxy

- Quando hai bisogno di controllo sull'accesso a un oggetto
- Quando vuoi ritardare la creazione di oggetti costosi fino a quando non sono realmente necessari
- Quando hai bisogno di implementare funzionalità trasversali come logging, caching o controllo degli accessi
- Quando vuoi rappresentare localmente oggetti remoti
- Quando vuoi proteggere l'oggetto originale da manipolazioni non autorizzate
