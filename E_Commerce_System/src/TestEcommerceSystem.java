import model.*;

public class TestEcommerceSystem {
    public static void main(String[] args) {
        // Test User (abstract class, so we use Customer and Admin)
        Customer customer = new Customer("u_1234567890", "john_doe", "securePass",
                "17-05-2025_12:00:00", "customer", "john@example.com", "0412345678");
        System.out.println("Customer: " + customer);

        Admin admin = new Admin("u_0987654321", "admin_user", "adminPass",
                "17-05-2025_13:00:00", "admin");
        System.out.println("Admin: " + admin);

        // Test Product
        Product product = new Product("p_00123", "X100", "Electronics", "Smartphone",
                499.99, 599.99, 20.0, 150);
        System.out.println("Product: " + product);

        // Test Order
        Order order = new Order("o_54321", customer.getUserId(), product.toString(),
                "17-05-2025_14:30:00");
        System.out.println("Order: " + order);
    }
}
