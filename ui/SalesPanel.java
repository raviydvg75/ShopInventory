package ui;

import database.DBConnection;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SalesPanel extends JPanel {

    private String loggedInMerchant;
    private JTable salesTable;
    private DefaultTableModel tableModel;
    private JLabel totalSalesLabel;
    private JLabel totalCountLabel;

    // Color Palette matching Dashboard
    private final Color PRIMARY_BLUE = new Color(14, 82, 184);
    private final Color DARK_SLATE = new Color(30, 38, 50);
    private final Color BG_LIGHT = new Color(245, 247, 250);
    private final Color CARD_BG = Color.WHITE;

    public SalesPanel(String merchantName) {
        this.loggedInMerchant = merchantName;
        initUI();
        loadSalesDataFromDB();
    }

    public SalesPanel() {
        this(null);
    }

    private void initUI() {
        setLayout(new BorderLayout(0, 15));
        setBackground(BG_LIGHT);
        setBorder(new EmptyBorder(20, 30, 20, 30));

        // Header + Stats Panel
        JPanel topContainer = new JPanel(new BorderLayout(0, 15));
        topContainer.setBackground(BG_LIGHT);

        JLabel titleLabel = new JLabel("Sales & Transactions History");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titleLabel.setForeground(PRIMARY_BLUE);
        topContainer.add(titleLabel, BorderLayout.NORTH);

        JPanel statsPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        statsPanel.setBackground(BG_LIGHT);

        totalSalesLabel = new JLabel("NPR 0.00");
        totalCountLabel = new JLabel("0 Transactions");

        statsPanel.add(createSummaryCard("Total Revenue", totalSalesLabel, new Color(40, 167, 69)));
        statsPanel.add(createSummaryCard("Total Transactions Count", totalCountLabel, PRIMARY_BLUE));

        topContainer.add(statsPanel, BorderLayout.CENTER);
        add(topContainer, BorderLayout.NORTH);

        // Center Table Setup
        String[] columnNames = {"Transaction ID", "Merchant", "Date & Time", "Grand Total (NPR)"};

        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        salesTable = new JTable(tableModel);
        salesTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        salesTable.setRowHeight(35);
        salesTable.setGridColor(new Color(230, 235, 240));

        JTableHeader header = salesTable.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setBackground(DARK_SLATE);
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(0, 40));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < salesTable.getColumnCount(); i++) {
            salesTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane scrollPane = new JScrollPane(salesTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 225, 230), 1));
        scrollPane.getViewport().setBackground(Color.WHITE);

        add(scrollPane, BorderLayout.CENTER);

        // Refresh Button
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setBackground(BG_LIGHT);

        JButton refreshBtn = new JButton("Refresh Sales");
        refreshBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        refreshBtn.setBackground(PRIMARY_BLUE);
        refreshBtn.setForeground(Color.WHITE);
        refreshBtn.setFocusPainted(false);
        refreshBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        refreshBtn.setPreferredSize(new Dimension(140, 35));

        refreshBtn.addActionListener(e -> loadSalesDataFromDB());

        bottomPanel.add(refreshBtn);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private JPanel createSummaryCard(String title, JLabel valueLabel, Color accentColor) {
        JPanel card = new JPanel(new BorderLayout(5, 5));
        card.setBackground(CARD_BG);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 225, 230), 1),
                new EmptyBorder(15, 20, 15, 20)
        ));

        JLabel titleLbl = new JLabel(title);
        titleLbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
        titleLbl.setForeground(new Color(110, 120, 135));

        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        valueLabel.setForeground(accentColor);

        card.add(titleLbl, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);

        return card;
    }

    public void loadSalesDataFromDB() {
        tableModel.setRowCount(0);

        double totalRevenue = 0.0;
        int transactionCount = 0;

        String sql = "SELECT transaction_id, merchant_name, transaction_date, grand_total " +
                "FROM transactions ORDER BY transaction_date DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("transaction_id");
                String merchant = rs.getString("merchant_name");
                String date = rs.getString("transaction_date");
                double total = rs.getDouble("grand_total");

                tableModel.addRow(new Object[]{
                        "TXN-" + id,
                        merchant,
                        date,
                        String.format("%.2f", total)
                });

                totalRevenue += total;
                transactionCount++;
            }

            totalSalesLabel.setText(String.format("NPR %.2f", totalRevenue));
            totalCountLabel.setText(transactionCount + " Transactions");

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Failed to load transactions from database:\n" + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}