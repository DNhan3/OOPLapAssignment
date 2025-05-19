package operation;

import model.Product;

import java.io.*;
import java.nio.file.*;
import java.util.*;

import db.read.ReadProductDB;

public class ProductOperation {
    private static ProductOperation instance;
    private static final String FILE_PATH = "src/db/products.txt";
    private int productPage = 0;

    public int getProductPage() {
        return productPage;
    }

    private ProductOperation() {
        List<Product> products = new ArrayList<>();
        ReadProductDB.readProductsFromFile().forEach(products::add);

        productPage = (int) Math.ceil(products.size() / 10.0);
    }

    public static ProductOperation getInstance() {
        if (instance == null) {
            instance = new ProductOperation();
        }
        return instance;
    }

    public boolean addProduct(Product product) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            bw.write(product.toString());
            bw.newLine();
            return true;
        } catch (IOException e) {
            System.out.println("Failed to add product: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteProduct(String productId) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(FILE_PATH));
            lines.removeIf(line -> line.contains("\"pro_id\":\"" + productId + "\""));
            Files.write(Paths.get(FILE_PATH), lines);
            return true;
        } catch (IOException e) {
            System.out.println("Failed to delete product: " + e.getMessage());
            return false;
        }
    }

    public Product getProductById(String productId) {
        for (Product product : ReadProductDB.readProductsFromFile()) {
            if (product.getProId().equals(productId)) {
                return product;
            }
        }
        return null;
    }

    public ProductListResult getProductList(int pageNumber) {
        List<Product> products = new ArrayList<>();
        ReadProductDB.readProductsFromFile().forEach(products::add);

        int totalPages = (int) Math.ceil(products.size() / 10.0);
        productPage = totalPages;
        int start = (pageNumber - 1) * 10;
        int end = Math.min(start + 10, products.size());
        List<Product> page = start < products.size() ? products.subList(start, end) : new ArrayList<>();

        return new ProductListResult(page, pageNumber, totalPages);
    }


    public boolean updateProduct(Product updatedProduct) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(FILE_PATH));
            List<String> updated = new ArrayList<>();

            for (String line : lines) {
                if (line.contains("\"pro_id\":\"" + updatedProduct.getProId() + "\"")) {
                    updated.add(updatedProduct.toString());
                } else {
                    updated.add(line);
                }
            }

            Files.write(Paths.get(FILE_PATH), updated);
            return true;
        } catch (IOException e) {
            System.out.println("Failed to update product: " + e.getMessage());
            return false;
        }
    }

    public void deleteAllProducts() {
        try {
            Files.write(Paths.get(FILE_PATH), new ArrayList<>()); // Clear file
        } catch (IOException e) {
            System.out.println("Failed to delete all products: " + e.getMessage());
        }
    }
}