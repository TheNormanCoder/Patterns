import java.util.ArrayList;
import java.util.List;

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