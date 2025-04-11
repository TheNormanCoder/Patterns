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