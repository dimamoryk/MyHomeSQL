package utils;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static volatile DatabaseConnection instance;
    private Connection connection;

    // Приватный конструктор для предотвращения прямого инстанцирования
    private DatabaseConnection() throws SQLException {
        this.connection = DriverManager.getConnection(
                System.getProperty("db.url"),
                System.getProperty("db.user"),
                System.getProperty("db.password")
        );
    }

    /**
     * Синхронизированный метод для ленивой инициализации экземпляра.
     */
    public static synchronized DatabaseConnection getInstance() throws SQLException {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    /**
     * Возвращает подключение к базе данных.
     */
    public Connection getConnection() {
        return connection;
    }
}



