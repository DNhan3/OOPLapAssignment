package operation;

import java.util.List;

import model.Customer;

public class CustomerListResult {
    public List<Customer> customerList;
    public int currentPage;
    public int totalPages;

    public CustomerListResult(List<Customer> list, int page, int total) {
        this.customerList = list;
        this.currentPage = page;
        this.totalPages = total;
    }
}