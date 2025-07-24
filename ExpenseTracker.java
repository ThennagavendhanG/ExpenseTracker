import java.io.*;
import java.util.*;

class Expense {
    String description;
    double amount;
    String category;

    Expense(String description, double amount, String category) {
        this.description = description;
        this.amount = amount;
        this.category = category;
    }

    @Override
    public String toString() {
        return description + " - $" + amount + " [" + category + "]";
    }
}

public class ExpenseTracker {
    static List<Expense> expenses = new ArrayList<>();
    static final String FILE_NAME = "expenses.txt";

    public static void main(String[] args) {
        loadExpenses();

        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n--- Personal Expense Tracker ---");
            System.out.println("1. Add Expense");
            System.out.println("2. View All Expenses");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            
            choice = scanner.nextInt();
            scanner.nextLine();  // Clear newline

            switch (choice) {
                case 1 -> addExpense(scanner);
                case 2 -> viewExpenses();
                case 3 -> {
                    saveExpenses();
                    System.out.println("Goodbye!");
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        } while (choice != 3);
    }

    private static void addExpense(Scanner scanner) {
        System.out.print("Enter description: ");
        String description = scanner.nextLine();

        System.out.print("Enter amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();  // Clear newline

        System.out.print("Enter category: ");
        String category = scanner.nextLine();

        Expense expense = new Expense(description, amount, category);
        expenses.add(expense);
        System.out.println("Expense added.");
    }

    private static void viewExpenses() {
        System.out.println("\nYour Expenses:");
        if (expenses.isEmpty()) {
            System.out.println("No expenses recorded.");
        } else {
            for (Expense e : expenses) {
                System.out.println(e);
            }
        }
    }

    private static void saveExpenses() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Expense e : expenses) {
                writer.write(e.description + "," + e.amount + "," + e.category);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving expenses: " + e.getMessage());
        }
    }

    private static void loadExpenses() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    expenses.add(new Expense(parts[0], Double.parseDouble(parts[1]), parts[2]));
                }
            }
        } catch (IOException e) {
            // Ignore if file doesn't exist
        }
    }
}
