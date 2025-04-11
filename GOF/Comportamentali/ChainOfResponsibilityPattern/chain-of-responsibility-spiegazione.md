# Chain of Responsibility Pattern - Spiegazione Completa

## Introduzione al Pattern

Il Chain of Responsibility è un pattern comportamentale che permette di passare una richiesta lungo una catena di gestori (handlers). Ogni gestore decide se può elaborare la richiesta o se deve passarla al gestore successivo nella catena.

### Componenti principali:

1. **Handler (Gestore)**: Un'interfaccia che definisce le operazioni che tutti i gestori concreti devono implementare.
2. **ConcreteHandler (Gestore Concreto)**: Implementazioni specifiche dell'interfaccia Handler.
3. **Client**: Inizializza la catena e invia richieste al primo gestore della catena.
4. **Request (Richiesta)**: L'oggetto che viene passato lungo la catena.

### Come funziona:

1. L'interfaccia `Handler` definisce due metodi:
   - `setNext()`: per impostare il prossimo gestore nella catena
   - `handleRequest()`: per elaborare la richiesta

2. I gestori concreti implementano l'interfaccia `Handler`:
   - Ogni gestore controlla se può elaborare la richiesta
   - Se non può, passa la richiesta al prossimo gestore nella catena
   - Se non c'è un gestore successivo, viene mostrato un messaggio di fallimento

3. Il `Client` crea una catena collegando i gestori e invia richieste al primo gestore della catena.

### Vantaggi:

1. **Disaccoppiamento**: Il client non deve conoscere quale gestore elaborerà la richiesta.
2. **Flessibilità**: Puoi facilmente aggiungere o rimuovere gestori dalla catena senza modificare il codice client.
3. **Responsabilità singola**: Ogni gestore si occupa solo di un tipo specifico di richiesta.
4. **Open/Closed Principle**: Puoi aggiungere nuovi gestori senza modificare il codice esistente.

## Casi d'uso

Il design pattern Chain of Responsibility viene utilizzato in diversi scenari pratici:

1. **Sistemi di gestione delle richieste di supporto**:
   - Le richieste di supporto client possono essere instradate attraverso vari livelli di supporto (livello 1, livello 2, livello 3) in base alla complessità del problema.

2. **Middleware in applicazioni web**:
   - In framework come Express.js, Django o Spring, le richieste HTTP passano attraverso una catena di middleware.

3. **Sistemi di approvazione**:
   - Nelle applicazioni di gestione workflow, come l'approvazione di rimborsi spese o richieste di ferie.

4. **Filtri di eventi**:
   - Nei sistemi event-driven, gli eventi possono essere filtrati attraverso una catena di gestori.

5. **Logger e gestione degli errori**:
   - Un sistema di logging può avere diversi livelli (debug, info, warning, error).

6. **Elaborazione documenti**:
   - In sistemi che elaborano documenti, ogni fase di elaborazione può essere un handler nella catena.

7. **Sistemi di sicurezza e controllo accessi**:
   - Le richieste di accesso possono passare attraverso diversi controlli.

8. **Pipeline di elaborazione dati**:
   - In sistemi di elaborazione dati, ogni fase della pipeline può essere un handler.

9. **Validazione input**:
   - Validare input utente attraverso diversi criteri.

10. **Calcolo imposte o sconti**:
    - In sistemi di e-commerce, il calcolo di sconti o imposte può seguire una catena di regole.

## Esempio Concreto: Sistema di Approvazione Rimborsi Spese

### Struttura del Sistema:

```java
// Classe che rappresenta una richiesta di rimborso spese
public class ExpenseRequest {
    private double amount;
    private String purpose;
    private String employeeId;
    
    // Costruttore e metodi getter
}

// Interfaccia per gli approvatori
public interface Approver {
    void setNext(Approver next);
    void processRequest(ExpenseRequest request);
}

// TeamLeader - può approvare spese fino a 1000€
public class TeamLeader implements Approver {
    // Implementazione
}

// Manager - può approvare spese fino a 5000€
public class DepartmentManager implements Approver {
    // Implementazione
}

// Director - può approvare spese fino a 20000€
public class Director implements Approver {
    // Implementazione
}

// CEO - può approvare qualsiasi spesa
public class CEO implements Approver {
    // Implementazione
}
```

