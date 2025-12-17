package UI.menu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class MainMenuBar extends JMenuBar {

	private final JMenuItem editItem;
	private final JMenuItem deleteItem;
	private final JMenuItem editProductItem;
	private final JMenuItem deleteProductItem;

	public MainMenuBar(ActionListener onAddCategory,
					   ActionListener onEditCategory,
					   ActionListener onDeleteCategory,
					   ActionListener onAddProduct,
					   ActionListener onEditProduct,
					   ActionListener onDeleteProduct,
					   ActionListener onAbout) {

		int shortcutKey = Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx();

		JMenu fileMenu = new JMenu("Plik");
		fileMenu.setMnemonic(KeyEvent.VK_P);

		JMenuItem exitItem = new JMenuItem("Wyjście");
		exitItem.setMnemonic(KeyEvent.VK_W);
		exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, shortcutKey));
		exitItem.addActionListener(e -> System.exit(0));
		fileMenu.add(exitItem);

		JMenu operationsMenu = new JMenu("Operacje");
		operationsMenu.setMnemonic(KeyEvent.VK_O);

		JMenuItem addCatItem = new JMenuItem("Dodaj kategorię");
		addCatItem.setMnemonic(KeyEvent.VK_D);
		addCatItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_K, shortcutKey));
		addCatItem.addActionListener(onAddCategory);

		editItem = new JMenuItem("Edytuj kategorię");
		editItem.setMnemonic(KeyEvent.VK_E);
		editItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_K, shortcutKey | KeyEvent.SHIFT_DOWN_MASK));
		editItem.addActionListener(onEditCategory);

		deleteItem = new JMenuItem("Usuń kategorię");
		deleteItem.setMnemonic(KeyEvent.VK_U);
		deleteItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, 0));
		deleteItem.addActionListener(onDeleteCategory);

		operationsMenu.add(addCatItem);
		operationsMenu.add(editItem);
		operationsMenu.add(deleteItem);
		operationsMenu.addSeparator(); //

		JMenuItem addProductItem = new JMenuItem("Dodaj produkt");
		addProductItem.setMnemonic(KeyEvent.VK_P);
		addProductItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, shortcutKey)); // Ctrl + P
		addProductItem.addActionListener(onAddProduct);

		editProductItem = new JMenuItem("Edytuj produkt");
		editProductItem.setMnemonic(KeyEvent.VK_T);
		editProductItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, shortcutKey | KeyEvent.SHIFT_DOWN_MASK));
		editProductItem.addActionListener(onEditProduct);

		deleteProductItem = new JMenuItem("Usuń produkt");
		deleteProductItem.setMnemonic(KeyEvent.VK_R); // Litera 'R'
		deleteProductItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_BACK_SPACE, shortcutKey));
		deleteProductItem.addActionListener(onDeleteProduct);

		operationsMenu.add(addProductItem);
		operationsMenu.add(editProductItem);
		operationsMenu.add(deleteProductItem);

		JMenu helpMenu = new JMenu("Pomoc");
		helpMenu.setMnemonic(KeyEvent.VK_H); // Alt + H

		JMenuItem aboutItem = new JMenuItem("O programie");
		aboutItem.setMnemonic(KeyEvent.VK_O);
		aboutItem.addActionListener(onAbout);
		helpMenu.add(aboutItem);

		add(fileMenu);
		add(operationsMenu);
		add(helpMenu);
	}

	public void setCategoryActionsEnabled(boolean enabled) {
		editItem.setEnabled(enabled);
		deleteItem.setEnabled(enabled);
	}

	public void setProductActionsEnabled(boolean enabled) {
		editProductItem.setEnabled(enabled);
		deleteProductItem.setEnabled(enabled);
	}
}