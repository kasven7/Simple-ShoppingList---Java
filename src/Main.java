import Database.DBProperties;
import Database.DatabaseConfig;
import Model.ProductCategory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        DatabaseConfig.createConfigFile();
        DatabaseConfig.readConfigFile();

        try {
            Connection conn = DriverManager.getConnection(
                    DBProperties.getUrl(),
                    DBProperties.getUser(),
                    DBProperties.getPassword()
            );
            System.out.println("✅ Połączenie udane!");

            ProductCategory productCategory = new ProductCategory(conn);
            ConsoleApp app = new ConsoleApp(productCategory);

            app.run();
            conn.close();
        } catch (SQLException e) {
            System.out.println("❌ Błąd połączenia: " + e.getMessage());
        }
    }
}