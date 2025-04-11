public class Main {
    public static void main(String[] args) {
        // Ottiene l'istanza della classe Singleton
        Singleton singleton = Singleton.getInstance();

        // Utilizza l'istanza della classe Singleton
        System.out.println(singleton.toString());
    }
}