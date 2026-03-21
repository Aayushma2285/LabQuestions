package labQuestions.coreJava;


abstract class Animal{
    abstract void sound();

    void eat(){
        System.out.println("Animal is eating");
    }
}

class Dog extends Animal{
    private String name;

    public Dog(String name) {
        this.name = name;
    }

    public String getName(){
        return name;
    }

    void sound(){
        System.out.println(name + "barks");
    }
}
public class OOPConcepts {
    public static void main(String[] args) {
        Animal a = new Dog("Bruno");

        a.sound();
        a.eat();

        Dog d = new Dog("Max");
        System.out.println("Name of the dog: " + d.getName());
    }
}
