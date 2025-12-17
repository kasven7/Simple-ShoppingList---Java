package UI.dialogs;

import Model.ProductCategory;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class EditCategoryDialog extends JDialog {

	public EditCategoryDialog(JFrame parent,
							  ProductCategory category,
							  String oldName,
							  Runnable onSuccess,
							  Consumer<String> setStatus) {
		super(parent, "Edytuj kategorię", true);

		setLayout(new BorderLayout());

		JTextField nameField = new JTextField(oldName, 20);

		JPanel form = new JPanel(new FlowLayout());
		form.add(new JLabel("Nowa nazwa kategorii:"));
		form.add(nameField);

		JButton saveBtn = new JButton("Zapisz");

		saveBtn.addActionListener(e -> {
			String newName = nameField.getText().trim();

			if (newName.isEmpty()) {
				setStatus.accept("⚠️ Nie edytowano — pusta nazwa.");
				return;
			}

			if (newName.equals(oldName)) {
				setStatus.accept("ℹ️ Nie zmieniono — nazwa pozostała: " + oldName);
				dispose();
				return;
			}

			int result = category.updateRecord(oldName, newName);

			if (result == 1) {
				setStatus.accept("✏️ Edytowano kategorię: " + oldName + " → " + newName);
				onSuccess.run();
				dispose();
			} else if (result == -1) {
				setStatus.accept("❌ Nie edytowano — istnieje już: " + newName);
				JOptionPane.showMessageDialog(this, "Taka kategoria już istnieje!", "Błąd", JOptionPane.ERROR_MESSAGE);
			} else {
				setStatus.accept("❌ Nie edytowano — nie znaleziono: " + oldName);
				JOptionPane.showMessageDialog(this, "Nie znaleziono kategorii!", "Błąd", JOptionPane.ERROR_MESSAGE);
			}
		});

		add(form, BorderLayout.CENTER);
		add(saveBtn, BorderLayout.SOUTH);

		pack();
		setLocationRelativeTo(parent);
	}
}