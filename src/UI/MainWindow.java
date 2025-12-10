package UI;

import UI.dialogs.AddCategoryDialog;
import UI.dialogs.EditCategoryDialog;
import UI.menu.MainMenuBar;
import UI.panels.CategoryPanel;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    private CategoryPanel categoryPanel;
    private MainMenuBar menuBar;

    public MainWindow() {
        setTitle("Shopping List - GUI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        // Panel główny
        categoryPanel = new CategoryPanel(AppContext.getProductCategory());

        // Menu z akcją
        menuBar = new MainMenuBar(
                e -> openAddCategoryDialog(),
                e -> openEditCategoryDialog(),
                e -> deleteSelectedCategory()
        );
        setJMenuBar(menuBar);

        // po starcie ustaw stan menu
        updateMenuState();

        add(categoryPanel, BorderLayout.CENTER);
    }

    private void openAddCategoryDialog() {
        new AddCategoryDialog(
                this,
                AppContext.getProductCategory(),
                () -> {
                    categoryPanel.reload();
                    updateMenuState();
                }
        ).setVisible(true);
    }

    private void updateMenuState() {
        menuBar.setCategoryActionsEnabled(categoryPanel.hasAnyCategory());
    }

    private void openEditCategoryDialog() {
        String selected = categoryPanel.getSelectedCategory();
        if (selected == null) {
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
                }
        ).setVisible(true);
    }

    private void deleteSelectedCategory() {
        String selected = categoryPanel.getSelectedCategory();
        if (selected == null) {
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

        if (confirm != JOptionPane.OK_OPTION) return;

        int result = AppContext.getProductCategory().deleteRecord(selected);

        if (result > 0) {
            categoryPanel.reload();
            updateMenuState();
        } else {
            JOptionPane.showMessageDialog(this, "Nie znaleziono kategorii lub nie udało się usunąć.");
        }
    }

}
