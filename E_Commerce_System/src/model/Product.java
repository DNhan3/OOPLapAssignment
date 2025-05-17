package model;

public class Product {
    private String proId;
    private String proModel;
    private String proCategory;
    private String proName;
    private double proCurrentPrice;
    private double proRawPrice;
    private double proDiscount;
    private int proLikesCount;

    public Product(String proId, String proModel, String proCategory,
            String proName, double proCurrentPrice, double proRawPrice,
            double proDiscount, int proLikesCount) {
        this.proId = proId;
        this.proModel = proModel;
        this.proCategory = proCategory;
        this.proName = proName;
        this.proCurrentPrice = proCurrentPrice;
        this.proRawPrice = proRawPrice;
        this.proDiscount = proDiscount;
        this.proLikesCount = proLikesCount;
    }

    public Product() {
        this("p_00001", "DefaultModel", "DefaultCategory", "DefaultProduct",
                99.99, 199.99, 50.0, 0);
    }

    @Override
    public String toString() {
        return "{\"pro_id\":\""         + proId + "\", " +
            "\"pro_model\":\""          + proModel + "\", " +
            "\"pro_category\":\""       + proCategory + "\", " +
            "\"pro_name\":\""           + proName + "\", " +
            "\"pro_current_price\":\""  + String.format("%.2f", proCurrentPrice) + "\", " +
            "\"pro_raw_price\":\""      + String.format("%.2f", proRawPrice) + "\", " +
            "\"pro_discount\":\""       + String.format("%.2f", proDiscount) + "\", " +
            "\"pro_likes_count\":\""    + proLikesCount + "\"}";
    }
}