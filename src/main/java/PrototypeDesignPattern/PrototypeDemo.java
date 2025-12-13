package PrototypeDesignPattern;


interface Shape extends Cloneable {
    Shape clone();
    void draw();
}

class Circle implements Shape {

    private int radius;

    public Circle(int radius) {
        this.radius = radius;
        System.out.println("Creating Circle with radius " + radius);
    }

    @Override
    public Shape clone() {
        return new Circle(this.radius);
    }

    @Override
    public void draw() {
        System.out.println("Drawing Circle with radius " + radius);
    }
}


public class PrototypeDemo {
    public static void main(String[] args) {

        // Create original object
        Shape originalCircle = new Circle(10);

        // Clone it
        Shape clonedCircle = originalCircle.clone();

        originalCircle.draw();
        clonedCircle.draw();
    }
}
