package UI.panels;

import Model.Product;
import UI.table.ProductTableModel;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.TableRowSorter;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import java.util.List;
import java.util.ArrayList;
import java.util.function.Consumer;

public class ProductPanel extends JPanel {

	private JTable table;
	private ProductTableModel tableModel;
	private TableRowSorter<ProductTableModel> sorter;
	private JTextField dateFromField;
	private JTextField dateToField;

	public ProductPanel(Product product, Consumer<String> setStatus) {
		setLayout(new BorderLayout());

		JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		dateFromField = new JTextField(10);
		dateToField = new JTextField(10);
		JButton applyFilterBtn = new JButton("Filtruj daty");
		JButton clearFilterBtn = new JButton("Wyczyść");

		filterPanel.add(new JLabel("Od (RRRR-MM-DD):"));
		filterPanel.add(dateFromField);
		filterPanel.add(new JLabel("Do (RRRR-MM-DD):"));
		filterPanel.add(dateToField);
		filterPanel.add(applyFilterBtn);
		filterPanel.add(clearFilterBtn);

		add(filterPanel, BorderLayout.NORTH);

		tableModel = new ProductTableModel(product);
		table = new JTable(tableModel);
		sorter = new TableRowSorter<>(tableModel);
		table.setRowSorter(sorter);

		add(new JScrollPane(table), BorderLayout.CENTER);

		applyFilterBtn.addActionListener(e -> applyDateFilter());
		clearFilterBtn.addActionListener(e -> {
			dateFromField.setText("");
			dateToField.setText("");
			sorter.setRowFilter(null);
			setStatus.accept("♻️ Wyczyszczono filtry dat.");
		});
	}

	private void applyDateFilter() {
		String fromStr = dateFromField.getText().trim();
		String toStr = dateToField.getText().trim();

		if (fromStr.isEmpty() && toStr.isEmpty()) {
			sorter.setRowFilter(null);
			return;
		}

		try {
			sorter.setRowFilter(new RowFilter<>() {
				@Override
				public boolean include(Entry<? extends ProductTableModel, ? extends Integer> entry) {
					Object value = entry.getValue(1);
					if (value == null) {
						return false;
					}

					String rowDate = value.toString().substring(0, 10);

					boolean matches = true;
					if (!fromStr.isEmpty()) {
						matches &= rowDate.compareTo(fromStr) >= 0;
					}
					if (!toStr.isEmpty()) {
						matches &= rowDate.compareTo(toStr) <= 0;
					}
					return matches;
				}
			});
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this,
					"Błąd formatu daty! Użyj formatu RRRR-MM-DD (np. 2025-12-17)",
					"Błąd filtrowania",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public void sortAlphabetically(boolean ascending) {
		List<RowSorter.SortKey> sortKeys = new ArrayList<>();
		sortKeys.add(new RowSorter.SortKey(0, ascending ? SortOrder.ASCENDING : SortOrder.DESCENDING));
		sorter.setSortKeys(sortKeys);
	}

	public void sortByDate(boolean newestFirst) {
		List<RowSorter.SortKey> sortKeys = new ArrayList<>();
		sortKeys.add(new RowSorter.SortKey(1, newestFirst ? SortOrder.DESCENDING : SortOrder.ASCENDING));
		sorter.setSortKeys(sortKeys);
	}

	public int getSelectedProductId() {
		int row = table.getSelectedRow();
		if (row == -1) return -1;

		int modelRow = table.convertRowIndexToModel(row);
		return tableModel.getProductAt(modelRow).getId();
	}

	public String getSelectedProductName() {
		int row = table.getSelectedRow();
		if (row == -1) return null;

		int modelRow = table.convertRowIndexToModel(row);
		return tableModel.getProductAt(modelRow).getName();
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

	public void setupContextMenu(Runnable onAdd, Runnable onEdit, Runnable onDelete, JMenu sortMenu) {
		JPopupMenu contextMenu = new JPopupMenu();

		JMenuItem addItem = new JMenuItem("Dodaj produkt");
		addItem.addActionListener(e -> onAdd.run());

		JMenuItem editItem = new JMenuItem("Edytuj produkt");
		editItem.addActionListener(e -> onEdit.run());

		JMenuItem deleteItem = new JMenuItem("Usuń produkt");
		deleteItem.addActionListener(e -> onDelete.run());

		contextMenu.add(addItem);
		contextMenu.add(editItem);
		contextMenu.add(deleteItem);
		contextMenu.addSeparator();
		contextMenu.add(sortMenu);
		table.setComponentPopupMenu(contextMenu);

		table.setComponentPopupMenu(contextMenu);

		table.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mousePressed(java.awt.event.MouseEvent e) {
				int r = table.rowAtPoint(e.getPoint());
				if (r >= 0 && r < table.getRowCount()) {
					table.setRowSelectionInterval(r, r);
				}
			}
		});
	}
}