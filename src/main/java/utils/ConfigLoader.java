package utils;
import java.io.FileInputStream;
import java.util.Properties;

public class ConfigLoader {

    private Properties properties;

    public ConfigLoader(String filePath) throws Exception {
        this.properties = new Properties();
        try (FileInputStream fis = new FileInputStream(filePath)) {
            properties.load(fis);
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}
