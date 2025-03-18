import model.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InvoiceGUI extends JFrame {
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
        UIManager.put("Panel.background", Color.DARK_GRAY);
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
        createInvoiceBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createInvoice();
            }
        });

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
        addItemBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addItem();
            }
        });

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
