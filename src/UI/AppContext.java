package UI;

import Database.DBProperties;
import Database.DatabaseConfig;
import Model.ProductCategory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AppContext {

    private static Connection connection;
    private static ProductCategory productCategory;

    // Inicjalizacja połączenia
    public static void init() {
        DatabaseConfig.createConfigFile();
        DatabaseConfig.readConfigFile();

        try {
            connection = DriverManager.getConnection(
                    DBProperties.getUrl(),
                    DBProperties.getUser(),
                    DBProperties.getPassword()
            );

            System.out.println("✅ Połączenie udane!");

            productCategory = new ProductCategory(connection);

        } catch (SQLException e) {
            System.out.println("❌ Błąd połączenia: " + e.getMessage());
        }
    }

    public static Connection getConnection() {
        return connection;
    }

    public static ProductCategory getProductCategory() {
        return productCategory;
    }
}