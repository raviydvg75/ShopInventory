package ui;

import database.DBConnection;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.imageio.ImageIO;

public class Profile extends JPanel {

    // Modern Color Palette
    private final Color COLOR_PRIMARY = new Color(24, 119, 242);       // Royal Blue
    private final Color COLOR_PRIMARY_HOVER = new Color(13, 90, 190);
    private final Color COLOR_DANGER = new Color(235, 59, 90);          // Soft Crimson
    private final Color COLOR_DANGER_HOVER = new Color(200, 35, 65);
    private final Color COLOR_BG_MAIN = new Color(245, 247, 250);        // Neutral Off-White
    private final Color COLOR_CARD_BG = Color.WHITE;
    private final Color COLOR_TEXT_DARK = new Color(33, 37, 41);
    private final Color COLOR_TEXT_MUTED = new Color(108, 117, 125);
    private final Color COLOR_BORDER = new Color(230, 235, 240);

    private JLabel imageLabel;
    private JButton uploadButton;
    private JButton changePasswordButton;
    private JButton logoutButton;

    // Dynamic Data Labels
    private JLabel shopNameValLabel;
    private JLabel ownerValLabel;
    private JLabel panValLabel;
    private JLabel locationValLabel;

    private static final int AVATAR_SIZE = 120; // Avatar dimension
    private final String currentMerchantName;

    // Constructor taking logged-in Merchant Name
    public Profile(String merchantName) {
        this.currentMerchantName = merchantName;

        setLayout(new GridBagLayout()); // Center card dynamically
        setBackground(COLOR_BG_MAIN);
        setBorder(new EmptyBorder(30, 30, 30, 30));

        add(createMainCard());

        // Load DB Data using merchant_name parameter
        loadUserDataFromDB(this.currentMerchantName);
    }

    // Default Constructor for testing
    public Profile() {
        this("Sample Merchant");
    }

    private JPanel createMainCard() {
        JPanel card = new RoundedPanel(16, COLOR_CARD_BG);
        card.setLayout(new BorderLayout(0, 20));
        card.setBorder(new EmptyBorder(30, 35, 35, 35));
        card.setPreferredSize(new Dimension(580, 480));

        // 1. Header
        card.add(createHeaderSection(), BorderLayout.NORTH);

        // 2. Content Body
        JPanel contentPanel = new JPanel(new BorderLayout(30, 0));
        contentPanel.setOpaque(false);
        contentPanel.add(createAvatarSection(), BorderLayout.WEST);
        contentPanel.add(createInfoSection(), BorderLayout.CENTER);

        card.add(contentPanel, BorderLayout.CENTER);

        // 3. Footer
        card.add(createFooterSection(), BorderLayout.SOUTH);

        return card;
    }

