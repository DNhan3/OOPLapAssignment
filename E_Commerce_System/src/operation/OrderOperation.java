package operation;

import model.Order;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.*;

import db.read.ReadOrderDB;

public class OrderOperation {
    private static OrderOperation instance;
    private static final String FILE_PATH = "src/db/orders.txt";
    private static int orderCount = ReadOrderDB.readOrdersFromFile(FILE_PATH).size();

    private OrderOperation() {}

    public static OrderOperation getInstance() {
        if (instance == null) {
            instance = new OrderOperation();
        }
        return instance;
    }

    public boolean placeOrder(String userId, String proId) {
        String orderId = generateUniqueOrderId();
        String orderTime = getCurrentTime();

        Order order = new Order(orderId, userId, proId, orderTime);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            bw.write(order.toString());
            bw.newLine();
            return true;
        } catch (IOException e) {
            System.out.println("Failed to place order: " + e.getMessage());
            return false;
        }
    }

    public List<Order> getOrdersByUserId(String userId) {
        List<Order> orders = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(Paths.get(FILE_PATH));
            for (String line : lines) {
                if (line.contains("\"user_id\":\"" + userId + "\"")) {
                    orders.add(parseOrderFromJson(line));
                }
            }
        } catch (IOException e) {
            System.out.println("Failed to read orders: " + e.getMessage());
        }
        return orders;
    }

    public boolean deleteOrdersByUser(String userId) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(FILE_PATH));
            lines.removeIf(line -> line.contains("\"user_id\":\"" + userId + "\""));
            Files.write(Paths.get(FILE_PATH), lines);
            return true;
        } catch (IOException e) {
            System.out.println("Failed to delete orders: " + e.getMessage());
            return false;
        }
    }

    public void deleteAllOrders() {
        try {
            Files.write(Paths.get(FILE_PATH), new ArrayList<>()); // Clear file
        } catch (IOException e) {
            System.out.println("Failed to delete all orders: " + e.getMessage());
        }
    }

    private Order parseOrderFromJson(String json) {
        Map<String, String> map = new HashMap<>();
        Matcher m = Pattern.compile("\\\"(.*?)\\\":\\\"(.*?)\\\"").matcher(json);
        while (m.find()) {
            map.put(m.group(1), m.group(2));
        }
        return new Order(map.get("order_id"), map.get("user_id"),
                         map.get("pro_id"), map.get("order_time"));
    }

    private String generateUniqueOrderId() {
        return "o_" + String.format("%05d", ++orderCount);
    }

    private String getCurrentTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy_HH:mm:ss"));
    }
}
