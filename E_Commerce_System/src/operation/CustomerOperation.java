package operation;

import model.Customer;
import model.User;

import java.io.*;
import java.nio.file.*;
import java.util.*;

import db.read.ReadUserDB;

public class CustomerOperation {
    private static CustomerOperation instance;
    private static final String FILE_PATH = "src/db/users.txt";

    private CustomerOperation() {}

    public static CustomerOperation getInstance() {
        if (instance == null) {
            instance = new CustomerOperation();
        }
        return instance;
    }

    public boolean validateEmail(String email) {
        return email != null && email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$");
    }

    public boolean validateMobile(String mobile) {
        return mobile != null && mobile.matches("^(04|03)\\d{8}$");
    }

    public boolean registerCustomer(String userName, String userPassword,
                                     String userEmail, String userMobile) {
        UserOperation userOp = UserOperation.getInstance();

        if (!userOp.validateUsername(userName) ||
            !userOp.validatePassword(userPassword) ||
            !validateEmail(userEmail) ||
            !validateMobile(userMobile) ||
            userOp.checkUsernameExist(userName)) {
            System.out.print(userOp.validateUsername(userName) ? "" : "Invalid username.\n");
            System.out.print(userOp.validatePassword(userPassword) ? "" : "Invalid password.\n");
            System.out.print(validateEmail(userEmail) ? "" : "Invalid email.\n");
            System.out.print(validateMobile(userMobile) ? "" : "Invalid mobile.\n");
            return false;
        }

        String userId = userOp.generateUniqueUserId();
        String registerTime = userOp.getCurrentTime();
        String encryptedPass = userOp.encryptPassword(userPassword);

        Customer customer = new Customer(userId, userName, encryptedPass, registerTime,
                                         "customer", userEmail, userMobile);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            bw.write(customer.toString());
            bw.newLine();
            return true;
        } catch (IOException e) {
            System.out.println("Failed to register customer: " + e.getMessage());
            return false;
        }
    }

    public boolean updateProfile(String attributeName, String value, Customer customer) {
        if (attributeName == null || value == null || customer == null) return false;

        switch (attributeName) {
            case "user_name":
                if (!UserOperation.getInstance().validateUsername(value)) return false;
                customer.setUserName(value);
                break;
            case "user_password":
                if (!UserOperation.getInstance().validatePassword(value)) return false;
                customer.setUserPassword(UserOperation.getInstance().encryptPassword(value));
                break;
            case "user_email":
                if (!validateEmail(value)) return false;
                customer.setUserEmail(value);
                break;
            case "user_mobile":
                if (!validateMobile(value)) return false;
                customer.setUserMobile(value);
                break;
            default:
                return false;
        }

        return overwriteCustomer(customer);
    }

    public boolean overwriteCustomer(Customer updatedCustomer) {
        try {

            List<User> users = ReadUserDB.read();

            for (User user : users) {
                if (user.getUserId().equals(updatedCustomer.getUserId())) {
                    updated.add(updatedCustomer.toString());
                } else {
                    updated.add(line);
                }
            }
            Files.write(Paths.get(FILE_PATH), updated);
            return true;
        } catch (IOException e) {
            System.out.println("Failed to update customer: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteCustomer(String customerId) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(FILE_PATH));
            lines.removeIf(line -> line.contains("\"user_id\":\"" + customerId + "\""));
            Files.write(Paths.get(FILE_PATH), lines);
            return true;
        } catch (IOException e) {
            System.out.println("Failed to delete customer: " + e.getMessage());
            return false;
        }
    }

    public CustomerListResult getCustomerList(int pageNumber) {
        List<Customer> customers = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(Paths.get(FILE_PATH));
            for (String line : lines) {
                if (line.contains("\"user_role\":\"customer\"")) {
                    customers.add((Customer) ReadUserDB.parseUserFromJson(line));
                }
            }
        } catch (IOException e) {
            System.out.println("Failed to read customers: " + e.getMessage());
        }

        int totalPages = (int) Math.ceil(customers.size() / 10.0);
        int start = (pageNumber - 1) * 10;
        int end = Math.min(start + 10, customers.size());
        List<Customer> page = start < customers.size() ? customers.subList(start, end) : new ArrayList<>();

        return new CustomerListResult(page, pageNumber, totalPages);
    }

    public void deleteAllCustomers() {
        try {
            List<String> lines = Files.readAllLines(Paths.get(FILE_PATH));
            lines.removeIf(line -> line.contains("\"user_role\":\"customer\""));
            Files.write(Paths.get(FILE_PATH), lines);
        } catch (IOException e) {
            System.out.println("Failed to delete all customers: " + e.getMessage());
        }
    }


   
}
