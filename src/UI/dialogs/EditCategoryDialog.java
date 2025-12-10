package UI.dialogs;

import Model.ProductCategory;

import javax.swing.*;
import java.awt.*;

public class EditCategoryDialog extends JDialog {

    public EditCategoryDialog(JFrame parent,
                              ProductCategory category,
                              String oldName,
                              Runnable onSuccess) {
        super(parent, "Edytuj kategorię", true);

        setLayout(new BorderLayout());

        JTextField nameField = new JTextField(oldName, 20);

        JPanel form = new JPanel(new FlowLayout());
        form.add(new JLabel("Nowa nazwa kategorii:"));
        form.add(nameField);

        JButton saveBtn = new JButton("Zapisz");

        saveBtn.addActionListener(e -> {
            String newName = nameField.getText().trim();

            int result = category.updateRecord(oldName, newName);

            if (result == 1) {
                onSuccess.run();
                dispose();
            } else if (result == -1) {
                JOptionPane.showMessageDialog(this,
                        "Taka kategoria już istnieje!",
                        "Błąd",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Nie znaleziono kategorii!",
                        "Błąd",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        add(form, BorderLayout.CENTER);
        add(saveBtn, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(parent);
    }
}