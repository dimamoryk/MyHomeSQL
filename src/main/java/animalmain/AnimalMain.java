package animalmain;

import animal.Animal;
import data.Command;
import factory.AnimalFactory;
import utils.DatabaseHelper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static data.Command.*;
import static factory.AnimalFactory.id;


public class AnimalMain {
    private static final Scanner scanner = new Scanner(System.in);
    private static final ArrayList<Animal> animals = new ArrayList<>();
    private static DatabaseHelper databaseHelper = new DatabaseHelper();


    public static void main(String... args) {

        try {
            loadAnimals(databaseHelper); // Загрузка животных из БД
        } catch (SQLException ex) {
            System.err.println("Ошибка загрузки животных из базы данных: " + ex.getMessage());
        }



        while (true) {
            printMenu(); // выводим меню
            String choice = scanner.nextLine().trim().toUpperCase();

            try {
                switch (choice) {
                    case "ADD":
                        addAnimal(databaseHelper);
                        break;
                    case "LIST":
                        listAnimals();
                        break;
                    case "FILTER":
                        filterAnimalsByType(databaseHelper); // фильтруем животных по типу
                        break;
                    case "EXIT":
                        System.out.println("Программа завершена");
                        System.exit(0);
                    default:
                        System.out.println("Неверная команда!");
                }
            } catch (Exception e) {
                System.err.println("ОШИБКА: " + e.getMessage());
            }
        }
    }


    // Метод вывода меню команд
    private static void printMenu() {
        System.out.println("\nМеню:");
        System.out.println("ADD - добавить животное");
        System.out.println("LIST - показать список животных");
        System.out.println("FILTER - выбрать животные по типу");
        System.out.println("EXIT - завершить программу");
        System.out.print("Введите команду: ");
    }

    // Добавляем новое животное
    private static void addAnimal(DatabaseHelper helper) throws Exception {

        boolean isValidType = false;
        String type = "";
        while (!isValidType) {
            System.out.print("Тип животного (cat/dog/duck): ");
            type = scanner.nextLine().trim();
            if ("cat".equalsIgnoreCase(type) || "dog".equalsIgnoreCase(type) || "duck".equalsIgnoreCase(type)) {
                isValidType = true;
            } else {
                System.out.println("Неправильный тип животного. Попробуйте ещё раз.");
            }
        }


        System.out.print("Имя животного: ");
        String name = scanner.nextLine().trim();

        // Обработка ошибок возраста
        System.out.print("Возраст животного: ");
        String inputAge = scanner.nextLine().trim();
        boolean validInput = false;
        int age = 0;
        while (!validInput) {
            try {
                age = Integer.parseInt(inputAge.trim());
                if (age >= 0) {
                    validInput = true;
                } else {
                    System.out.println("Возраст не может быть отрицательным! Пожалуйста, введите возраст снова.");
                    inputAge = scanner.nextLine();
                }
            } catch (NumberFormatException e) {
                System.out.println("Вы ввели неверный формат возраста. Введите число:");
                inputAge = scanner.nextLine();
            }
        }
        System.out.println("Корректный возраст принят.");


        // Обработка ошибок веса
        System.out.print("Вес животного: ");
        String inputWeight = scanner.nextLine().trim();
        validInput = false;
        double weight = 0;
        while (!validInput) {
            try {
                weight = Double.parseDouble(inputWeight);
                if (weight >= 0) {
                    validInput = true;
                } else {
                    System.out.println("Вес не может быть отрицательным! Пожалуйста, введите вес снова.");
                    inputWeight = scanner.nextLine();
                }
            } catch (NumberFormatException e) {
                System.out.println("Вы ввели неверный формат веса. Введите число:");
                inputWeight = scanner.nextLine();
            }
        }
        System.out.println("Корректный вес принят.");

        // Обработка выбора цвета
        System.out.print("Цвет животного: ");
        String color = scanner.nextLine();

        // Список допустимых цветов
        String[] colors = {"чёрный", "белый", "серый"};

        while (true) {  // Бесконечный цикл для повторения попытки ввода
            System.out.println("Выберите цвет животного:");

            // Отображение вариантов цветов
            for (int i = 0; i < colors.length; i++) {
                System.out.printf("%d. %s\n", i + 1, colors[i]);
            }

            // Получаем выбор пользователя
            String choice = scanner.nextLine();
            try {
                int index = Integer.parseInt(choice) - 1;
                if (index >= 0 && index < colors.length) {
                    color = colors[index];
                    break; // Выходим из цикла, если выбран правильный вариант
                } else {
                    System.out.println("Выбран неверный номер цвета. Попробуйте ещё раз.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Вы ввели некорректный символ. Введите цифру от 1 до " + colors.length + ".");
            }
        }

        // Создаем объект животного используя фабрику
        Animal animal = AnimalFactory.createAnimal(id, name, age, weight, color, type);
        animals.add(animal);
        System.out.println("Животное успешно добавлено.");
    }
    // Загружаем животных из базы данных
    private static void loadAnimals(DatabaseHelper dbHelper) throws SQLException {
        List<Animal> loadedAnimals = dbHelper.loadAllAnimalsFromDB();
        animals.addAll(loadedAnimals);
    }

    // Показываем список всех животных
    private static void listAnimals() {
        if (animals.isEmpty()) {
            System.out.println("Нет животных в списке.");
        } else {
            System.out.println("Список животных:");
            for (Animal a : animals) {
                System.out.printf("%s (%s), %d лет\n", a.getName(), a.getType(), a.getAge());
            }
        }
    }

    // Фильтрация животных по типу
    private static void filterAnimalsByType(DatabaseHelper helper) throws SQLException {
        System.out.print("Фильтр по типу животного (cat/dog/duck): ");
        String type = scanner.nextLine().trim();
        List<Animal> filteredAnimals = helper.filterAnimalsByType(type);
        if (filteredAnimals.isEmpty()) {
            System.out.println("Нет животных указанного типа.");
        } else {
            System.out.println("Отфильтрованные животные:");
            for (Animal a : filteredAnimals) {
                System.out.printf("%s (%s), %d лет\n", a.getName(), a.getType(), a.getAge());
            }
        }
    }
}






