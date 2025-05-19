package operation;

import model.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import db.read.ReadUserDB;

public class UserOperation {
    private static UserOperation instance;
    private static int userCount = ReadUserDB.read().size();

    private UserOperation() {}

    public static UserOperation getInstance() {
        if (instance == null) {
            instance = new UserOperation();
        }
        return instance;
    }

    public String generateUniqueUserId() {
        return "u_" + String.format("%010d", ++userCount);
    }

    /**
     * Encrypts the password by interleaving two characters from a random string
     * and one character from the password, repeating until the password is fully processed.
     * Adds "^^" at the start and "$$" at the end.
     *
     * @param userPassword The password to encrypt
     * @return Encrypted password
     */
    public String encryptPassword(String userPassword) {
        if (userPassword == null) return null;
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random rand = new Random();
        StringBuilder encrypted = new StringBuilder("^^");
        for (int i = 0; i < userPassword.length(); i++) {
            encrypted.append(chars.charAt(rand.nextInt(chars.length())));
            encrypted.append(chars.charAt(rand.nextInt(chars.length())));
            encrypted.append(userPassword.charAt(i));
        }
        encrypted.append("$$");
        return encrypted.toString();
    }

    /**
     * Decrypts the password encrypted by encryptPassword.
     *
     * @param encryptedPassword The encrypted password to decrypt
     * @return Original user-provided password
     */
    public String decryptPassword(String encryptedPassword) {
        if (encryptedPassword == null || !encryptedPassword.startsWith("^^") || !encryptedPassword.endsWith("$$")) {
            return null;
        }
        StringBuilder decrypted = new StringBuilder();
        String content = encryptedPassword.substring(2, encryptedPassword.length() - 2);
        for (int i = 2; i < content.length(); i += 3) {
            decrypted.append(content.charAt(i));
        }
        return decrypted.toString();
    }

    public boolean checkUsernameExist(String userName) {
        List<User> users = ReadUserDB.read();
        for (User user : users) {
            if (user.getUserName().equals(userName)) {
                return true;
            }
        }
        return false;
    }

    public boolean validateUsername(String userName) {
        return userName != null && userName.matches("[a-zA-Z_]{5,}");
    }

    public boolean validatePassword(String userPassword) {
        return userPassword != null && userPassword.length() >= 5 &&
               userPassword.matches("(?=.*[a-zA-Z])(?=.*\\d).+");
    }

    public User login(String userName, String userPassword) {
        List<User> users = ReadUserDB.read();
        for (User user : users) {
            if (user.getUserName().equals(userName) && decryptPassword(user.getUserPassword()).equals(userPassword)) {
                return user;
            }
        }
        return null;
    }

    public String getCurrentTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy_HH:mm:ss"));
    }
}
