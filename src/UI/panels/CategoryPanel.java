package UI.panels;

import Model.ProductCategory;
import UI.table.CategoryTableModel;

import javax.swing.*;
import java.awt.*;

public class CategoryPanel extends JPanel {

    private JTable table;
    private CategoryTableModel tableModel;

    public CategoryPanel(ProductCategory category) {
        setLayout(new BorderLayout());

        // tabela + model
        tableModel = new CategoryTableModel(category);
        table = new JTable(tableModel);

        // włączenie przewijania
        add(new JScrollPane(table), BorderLayout.CENTER);

        // załadowanie danych
        tableModel.loadData();
    }

    public void reload() {
        tableModel.loadData();
    }
}