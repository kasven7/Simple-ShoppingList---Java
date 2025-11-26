package Database;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class DatabaseConfig {
    public static void createConfigFile() {
        File configFile = new File("config.properties");

        if (!configFile.exists()) {
            Properties defaultProps = new Properties();

            defaultProps.setProperty("db.host", "localhost");
            defaultProps.setProperty("db.port", "3306");
            defaultProps.setProperty("db.user", "root");
            defaultProps.setProperty("db.password", "admin");
            defaultProps.setProperty("db.databaseName", "lista_zakupow");
            defaultProps.setProperty("db.url", "jdbc:mysql://localhost:3306/lista_zakupow");

            try {
                defaultProps.store(new FileOutputStream(configFile), "Default db settings.");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void readConfigFile() {
        Properties props = new Properties();

        try {
            props.load(new FileInputStream("config.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String host = props.getProperty("db.host");
        String port = props.getProperty("db.port");
        String databaseName = props.getProperty("db.databaseName");
        String user = props.getProperty("db.user");
        String password = props.getProperty("db.password");
        String url = String.format("jdbc:mysql://%s:%s/%s", host, port, databaseName);

        DBProperties.setHost(host);
        DBProperties.setPort(port);
        DBProperties.setDatabaseName(databaseName);
        DBProperties.setUrl(url);
        DBProperties.setUser(user);
        DBProperties.setPassword(password);

        System.out.println(DBProperties.getUrl());
    }
}