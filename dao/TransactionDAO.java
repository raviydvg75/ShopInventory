package dao;

import database.DBConnection;
import model.Transaction;
import model.TransactionItem;

import java.sql.*;
import java.util.List;

public class TransactionDAO {

    /**
     * Saves main Transaction header and returns generated transaction ID.
     */
    public int saveTransaction(Transaction transaction) {

        String sql = "INSERT INTO transactions (merchant_name, transaction_date, grand_total) VALUES (?, NOW(), ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Fallback to "Mero Pasal" if merchantName is null
            String merchantName = transaction.getMerchantName();
            if (merchantName == null || merchantName.trim().isEmpty()) {
                merchantName = "Mero Pasal";
            }

            ps.setString(1, merchantName);
            ps.setDouble(2, transaction.getGrandTotal());

            int rows = ps.executeUpdate();

            if (rows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1); // Auto-generated transaction_id
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    /**
     * Saves a single transaction line item.
     */
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

    /**
     * Batch saves all transaction items at once (faster and safer).
     */
    public boolean saveTransactionItemsBatch(int transactionId, List<TransactionItem> items) {

        String sql = "INSERT INTO transaction_items (transaction_id, product_name, quantity, unit_price, total) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            conn.setAutoCommit(false); // Enable manual transaction control

            for (TransactionItem item : items) {
                ps.setInt(1, transactionId);
                ps.setString(2, item.getProductName());
                ps.setInt(3, item.getQuantity());
                ps.setDouble(4, item.getUnitPrice());
                ps.setDouble(5, item.getTotal());
                ps.addBatch();
            }

            int[] results = ps.executeBatch();
            conn.commit(); // Commit all rows to MySQL

            return results.length > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}