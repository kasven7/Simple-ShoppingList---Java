package UI.menu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class MainMenuBar extends JMenuBar {

    public MainMenuBar(ActionListener onAddCategory,
                       ActionListener onEditCategory,
                       ActionListener onDeleteCategory) {

        int shortcutKey = Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx();

        // Menu "Plik"
        JMenu fileMenu = new JMenu("Plik");

        JMenuItem exitItem = new JMenuItem("Wyjście");
        exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, shortcutKey));
        exitItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitItem);

        // Menu "Operacje"
        JMenu operationsMenu = new JMenu("Operacje");

        JMenuItem addItem = new JMenuItem("Dodaj kategorię");
        addItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, shortcutKey));
        addItem.addActionListener(onAddCategory);
        operationsMenu.add(addItem);

        JMenuItem editItem = new JMenuItem("Edytuj kategorię");
        editItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, shortcutKey));
        editItem.addActionListener(onEditCategory);
        operationsMenu.add(editItem);

        JMenuItem deleteItem = new JMenuItem("Usuń kategorię");
        deleteItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, shortcutKey)); // ⌘⌫ / Ctrl+Backspace
        deleteItem.addActionListener(onDeleteCategory);
        operationsMenu.add(deleteItem);

        add(fileMenu);
        add(operationsMenu);
    }
}