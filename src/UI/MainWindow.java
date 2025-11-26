package UI;

import UI.menu.MainMenuBar;
import UI.panels.CategoryPanel;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

    public MainWindow() {
        setTitle("Shopping List - GUI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // ustawienie layoutu
        setLayout(new BorderLayout());

        // dodanie menu głównego
        setJMenuBar(new MainMenuBar());

        // Placeholder – na razie panel główny jest pusty
        add(new JLabel("Witaj w aplikacji Shopping List!", SwingConstants.CENTER), BorderLayout.CENTER);
        add(new CategoryPanel(AppContext.getProductCategory()), BorderLayout.CENTER);

    }
}
