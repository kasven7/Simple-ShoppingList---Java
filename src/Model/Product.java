package Model;

import lombok.Getter;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Product {
	@Getter
	private int id;

	@Getter
	private String name;

	@Getter
	private int categoryId;

	@Getter
	private Timestamp createdAt;

	private Connection conn;

	public Product(Connection conn) {
		this.conn = conn;
		createTable();
	}

	public Product(int id, String name, int categoryId, Timestamp createdAt) {
		this.id = id;
		this.name = name;
		this.categoryId = categoryId;
		this.createdAt = createdAt;
	}

	private void createTable() {
		boolean isEmpty = true;

		if (conn == null) {
			return;
		}

		try (Statement stmt = conn.createStatement()) {

			String sqlCreateTable = "CREATE TABLE IF NOT EXISTS product ("
					+ "id INT PRIMARY KEY AUTO_INCREMENT,"
					+ "name VARCHAR(255) NOT NULL,"
					+ "category_id INT NOT NULL,"
					+ "created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,"
					+ "CONSTRAINT fk_product_category "
					+ "FOREIGN KEY (category_id) REFERENCES product_category(id) "
					+ "ON DELETE RESTRICT ON UPDATE CASCADE"
					+ ")";

			stmt.executeUpdate(sqlCreateTable);

			String sqlCheckCount = "SELECT COUNT(*) AS total FROM product";
			ResultSet rs = stmt.executeQuery(sqlCheckCount);

			if (rs.next()) {
				isEmpty = rs.getInt("total") == 0;
			}
			rs.close();

			if (isEmpty) {
				String sqlInsertDefaults =
						"INSERT INTO product (name, category_id) VALUES "
								+ "('chleb', (SELECT id FROM product_category WHERE name='pieczywo' LIMIT 1)),"
								+ "('bulki', (SELECT id FROM product_category WHERE name='pieczywo' LIMIT 1)),"
								+ "('woda', (SELECT id FROM product_category WHERE name='napoje' LIMIT 1)),"
								+ "('cola', (SELECT id FROM product_category WHERE name='napoje' LIMIT 1)),"
								+ "('mikser', (SELECT id FROM product_category WHERE name='agd' LIMIT 1)),"
								+ "('kurczak', (SELECT id FROM product_category WHERE name='mieso' LIMIT 1))";

				stmt.executeUpdate(sqlInsertDefaults);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public int addRecord(String productName, int categoryId) {
		String sql = "INSERT INTO product (name, category_id) VALUES ('" + productName + "', " + categoryId + ")";

		try (Statement stmt = conn.createStatement()) {
			return stmt.executeUpdate(sql);
		} catch (SQLException e) {
			return 0;
		}
	}

	public int updateRecord(int id, String newName) {
		if (this.conn == null) {
			return 0;
		}

		String sql = "UPDATE product SET name='" + newName + "' WHERE id=" + id;

		try (Statement stmt = conn.createStatement()) {
			return stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public int deleteById(int productId) {
		if (this.conn == null) {
			return 0;
		}
		String sql = "DELETE FROM product WHERE id=" + productId;

		try (Statement stmt = conn.createStatement()) {
			return stmt.executeUpdate(sql);
		} catch (SQLException e) {
			return 0;
		}
	}

	public List<Product> getAllByCategoryId(int categoryId) {
		List<Product> result = new ArrayList<>();
		String sql = "SELECT id, name, category_id, created_at FROM product WHERE category_id=" + categoryId + " ORDER BY created_at DESC";

		try (Statement stmt = conn.createStatement()) {
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				result.add(new Product(
						rs.getInt("id"),
						rs.getString("name"),
						rs.getInt("category_id"),
						rs.getTimestamp("created_at")
				));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
}