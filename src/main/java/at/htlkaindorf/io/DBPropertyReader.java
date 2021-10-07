package at.htlkaindorf.io;

import at.htlkaindorf.db.DBProperties;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * @author dorjab18
 * Klasse: 4BHIF
 */
public final class DBPropertyReader {
    public static final String PROPERTY_DATASOURCE_URL = "datasource.url";
    public static final String PROPERTY_DATASOURCE_USERNAME = "datasource.username";
    public static final String PROPERTY_DATASOURCE_PASSWORD = "datasource.password";
    public static final String PROPERTY_DATASOURCE_DRIVER_CLASS_NAME = "datasource.driver-class-name";

    private static Properties props = new Properties();

    private DBPropertyReader() {}

    static {
        Path path = Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "dbConnect.properties");
        try(FileInputStream fis = new FileInputStream(path.toFile())) {
            props.load(fis);
        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static String getProperty(String key) {
        return props.getProperty(key);
    }

    public static DBProperties getProperties() {
        return new DBProperties(getProperty(PROPERTY_DATASOURCE_URL), getProperty(PROPERTY_DATASOURCE_USERNAME),
                getProperty(PROPERTY_DATASOURCE_PASSWORD), getProperty(PROPERTY_DATASOURCE_DRIVER_CLASS_NAME));
    }

    public static void main(String[] args) {
        System.out.println(DBPropertyReader.getProperty(PROPERTY_DATASOURCE_URL));
        System.out.println(DBPropertyReader.getProperty(PROPERTY_DATASOURCE_USERNAME));
        System.out.println(DBPropertyReader.getProperty(PROPERTY_DATASOURCE_PASSWORD));
        System.out.println(DBPropertyReader.getProperty(PROPERTY_DATASOURCE_DRIVER_CLASS_NAME));

        System.out.println(DBPropertyReader.getProperties());
    }
}
