# Pattern Composite - Spiegazione

## Cos'è il Pattern Composite

Il Pattern Composite è un pattern strutturale di progettazione che permette di comporre oggetti in strutture ad albero per rappresentare gerarchie parte-intero. Con questo pattern si possono lavorare con oggetti singoli (foglie) e composizioni di oggetti (compositi) in modo uniforme.

## Struttura del Pattern

La struttura del Pattern Composite è composta da:

1. **Component** (src/Component.java): È l'interfaccia comune che definisce il comportamento sia per gli elementi semplici che per quelli composti.
   - Dichiara operazioni comuni come `operation()`
   - Fornisce metodi per gestire i figli (`add`, `remove`, `getChild`)

```java
public interface Component {
    void operation();
    void add(Component component);
    void remove(Component component);
    Component getChild(int index);
}
```

2. **Leaf** (src/Leaf.java): Rappresenta gli elementi foglia nella gerarchia, che non hanno figli.
   - Implementa `operation()` con comportamento specifico
   - Lancia eccezioni per i metodi di gestione dei figli dato che non supporta queste operazioni

```java
public class Leaf implements Component {
    @Override
    public void operation() {
        System.out.println("Eseguo l'operazione specifica di Leaf");
    }

    @Override
    public void add(Component component) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void remove(Component component) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Component getChild(int index) {
        throw new UnsupportedOperationException();
    }
}
```

3. **Composite** (src/Composite.java): Rappresenta gli elementi composti che possono contenere altri elementi.
   - Mantiene una lista di figli (altri Component)
   - Implementa `operation()` eseguendo la propria operazione e poi delegandola ricorsivamente ai figli
   - Implementa i metodi di gestione dei figli per aggiungere, rimuovere e accedere ai componenti figli

```java
import java.util.ArrayList;
import java.util.List;

public class Composite implements Component {
    private List<Component> children = new ArrayList<>();

    @Override
    public void operation() {
        System.out.println("Eseguo l'operazione specifica di Composite");
        for (Component child : children) {
            child.operation();
        }
    }

    @Override
    public void add(Component component) {
        children.add(component);
    }

    @Override
    public void remove(Component component) {
        children.remove(component);
    }

    @Override
    public Component getChild(int index) {
        return children.get(index);
    }
}
```

4. **Client** (src/Main.java): Utilizza gli oggetti attraverso l'interfaccia Component, senza distinguere tra elementi semplici e composti.

```java
public class Main {
    public static void main(String[] args) {
        // Creazione dell'albero della gerarchia
        Component root = new Composite();
        Component branch1 = new Composite();
        Component branch2 = new Composite();
        Component leaf1 = new Leaf();
        Component leaf2 = new Leaf();
        Component leaf3 = new Leaf();

        root.add(branch1);
        root.add(branch2);
        branch1.add(leaf1);
        branch2.add(leaf2);
        branch2.add(leaf3);

        // Esecuzione dell'operazione su tutti gli oggetti dell'albero
        root.operation();
    }
}
```

## Come funziona

Nel file Main.java si può vedere il pattern in azione:

1. Viene creata una struttura ad albero con un root (Composite), due branch (Composite) e tre foglie (Leaf)
2. Quando viene chiamato `root.operation()`:
   - Il root esegue la sua operazione
   - Poi chiama ricorsivamente `operation()` su tutti i suoi figli
   - I branch fanno lo stesso con i propri figli
   - Le foglie eseguono semplicemente la loro operazione

L'output completo sarà:
```
Eseguo l'operazione specifica di Composite   (dal root)
Eseguo l'operazione specifica di Composite   (da branch1)
Eseguo l'operazione specifica di Leaf        (da leaf1)
Eseguo l'operazione specifica di Composite   (da branch2)
Eseguo l'operazione specifica di Leaf        (da leaf2)
Eseguo l'operazione specifica di Leaf        (da leaf3)
```

## A cosa serve il Pattern Composite

Il Pattern Composite serve a risolvere diversi problemi pratici nello sviluppo software:

1. **Gestione uniforme di elementi individuali e gruppi**: Consente di trattare un singolo oggetto e una collezione di oggetti con la stessa interfaccia. Senza questo pattern, dovresti gestire separatamente oggetti individuali e collezioni, complicando il codice con molti controlli condizionali.

2. **Semplificazione delle operazioni ricorsive**: Permette di eseguire operazioni ricorsive su strutture complesse con una singola chiamata. Ad esempio, `root.operation()` propaga automaticamente l'operazione a tutti i componenti dell'albero senza dover gestire manualmente la traversata.

3. **Eliminazione delle verifiche di tipo**: Evita di dover scrivere codice del tipo `if (oggetto instanceof Leaf) {...} else if (oggetto instanceof Composite) {...}`.

4. **Implementazione del principio "Aperto/Chiuso"**: Puoi aggiungere nuovi tipi di componenti senza modificare il codice esistente.

## Casi d'uso comuni

- **Interfacce grafiche**: Un contenitore (finestra, pannello) può contenere altri componenti UI (bottoni, campi di testo) o altri contenitori.

- **Sistemi di file**: Un filesystem dove le cartelle (composite) possono contenere file (leaf) o altre cartelle.

- **Documenti strutturati**: Un documento che contiene sezioni, che a loro volta possono contenere paragrafi o altre sezioni.

- **Menu di navigazione**: Menu con voci che possono essere semplici link o sottomenu con altre voci.

- **Diagrammi**: Elementi grafici che possono essere semplici o gruppi di altri elementi.

- **Strutture organizzative**: Dipartimenti che contengono dipendenti o altri dipartimenti.

## Il ruolo di Leaf e Composite

La classe `Leaf` nel Pattern Composite non rappresenta di per sé un'operazione o una funzionalità specifica, ma piuttosto un componente "atomico" (non divisibile) nella gerarchia. 

Quando aggiungi una `Leaf` a un `Composite`, stai effettivamente aggiungendo un componente che implementa un comportamento specifico attraverso il suo metodo `operation()`.

In un'implementazione reale, `operation()` potrebbe eseguire qualsiasi funzionalità concreta richiesta dall'applicazione. Per esempio:
- Disegnare un elemento grafico
- Elaborare un file
- Eseguire un calcolo
- Mostrare un elemento dell'interfaccia utente

Quando aggiungi diverse istanze di `Leaf` (o sottoclassi di `Leaf` con comportamenti diversi) a un `Composite`, stai effettivamente componendo diverse funzionalità all'interno della struttura composita. Il `Composite` diventa un contenitore di funzionalità che possono essere eseguite insieme attraverso una singola chiamata.

## Vantaggi del Pattern Composite

- **Uniformità**: Il client può trattare oggetti singoli e composizioni di oggetti allo stesso modo.
- **Ricorsività**: Le operazioni vengono propagate in modo ricorsivo nella struttura ad albero.
- **Estensibilità**: È facile aggiungere nuovi tipi di componenti senza modificare il codice esistente.
- **Flessibilità**: Consente di creare strutture complesse e annidate secondo necessità.
- **Semplicità del codice client**: Il client non deve conoscere i dettagli della struttura.

## Quando utilizzare il Pattern Composite

Il pattern Composite è particolarmente utile quando:

- Hai bisogno di rappresentare gerarchie di oggetti
- Vuoi che il codice client tratti uniformemente oggetti singoli e composizioni
- La struttura ha una natura ricorsiva
- Desideri applicare operazioni in modo uniforme attraverso l'intera struttura
