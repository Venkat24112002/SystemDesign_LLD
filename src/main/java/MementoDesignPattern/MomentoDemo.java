package MementoDesignPattern;

import java.util.Stack;

// Memento
class PersonMemento {
    private final int height;
    private final int weight;

    public PersonMemento(int height, int weight) {
        this.height = height;
        this.weight = weight;
    }

    public int getHeight() {
        return height;
    }

    public int getWeight() {
        return weight;
    }
}

// Originator
class Person {
    private int height;
    private int weight;

    public Person(int height, int weight) {
        this.height = height;
        this.weight = weight;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void showState() {
        System.out.println("Height: " + height + ", Weight: " + weight);
    }

    // Save state
    public PersonMemento save() {
        return new PersonMemento(height, weight);
    }

    // Restore state
    public void restore(PersonMemento memento) {
        this.height = memento.getHeight();
        this.weight = memento.getWeight();
    }
}

class History {
    private Stack<PersonMemento> history = new Stack<>();

    public void save(PersonMemento memento) {
        history.push(memento);
    }

    public PersonMemento undo() {
        return history.pop();
    }
}

public class MomentoDemo {
    public static void main(String[] args) {

        Person person = new Person(170, 70);
        History history = new History();

        person.showState();

        // Save initial state
        history.save(person.save());

        // Change state
        person.setHeight(175);
        person.setWeight(75);
        person.showState();

        // Undo
        person.restore(history.undo());
        person.showState();
    }
}
