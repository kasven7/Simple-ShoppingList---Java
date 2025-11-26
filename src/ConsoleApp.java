import Model.ProductCategory;

import java.util.Scanner;

public class ConsoleApp {
    private final ProductCategory category;
    private final Scanner scanner;

    public ConsoleApp(ProductCategory category) {
        this.category = category;
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        while (true) {
            System.out.println("\nKategorie listy zakupów:");
            System.out.println("1 - dodaj nową kategorię");
            System.out.println("2 - edytuj istniejącą");
            System.out.println("3 - wyświetl wszystkie");
            System.out.println("4 - usuń kategorię");
            System.out.println("0 - zakończ");
            System.out.print("Twój wybór: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> addCategory();
                case "2" -> updateCategory();
                case "3" -> category.readTable();
                case "4" -> deleteCategory();
                case "0" -> {
                    System.out.println("👋 Do zobaczenia!");
                    return;
                }
                default -> System.out.println("❌ Niepoprawny wybór.");
            }
        }
    }

    private void addCategory() {
        System.out.print("Podaj nazwę nowej kategorii: ");

        String name = scanner.nextLine();
        int result = category.addRecord(name);

        System.out.println(result > 0 ? "✅ Dodano!" : "⚠️ Podana nazwa już istnieje!.");
    }

    private void updateCategory() {
        System.out.print("Podaj nazwę istniejącej kategorii: ");
        String oldName = scanner.nextLine();

        System.out.print("Podaj nową nazwę: ");
        String newName = scanner.nextLine();

        int result = category.updateRecord(oldName, newName);

        switch (result) {
            case 1 -> System.out.println("✅ Zaktualizowano!");
            case 0 -> System.out.println("⚠️ Nie znaleziono kategorii o nazwie: " + oldName);
            case -1 -> System.out.println("❌ Nie można zmienić — nazwa '" + newName + "' już istnieje!");
            default -> System.out.println("❌ Nieoczekiwany błąd.");
        }
    }

    private void deleteCategory() {
        System.out.print("Podaj nazwę kategorii do usunięcia: ");

        String name = scanner.nextLine();
        int result = category.deleteRecord(name);

        System.out.println(result > 0 ? "🗑️ Usunięto!" : "⚠️ Nie znaleziono kategorii.");
    }
}