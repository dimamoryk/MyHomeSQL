package factory;

import animal.Animal;
import birds.Duck;
import pets.Cat;
import pets.Dog;


public class AnimalFactory {
    public static long id = 0L;
    static String name = "";
    static int age = 0;
    static double weight = 0;
    static String color = "";
    static String type = "";

    public AnimalFactory(long id, String name, int age, double weight, String color, String type) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.color = color;
        this.type = type;
    }

    public static Animal createAnimal(long id, String name, int age, double weight, String color, String type) throws Exception {

        if (type.equalsIgnoreCase("cat")) {
            return new Cat(id, name, age, weight, color, type); // правильное расположение аргументов
        } else if (type.equalsIgnoreCase("dog")) {
            return new Dog(id, name, age, weight, color, type);
        } else if (type.equalsIgnoreCase("duck")) {
            return new Duck(id, name, age, weight, color, type);
        } else {
            throw new Exception("Неподдерживаемый тип животного: '" + type + "'");
        }

    }
}








