package db.read;
import model.Order;

import java.io.*;
import java.util.*;
import java.util.regex.*;

public class ReadOrderDB {
    public static List<Order> readOrdersFromFile(String filePath) {
        List<Order> orders = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                while (!line.trim().endsWith("}")) {
                    line += br.readLine();
                }
                orders.add(parseOrderFromJson(line));
            }
        } catch (IOException e) {
            System.out.println("Failed to read order file: " + e.getMessage());
        }
        return orders;
    }

    private static Order parseOrderFromJson(String json) {
        Map<String, String> map = new HashMap<>();
        Matcher m = Pattern.compile("\\\"(.*?)\\\":\\\"(.*?)\\\"").matcher(json);
        while (m.find()) {
            map.put(m.group(1), m.group(2));
        }

        return new Order(
            map.get("order_id"),
            map.get("user_id"),
            map.get("pro_id"),
            map.get("order_time")
        );
    }
}
