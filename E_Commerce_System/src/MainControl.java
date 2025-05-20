import java.util.Scanner;

import model.Customer;
import model.Product;
import model.User;
import operation.CustomerOperation;
import operation.OrderOperation;
import operation.ProductOperation;
import operation.UserOperation;

public class MainControl {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        switch(IOInterface.printMenu(scanner)){
            case 1:
                login(scanner);
                break;
            case 2:
                register(scanner);
                break;
            case 3:
                IOInterface.exit();
                break;
            default:
                break;
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
            
            ProductOperation productOp = ProductOperation.getInstance();
            OrderOperation orderOp = OrderOperation.getInstance();
            CustomerOperation cusOp = CustomerOperation.getInstance();
            String productId = null;
            switch (IOInterface.printCustomerMenu(user.getUserName(), scanner)) {
                case 1:
                    int page = 1;
                    do {
                        String input = IOInterface.printProductList1(scanner);
                        if (input.equals("q")) {
                            break;
                        }
                        try {
                            page = Integer.parseInt(input);
                            if (page < 1 || page > productOp.getProductPage()) {
                                IOInterface.printError("Invalid page number. Please try again.");
                            } else {
                                productOp.getProductList(page).display();
                            }
                        } catch (NumberFormatException e) {
                            IOInterface.printError("Invalid input. Please enter a number.");
                        }
                    } while (true);
                    break;
                case 2:
                    productOp = ProductOperation.getInstance();
                    productId = IOInterface.getProductId(scanner);
                    Product product = productOp.getProductById(productId);
                    if (product != null) {
                        IOInterface.print("Product found: " + product);
                    } else {
                        IOInterface.printError("Product not found.");
                    }
                    break;
                case 3:
                    productId = IOInterface.getProductId(scanner);
                    if(orderOp.placeOrder(user.getUserId(), productId)){
                        IOInterface.successful();
                    }
                    else{
                        IOInterface.failed();
                    }
                    break;
                case 4:
                    IOInterface.printOrders(orderOp.getOrdersByUserId(user.getUserId()));
                    break;
                case 5:
                    Customer cus = IOInterface.updatedCustomer( user.getUserId(), scanner);
                    cusOp.updateProfile(new Customer( user.getUserId(), cus.getUserName(), UserOperation.getInstance().encryptPassword(cus.getUserPassword()), user.getUserRegisterTime(), cus.getUserEmail(), cus.getUserMobile()), (Customer) user);
                    IOInterface.successful();
                    break;
                case 6:
                    if (IOInterface.confirmDeletetion(scanner)) {
                        cusOp.deleteCustomer(user.getUserId());
                        IOInterface.successful();
                    } else {
                        IOInterface.failed();
                    }
                    break;
                
                case 7:
                    IOInterface.exit();
                    break;
                default:
                    break;
            }
        } else if (user != null && user.getUserRole().equals("admin")) {
            IOInterface.printError("Admin not working yet.");
        }
        else {
            IOInterface.printError("Invalid username or password.");
        }
    }

/*
    john_doe 3456
    admin XtIa
*/

    public static void register(Scanner scanner) {
        CustomerOperation cusOp = CustomerOperation.getInstance();
        if (cusOp.registerCustomer(IOInterface.printRegister(scanner))) {
            System.out.println("Registration successful!");
        } else {
            System.out.println("Registration failed. Please try again.");
        }
    }
}
