package UI.menu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class MainMenuBar extends JMenuBar {

    private final JMenuItem editItem;
    private final JMenuItem deleteItem;
    private final JMenuItem addProductItem;

    public MainMenuBar(ActionListener onAddCategory,
                       ActionListener onEditCategory,
                       ActionListener onDeleteCategory,
                       ActionListener onAddProduct) {

        int shortcutKey = Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx();

        JMenu fileMenu = new JMenu("Plik");
        JMenuItem exitItem = new JMenuItem("Wyjście");
        exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, shortcutKey));
        exitItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitItem);

        JMenu operationsMenu = new JMenu("Operacje");

        JMenuItem addItem = new JMenuItem("Dodaj kategorię");
        addItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, shortcutKey));
        addItem.addActionListener(onAddCategory);

        editItem = new JMenuItem("Edytuj kategorię");
        editItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, shortcutKey));
        editItem.addActionListener(onEditCategory);

        deleteItem = new JMenuItem("Usuń kategorię");
        deleteItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, shortcutKey));
        deleteItem.addActionListener(onDeleteCategory);

        addProductItem = new JMenuItem("Dodaj produkt");
        addProductItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, shortcutKey));
        addProductItem.addActionListener(onAddProduct);
        operationsMenu.addSeparator();
        operationsMenu.add(addProductItem);

        operationsMenu.add(addItem);
        operationsMenu.add(editItem);
        operationsMenu.add(deleteItem);

        add(fileMenu);
        add(operationsMenu);
    }

    /** Włącza/wyłącza akcje zależne od istnienia kategorii */
    public void setCategoryActionsEnabled(boolean enabled) {
        editItem.setEnabled(enabled);
        deleteItem.setEnabled(enabled);
    }

    public void setProductActionsEnabled(boolean enabled) { addProductItem.setEnabled(enabled); }
}