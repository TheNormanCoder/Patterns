// Client code
public class Client {
    public static void main(String[] args) {
        Handler handler1 = new ConcreteHandler1();
        Handler handler2 = new ConcreteHandler2();
        handler1.setNext(handler2);

        Request request1 = new Request(RequestType.TYPE1);
        handler1.handleRequest(request1);

        Request request2 = new Request(RequestType.TYPE2);
        handler1.handleRequest(request2);

        Request request3 = new Request(RequestType.TYPE3);
        handler1.handleRequest(request3);
    }
}