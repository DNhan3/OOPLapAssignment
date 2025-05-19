package operation;

import java.util.List;
import model.Product;

public class ProductListResult {
    public List<Product> productList;
    public int currentPage;
    public int totalPages;

    public ProductListResult(List<Product> list, int page, int total) {
        this.productList = list;
        this.currentPage = page;
        this.totalPages = total;
    }

    public void display() {
        System.out.println("Current Page: " + currentPage);
        System.out.println("Total Pages: " + totalPages);
        int cnt = 1;
        for (Product product : productList) {
            System.out.print(cnt++ + ". ");
            System.out.println(product);
        }
    }
}
