package UI.panels;

import Model.ProductCategory;
import UI.table.CategoryTableModel;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.function.Consumer;
import java.util.List;
import java.util.ArrayList;
import javax.swing.RowSorter;
import javax.swing.SortOrder;

public class CategoryPanel extends JPanel {

	private JTable table;
	private CategoryTableModel tableModel;
	private TableRowSorter<CategoryTableModel> sorter;

	public CategoryPanel(ProductCategory category) {
		setLayout(new BorderLayout());

		tableModel = new CategoryTableModel(category);
		table = new JTable(tableModel);

		sorter = new TableRowSorter<>(tableModel);
		table.setRowSorter(sorter);

		add(new JScrollPane(table), BorderLayout.CENTER);

		tableModel.loadData();
	}

	public void sortAlphabetically(boolean ascending) {
		List<RowSorter.SortKey> sortKeys = new ArrayList<>();
		sortKeys.add(new RowSorter.SortKey(0, ascending ? SortOrder.ASCENDING : SortOrder.DESCENDING));
		sorter.setSortKeys(sortKeys);
	}

	public void reload() {
		tableModel.loadData();
	}

	public boolean hasAnyCategory() {
		return tableModel.getRowCount() > 0;
	}

	public String getSelectedCategory() {
		int row = table.getSelectedRow();
		if (row == -1) {
			return null;
		}

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

	// Wewnątrz klasy CategoryPanel
	public void setupContextMenu(Runnable onAdd, Runnable onEdit, Runnable onDelete, JMenu sortMenu) {
		JPopupMenu contextMenu = new JPopupMenu();

		JMenuItem addItem = new JMenuItem("Dodaj kategorię");
		addItem.addActionListener(e -> onAdd.run());

		JMenuItem editItem = new JMenuItem("Edytuj kategorię");
		editItem.addActionListener(e -> onEdit.run());

		JMenuItem deleteItem = new JMenuItem("Usuń kategorię");
		deleteItem.addActionListener(e -> onDelete.run());

		contextMenu.add(addItem);
		contextMenu.add(editItem);
		contextMenu.add(deleteItem);
		contextMenu.addSeparator();
		contextMenu.add(sortMenu);

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