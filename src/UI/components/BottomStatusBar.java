package UI.components;

import javax.swing.*;
import java.awt.*;

public class BottomStatusBar extends JPanel {
	private final JLabel label;

	public BottomStatusBar() {
		setLayout(new BorderLayout());
		label = new JLabel("Gotowe.");
		label.setBorder(BorderFactory.createEmptyBorder(6, 10, 6, 10));
		add(label, BorderLayout.CENTER);

		setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(220, 220, 220)));
	}

	public void setMessage(String message) {
		label.setText(message);
	}
}