### Funzionamento:

1. **Richiesta di rimborso spese**: Contiene l'importo, lo scopo e l'ID del dipendente.
2. **Catena di approvatori**: Ogni livello manageriale può approvare fino a un certo limite:
   - **Team Leader**: fino a 1.000€
   - **Manager di Dipartimento**: fino a 5.000€
   - **Direttore**: fino a 20.000€
   - **CEO**: qualsiasi importo
3. **Processo di approvazione**:
   - La richiesta viene inizialmente inviata al Team Leader
   - Se l'importo supera il limite del Team Leader, la richiesta viene inoltrata al Manager di Dipartimento
   - Se l'importo supera anche questo limite, viene inoltrata al Direttore
   - Se l'importo supera anche il limite del Direttore, viene inoltrata al CEO

### Vantaggi in questo scenario:

1. **Flessibilità nella gerarchia di approvazione**: Facile modificare la catena.
2. **Separazione delle responsabilità**: Ogni livello gestisce solo richieste entro il proprio limite.
3. **Scalabilità**: Facile aggiungere nuovi approvatori.
4. **Trasparenza del processo**: Chiaro tracciamento del flusso di approvazione.

## Il Pattern nei Framework e Tecnologie Moderne

Il Chain of Responsibility è alla base di diversi framework e pattern moderni:

1. **Middleware in framework web**:
   - Express.js (Node.js), ASP.NET Core, Django (Python)

2. **Interceptor Pattern**:
   - Spring Framework, Hibernate

3. **Filtri di servlet in Java EE/Jakarta EE**

4. **Event Propagation nei sistemi GUI**:
   - Bubbling degli eventi in JavaScript/DOM

5. **Pipeline Pattern**:
   - Apache Camel, Azure Data Factory

6. **Command Line Parser**:
   - Picocli, Commons CLI

7. **Aspect-Oriented Programming (AOP)**:
   - Spring AOP

8. **Error/Exception Handling**

9. **Sistemi di logging**:
   - Log4j, SLF4J

10. **Orchestratori di microservizi**:
    - Gateway API come Kong, Zuul, Spring Cloud Gateway

## Focus sui Gateway API per Microservizi

Nei gateway API per microservizi, il Chain of Responsibility viene implementato attraverso filtri o plugin configurabili.

### Flusso di una richiesta in un gateway API:

```
Client → Gateway API → [Autenticazione] → [Rate Limiting] → [Logging] → [Routing] → Microservizio
```

### Esempio con Kong:

```yaml
# Definizione dei servizi
services:
  - name: user-service
    url: http://internal-user-service:8080
    routes:
      - paths: ["/api/users"]
    plugins:
      - name: key-auth       # Filtro di autenticazione
      - name: rate-limiting  # Filtro di rate limiting
      - name: http-log       # Filtro di logging
```

### Esempio con Spring Cloud Gateway:

```java
@Configuration
public class GatewayConfig {
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
            .route("user_service_route", r -> r.path("/api/users/**")
                .filters(f -> f
                    .addRequestHeader("X-Gateway-Tag", "user-gateway")  // Trasformazione
                    .requestRateLimiter()                              // Rate limiting
                    .circuitBreaker(config -> config                   // Resilienza
                        .setName("userServiceCircuitBreaker")
                        .setFallbackUri("forward:/fallback/users"))
                    .retry(3))                                         // Ripetizione
                .uri("lb://USER-SERVICE"))
            .build();
    }
}
```

### Vantaggi nei Gateway API:

1. **Separazione delle responsabilità**: Ogni aspetto è gestito da un componente specializzato
2. **Configurabilità**: La catena può essere configurata dinamicamente
3. **Riusabilità**: Gli stessi filtri possono essere applicati a diversi endpoint
4. **Scalabilità**: Facile aggiungere nuove funzionalità
5. **Osservabilità**: Ogni punto della catena può emettere metriche e log

## Conclusione

Il Chain of Responsibility è un pattern potente e flessibile che trova applicazione in numerosi contesti della programmazione moderna. La sua capacità di disaccoppiare mittenti e ricevitori, unita alla possibilità di modificare dinamicamente la catena di elaborazione, lo rende particolarmente adatto per sistemi complessi e in evoluzione come architetture a microservizi, framework web e sistemi di middleware.
