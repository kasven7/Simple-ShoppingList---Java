package UI.panels;

import Model.ProductCategory;
import UI.table.CategoryTableModel;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

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

    public boolean hasAnyCategory() {
        return tableModel.getRowCount() > 0;
    }

    public String getSelectedCategory() {
        int row = table.getSelectedRow();
        if (row == -1) return null;

        // Jeśli sorter jest aktywny, trzeba konwertować index
        int modelRow = table.convertRowIndexToModel(row);

        return (String) tableModel.getValueAt(modelRow, 0);
    }

    public void onCategorySelected(Consumer<String> callback) {
        table.getSelectionModel().addListSelectionListener(e -> {
            if (e.getValueIsAdjusting()) return;

            String selected = getSelectedCategory();
            if (selected != null) {
                callback.accept(selected);
            }
        });
    }
}