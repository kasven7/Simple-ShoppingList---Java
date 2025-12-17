package UI;

import Database.DBProperties;
import Database.DatabaseConfig;
import Model.Product;
import Model.ProductCategory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AppContext {

	private static Connection connection;
	private static ProductCategory productCategory;
	private static Product product;

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
		} catch (SQLException e) {
			System.out.println("❌ Błąd połączenia: " + e.getMessage());
			connection = null;
		}

		productCategory = new ProductCategory(connection);
		product = new Product(connection);
	}

	public static ProductCategory getProductCategory() {
		return productCategory;
	}

	public static Product getProduct() {
		return product;
	}
}