package model;

public class User {

    private int id;
    private String shopName;
    private String merchantName;
    private String panNumber;
    private String location;
    private String password;

    // Default Constructor
    public User() {
    }

    // Parameterized Constructor
    public User(String shopName, String merchantName, String panNumber,
                String location, String password) {
        this.shopName = shopName;
        this.merchantName = merchantName;
        this.panNumber = panNumber;
        this.location = location;
        this.password = password;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getShopName() {
        return shopName;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public String getPanNumber() {
        return panNumber;
    }

    public String getLocation() {
        return location;
    }

    public String getPassword() {
        return password;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public void setPanNumber(String panNumber) {
        this.panNumber = panNumber;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "user{" +
                "id=" + id +
                ", shopName='" + shopName + '\'' +
                ", merchantName='" + merchantName + '\'' +
                ", panNumber='" + panNumber + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}