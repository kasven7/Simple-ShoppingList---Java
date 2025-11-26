package UI.table;

import Model.ProductCategory;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class CategoryTableModel extends AbstractTableModel {

    private final ProductCategory category;
    private List<String> categories = new ArrayList<>();

    public CategoryTableModel(ProductCategory category) {
        this.category = category;
    }

    public void loadData() {
        categories = category.getAll();   // dodamy getAll() za chwilę
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return categories.size();
    }

    @Override
    public int getColumnCount() {
        return 1; // jedna kolumna — nazwa kategorii
    }

    @Override
    public String getColumnName(int column) {
        return "Kategoria";
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return categories.get(rowIndex);
    }
}