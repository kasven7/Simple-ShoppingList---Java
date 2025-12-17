package UI.dialogs;

import Model.Product;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class EditProductDialog extends JDialog {
	public EditProductDialog(JFrame parent, Product product, int productId, String oldName, Runnable onSuccess, Consumer<String> setStatus) {
		super(parent, "Edytuj produkt", true);
		setLayout(new BorderLayout());

		JTextField nameField = new JTextField(oldName, 20);
		JPanel form = new JPanel(new FlowLayout());
		form.add(new JLabel("Nowa nazwa produktu:"));
		form.add(nameField);

		JButton saveBtn = new JButton("Zapisz");
		saveBtn.addActionListener(e -> {
			String newName = nameField.getText().trim();
			if (newName.isEmpty()) {
				setStatus.accept("⚠️ Nazwa produktu nie może być pusta.");
				return;
			}

			int result = product.updateRecord(productId, newName);
			if (result > 0) {
				setStatus.accept("✏️ Zmieniono nazwę produktu: " + oldName + " → " + newName);
				onSuccess.run();
				dispose();
			} else {
				setStatus.accept("❌ Błąd edycji produktu.");
			}
		});

		add(form, BorderLayout.CENTER);
		add(saveBtn, BorderLayout.SOUTH);
		pack();
		setLocationRelativeTo(parent);
	}
}