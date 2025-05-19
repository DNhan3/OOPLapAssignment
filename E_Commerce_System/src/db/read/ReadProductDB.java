package db.read;
import model.Product;

import java.io.*;
import java.util.*;
import java.util.regex.*;

public class ReadProductDB {
    private static String filePath = "src/db/products.txt";

    public static List<Product> readProductsFromFile() {
        List<Product> products = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                while (!line.trim().endsWith("}")) {
                    line += br.readLine();
                }
                products.add(parseProductFromJson(line));
            }
        } catch (IOException e) {
            System.out.println("Failed to read product file: " + e.getMessage());
        }
        return products;
    }

    public static Product parseProductFromJson(String json) {
        Map<String, String> map = new HashMap<>();
        Matcher m = Pattern.compile("\\\"(.*?)\\\":(\\\".*?\\\"|\\d+\\.\\d+|\\d+)").matcher(json);
        while (m.find()) {
            String key = m.group(1);
            String rawValue = m.group(2);
            String value = rawValue.replaceAll("^\\\"|\\\"$", "");
            map.put(key, value);
        }

        return new Product(
            map.get("pro_id"),
            map.get("pro_model"),
            map.get("pro_category"),
            map.get("pro_name"),
            Double.parseDouble(map.get("pro_current_price")),
            Double.parseDouble(map.get("pro_raw_price")),
            Double.parseDouble(map.get("pro_discount")),
            Integer.parseInt(map.get("pro_likes_count"))
        );
    }
}
