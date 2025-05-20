import java.util.List;
import java.util.Scanner;

import model.Customer;
import model.Order;
import operation.ProductOperation;

public class IOInterface {
    public static IOInterface instance = null;

    public static IOInterface getInstance() {
        if (instance == null) {
            instance = new IOInterface();
        }
        return instance;
    }

    public static void printWelcome() {
        System.out.println("Welcome to the E-Commerce System!");
        System.out.println("==================================");
    }

    public static int printMenu(Scanner scanner) {
        System.out.println("====== E-Commerce System ======");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Quit");
        System.out.println("==============================");
        System.out.print("Enter your choice: ");

        int choice = scanner.nextInt();
        scanner.nextLine();
        return choice;
    }

    public static void printLogin() {
        System.out.println("Enter username and password: ");
    }

    public static int printCustomerMenu(String userName, Scanner scanner) {
        System.out.println("Login successful. Welcome, " + userName + "!");
        System.out.println("====== Customer Menu ======");
        System.out.println("1. View all products");
        System.out.println("2. Search product by id");
        System.out.println("3. Place an order");
        System.out.println("4. View my orders");
        System.out.println("5. Update profile");
        System.out.println("6. Delete your account");
        System.out.println("7. Logout");
        System.out.println("==============================");
        System.out.print("Enter your choice: ");

        int choice = scanner.nextInt();
        scanner.nextLine();
        return choice;
    }

    public static void exit() {
        System.out.println("Thank you for using the E-Commerce System. Goodbye!");
    }

    public static Customer printRegister(Scanner scanner) {
        System.out.println("====== Register ======");
        System.out.println("Enter username: ");
        String username = scanner.next();
        System.out.println("Enter password: ");
        String password = scanner.next();
        System.out.println("Enter email: ");
        String email = scanner.next();
        System.out.println("Enter mobile: ");
        String mobile = scanner.next();
        System.out.println("==============================");
        return new Customer(null, username, password, null, email, mobile);
    }

    public static void successful() {
        System.out.println("Operation successful!");
    }

    public static void failed() {
        System.out.println("Operation fail");
    }

    public static void printError(String errorMessage) {
        System.out.println("Error: " + errorMessage);
    }

    public static String printProductList1(Scanner scanner) {
        System.out.println("Total pages: " + ProductOperation.getInstance().getProductPage());
        System.out.println("Enter page number or 'q' to quit:");
        String input = scanner.nextLine();
        return input;
    }

    public static String getProductId(Scanner scanner) {
        System.out.println("Enter product ID:");
        String productId = scanner.nextLine();
        return productId;
    }

    public static void print(String s) {
        System.out.println(s);
    }

    public static void printOrders(List<Order> orders) {
        System.out.println("Your orders:");
        int cnt = 0;
        for (Order order : orders) {
            System.out.println(++cnt + ". " + order);
        }
    }

    public static Customer updatedCustomer(String customerID, Scanner scanner) {
        System.out.println("Enter new username:");
        String newUsername = scanner.nextLine();
        System.out.println("Enter new password:");
        String newPassword = scanner.nextLine();
        System.out.println("Enter new email:");
        String newEmail = scanner.nextLine();
        System.out.println("Enter new mobile:");
        String newMobile = scanner.nextLine();

        return new Customer(null, newUsername, newPassword, null, newEmail, newMobile);
    }

    public static boolean confirmDeletetion(Scanner scanner) {
        System.out.println("Are you sure you want to delete your account? (yes/no)");
        String confirm = scanner.nextLine();
        if (confirm.equals("yes")){
            return true;
        } else return false;
    }
}