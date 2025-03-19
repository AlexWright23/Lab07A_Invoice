//main


//main


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

// product class
class Product {
    String name;
    double unitPrice;

    public Product(String name, double unitPrice) {
        this.name = name;
        this.unitPrice = unitPrice;
    }
}

// LineItem class
class LineItem {
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

// address class
class Address {
    String street, city, state, zip;

    public Address(String street, String city, String state, String zip) {
        this.street = street;
        this.city = city;
        this.state = state;
        this.zip = zip;
    }
}

// customer class
class Customer {
    String name;
    Address address;

    public Customer(String name, Address address) {
        this.name = name;
        this.address = address;
    }
}

// invoice class
class Invoice {
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
        sb.append("Invoice: ").append(title).append("\n");
        sb.append("Customer: ").append(customer.name).append("\n");
        sb.append("Address: ").append(customer.address.street).append(", ")
                .append(customer.address.city).append(", ")
                .append(customer.address.state).append(" ")
                .append(customer.address.zip).append("\n\n");
        sb.append("Items:\n");
        for (LineItem item : lineItems) {
            sb.append(item.product.name).append(" - ")
                    .append(item.quantity).append(" x ")
                    .append("$" + item.product.unitPrice).append(" = ")
                    .append("$" + item.getTotal()).append("\n");
        }
        sb.append("\nTotal Amount Due: $").append(getTotalAmount()).append("\n");
        return sb.toString();
    }
}

// GUI application
public class InvoiceApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new InvoiceGUI());
    }
}

class InvoiceGUI extends JFrame {
    private JTextArea outputArea;
    private JTextField nameField, streetField, cityField, stateField, zipField;
    private JTextField productField, priceField, quantityField;
    private Invoice invoice;

    public InvoiceGUI() {
        setTitle("Invoice Application");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        getContentPane().setBackground(Color.DARK_GRAY);
        UIManager.put("Button.background", Color.LIGHT_GRAY);
        UIManager.put("Button.foreground", Color.BLACK);
        UIManager.put("Label.foreground", Color.WHITE);
        UIManager.put("TextField.background", Color.LIGHT_GRAY);
        UIManager.put("TextField.foreground", Color.BLACK);
        UIManager.put("TextArea.background", Color.LIGHT_GRAY);
        UIManager.put("TextArea.foreground", Color.BLACK);

        add(new JLabel("Customer Name:"));
        nameField = new JTextField(20);
        add(nameField);

        add(new JLabel("Street:"));
        streetField = new JTextField(20);
        add(streetField);

        add(new JLabel("City:"));
        cityField = new JTextField(10);
        add(cityField);

        add(new JLabel("State:"));
        stateField = new JTextField(5);
        add(stateField);

        add(new JLabel("ZIP:"));
        zipField = new JTextField(5);
        add(zipField);

        JButton createInvoiceBtn = new JButton("Create Invoice");
        createInvoiceBtn.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        add(createInvoiceBtn);
        createInvoiceBtn.addActionListener(e -> createInvoice());

        add(new JLabel("Product Name:"));
        productField = new JTextField(15);
        add(productField);

        add(new JLabel("Unit Price:"));
        priceField = new JTextField(5);
        add(priceField);

        add(new JLabel("Quantity:"));
        quantityField = new JTextField(5);
        add(quantityField);

        JButton addItemBtn = new JButton("Add Item");
        addItemBtn.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        add(addItemBtn);
        addItemBtn.addActionListener(e -> addItem());

        outputArea = new JTextArea(10, 40);
        add(new JScrollPane(outputArea));

        setVisible(true);
    }

    private void createInvoice() {
        String name = nameField.getText();
        Address address = new Address(streetField.getText(), cityField.getText(),
                stateField.getText(), zipField.getText());
        Customer customer = new Customer(name, address);
        invoice = new Invoice("Invoice", customer);
        outputArea.setText("Invoice created for " + name);
    }

    private void addItem() {
        if (invoice == null) {
            outputArea.setText("Create an invoice first!");
            return;
        }
        String productName = productField.getText();
        double price = Double.parseDouble(priceField.getText());
        int quantity = Integer.parseInt(quantityField.getText());
        Product product = new Product(productName, price);
        invoice.addLineItem(new LineItem(product, quantity));
        outputArea.setText(invoice.generateInvoice());
    }
}