    private JPanel createHeaderSection() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        headerPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, COLOR_BORDER),
                new EmptyBorder(0, 0, 15, 0)
        ));

        JLabel title = new JLabel("Merchant Profile");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(COLOR_TEXT_DARK);

        JLabel statusBadge = new JLabel(" ACTIVE MERCHANT ");
        statusBadge.setFont(new Font("Segoe UI", Font.BOLD, 10));
        statusBadge.setForeground(new Color(40, 167, 69));
        statusBadge.setBackground(new Color(220, 245, 226));
        statusBadge.setOpaque(true);
        statusBadge.setBorder(new EmptyBorder(4, 8, 4, 8));

        headerPanel.add(title, BorderLayout.WEST);
        headerPanel.add(statusBadge, BorderLayout.EAST);

        return headerPanel;
    }

    private JPanel createAvatarSection() {
        JPanel avatarPanel = new JPanel();
        avatarPanel.setLayout(new BoxLayout(avatarPanel, BoxLayout.Y_AXIS));
        avatarPanel.setOpaque(false);

        imageLabel = new JLabel();
        loadDefaultAvatar();
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel imageWrapper = new JPanel(new GridBagLayout());
        imageWrapper.setOpaque(false);
        imageWrapper.setPreferredSize(new Dimension(130, 130));
        imageWrapper.setMaximumSize(new Dimension(130, 130));
        imageWrapper.setBorder(new LineBorder(COLOR_BORDER, 2, true));
        imageWrapper.add(imageLabel);

        uploadButton = createStyledButton("📷 Change Photo", COLOR_BG_MAIN, COLOR_TEXT_DARK, false);
        uploadButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        uploadButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        uploadButton.setMaximumSize(new Dimension(130, 32));
        uploadButton.addActionListener(e -> chooseAndUploadPhoto());

        avatarPanel.add(imageWrapper);
        avatarPanel.add(Box.createVerticalStrut(15));
        avatarPanel.add(uploadButton);

        return avatarPanel;
    }

    private void chooseAndUploadPhoto() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select Profile Picture");

        FileNameExtensionFilter imageFilter = new FileNameExtensionFilter(
                "Image Files (*.png, *.jpg, *.jpeg)", "png", "jpg", "jpeg"
        );
        fileChooser.setFileFilter(imageFilter);

        int userSelection = fileChooser.showOpenDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                BufferedImage originalImg = ImageIO.read(selectedFile);
                if (originalImg != null) {
                    BufferedImage circularImg = getCircularImage(originalImg, AVATAR_SIZE);
                    imageLabel.setIcon(new ImageIcon(circularImg));
                    imageLabel.setText("");
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Invalid image file.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Failed to load image: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private BufferedImage getCircularImage(BufferedImage source, int size) {
        BufferedImage circularImage = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = circularImage.createGraphics();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        g2.setClip(new Ellipse2D.Double(0, 0, size, size));

        int width = source.getWidth();
        int height = source.getHeight();
        int minDimension = Math.min(width, height);
        int cropX = (width - minDimension) / 2;
        int cropY = (height - minDimension) / 2;

        g2.drawImage(source, 0, 0, size, size, cropX, cropY, cropX + minDimension, cropY + minDimension, null);
        g2.dispose();

        return circularImage;
    }

    private void loadDefaultAvatar() {
        try {
            BufferedImage defaultImg = ImageIO.read(getClass().getResource("/images/profile.png"));
            imageLabel.setIcon(new ImageIcon(getCircularImage(defaultImg, AVATAR_SIZE)));
        } catch (Exception e) {
            imageLabel.setText("👤");
            imageLabel.setFont(new Font("Segoe UI", Font.PLAIN, 75));
            imageLabel.setPreferredSize(new Dimension(AVATAR_SIZE, AVATAR_SIZE));
            imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        }
    }

    private JPanel createInfoSection() {
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(4, 1, 0, 12));
        infoPanel.setOpaque(false);

        shopNameValLabel = new JLabel("Loading...");
        ownerValLabel = new JLabel("Loading...");
        panValLabel = new JLabel("Loading...");
        locationValLabel = new JLabel("Loading...");

        infoPanel.add(createInfoRow("Shop Name", shopNameValLabel));
        infoPanel.add(createInfoRow("Merchant Name", ownerValLabel));
        infoPanel.add(createInfoRow("PAN Number", panValLabel));
        infoPanel.add(createInfoRow("Location", locationValLabel));

        return infoPanel;
    }

    private JPanel createInfoRow(String labelText, JLabel valueLabel) {
        JPanel row = new JPanel(new BorderLayout());
        row.setOpaque(false);

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        label.setForeground(COLOR_TEXT_MUTED);

        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        valueLabel.setForeground(COLOR_TEXT_DARK);

        row.add(label, BorderLayout.NORTH);
        row.add(valueLabel, BorderLayout.SOUTH);

        return row;
    }

    /**
     * Queries database table 'user' using column 'merchant_name'
     */
    public void loadUserDataFromDB(String merchantName) {
        String sql = "SELECT shop_name, merchant_name, pan_number, location FROM users WHERE merchant_name = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, merchantName);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    shopNameValLabel.setText(rs.getString("shop_name") != null ? rs.getString("shop_name") : "N/A");
                    ownerValLabel.setText(rs.getString("merchant_name") != null ? rs.getString("merchant_name") : "N/A");
                    panValLabel.setText(rs.getString("pan_number") != null ? rs.getString("pan_number") : "N/A");
                    locationValLabel.setText(rs.getString("location") != null ? rs.getString("location") : "N/A");
                } else {
                    shopNameValLabel.setText("Merchant Not Found");
                    ownerValLabel.setText(merchantName);
                    panValLabel.setText("N/A");
                    locationValLabel.setText("N/A");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Database query error: " + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private JPanel createFooterSection() {
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        footerPanel.setOpaque(false);
        footerPanel.setBorder(new EmptyBorder(15, 0, 0, 0));

        changePasswordButton = createStyledButton("🔒 Change Password", COLOR_PRIMARY, Color.WHITE, true);
        changePasswordButton.setPreferredSize(new Dimension(160, 38));
        changePasswordButton.addActionListener(e -> openChangePasswordDialog());

        logoutButton = createStyledButton("🚪 Logout", COLOR_DANGER, Color.WHITE, true);
        logoutButton.setPreferredSize(new Dimension(110, 38));
        logoutButton.addActionListener(e -> handleLogout());

        footerPanel.add(changePasswordButton);
        footerPanel.add(logoutButton);

        return footerPanel;
    }

    private void openChangePasswordDialog() {
        JPasswordField oldPassField = new JPasswordField();
        JPasswordField newPassField = new JPasswordField();
        JPasswordField confirmPassField = new JPasswordField();

        Object[] message = {
                "Current Password:", oldPassField,
                "New Password:", newPassField,
                "Confirm New Password:", confirmPassField
        };

        int option = JOptionPane.showConfirmDialog(
                this, message, "Change Password", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE
        );

        if (option == JOptionPane.OK_OPTION) {
            String oldPass = new String(oldPassField.getPassword());
            String newPass = new String(newPassField.getPassword());
            String confirmPass = new String(confirmPassField.getPassword());

            if (newPass.isEmpty() || oldPass.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Fields cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!newPass.equals(confirmPass)) {
                JOptionPane.showMessageDialog(this, "New passwords do not match.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            updatePasswordInDB(oldPass, newPass);
        }
    }

    private void updatePasswordInDB(String oldPass, String newPass) {
        String verifySql = "SELECT password FROM user WHERE merchant_name = ?";
        String updateSql = "UPDATE user SET password = ? WHERE merchant_name = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement psVerify = conn.prepareStatement(verifySql)) {

            psVerify.setString(1, currentMerchantName);
            ResultSet rs = psVerify.executeQuery();

            if (rs.next()) {
                String dbPass = rs.getString("password");
                if (!dbPass.equals(oldPass)) {
                    JOptionPane.showMessageDialog(this, "Incorrect current password.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try (PreparedStatement psUpdate = conn.prepareStatement(updateSql)) {
                    psUpdate.setString(1, newPass);
                    psUpdate.setString(2, currentMerchantName);
                    psUpdate.executeUpdate();

                    JOptionPane.showMessageDialog(this, "Password updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleLogout() {
        int confirm = JOptionPane.showConfirmDialog(
                this, "Are you sure you want to logout?", "Confirm Logout", JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            Window window = SwingUtilities.getWindowAncestor(this);
            if (window != null) {
                window.dispose();
            }
        }
    }

    private JButton createStyledButton(String text, Color bg, Color fg, boolean isPrimary) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setBackground(bg);
        button.setForeground(fg);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        if (isPrimary) {
            button.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        } else {
            button.setBorder(BorderFactory.createLineBorder(COLOR_BORDER, 1));
        }

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (bg.equals(COLOR_PRIMARY)) button.setBackground(COLOR_PRIMARY_HOVER);
                else if (bg.equals(COLOR_DANGER)) button.setBackground(COLOR_DANGER_HOVER);
                else button.setBackground(new Color(230, 233, 238));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(bg);
            }
        });

        return button;
    }

    private static class RoundedPanel extends JPanel {
        private final int cornerRadius;
        private final Color backgroundColor;

        public RoundedPanel(int radius, Color bgColor) {
            this.cornerRadius = radius;
            this.backgroundColor = bgColor;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D graphics = (Graphics2D) g.create();
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            graphics.setColor(backgroundColor);
            graphics.fill(new RoundRectangle2D.Double(0, 0, getWidth() - 1, getHeight() - 1, cornerRadius, cornerRadius));
            graphics.setColor(new Color(230, 235, 240));
            graphics.draw(new RoundRectangle2D.Double(0, 0, getWidth() - 1, getHeight() - 1, cornerRadius, cornerRadius));
            graphics.dispose();
        }
    }
}