package BuilderDesignPattern;

public class Person {
    private String name;
    private int age;

    // Private constructor - only Builder can create Person
    public Person(Builder builder) {
        this.name = builder.name;
        this.age = builder.age;
    }

    // Static Builder class
    public static class Builder {
        private String name;
        private int age;

        public Builder setName(String name) {
            this.name = name;
            return this; // returning SAME builder object
        }

        public Builder setAge(int age) {
            this.age = age;
            return this; // returning SAME builder object
        }

        public Person build() {
            return new Person(this);
        }
    }

    public static void main(String[] args){
        Person p = new Person.Builder().setName("hello").build();
    }
}
