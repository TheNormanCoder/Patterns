# Il Pattern Visitor: Spiegazione Completa

## Introduzione al Pattern Visitor

Il Visitor è un design pattern comportamentale che permette di separare algoritmi dalla struttura di oggetti su cui operano. Questo pattern implementa il principio di separazione delle responsabilità e rispetta il principio Open/Closed (aperto per l'estensione, chiuso per la modifica).

## Componenti del Pattern

### Struttura Base

1. **Visitor (Interfaccia)**: Dichiara i metodi di visita per ciascun tipo di elemento concreto.
2. **Element (Interfaccia)**: Dichiara il metodo `accept` che accetta un visitor.
3. **ConcreteElement (Classi)**: Implementano l'interfaccia Element.
4. **ConcreteVisitor**: Implementa l'interfaccia Visitor con le operazioni specifiche per ogni tipo di elemento.
5. **Client**: Crea gli elementi e il visitor, quindi fa in modo che gli elementi accettino il visitor.

### Esempio Base

```java
// Visitor interface
public interface Visitor {
    void visitConcreteElementA(ConcreteElementA element);
    void visitConcreteElementB(ConcreteElementB element);
}

// Element interface
public interface Element {
    void accept(Visitor visitor);
}

// ConcreteElement classes
public class ConcreteElementA implements Element {
    @Override
    public void accept(Visitor visitor) {
        visitor.visitConcreteElementA(this);
    }

    public void operationA() {
        System.out.println(this.getClass());
    }
}

public class ConcreteElementB implements Element {
    @Override
    public void accept(Visitor visitor) {
        visitor.visitConcreteElementB(this);
    }

    public void operationB() {
        System.out.println(this.getClass());
    }
}

// ConcreteVisitor
public class ConcreteVisitor implements Visitor {
    @Override
    public void visitConcreteElementA(ConcreteElementA element) {
        element.operationA();
    }

    @Override
    public void visitConcreteElementB(ConcreteElementB element) {
        element.operationB();
    }
}

// Client code
public class Client {
    public static void main(String[] args) {
        List<Element> elements = new ArrayList<>();
        elements.add(new ConcreteElementA());
        elements.add(new ConcreteElementB());

        ConcreteVisitor concreteVisitor = new ConcreteVisitor();

        for (Element element : elements) {
            element.accept(concreteVisitor);
        }
    }
}
```

## Come Funziona il Pattern

Il Visitor utilizza un meccanismo chiamato "double dispatch" che è fondamentale per capire come funziona:

1. Il client chiama `element.accept(visitor)`
2. L'elemento determina quale metodo del visitor chiamare (`visitor.visit(this)`)
3. Il visitor determina cosa fare basandosi sul tipo concreto dell'elemento

Questo meccanismo permette di aggiungere facilmente nuove operazioni senza modificare le classi degli elementi.

## Chi Possiede l'Algoritmo?

Una domanda importante è: chi possiede effettivamente l'algoritmo nel pattern Visitor? La risposta è: **il Visitor**.

Nel pattern Visitor:
- La **struttura** è rappresentata dalle classi `Element`
- L'**algoritmo** è contenuto nelle classi `Visitor`

Il Visitor implementa metodi specifici per ogni tipo di elemento, e questi metodi contengono la logica operativa (l'algoritmo) per ciascun tipo.

## Il Nome "Visitor"

Il nome "Visitor" può sembrare fuorviante se ci si aspetta che sia l'elemento a "visitare" l'algoritmo, ma in realtà è il contrario. Il nome deriva dalla metafora che sta alla base del pattern:

- Il Visitor "fa visita" agli elementi della struttura
- Durante la visita, applica il suo algoritmo specifico a ciascun elemento
- L'elemento "accoglie" (accept) il visitatore e gli consente di operare su di sé

## La Relazione Reciproca tra Visitor ed Elementi

C'è una reciprocità interessante nella relazione:

1. Il Visitor contiene l'algoritmo principale (la logica operativa)
2. Ma allo stesso tempo, il Visitor spesso chiama metodi specifici degli elementi:

```java
@Override
public void visitConcreteElementA(ConcreteElementA element) {
    element.operationA();
}
```

Il Visitor usa i metodi degli elementi come parte del suo algoritmo. Quindi c'è una collaborazione: il Visitor orchestra operazioni più complesse utilizzando i dati e le operazioni "atomiche" fornite dagli elementi.

## Un Esempio Pratico: Sistema di Documenti

Per illustrare meglio l'utilità del pattern, consideriamo un sistema di gestione documenti con diversi tipi di documenti (PDF, DOCX, HTML) e varie operazioni (stampa, esportazione, validazione).

```java
// Interfaccia Document (Element)
interface Document {
    void accept(DocumentVisitor visitor);
}

// Classi concrete di Document
class PDFDocument implements Document {
    private String content;
    private int pages;

    // Getter e costruttore...

    @Override
    public void accept(DocumentVisitor visitor) {
        visitor.visit(this);
    }
}

class DOCXDocument implements Document {
    private String content;
    private boolean hasTrackedChanges;

    // Getter e costruttore...

    @Override
    public void accept(DocumentVisitor visitor) {
        visitor.visit(this);
    }
}

class HTMLDocument implements Document {
    private String content;
    private boolean isResponsive;

    // Getter e costruttore...

    @Override
    public void accept(DocumentVisitor visitor) {
        visitor.visit(this);
    }
}

// Interfaccia Visitor
interface DocumentVisitor {
    void visit(PDFDocument document);
    void visit(DOCXDocument document);
    void visit(HTMLDocument document);
}

// Esempio di un Visitor concreto
class PrintVisitor implements DocumentVisitor {
    @Override
    public void visit(PDFDocument document) {
        System.out.println("Stampa PDF (" + document.getPages() + " pagine): " + document.getContent());
    }

    @Override
    public void visit(DOCXDocument document) {
        System.out.println("Stampa DOCX" + (document.hasTrackedChanges() ? " (con modifiche tracciate)" : "") 
            + ": " + document.getContent());
    }

    @Override
    public void visit(HTMLDocument document) {
        System.out.println("Stampa HTML con rendering: " + document.getContent());
    }
}

// Altri visitor: ExportVisitor, ValidationVisitor...
```

## Esempio di utilizzo concreto

Ecco come si utilizza concretamente il pattern Visitor nel sistema di documenti:

```java
public class DocumentSystem {
    public static void main(String[] args) {
        // Creazione di vari documenti
        List<Document> documents = new ArrayList<>();
        documents.add(new PDFDocument("Rapporto annuale", 15));
        documents.add(new DOCXDocument("Contratto di lavoro", true));
        documents.add(new HTMLDocument("<html><body>Homepage</body></html>", true));
        
        // Creazione dei visitor per diverse operazioni
        DocumentVisitor printVisitor = new PrintVisitor();
        DocumentVisitor exportVisitor = new ExportVisitor();
        DocumentVisitor validationVisitor = new ValidationVisitor();
        
        System.out.println("=== STAMPA DOCUMENTI ===");
        for (Document document : documents) {
            document.accept(printVisitor);
        }
        
        System.out.println("\n=== ESPORTAZIONE DOCUMENTI ===");
        for (Document document : documents) {
            document.accept(exportVisitor);
        }
        
        System.out.println("\n=== VALIDAZIONE DOCUMENTI ===");
        for (Document document : documents) {
            document.accept(validationVisitor);
        }
    }
}
```

Output di esempio:
```
=== STAMPA DOCUMENTI ===
Stampa PDF (15 pagine): Rapporto annuale
Stampa DOCX (con modifiche tracciate): Contratto di lavoro
Stampa HTML con rendering: <html><body>Homepage</body></html>

=== ESPORTAZIONE DOCUMENTI ===
Esportazione PDF in formato immagine
Esportazione DOCX in formato PDF (nascondendo le modifiche)
Esportazione HTML in formato responsive PDF

=== VALIDAZIONE DOCUMENTI ===
Validazione struttura PDF: valida
Validazione DOCX: verifica formattazione e controllo ortografico
Validazione HTML: verifica tag, CSS e responsività
```

In questo esempio, possiamo vedere chiaramente i vantaggi del pattern Visitor:

1. Abbiamo creato tre tipologie di documenti diversi.
2. Abbiamo implementato tre operazioni diverse (stampa, esportazione, validazione) come visitor.
3. Possiamo applicare qualsiasi visitor a qualsiasi documento senza modificare le classi dei documenti.
4. Se volessimo aggiungere una nuova operazione (ad esempio, un `StatisticsVisitor`), basterebbe creare una nuova classe che implementa `DocumentVisitor` senza toccare le classi dei documenti.

## Vantaggi del Pattern Visitor

1. **Separazione delle responsabilità**: Le operazioni sono separate dagli oggetti su cui operano.
2. **Facilità di aggiunta di nuove operazioni**: Per aggiungere una nuova operazione, basta creare un nuovo Visitor.
3. **Single Responsibility Principle**: Ogni visitor implementa una specifica operazione.
4. **Open/Closed Principle**: Puoi aggiungere nuovi comportamenti senza modificare le classi esistenti.

## Quando Usare il Pattern Visitor

- Quando hai una struttura di oggetti stabile ma vuoi aggiungere nuove operazioni frequentemente.
- Quando le operazioni devono essere eseguite su oggetti di classi diverse con interfacce diverse.
- Quando le classi degli oggetti non possono o non dovrebbero essere modificate.
- Quando hai operazioni complesse che dipendono dal tipo dell'oggetto.

## Conclusione

Il pattern Visitor rappresenta un potente strumento per mantenere il codice organizzato e rispettare i principi SOLID. Permette di aggiungere nuove operazioni senza modificare la struttura degli oggetti, creando una chiara separazione tra i dati (gli elementi) e gli algoritmi che operano su di essi (i visitor).

La sua vera potenza emerge in sistemi complessi con molti tipi di oggetti e diverse operazioni che devono essere eseguite su di essi.
