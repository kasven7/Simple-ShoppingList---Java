package UI;

import UI.components.BottomStatusBar;
import UI.dialogs.AddCategoryDialog;
import UI.dialogs.AddProductDialog;
import UI.dialogs.EditCategoryDialog;
import UI.menu.MainMenuBar;
import UI.panels.CategoryPanel;
import UI.panels.ProductPanel;

import javax.swing.*;
import java.awt.*;

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

        // Panele
        categoryPanel = new CategoryPanel(AppContext.getProductCategory());
        productPanel = new ProductPanel(AppContext.getProduct());

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, categoryPanel, productPanel);
        splitPane.setDividerLocation(300);
        splitPane.setResizeWeight(0.35);
        add(splitPane, BorderLayout.CENTER);

        // Status bar
        statusBar = new BottomStatusBar();
        add(statusBar, BorderLayout.SOUTH);

        // Menu
        menuBar = new MainMenuBar(
                e -> openAddCategoryDialog(),
                e -> openEditCategoryDialog(),
                e -> deleteSelectedCategory(),
                e -> openAddProductDialog()
        );
        setJMenuBar(menuBar);

        // Klik w kategorię -> produkty
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

        updateMenuState();
    }

    private void openAddCategoryDialog() {
    new AddCategoryDialog(
            this,
            AppContext.getProductCategory(),
            () -> { categoryPanel.reload(); updateMenuState(); },
            this::setStatus
    ).setVisible(true);
}

    private void updateMenuState() {
        menuBar.setCategoryActionsEnabled(categoryPanel.hasAnyCategory());
        menuBar.setProductActionsEnabled(selectedCategoryId != null);
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
                () -> { categoryPanel.reload(); updateMenuState(); },
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

    private void setStatus(String msg) {
        statusBar.setMessage(msg);
    }

}
