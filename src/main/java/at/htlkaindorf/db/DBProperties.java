package at.htlkaindorf.db;

import at.htlkaindorf.io.DBPropertyReader;
import at.htlkaindorf.io.XML_IO;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.io.IOException;

/**
 * @author dorjab18
 * Klasse: 4BHIF
 */
@XmlRootElement
public class DBProperties {
    private String url;
    private String username;
    private String password;
    private String driverClassName;

    public static DBProperties readProperties(File file) throws IOException, JAXBException {
        if(file.exists())
            return XML_IO.readDBProperties(file);

        DBProperties properties = DBPropertyReader.getProperties();
        XML_IO.writeDBProperties(properties, file);
        return properties;
    }

    public DBProperties() {}

    public DBProperties(String url, String username, String password, String driverClassName) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.driverClassName = driverClassName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    @Override
    public String toString() {
        return "DBProperties{" +
                "url='" + url + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", driverClassName='" + driverClassName + '\'' +
                '}';
    }

    public static void main(String[] args) {
        File file = new File("src/main/resources/test.xml");
        file.delete();
        try {
            System.out.println(DBProperties.readProperties(file));
            System.out.println(DBProperties.readProperties(file));
        }catch(IOException|JAXBException e) {
            e.printStackTrace();
        }
    }
}
