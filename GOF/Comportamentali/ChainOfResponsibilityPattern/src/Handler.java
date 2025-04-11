// Handler interface
public interface Handler {
    void setNext(Handler next);
    void handleRequest(Request request);
}