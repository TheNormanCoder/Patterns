# Pattern Singleton: Guida Completa

## Introduzione al Pattern Singleton

Il Singleton è un design pattern creazionale che garantisce che una classe abbia una sola istanza e fornisce un punto di accesso globale a tale istanza. Questo pattern è utile quando è necessario che una classe abbia esattamente un'istanza disponibile per tutti i client dell'applicazione.

## Implementazione Base

```java
public class Singleton {
    private static Singleton instance;

    private Singleton() {}

    public static Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }
}
```

### Come funziona

1. **Classe Singleton**: 
   - Ha un campo `instance` statico privato che contiene l'unica istanza della classe
   - Il costruttore è privato (`private Singleton() {}`) per impedire la creazione di nuove istanze dall'esterno
   - Fornisce un metodo statico pubblico `getInstance()` che restituisce l'istanza unica

2. **Metodo getInstance()**:
   - Controlla se l'istanza esiste già (`if (instance == null)`)
   - Se non esiste, la crea (`instance = new Singleton()`)
   - Restituisce sempre la stessa istanza (`return instance`)

3. **Utilizzo**:
   ```java
   public class Main {
       public static void main(String[] args) {
           // Ottiene l'istanza della classe Singleton
           Singleton singleton = Singleton.getInstance();

           // Utilizza l'istanza della classe Singleton
           System.out.println(singleton.toString());
       }
   }
   ```

## Implementazioni Thread-Safe

L'implementazione base non è thread-safe. In un ambiente multi-thread, potrebbe accadere che due thread verifichino simultaneamente che `instance` sia null e creino due istanze diverse.

### 1. Singleton Thread-Safe con synchronized

```java
public class SingletonThreadSafe {
    private static SingletonThreadSafe instance;
    
    private SingletonThreadSafe() {}
    
    // Metodo sincronizzato per evitare problemi di concorrenza
    public static synchronized SingletonThreadSafe getInstance() {
        if (instance == null) {
            instance = new SingletonThreadSafe();
        }
        return instance;
    }
}
```

### 2. Singleton con inizializzazione eager (anticipata)

```java
public class SingletonEager {
    // L'istanza viene creata al caricamento della classe
    private static final SingletonEager instance = new SingletonEager();
    
    private SingletonEager() {}
    
    public static SingletonEager getInstance() {
        return instance;
    }
}
```

### 3. Singleton con Double-Checked Locking

```java
public class SingletonDoubleChecked {
    // Il volatile garantisce che le modifiche siano visibili a tutti i thread
    private static volatile SingletonDoubleChecked instance;
    
    private SingletonDoubleChecked() {}
    
    public static SingletonDoubleChecked getInstance() {
        // Prima verifica (non sincronizzata)
        if (instance == null) {
            // Sincronizzazione solo quando necessario
            synchronized (SingletonDoubleChecked.class) {
                // Seconda verifica (sincronizzata)
                if (instance == null) {
                    instance = new SingletonDoubleChecked();
                }
            }
        }
        return instance;
    }
}
```

### 4. Singleton usando Holder Pattern

```java
public class SingletonHolder {
    private SingletonHolder() {}
    
    // Classe interna statica che contiene l'istanza
    private static class SingletonHelperHolder {
        // Creato quando la classe SingletonHelperHolder viene caricata
        private static final SingletonHolder INSTANCE = new SingletonHolder();
    }
    
    public static SingletonHolder getInstance() {
        return SingletonHelperHolder.INSTANCE;
    }
}
```

### 5. Singleton con Enum

```java
public enum SingletonEnum {
    INSTANCE;
    
    // Puoi aggiungere metodi e campi
    public void doSomething() {
        // implementazione
    }
}
```

## Quando Usare il Pattern Singleton

Il pattern Singleton è utile in diversi scenari:

1. **Gestione di risorse condivise**: 
   - Connessioni database (pool di connessioni)
   - File system
   - Spool di stampa
   - Thread pool

2. **Configurazione dell'applicazione**: 
   - Memorizzare impostazioni globali accessibili da qualsiasi parte del programma
   - Caricare configurazioni da file esterni una sola volta

3. **Logging**:
   - Implementare un sistema di logging centralizzato
   - Garantire che tutti i messaggi di log vengano gestiti dallo stesso oggetto

