package model;

import java.util.ArrayList;
import java.util.List;

public class Invoice {
    String title;
    Customer customer;
    List<LineItem> lineItems = new ArrayList<>();

    public Invoice(String title, Customer customer) {
        this.title = title;
        this.customer = customer;
    }

    public void addLineItem(LineItem item) {
        lineItems.add(item);
    }

    public double getTotalAmount() {
        return lineItems.stream().mapToDouble(LineItem::getTotal).sum();
    }

    public String generateInvoice() {
        StringBuilder sb = new StringBuilder();
        sb.append("Invoice: ").append(title).append("\\n");
        sb.append("Customer: ").append(customer.name).append("\\n");
        sb.append("Address: ").append(customer.address.street).append(", ")
                .append(customer.address.city).append(", ")
                .append(customer.address.state).append(" ")
                .append(customer.address.zip).append("\\n\\n");
        sb.append("Items:\\n");
        for (LineItem item : lineItems) {
            sb.append(item.product.name).append(" - ")
                    .append(item.quantity).append(" x ")
                    .append("$" + item.product.unitPrice).append(" = ")
                    .append("$" + item.getTotal()).append("\\n");
        }
        sb.append("\\nTotal Amount Due: $").append(getTotalAmount()).append("\\n");
        return sb.toString();
    }
}
