package model;

public class LineItem {
    Product product;
    int quantity;

    public LineItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public double getTotal() {
        return quantity * product.unitPrice;
    }
}
