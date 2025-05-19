package db.read;
import model.Customer;
import model.Admin;
import model.User;

import java.io.*;
import java.util.*;
import java.util.regex.*;

public class ReadUserDB {
    public static List<User> read() {
        String filePath = "src/db/users.txt";
        List<User> users = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                while (!line.trim().endsWith("}")) {
                    line += br.readLine();
                }
                users.add(parseUserFromJson(line));
            }
        } catch (IOException e) {
            System.out.println("Failed to read file: " + e.getMessage());
        }
        return users;
    }
    
    public static User parseUserFromJson(String json) {
        Map<String, String> map = new HashMap<>();
        Pattern p = Pattern.compile("\\\"(.*?)\\\":\\\"(.*?)\\\"");
        Matcher m = p.matcher(json);
        while (m.find()) {
            map.put(m.group(1), m.group(2));
        }

        String id = map.get("user_id");
        String name = map.get("user_name");
        String pass = map.get("user_password");
        String regTime = map.get("user_register_time");
        String role = map.get("user_role");

        if ("admin".equals(role)) {
            return new Admin(id, name, pass, regTime, role);
        } else {
            String email = map.getOrDefault("user_email", ""); //avoid null pointer exception
            String mobile = map.getOrDefault("user_mobile", "");
            return new Customer(id, name, pass, regTime, role, email, mobile);
        }
    }
}
