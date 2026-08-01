package model;

public class TransactionItem {

    private int itemId;
    private int transactionId;
    private String productName;
    private int quantity;
    private double unitPrice;
    private double total;

    // Default Constructor
    public TransactionItem() {
    }

    // Constructor without itemId (For creating new items before DB insert)
    public TransactionItem(int transactionId, String productName, int quantity, double unitPrice) {
        this.transactionId = transactionId;
        this.productName = productName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.total = quantity * unitPrice; // Auto-calculate total
    }

    // Full Constructor (For reading existing items from DB)
    public TransactionItem(int itemId, int transactionId, String productName, int quantity, double unitPrice, double total) {
        this.itemId = itemId;
        this.transactionId = transactionId;
        this.productName = productName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.total = total;
    }

    // Getters
    public int getItemId() { return itemId; }
    public int getTransactionId() { return transactionId; }
    public String getProductName() { return productName; }
    public int getQuantity() { return quantity; }
    public double getUnitPrice() { return unitPrice; }
    public double getTotal() { return total; }

    // Setters
    public void setItemId(int itemId) { this.itemId = itemId; }
    public void setTransactionId(int transactionId) { this.transactionId = transactionId; }
    public void setProductName(String productName) { this.productName = productName; }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        this.total = this.quantity * this.unitPrice; // Keep total updated
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
        this.total = this.quantity * this.unitPrice; // Keep total updated
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "TransactionItem{" +
                "itemId=" + itemId +
                ", transactionId=" + transactionId +
                ", productName='" + productName + '\'' +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                ", total=" + total +
                '}';
    }
}