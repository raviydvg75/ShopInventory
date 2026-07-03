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

    // Parameterized Constructor
    public TransactionItem(int transactionId, String productName,
                           int quantity, double unitPrice, double total) {

        this.transactionId = transactionId;
        this.productName = productName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.total = total;
    }

    // Getters
    public int getItemId() {
        return itemId;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public String getProductName() {
        return productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public double getTotal() {
        return total;
    }

    // Setters
    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
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