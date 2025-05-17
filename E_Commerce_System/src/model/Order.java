package model;

public class Order {
    private String orderId;
    private String userId;
    private String proId;
    private String orderTime;

    public Order(String orderId, String userId, String proId, String orderTime) {
        this.orderId = orderId;
        this.userId = userId;
        this.proId = proId;
        this.orderTime = orderTime;
    }

    public Order() {
        this("o_00001", "u_0000000000", "p_00001",
             java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy_HH:mm:ss")));
    }

    @Override
    public String toString() {
        return "{\"order_id\":\""   + orderId + 
            "\", \"user_id\":\""    + userId + 
            "\", \"pro_id\":\""     + proId + 
            "\", \"order_time\":\"" + orderTime + 
            "\"}";
    }
}
