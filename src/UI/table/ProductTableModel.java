package UI.table;

import Model.Product;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class ProductTableModel extends AbstractTableModel {

    private final Product product;
    private List<Product> products = new ArrayList<>();

    public ProductTableModel(Product product) {
        this.product = product;
    }

    public void loadData(int categoryId) {
        products = product.getAllByCategoryId(categoryId);
        fireTableDataChanged();
    }

    public void clear() {
        products = new ArrayList<>();
        fireTableDataChanged();
    }

    public Product getProductAt(int rowIndex)
    {
        return products.get(rowIndex);
    }

    @Override
    public int getRowCount() {
        return products.size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public String getColumnName(int column) {
        return switch (column) {
            case 0 -> "Produkt";
            case 1 -> "Utworzono";
            default -> "";
        };
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Product p = products.get(rowIndex);

        return switch (columnIndex) {
            case 0 -> p.getName();
            case 1 -> p.getCreatedAt();
            default -> null;
        };
    }
}