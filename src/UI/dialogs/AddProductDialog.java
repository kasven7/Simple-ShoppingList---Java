package UI.dialogs;

import Model.Product;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class AddProductDialog extends JDialog {
    public AddProductDialog(JFrame parent,
                            Product product,
                            int categoryId,
                            String categoryName,
                            Runnable onSuccess,
                            Consumer<String> setStatus) {
        super(parent, "Dodaj produkt", true);

        setLayout(new BorderLayout());

        JTextField nameField = new JTextField(20);

        JPanel form = new JPanel(new GridLayout(2, 2, 10, 10));
        form.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        form.add(new JLabel("Kategoria:"));
        form.add(new JLabel(categoryName));

        form.add(new JLabel("Nazwa produktu:"));
        form.add(nameField);

        JButton addBtn = new JButton("Dodaj");
        addBtn.addActionListener(e -> {
            String name = nameField.getText().trim();

            if (name.isEmpty()) {
                setStatus.accept("⚠️ Nie dodano produktu — pusta nazwa.");
                return;
            }

            int result = product.addRecord(name, categoryId);
            if (result > 0) {
                setStatus.accept("✅ Dodano produkt: " + name + " (kategoria: " + categoryName + ")");
                onSuccess.run();
                dispose();
            } else {
                setStatus.accept("❌ Nie dodano produktu: " + name);
                JOptionPane.showMessageDialog(this, "Nie udało się dodać produktu (możliwy duplikat).");
            }
        });

        add(form, BorderLayout.CENTER);
        add(addBtn, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(parent);
    }
}