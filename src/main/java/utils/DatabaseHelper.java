package utils;

import animal.Animal;
import birds.Duck;
import pets.Cat;
import pets.Dog;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper {

    String dbUrl = System.getProperty("db.url");
    String dbUser = System.getProperty("db.user");
    String dbPassword = System.getProperty("db.password");


    // Получаем соединение с базой данных
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    // Метод для добавления животного в базу данных
    public boolean insertAnimal(Animal animal) throws SQLException {
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(
                     "INSERT INTO animals (id, name, age, weight, color, type) VALUES (?, ?, ?, ?, ?)",
                     Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, animal.getName());
            pstmt.setInt(2, animal.getAge());
            pstmt.setDouble(3, animal.getWeight());
            pstmt.setString(4, animal.getColor());
            pstmt.setString(5, animal.getClass().getSimpleName());
            pstmt.setLong(6, animal.getId());
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    // Метод для выборки животных определенного типа из базы данных
    public List<Animal> filterAnimalsByType(String type) throws SQLException {
        List<Animal> result = new ArrayList<>();

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM animals WHERE type=?");) {
            pstmt.setString(1, type);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    long id = rs.getInt("id");
                    String name = rs.getString("name");
                    int age = rs.getInt("age");
                    double weight = rs.getDouble("weight");
                    String color = rs.getString("color");

                    Animal animal;
                    if ("Cat".equals(type)) {
                        animal = new Cat(id, name, age, weight, color, type);
                    } else if ("Dog".equals(type)) {
                        animal = new Dog(id, name, age, weight, color, type);
                    } else if ("Duck".equals(type)) {
                        animal = new Duck(id, name, age, weight, color, type);
                    } else {
                        throw new IllegalArgumentException("Неподдерживаемый тип животного.");
                    }
                    result.add(animal);
                }
            }
        }
        return result;
    }

    // Загружаем список всех животных из базы данных
    public List<Animal> loadAllAnimalsFromDB() throws SQLException {
        List<Animal> result = new ArrayList<>();

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM animals")) {

            while (rs.next()) {
                long id = rs.getInt("id");
                String name = rs.getString("name");
                int age = rs.getInt("age");
                double weight = rs.getDouble("weight");
                String color = rs.getString("color");
                String type = rs.getString("type");

                Animal animal;
                switch (type) {
                    case "Cat":
                        animal = new Cat(id, name, age, weight, color, type);
                        break;
                    case "Dog":
                        animal = new Dog(id, name, age, weight, color, type);
                        break;
                    case "Duck":
                        animal = new Duck(id, name, age, weight, color, type);
                        break;
                    default:
                        System.out.println("Тип '" + type + "' неизвестен!");
                        continue;
                }
                result.add(animal);
            }
        }
        return result;
    }
}