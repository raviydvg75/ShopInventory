package ui;

import dao.TransactionDAO;
import model.Transaction;
import model.TransactionItem;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TransactionPanel extends JPanel {

    private final Color BLUE = new Color(0, 56, 147);
    private final Color RED = new Color(220, 20, 60);

    private JComboBox<String> categoryBox;
    private JComboBox<String> productBox;
    private JSpinner qtySpinner;
    private JTextField priceField;

    private JButton addButton;
    private JButton removeButton;
    private JButton clearButton;
    private JButton receiptButton;

    private JTable table;
    private DefaultTableModel model;

    private JLabel totalLabel;
    private double grandTotal = 0;

    public TransactionPanel() {

        setLayout(new BorderLayout(20, 20));
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(20, 20, 20, 20));

        createTitle();
        createBody();
        registerEvents();
    }

    // ==========================
    // Title
    // ==========================
    private void createTitle() {

        JLabel title = new JLabel("ADD TRANSACTION", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 30));
        title.setForeground(BLUE);

        add(title, BorderLayout.NORTH);
    }

    // ==========================
    // Main Body
    // ==========================
    private void createBody() {

        JPanel body = new JPanel(new BorderLayout(20, 20));
        body.setBackground(Color.WHITE);

        body.add(createInputPanel(), BorderLayout.NORTH);
        body.add(createTablePanel(), BorderLayout.CENTER);
        body.add(createBottomPanel(), BorderLayout.SOUTH);

        add(body, BorderLayout.CENTER);
    }

    private void loadProducts() {

        productBox.removeAllItems();

        if (categoryBox.getSelectedItem() == null) {
            return;
        }

        String category = categoryBox.getSelectedItem().toString();

        switch (category) {

            case "Beverages":
                productBox.addItem("Coca Cola");
                productBox.addItem("Pepsi");
                productBox.addItem("Sprite");
                productBox.addItem("Fanta");
                break;

            case "Snacks":
                productBox.addItem("Lays");
                productBox.addItem("Kurkure");
                productBox.addItem("Biscuits");
                break;

            case "Bakery":
                productBox.addItem("Bread");
                productBox.addItem("Cake");
                productBox.addItem("Bun");
                break;

            case "Dairy":
                productBox.addItem("Milk");
                productBox.addItem("Butter");
                productBox.addItem("Cheese");
                break;
        }
    }

    private void registerEvents() {

        // Add Item
        addButton.addActionListener(e -> addItem());

        // Remove Selected Item
        removeButton.addActionListener(e -> removeSelectedItem());

        // Clear Bill
        clearButton.addActionListener(e -> clearBill());

        // Generate Receipt & Complete Transaction
        receiptButton.addActionListener(e -> completeTransaction());

        // Change products when category changes
        categoryBox.addActionListener(e -> loadProducts());
    }

    private void completeTransaction() {

        if (model.getRowCount() == 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "No items added.",
                    "Empty Bill",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        Transaction transaction = new Transaction();
        transaction.setGrandTotal(grandTotal);

        TransactionDAO dao = new TransactionDAO();
        int transactionId = dao.saveTransaction(transaction);

        if (transactionId == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Failed to save transaction to database.",
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        for (int i = 0; i < model.getRowCount(); i++) {

            TransactionItem item = new TransactionItem();

            item.setTransactionId(transactionId);
            item.setProductName(model.getValueAt(i, 0).toString());
            item.setQuantity(Integer.parseInt(model.getValueAt(i, 1).toString()));
            item.setUnitPrice(Double.parseDouble(model.getValueAt(i, 2).toString()));
            item.setTotal(Double.parseDouble(model.getValueAt(i, 3).toString()));

            dao.saveTransactionItem(
                    item.getTransactionId(),
                    item.getProductName(),
                    item.getQuantity(),
                    item.getUnitPrice(),
                    item.getTotal()
            );
        }

        generateReceipt();
        clearBill();

        JOptionPane.showMessageDialog(
                this,
                "Transaction #" + transactionId + " saved successfully!"
        );
    }

    private JPanel createInputPanel() {

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(new TitledBorder("Transaction Details"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Category
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Category"), gbc);

        gbc.gridx = 1;
        categoryBox = new JComboBox<>();
        categoryBox.addItem("Beverages");
        categoryBox.addItem("Snacks");
        categoryBox.addItem("Bakery");
        categoryBox.addItem("Dairy");

        categoryBox.setPreferredSize(new Dimension(180, 35));
        panel.add(categoryBox, gbc);

        // Product
        gbc.gridx = 2;
        panel.add(new JLabel("Product"), gbc);

        gbc.gridx = 3;
        productBox = new JComboBox<>();
        productBox.setPreferredSize(new Dimension(180, 35));
        panel.add(productBox, gbc);
        loadProducts();

        // Quantity
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Quantity"), gbc);

        gbc.gridx = 1;
        qtySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 1000, 1));
        qtySpinner.setPreferredSize(new Dimension(180, 35));
        panel.add(qtySpinner, gbc);

        // Price
        gbc.gridx = 2;
        panel.add(new JLabel("Price"), gbc);

        gbc.gridx = 3;
        priceField = new JTextField("100");
        priceField.setPreferredSize(new Dimension(180, 35));
        panel.add(priceField, gbc);

        // Buttons
        JPanel buttons = new JPanel(new FlowLayout());
        buttons.setBackground(Color.WHITE);

        addButton = new JButton("Add Item");
        removeButton = new JButton("Remove Selected");

        addButton.setPreferredSize(new Dimension(170, 40));
        removeButton.setPreferredSize(new Dimension(170, 40));

        addButton.setBackground(BLUE);
        addButton.setForeground(Color.WHITE);

        removeButton.setBackground(RED);
        removeButton.setForeground(Color.WHITE);

        buttons.add(addButton);
        buttons.add(removeButton);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 4;

        panel.add(buttons, gbc);

        return panel;
    }

    private JScrollPane createTablePanel() {

        String[] columns = {
                "Product",
                "Quantity",
                "Unit Price",
                "Total"
        };

        model = new DefaultTableModel(columns, 0);

        table = new JTable(model);

        table.setRowHeight(35);
        table.setFont(new Font("Arial", Font.PLAIN, 15));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 15));
        table.getTableHeader().setReorderingAllowed(false);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(new TitledBorder("Current Bill"));

        return scrollPane;
    }

    private void calculateGrandTotal() {

        grandTotal = 0.0;

        for (int i = 0; i < model.getRowCount(); i++) {
            double total = Double.parseDouble(model.getValueAt(i, 3).toString());
            grandTotal += total;
        }

        totalLabel.setText(String.format("Grand Total : Rs. %.2f", grandTotal));
    }

    private void addItem() {

        if (productBox.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Please select a product.");
            return;
        }

        try {
            String product = productBox.getSelectedItem().toString();
            int qty = (Integer) qtySpinner.getValue();
            double price = Double.parseDouble(priceField.getText().trim());

            if (price <= 0) {
                JOptionPane.showMessageDialog(this, "Price must be greater than 0.");
                return;
            }

            double total = qty * price;

            model.addRow(new Object[]{
                    product,
                    qty,
                    price,
                    total
            });

            calculateGrandTotal();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Invalid price input. Please enter a valid number.",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void removeSelectedItem() {

        int selectedRow = table.getSelectedRow();

        if (selectedRow != -1) {
            model.removeRow(selectedRow);
            calculateGrandTotal();
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select a row from the table to remove.",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }

    private void clearBill() {

        model.setRowCount(0);

        grandTotal = 0.0;
        totalLabel.setText("Grand Total : Rs. 0.00");

        qtySpinner.setValue(1);
        priceField.setText("100");

        categoryBox.setSelectedIndex(0);
        loadProducts();

        if (productBox.getItemCount() > 0) {
            productBox.setSelectedIndex(0);
        }
    }

    private void generateReceipt() {

        if (model.getRowCount() == 0) {
            return;
        }

        StringBuilder receipt = new StringBuilder();

        receipt.append("====================================\n");
        receipt.append("          MERO PASAL\n");
        receipt.append("====================================\n\n");

        receipt.append(String.format("%-15s %-5s %-10s %-10s\n",
                "Product", "Qty", "Price", "Total"));

        receipt.append("------------------------------------\n");

        for (int i = 0; i < model.getRowCount(); i++) {

            String product = model.getValueAt(i, 0).toString();
            int qty = Integer.parseInt(model.getValueAt(i, 1).toString());
            double price = Double.parseDouble(model.getValueAt(i, 2).toString());
            double total = Double.parseDouble(model.getValueAt(i, 3).toString());

            receipt.append(String.format(
                    "%-15s %-5d %-10.2f %-10.2f\n",
                    product,
                    qty,
                    price,
                    total
            ));
        }

        receipt.append("------------------------------------\n");
        receipt.append(String.format("Grand Total : Rs. %.2f\n", grandTotal));
        receipt.append("------------------------------------\n");
        receipt.append("Thank you for shopping!\n");

        JTextArea area = new JTextArea(receipt.toString());
        area.setEditable(false);
        area.setFont(new Font("Monospaced", Font.PLAIN, 14));

        JOptionPane.showMessageDialog(
                this,
                new JScrollPane(area),
                "Receipt",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private JPanel createBottomPanel() {

        JPanel panel = new JPanel(new BorderLayout(20, 20));
        panel.setBackground(Color.WHITE);

        totalLabel = new JLabel("Grand Total : Rs. 0.00");
        totalLabel.setFont(new Font("Arial", Font.BOLD, 28));
        totalLabel.setForeground(RED);

        panel.add(totalLabel, BorderLayout.WEST);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(Color.WHITE);

        clearButton = new JButton("Clear Bill");
        receiptButton = new JButton("Generate Receipt");

        clearButton.setPreferredSize(new Dimension(160, 40));
        receiptButton.setPreferredSize(new Dimension(190, 40));

        clearButton.setBackground(Color.GRAY);
        clearButton.setForeground(Color.WHITE);

        receiptButton.setBackground(BLUE);
        receiptButton.setForeground(Color.WHITE);

        buttonPanel.add(clearButton);
        buttonPanel.add(receiptButton);

        panel.add(buttonPanel, BorderLayout.EAST);

        return panel;
    }
}