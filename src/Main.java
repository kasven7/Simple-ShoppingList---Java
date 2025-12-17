import UI.AppContext;
import UI.MainWindow;

import javax.swing.*;

public class Main {
	public static void main(String[] args) {
		AppContext.init();
		SwingUtilities.invokeLater(() -> new MainWindow().setVisible(true));
	}
}