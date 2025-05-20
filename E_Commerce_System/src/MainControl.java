import java.util.Scanner;

import model.Customer;
import model.Order;
import model.Product;
import model.User;
import operation.CustomerOperation;
import operation.OrderOperation;
import operation.ProductOperation;
import operation.UserOperation;

public class MainControl {
    public static void main(String[] args) {
        IOInterface.printMenu();
        
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                login(scanner);
                break;
            case 2:
                register(scanner);
                break;
            case 3:
                System.out.println("Quiting...");
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }

        scanner.close();
    }

    public static void login(Scanner scanner) {
        UserOperation userOp = UserOperation.getInstance();
        IOInterface.printLogin();
        String username = scanner.next();
        String password = scanner.next();
        User user = userOp.login(username, password);
        if (user != null && user.getUserRole().equals("customer")) {
            IOInterface.printCustomerMenu(user.getUserName());
            scanner.nextLine(); // Consume newline
            ProductOperation productOp = ProductOperation.getInstance();
            OrderOperation orderOp = OrderOperation.getInstance();
            CustomerOperation cusOp = CustomerOperation.getInstance();
            String choice = scanner.nextLine();
            String productId = null;
            switch (choice) {
                case "1":
                    int page = 1;
                    do {
                        System.out.println("Total pages: " + productOp.getProductPage());
                        System.out.println("Enter page number or 'q' to quit:");
                        String input = scanner.nextLine();
                        if (input.equals("q")) {
                            break;
                        }
                        try {
                            page = Integer.parseInt(input);
                            if (page < 1 || page > productOp.getProductPage()) {
                                System.out.println("Invalid page number. Please try again.");
                            } else {
                                productOp.getProductList(page).display();
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Please enter a number.");
                        }
                    } while (true);
                    break;
                case "2":
                    productOp = ProductOperation.getInstance();
                    System.out.println("Enter product ID to search:");
                    productId = scanner.nextLine();
                    Product product = productOp.getProductById(productId);
                    if (product != null) {
                        System.out.println("Product found: " + product);
                    } else {
                        System.out.println("Product not found.");
                    }
                    break;
                case "3":
                    System.out.println("Enter product ID to place order:");
                    productId = scanner.nextLine();
                    if(orderOp.placeOrder(user.getUserId(), productId)){
                        System.out.println("Place order successfully.");
                    }
                    else{
                        System.out.println("Place order failed.");
                    }
                    break;
                case "4":
                    System.out.println("Your orders:");
                    int cnt = 0;
                    for (Order order : orderOp.getOrdersByUserId(user.getUserId())) {
                        System.out.println(++cnt + ". " + order);
                    }
                    break;
                case "5":
                    System.out.println("Enter new username:");
                    String newUsername = scanner.nextLine();
                    System.out.println("Enter new password:");
                    String newPassword = scanner.nextLine();
                    System.out.println("Enter new email:");
                    String newEmail = scanner.nextLine();
                    System.out.println("Enter new mobile:");
                    String newMobile = scanner.nextLine();
                    cusOp.updateProfile(new Customer( user.getUserId(), newUsername, UserOperation.getInstance().encryptPassword(newPassword), user.getUserRegisterTime(), newEmail, newMobile), (Customer) user);
                    System.out.println("Profile updated successfully.");
                    break;
                case "6":
                    System.out.println("Are you sure you want to delete your account? (yes/no)");
                    String confirm = scanner.nextLine();
                    if (confirm.equalsIgnoreCase("yes")) {
                        cusOp.deleteCustomer(user.getUserId());
                        System.out.println("Account deleted successfully.");
                    } else {
                        System.out.println("Account deletion canceled.");
                    }
                    break;
                
                case "7":
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } else if (user != null && user.getUserRole().equals("admin")) {
            System.out.println("Admin not working yet.");
        }
         else {
            System.out.println("Invalid username or password.");
        }
    }

/*
    john_doe 3456
    admin XtIa
*/

    public static void register(Scanner scanner) {
        CustomerOperation cusOp = CustomerOperation.getInstance();
        System.out.println("Enter username: ");
        String username = scanner.next();
        System.out.println("Enter password: ");
        String password = scanner.next();
        System.out.println("Enter email: ");
        String email = scanner.next();
        System.out.println("Enter mobile: ");
        String mobile = scanner.next();

        if (cusOp.registerCustomer(username, password, email, mobile)) {
            System.out.println("Registration successful!");
        } else {
            System.out.println("Registration failed. Please try again.");
        }
    }
}
