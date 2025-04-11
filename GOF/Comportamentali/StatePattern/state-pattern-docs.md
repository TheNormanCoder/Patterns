# Pattern State

Il Pattern State è un design pattern comportamentale che permette a un oggetto di modificare il suo comportamento quando il suo stato interno cambia. Questo pattern è utile quando un oggetto deve cambiare il suo comportamento in base al suo stato, e il comportamento cambia durante l'esecuzione.

## Struttura del Pattern State

La struttura del Pattern State include i seguenti componenti:

1. **Interfaccia State**: Definisce un'interfaccia comune per tutti gli stati concreti.
   ```java
   interface State {
       void handle();
   }
   ```

2. **Stati Concreti**: Implementazioni concrete dell'interfaccia State.
   - `ConcreteStateA`: Rappresenta uno stato specifico e implementa il comportamento associato.
   - `ConcreteStateB`: Rappresenta un altro stato specifico con il suo comportamento.

3. **Context**: Mantiene un riferimento allo stato corrente e può cambiare stato quando necessario.
   ```java
   class Context {
       private State currentState;
       
       public void setState(State state) {
           this.currentState = state;
       }
       
       public void request() {
           this.currentState.handle();
       }
   }
   ```

4. **Client** (nel nostro caso, la classe `Main`): Utilizza il Context per interagire con gli stati.

## Come funziona il Pattern State

1. Il Context mantiene un riferimento a uno degli stati concreti e delega a questo stato le richieste specifiche.
2. Quando lo stato del Context cambia, viene sostituito l'oggetto State con un'altra implementazione.
3. Il client interagisce con il Context, che a sua volta delega le operazioni allo stato corrente.

## Esempio di utilizzo

```java
public static void main(String[] args) {
    Context context = new Context();
    context.setState(new ConcreteStateA());
    context.request();           // Output: "Sono nello stato A"
    context.setState(new ConcreteStateB());
    context.request();           // Output: "Sono nello stato B"
}
```

Qui vediamo che:
1. Viene creato un oggetto Context
2. Il suo stato viene impostato su ConcreteStateA
3. Quando viene chiamato `request()`, viene eseguito il comportamento dello stato A
4. Poi lo stato viene cambiato in ConcreteStateB
5. Quando viene chiamato di nuovo `request()`, viene eseguito il comportamento dello stato B

## Vantaggi del Pattern State

1. **Separazione delle responsabilità**: I comportamenti specifici per ogni stato sono incapsulati in classi separate.
2. **Estensibilità**: È facile aggiungere nuovi stati senza modificare il Context o gli stati esistenti.
3. **Eliminazione di condizioni complesse**: Sostituisce grandi blocchi condizionali (if/else o switch) con polimorfismo.
4. **Migliore organizzazione del codice**: Rende più chiare le transizioni di stato.

# Relazione tra State Pattern ed Event Sourcing

Il Pattern State può essere considerato una delle basi concettuali che supportano il pattern Event Sourcing, sebbene ci siano differenze significative tra i due.

## Confronto tra State Pattern ed Event Sourcing

### Gestione dello stato
- **State Pattern**: L'oggetto Context mantiene un riferimento al suo stato attuale e cambia comportamento quando lo stato cambia.
- **Event Sourcing**: Lo stato di un sistema è determinato dalla sequenza di eventi che sono stati applicati nel tempo.

### Transizioni di stato
- **State Pattern**: Le transizioni sono esplicite e dirette (chiami `setState()` per cambiare stato).
- **Event Sourcing**: Le transizioni di stato sono il risultato dell'applicazione di eventi, dove ogni evento causa una modifica incrementale allo stato.

### Concetto di storia
- **State Pattern**: Di per sé non mantiene una storia degli stati precedenti.
- **Event Sourcing**: È fondamentalmente basato sulla storia completa: tutti gli eventi vengono conservati in ordine cronologico, permettendo di ricostruire lo stato in qualsiasi momento.

## Come Event Sourcing estende i concetti del State Pattern

L'Event Sourcing può essere visto come un'estensione del Pattern State con questi concetti aggiuntivi:

1. **Event Store**: Viene mantenuto un registro immutabile di tutti gli eventi che modificano lo stato.

2. **Ricostruzione dello stato**: Lo stato attuale è il risultato dell'applicazione sequenziale di tutti gli eventi storici.

3. **Proiezioni**: Rappresentazioni specializzate dello stato ottimizzate per casi d'uso specifici.

4. **Idempotenza**: Gli eventi possono essere riapplicati senza effetti collaterali.

## Esempio concettuale

Nel State Pattern:
```java
Context context = new Context();
context.setState(new ConcreteStateA());  // Cambiamento diretto di stato
context.setState(new ConcreteStateB());  // Altro cambiamento diretto
```

In un sistema Event Sourcing basato su questi concetti:
```java
// Approccio Event Sourcing
EventStore store = new EventStore();
Context context = new Context();

// Invece di cambiare direttamente lo stato, si applicano eventi
store.append(new StateChangedToAEvent());
store.append(new StateChangedToBEvent());

// Per ottenere lo stato attuale, si riapplicano tutti gli eventi
for (Event event : store.getAllEvents()) {
    context.apply(event); // Ogni evento modifica incrementalmente lo stato
}
```

## Conclusione

Il Pattern State fornisce un modello per gestire i cambiamenti di comportamento basati sullo stato, che è un concetto fondamentale per Event Sourcing. Tuttavia, Event Sourcing aggiunge dimensioni critiche:

1. La persistenza temporale degli eventi
2. La capacità di ricostruire lo stato in qualsiasi momento
3. La separazione tra comandi (che generano eventi) ed eventi (che cambiano lo stato)

Quindi, il Pattern State può essere considerato come uno dei fondamenti concettuali dell'Event Sourcing, ma Event Sourcing incorpora molti altri principi e pratiche per creare un approccio più completo alla gestione dello stato e della storia di un sistema.