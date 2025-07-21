package pets;

import animal.Animal;

public class Dog extends Animal {
    public Dog( long id, String name, int age, double weight, String color, String type) {
        super(id, name, age, weight, color, type);

    }

    @Override
    public void say() {
        System.out.println("Гав");
    }
}
