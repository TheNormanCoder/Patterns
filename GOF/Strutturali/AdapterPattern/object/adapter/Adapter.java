package object.adapter;

// Adapter
public class Adapter extends Adaptee implements Target {
    public void request() {
        specificRequest();
    }
}
