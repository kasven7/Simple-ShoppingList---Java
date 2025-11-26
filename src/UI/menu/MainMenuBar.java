package UI.menu;

import javax.swing.*;
import java.awt.event.KeyEvent;

public class MainMenuBar extends JMenuBar {
    public MainMenuBar() {
        // Menu "Plik"
        JMenu fileMenu = new JMenu("Plik");
        fileMenu.setMnemonic(KeyEvent.VK_P); // ALT+P

        JMenuItem exitItem = new JMenuItem("Wyjście");
        exitItem.setMnemonic(KeyEvent.VK_W); // ALT+W
        exitItem.addActionListener(e -> System.exit(0));

        fileMenu.add(exitItem);


        // Menu "Operacje"
        JMenu operationsMenu = new JMenu("Operacje");

        JMenuItem addItem = new JMenuItem("Dodaj produkt");
        JMenuItem editItem = new JMenuItem("Edytuj produkt");
        JMenuItem deleteItem = new JMenuItem("Usuń produkt");

        // np. editItem.setEnabled(false);

        operationsMenu.add(addItem);
        operationsMenu.add(editItem);
        operationsMenu.add(deleteItem);


        // dodanie menu do paska
        add(fileMenu);
        add(operationsMenu);
    }
}
