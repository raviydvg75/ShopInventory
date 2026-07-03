package dao;

import database.DBConnection;
import model.Transaction;

import java.sql.*;

public class TransactionDAO {

    // Save Transaction and return generated transaction ID
    public int saveTransaction(Transaction transaction) {

        String sql = "INSERT INTO transactions (merchant_name, transaction_date, grand_total) VALUES (?, NOW(), ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, transaction.getMerchantName());
            ps.setDouble(2, transaction.getGrandTotal());

            int rows = ps.executeUpdate();

            if (rows > 0) {

                ResultSet rs = ps.getGeneratedKeys();

                if (rs.next()) {
                    return rs.getInt(1); // transaction_id
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }
    public boolean saveTransactionItem(int transactionId,
                                       String product,
                                       int quantity,
                                       double unitPrice,
                                       double total) {

        String sql = "INSERT INTO transaction_items (transaction_id, product_name, quantity, unit_price, total) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, transactionId);
            ps.setString(2, product);
            ps.setInt(3, quantity);
            ps.setDouble(4, unitPrice);
            ps.setDouble(5, total);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}