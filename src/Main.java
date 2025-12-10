import UI.AppContext;
import UI.MainWindow;
import UI.components.BottomStatusBar;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        // 1. Inicjalizacja bazy
        AppContext.init();

        // 2. Start GUI
        SwingUtilities.invokeLater(() -> {
            new MainWindow().setVisible(true);
        });
    }
}