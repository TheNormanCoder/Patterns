// Context
class Context {
    private State currentState;

    public void setState(State state) {
        this.currentState = state;
    }

    public void request() {
        this.currentState.handle();
    }
}



