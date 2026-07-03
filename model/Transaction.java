package model;

public class Transaction {

    private int transactionId;
    private String merchantName;
    private String transactionDate;
    private double grandTotal;

    // Default Constructor
    public Transaction() {
    }

    // Parameterized Constructor
    public Transaction(String merchantName, String transactionDate, double grandTotal) {
        this.merchantName = merchantName;
        this.transactionDate = transactionDate;
        this.grandTotal = grandTotal;
    }

    // Getters
    public int getTransactionId() {
        return transactionId;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public double getGrandTotal() {
        return grandTotal;
    }

    // Setters
    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public void setGrandTotal(double grandTotal) {
        this.grandTotal = grandTotal;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId=" + transactionId +
                ", merchantName='" + merchantName + '\'' +
                ", transactionDate='" + transactionDate + '\'' +
                ", grandTotal=" + grandTotal +
                '}';
    }
}