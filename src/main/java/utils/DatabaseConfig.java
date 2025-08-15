package utils;

public class DatabaseConfig {
    public static String getDbUrl() {
        return System.getenv("DB_URL");
    }

    public static String getDbUser() {
        return System.getenv("DB_USER");
    }

    public static String getDbPassword() {
        return System.getenv("DB_PASSWORD");
    }

}
