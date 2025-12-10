package UI.panels;

import Model.Product;
import UI.table.ProductTableModel;

import javax.swing.*;
import java.awt.*;

public class ProductPanel extends JPanel {

    private JTable table;
    private ProductTableModel tableModel;

    public ProductPanel(Product product) {
        setLayout(new BorderLayout());

        tableModel = new ProductTableModel(product);
        table = new JTable(tableModel);

        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    public void loadProductsForCategory(int categoryId) {
        tableModel.loadData(categoryId);
    }

    public void clear() {
        tableModel.clear();
    }

    public boolean hasAnyProduct() {
        return tableModel.getRowCount() > 0;
    }
}