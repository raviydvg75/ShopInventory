package dao;

import database.DBConnection;
import model.User;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO{

    // Register User
    public boolean registerUser(User user) {

        String sql = "INSERT INTO users (shop_name, merchant_name, pan_number, location, password) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user.getShopName());
            ps.setString(2, user.getMerchantName());
            ps.setString(3, user.getPanNumber());
            ps.setString(4, user.getLocation());

            String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());

            ps.setString(5, hashedPassword);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Registration Error: " + e.getMessage());
            return false;
        }
    }

    // Login User
    public boolean loginUser(String merchantName, String password) {

        String sql = "SELECT * FROM users WHERE merchant_name = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, merchantName);

            ps.setString(1, merchantName);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    String storedHash = rs.getString("password");

                    return BCrypt.checkpw(password, storedHash);

                }

                return false;
            }


        } catch (SQLException e) {
            System.out.println("Login Error: " + e.getMessage());
            return false;
        }
    }
}