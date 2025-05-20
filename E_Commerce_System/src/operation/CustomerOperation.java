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

    public boolean registerCustomer(Customer customer_) {
        if (customer_ == null) return false;
        String userName = customer_.getUserName();
        String userPassword = customer_.getUserPassword();
        String userEmail = customer_.getUserEmail();
        String userMobile = customer_.getUserMobile();
        if (userName == null || userPassword == null || userEmail == null || userMobile == null) {
            return false;
        }
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

    public boolean updateProfile(Customer newCustomer, Customer oldCustomer) {
        if (newCustomer == null || oldCustomer == null) return false;

        newCustomer.setUserId(oldCustomer.getUserId());

        return overwriteCustomer(newCustomer);
    }

    public boolean overwriteCustomer(Customer updatedCustomer) {
        try {

            List<User> users = ReadUserDB.read();
            List<User> updated = new ArrayList<>();

            for (User user : users) {
                if (user.getUserId().equals(updatedCustomer.getUserId())) {
                    updated.add(updatedCustomer);
                } else {
                    updated.add(user);
                }
            }

            List<String> userStrings = new ArrayList<>();
            for (User user : updated) {
                userStrings.add(user.toString());
            }
            Files.write(Paths.get(FILE_PATH), userStrings);
            return true;
        } catch (IOException e) {
            System.out.println("Failed to update customer: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteCustomer(String customerId) {
        try {
            List<User> users = ReadUserDB.read();
            List<User> updated = new ArrayList<>();

            for (User user : users) {
                if (!user.getUserId().equals(customerId)) {
                    updated.add(user);
                }
            }

            List<String> userStrings = new ArrayList<>();
            for (User user : updated) {
                userStrings.add(user.toString());
            }
            Files.write(Paths.get(FILE_PATH), userStrings);
            return true;
        } catch (IOException e) {
            System.out.println("Failed to delete customer: " + e.getMessage());
            return false;
        }
    }

   
}
