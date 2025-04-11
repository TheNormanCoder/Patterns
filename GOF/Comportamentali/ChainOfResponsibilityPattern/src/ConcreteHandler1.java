public class ConcreteHandler1 implements Handler {
    private Handler nextHandler;

    @Override
    public void setNext(Handler next) {
        this.nextHandler = next;
    }

    @Override
    public void handleRequest(Request request) {
        if (request.getType() == RequestType.TYPE1) {
            System.out.println("Handled by ConcreteHandler1");
        } else if (nextHandler != null) {
            nextHandler.handleRequest(request);
        } else {
            System.out.println("All handlers have failed to handle the request");
        }
    }
}