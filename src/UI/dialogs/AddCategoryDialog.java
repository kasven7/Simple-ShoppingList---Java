package UI.dialogs;

import Model.ProductCategory;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class AddCategoryDialog extends JDialog {

    public AddCategoryDialog(JFrame parent, ProductCategory category, Runnable onSuccess, Consumer<String> setStatus) {
        super(parent, "Dodaj kategorię", true);

        setLayout(new BorderLayout());

        JTextField nameField = new JTextField(20);

        JPanel form = new JPanel(new FlowLayout());
        form.add(new JLabel("Nazwa kategorii:"));
        form.add(nameField);

        JButton addBtn = new JButton("Dodaj");
        addBtn.addActionListener(e -> {
            String name = nameField.getText().trim();

            if (name.isEmpty()) {
                setStatus.accept("⚠️ Nie dodano — pusta nazwa kategorii.");
                return;
            }

            int result = category.addRecord(name);
            if (result > 0) {
                setStatus.accept("✅ Dodano nową kategorię: " + name);
                onSuccess.run();
                dispose();
            } else {
                setStatus.accept("⚠️ Nie dodano — kategoria już istnieje: " + name);
                JOptionPane.showMessageDialog(this, "Taka kategoria już istnieje!");
            }
        });

        add(form, BorderLayout.CENTER);
        add(addBtn, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(parent);
    }
}