4. **Caching**:
   - Gestire una cache centralizzata per migliorare le performance
   - Evitare duplicazioni di dati in memoria

5. **Gestione di dialoghi in applicazioni GUI**:
   - Dialog boxes che devono apparire una sola volta
   - Gestione centralizzata delle finestre

6. **Gestione di servizi**:
   - Service manager
   - Factory manager

7. **Implementazione di altri pattern**:
   - Factory method
   - Builder
   - Facade

## Vantaggi e Svantaggi

### Vantaggi
- Garantisce che una classe abbia una sola istanza
- Fornisce un punto di accesso globale a quella istanza
- L'oggetto viene inizializzato solo quando richiesto per la prima volta (lazy initialization)
- Evita la creazione di oggetti duplicati che consumano risorse

### Svantaggi
- Può introdurre dipendenze nascoste tra componenti
- Rende più difficile il testing delle classi che lo utilizzano
- In ambienti multi-thread richiede particolare attenzione all'implementazione
- Viola il principio di responsabilità singola (la classe gestisce sia la propria funzionalità che il proprio ciclo di vita)

## Dependency Injection: Un'Alternativa Moderna

La dependency injection (DI) è un'alternativa moderna al pattern Singleton che mantiene molti dei suoi vantaggi evitandone alcuni svantaggi significativi.

### Cos'è la Dependency Injection

La dependency injection è un pattern in cui le dipendenze di un oggetto (altri oggetti di cui ha bisogno) vengono "iniettate" dall'esterno invece di essere create internamente. Un framework o container DI si occupa di:

1. Creare le istanze degli oggetti
2. Gestire il loro ciclo di vita
3. "Iniettare" queste istanze dove necessario

### Vantaggi della Dependency Injection rispetto al Singleton

1. **Migliore testabilità**:
   - Con il Singleton è difficile sostituire l'istanza con mock durante i test
   - Con DI, puoi facilmente iniettare mock o stub per testing unitario

2. **Inversione del controllo**:
   - Con il Singleton, la classe controlla la propria istanza
   - Con DI, il controllo è invertito e delegato al container DI

3. **Minore accoppiamento**:
   - Singleton crea dipendenze rigide e nascoste
   - DI rende le dipendenze esplicite e modificabili

4. **Configurazione flessibile**:
   - Con DI puoi configurare diverse implementazioni per diverse situazioni
   - Un Singleton è più rigido nella sua configurazione

5. **Gestione del ciclo di vita più sofisticata**:
   - DI può gestire diversi scope (singleton, prototype, session, request, ecc.)
   - Il pattern Singleton offre un solo scope possibile

### Esempio pratico

**Con Singleton:**
```java
public class DatabaseService {
    private static DatabaseService instance;
    
    private DatabaseService() {}
    
    public static DatabaseService getInstance() {
        if (instance == null) {
            instance = new DatabaseService();
        }
        return instance;
    }
    
    public void query() {
        // Implementazione
    }
}

public class UserService {
    private DatabaseService db = DatabaseService.getInstance();
    
    public void saveUser() {
        db.query();
    }
}
```

**Con Dependency Injection:**
```java
public class DatabaseService {
    // Normale classe, non singleton
    public void query() {
        // Implementazione
    }
}

public class UserService {
    private final DatabaseService db;
    
    // La dipendenza viene iniettata dal costruttore
    public UserService(DatabaseService db) {
        this.db = db;
    }
    
    public void saveUser() {
        db.query();
    }
}

// Con un framework DI (esempio Spring):
@Service
public class UserService {
    private final DatabaseService db;
    
    @Autowired
    public UserService(DatabaseService db) {
        this.db = db;
    }
}
```

## Conclusione

Il pattern Singleton è un potente strumento di progettazione che offre una soluzione elegante quando è necessario garantire che una classe abbia una sola istanza. Tuttavia, come ogni pattern, dovrebbe essere utilizzato con consapevolezza delle sue limitazioni.

Nelle applicazioni moderne, la dependency injection offre spesso un'alternativa più flessibile e manutenibile, mantenendo i vantaggi del Singleton ma eliminandone molti svantaggi. La scelta tra Singleton e DI dovrebbe basarsi sulle specifiche esigenze dell'applicazione e sui principi di design che si desidera seguire.