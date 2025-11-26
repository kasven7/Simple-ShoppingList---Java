package Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductCategory {
    private int id;
    private String name;
    private Connection conn;

    public ProductCategory(Connection conn) {
        this.conn = conn;

        createTable();
    }

    private void createTable() {
        try {
            Statement stmt = conn.createStatement();

            String sqlCreateTable = "CREATE TABLE IF NOT EXISTS product_category ("
                    + "id INT PRIMARY KEY AUTO_INCREMENT,"
                    + "name VARCHAR(255) NOT NULL UNIQUE)";
            String sqlInsertDefaultValues = "INSERT INTO product_category (name) VALUES"
                    + "('pieczywo'), "
                    + "('napoje'), "
                    + "('agd'), "
                    + "('mieso') ";
            String sqlCheckCount = "SELECT COUNT(*) AS total FROM product_category";

            ResultSet rs = stmt.executeQuery(sqlCheckCount);
            boolean isEmpty = true;

            if (rs.next()) {
                isEmpty = rs.getInt("total") == 0;
            }
            rs.close();

            stmt.executeUpdate(sqlCreateTable);

            if (isEmpty) {
                stmt.executeUpdate(sqlInsertDefaultValues);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public int addRecord(String categoryName) {
        String addSql = "INSERT INTO product_category (name) VALUES ('" + categoryName + "')";

        try (Statement stmt = conn.createStatement()) {
            return stmt.executeUpdate(addSql);
        } catch (SQLException e) {
            return 0;
        }
    }

    public List<String> getAll() {
        List<String> result = new ArrayList<>();

        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT name FROM product_category");

            while (rs.next()) {
                result.add(rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public void readTable() {
        try {
            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM product_category");
            System.out.println("\nNazwy kategorii:");
            while (rs.next()) {
                String name = rs.getString("name");
                System.out.println(String.format("%s", name));
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public int updateRecord(String categoryName, String updatedCategoryName) {
        String updateSql = "UPDATE product_category SET name='" + updatedCategoryName + "' WHERE name='" + categoryName + "'";

        try (Statement stmt = conn.createStatement()) {
            return stmt.executeUpdate(updateSql);
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {  // duplicate key
                return -1;
            }
            return 0;
        }
    }

    public int deleteRecord(String categoryName) {
        String deleteSql = "DELETE FROM product_category WHERE name='" + categoryName + "'";

        try (Statement stmt = conn.createStatement()) {
            return stmt.executeUpdate(deleteSql);
        } catch (SQLException e) {
            return 0;
        }
    }
}