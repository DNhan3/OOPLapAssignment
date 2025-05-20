
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

    public static void printMenu() {
        System.out.println("====== E-Commerce System ======");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Quit");
        System.out.println("==============================");
        System.out.print("Enter your choice: ");
    }

    public static void printLogin() {
        System.out.println("Enter username and password: ");
    }

    public static void printCustomerMenu(String userName) {
        System.out.println("Login successful. Welcome, " + userName + "!");
        System.out.println("====== Customer Menu ======");
        System.out.println("1. View all products");
        System.out.println("2. Search product by id");
        System.out.println("3. Place an order");
        System.out.println("4. View my orders");
        System.out.println("5. Update profile");
        System.out.println("6. Logout");
        System.out.println("==============================");
        System.out.print("Enter your choice: ");
    }

}