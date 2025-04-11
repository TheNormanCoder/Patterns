# Pattern Adapter in Java

Il Pattern Adapter è un design pattern strutturale che consente a oggetti con interfacce incompatibili di collaborare tra loro. Funziona come un ponte tra due interfacce incompatibili.

![Class Adapter Pattern](https://github.com/TheNormanCoder/AdapterPattern/raw/main/image.png)

![Object Adapter Pattern](https://github.com/TheNormanCoder/AdapterPattern/raw/main/image1.PNG)

## Componenti principali

1. **Target**: Un'interfaccia che il client utilizza.
2. **Adaptee**: La classe esistente con un'interfaccia incompatibile.
3. **Adapter**: La classe che fa da ponte tra Target e Adaptee.
4. **Client**: La classe che interagisce con l'oggetto Target.

## Due tipi di Adapter Pattern

Esistono due implementazioni diverse del pattern Adapter:

### 1. Class Adapter

In questa implementazione, l'Adapter **estende** l'Adaptee e **implementa** l'interfaccia Target:

```java
// Class Adapter
public class Adapter extends Adaptee implements Target {
    public void request() {
        specificRequest();
    }
}
```

### 2. Object Adapter

In questa implementazione, l'Adapter **contiene** un'istanza dell'Adaptee:

```java
// Object Adapter
public class Adapter implements Target {
    private Adaptee adaptee;

    public Adapter(Adaptee adaptee) {
        this.adaptee = adaptee;
    }

    public void request() {
        adaptee.specificRequest();
    }
}
```

## Come funziona

1. Il Client vuole usare una classe (Adaptee) che ha un'interfaccia incompatibile.
2. Anziché modificare il Client o l'Adaptee, viene creato un Adapter.
3. L'Adapter implementa l'interfaccia che il Client si aspetta (Target).
4. Quando il Client chiama un metodo dell'Adapter, quest'ultimo traduce la chiamata in un formato che l'Adaptee può comprendere.

## Esempi di utilizzo

```java
// Client con Class Adapter
Target target = new Adapter();
target.request();

// Client con Object Adapter
Adaptee adaptee = new Adaptee();
Target target = new Adapter(adaptee);
target.request();
```

## Vantaggi

- **Separazione delle responsabilità**: Il client non deve preoccuparsi dell'implementazione dell'Adaptee.
- **Riutilizzo del codice**: Permette di riutilizzare classi esistenti anche se le loro interfacce non corrispondono a quelle necessarie.
- **Flessibilità**: Puoi utilizzare l'Object Adapter per adattare più Adaptee a un'unica interfaccia Target.

## Differenze tra le due implementazioni

- **Class Adapter**: Usa l'ereditarietà. È più limitato ma può sovrascrivere comportamenti dell'Adaptee.
- **Object Adapter**: Usa la composizione. È più flessibile e permette di adattare più classi Adaptee.

### Confronto dettagliato tra Class Adapter e Object Adapter

1. **Relazione con l'Adaptee**:
   - Class Adapter: relazione di ereditarietà (IS-A)
   - Object Adapter: relazione di composizione (HAS-A)

2. **Flessibilità**:
   - Class Adapter: può adattare solo una classe Adaptee (limitazione dell'ereditarietà singola in Java)
   - Object Adapter: può adattare più classi Adaptee o intere gerarchie di classi

3. **Implementazione in Java**:
   - Class Adapter: `public class Adapter extends Adaptee implements Target`
   - Object Adapter: `public class Adapter implements Target { private Adaptee adaptee; }`

4. **Istanziazione nel client**:
   - Class Adapter: `Target target = new Adapter();`
   - Object Adapter: `Target target = new Adapter(new Adaptee());`

In Java, l'Object Adapter è spesso preferito perché Java non supporta l'ereditarietà multipla di classi, e la composizione offre maggiore flessibilità e consente di adattare sottoclassi dell'Adaptee senza creare nuovi adapter.

## Casi d'uso comuni

- Integrazione di librerie di terze parti
- Compatibilità con vecchie API
- Adattamento di strutture dati diverse
- Connessione tra sistemi legacy e moderni

Il pattern Adapter è particolarmente utile quando devi integrare componenti esistenti che non possono essere modificati direttamente con il tuo sistema attuale.
