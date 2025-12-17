package UI;

import UI.components.BottomStatusBar;
import UI.dialogs.AddCategoryDialog;
import UI.dialogs.AddProductDialog;
import UI.dialogs.EditCategoryDialog;
import UI.menu.MainMenuBar;
import UI.panels.CategoryPanel;
import UI.panels.ProductPanel;
import UI.dialogs.EditProductDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class MainWindow extends JFrame {
	private CategoryPanel categoryPanel;
	private ProductPanel productPanel;
	private MainMenuBar menuBar;
	private BottomStatusBar statusBar;
	private Integer selectedCategoryId = null;
	private String selectedCategoryName = null;

	public MainWindow() {
		setTitle("Shopping List - GUI");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 600);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());

		categoryPanel = new CategoryPanel(AppContext.getProductCategory());
		productPanel = new ProductPanel(AppContext.getProduct(), this::setStatus);

		categoryPanel.setupContextMenu(
				this::openAddCategoryDialog,
				this::openEditCategoryDialog,
				this::deleteSelectedCategory,
				createCategorySortMenu()
		);

		productPanel.setupContextMenu(
				this::openAddProductDialog,
				this::openEditProductDialog,
				this::deleteSelectedProduct,
				createProductSortMenu()
		);

		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, categoryPanel, productPanel);
		splitPane.setDividerLocation(300);
		splitPane.setResizeWeight(0.35);
		add(splitPane, BorderLayout.CENTER);

		statusBar = new BottomStatusBar();
		add(statusBar, BorderLayout.SOUTH);

		menuBar = new MainMenuBar(
				e -> openAddCategoryDialog(),
				e -> openEditCategoryDialog(),
				e -> deleteSelectedCategory(),
				e -> openAddProductDialog(),
				e -> openEditProductDialog(),
				e -> deleteSelectedProduct(),
				e -> showAboutDialog()
		);
		setJMenuBar(menuBar);
		getJMenuBar().add(createGlobalSortMenu());

		categoryPanel.onCategorySelected(categoryName -> {
			Integer categoryId = AppContext.getProductCategory().getIdByName(categoryName);

			selectedCategoryName = categoryName;
			selectedCategoryId = categoryId;

			if (categoryId != null) {
				productPanel.loadProductsForCategory(categoryId);
				setStatus("📂 Wybrano kategorię: " + categoryName);
			} else {
				productPanel.clear();
				setStatus("⚠️ Nie znaleziono ID dla kategorii: " + categoryName);
			}

			updateMenuState();
		});

		categoryPanel.setupContextMenu(this::openAddCategoryDialog, this::openEditCategoryDialog, this::deleteSelectedCategory, createCategorySortMenu());
		productPanel.setupContextMenu(this::openAddProductDialog, this::openEditProductDialog, this::deleteSelectedProduct, createProductSortMenu());

		updateMenuState();
	}

	private void showAboutDialog() {
		String message = "<html><body style='width: 250px; padding: 10px;'>" +
				"<h2 style='color: #2c3e50;'>Shopping List - GUI</h2>" +
				"<hr>" +
				"<b>Wersja:</b> 1.0.4-PRO (Commercial Build)<br>" +
				"<b>Autor:</b> Kacper Kowalski oraz Jakub Bromber<br>" +
				"<b>Status:</b>OK<br><br>" +
				"<p style='font-size: 9px; color: gray;'>" +
				"Wszelkie prawa zastrzeżone © 2025. Program stworzony na potrzeby projektu edukacyjnego.</p>" +
				"</body></html>";

		JOptionPane.showMessageDialog(
				this,
				message,
				"O programie",
				JOptionPane.INFORMATION_MESSAGE
		);
	}

	private void openAddCategoryDialog() {
		new AddCategoryDialog(
				this,
				AppContext.getProductCategory(),
				() -> {
					categoryPanel.reload();
					updateMenuState();
				},
				this::setStatus
		).setVisible(true);
	}

	private void updateMenuState() {
		menuBar.setCategoryActionsEnabled(categoryPanel.hasAnyCategory());
		menuBar.setProductActionsEnabled(productPanel.hasAnyProduct());
	}

	private void openEditCategoryDialog() {
		String selected = categoryPanel.getSelectedCategory();
		if (selected == null) {
			setStatus("⚠️ Nie edytowano — nie wybrano kategorii.");
			JOptionPane.showMessageDialog(this, "Najpierw wybierz kategorię z tabeli!");

			return;
		}

		new EditCategoryDialog(
				this,
				AppContext.getProductCategory(),
				selected,
				() -> {
					categoryPanel.reload();
					updateMenuState();
				},
				this::setStatus
		).setVisible(true);
	}

	private void deleteSelectedCategory() {
		String selected = categoryPanel.getSelectedCategory();
		if (selected == null) {
			setStatus("⚠️ Nie usunięto — nie wybrano kategorii.");
			JOptionPane.showMessageDialog(this, "Najpierw wybierz kategorię z tabeli!");

			return;
		}

		int confirm = JOptionPane.showConfirmDialog(
				this,
				"Na pewno usunąć kategorię: \"" + selected + "\"?",
				"Potwierdź usunięcie",
				JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.WARNING_MESSAGE
		);

		if (confirm != JOptionPane.OK_OPTION) {
			setStatus("ℹ️ Anulowano usuwanie kategorii: " + selected);

			return;
		}

		int result = AppContext.getProductCategory().deleteRecord(selected);

		if (result > 0) {
			categoryPanel.reload();
			updateMenuState();
			setStatus("🗑️ Usunięto kategorię: " + selected);
		} else {
			setStatus("❌ Nie usunięto — nie znaleziono lub błąd: " + selected);
			JOptionPane.showMessageDialog(this, "Nie znaleziono kategorii lub nie udało się usunąć.");
		}
	}

	private void openAddProductDialog() {
		if (selectedCategoryId == null || selectedCategoryName == null) {
			setStatus("⚠️ Najpierw wybierz kategorię, aby dodać produkt.");
			JOptionPane.showMessageDialog(this, "Najpierw wybierz kategorię z lewej strony!");

			return;
		}

		new AddProductDialog(
				this,
				AppContext.getProduct(),
				selectedCategoryId,
				selectedCategoryName,
				() -> productPanel.loadProductsForCategory(selectedCategoryId),
				this::setStatus
		).setVisible(true);
	}

	private void openEditProductDialog() {
		int productId = productPanel.getSelectedProductId();
		String productName = productPanel.getSelectedProductName();

		if (productId == -1) {
			setStatus("⚠️ Najpierw wybierz produkt do edycji.");

			return;
		}

		new EditProductDialog(
				this,
				AppContext.getProduct(),
				productId,
				productName,
				() -> productPanel.loadProductsForCategory(selectedCategoryId),
				this::setStatus
		).setVisible(true);
	}

	private void deleteSelectedProduct() {
		int productId = productPanel.getSelectedProductId();
		String productName = productPanel.getSelectedProductName();

		if (productId == -1) {
			setStatus("⚠️ Nie usunięto — nie wybrano produktu.");
			JOptionPane.showMessageDialog(this, "Najpierw wybierz produkt z tabeli po prawej!");

			return;
		}

		int confirm = JOptionPane.showConfirmDialog(
				this,
				"Na pewno usunąć produkt: \"" + productName + "\"?",
				"Potwierdź usunięcie",
				JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.WARNING_MESSAGE
		);

		if (confirm != JOptionPane.OK_OPTION) {
			setStatus("ℹ️ Anulowano usuwanie produktu: " + productName);

			return;
		}

		int result = AppContext.getProduct().deleteById(productId);

		if (result > 0) {
			productPanel.loadProductsForCategory(selectedCategoryId);
			setStatus("🗑️ Usunięto produkt: " + productName);
		} else {
			setStatus("❌ Nie udało się usunąć produktu: " + productName);
		}
	}

	private JMenu createCategorySortMenu() {
		JMenu sortCatMenu = new JMenu("Sortowanie kategorii");

		JMenu catAlpha = new JMenu("Alfabetycznie");
		JMenuItem catAZ = new JMenuItem("Rosnąco (A-Z)");
		catAZ.addActionListener(e -> categoryPanel.sortAlphabetically(true));
		JMenuItem catZA = new JMenuItem("Malejąco (Z-A)");
		catZA.addActionListener(e -> categoryPanel.sortAlphabetically(false));

		catAlpha.add(catAZ);
		catAlpha.add(catZA);

		sortCatMenu.add(catAlpha);

		return sortCatMenu;
	}

	private JMenu createGlobalSortMenu() {
		JMenu globalSort = new JMenu("Sortowanie");
		globalSort.setMnemonic(KeyEvent.VK_S);

		JMenu catSub = createCategorySortMenu();
		catSub.setText("Kategorie");
		catSub.setMnemonic(KeyEvent.VK_K);

		JMenu prodSub = createProductSortMenu();
		prodSub.setText("Produkty");
		prodSub.setMnemonic(KeyEvent.VK_P);

		globalSort.add(catSub);
		globalSort.add(prodSub);

		return globalSort;
	}

	private JMenu createProductSortMenu() {
		JMenu prodSort = new JMenu("Sortowanie produktów");

		JMenu prodAlpha = new JMenu("Alfabetycznie");
		JMenuItem prodAZ = new JMenuItem("Rosnąco (A-Z)");
		prodAZ.addActionListener(e -> productPanel.sortAlphabetically(true));
		JMenuItem prodZA = new JMenuItem("Malejąco (Z-A)");
		prodZA.addActionListener(e -> productPanel.sortAlphabetically(false));
		prodAlpha.add(prodAZ);
		prodAlpha.add(prodZA);

		JMenu prodDate = new JMenu("Po dacie");
		JMenuItem dateNewest = new JMenuItem("Od najmłodszego");
		dateNewest.addActionListener(e -> productPanel.sortByDate(true));
		JMenuItem dateOldest = new JMenuItem("Od najstarszego");
		dateOldest.addActionListener(e -> productPanel.sortByDate(false));
		prodDate.add(dateNewest);
		prodDate.add(dateOldest);

		prodSort.add(prodAlpha);
		prodSort.add(prodDate);

		return prodSort;
	}

	private void setStatus(String msg) {
		statusBar.setMessage(msg);
	}
}