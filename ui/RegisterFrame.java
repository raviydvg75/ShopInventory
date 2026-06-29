package ui;

import dao.UserDAO;
import model.User;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class RegisterFrame extends JFrame {

    private JTextField shopNameField;
    private JTextField merchantNameField;
    private JTextField panField;
    private JTextField locationField;

    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;

    private JButton registerButton;

    public RegisterFrame() {

        Color nepalRed = new Color(220, 20, 60);
        Color nepalBlue = new Color(0, 56, 147);
        Color lightBg = new Color(245, 247, 250);

        setTitle("Mero Pasal - Register");
        setSize(700, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel(null);
        panel.setBackground(lightBg);

        JPanel header = new JPanel(null);
        header.setBackground(nepalBlue);
        header.setBounds(0, 0, 700, 120);

        JLabel title = new JLabel("MERO PASAL");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setBounds(100, 15, 500, 40);

        JLabel subtitle = new JLabel("Merchant Registration");
        subtitle.setForeground(Color.WHITE);
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitle.setHorizontalAlignment(JLabel.CENTER);
        subtitle.setBounds(100, 55, 500, 30);

        header.add(title);
        header.add(subtitle);

        JPanel accentLine = new JPanel();
        accentLine.setBackground(nepalRed);
        accentLine.setBounds(0, 120, 700, 5);

        Border border = BorderFactory.createLineBorder(nepalBlue, 2);

        JLabel lblShop = new JLabel("Shop Name");
        lblShop.setForeground(nepalBlue);
        lblShop.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblShop.setBounds(100, 150, 150, 25);

        shopNameField = new JTextField();
        shopNameField.setBounds(100, 180, 500, 40);
        shopNameField.setBorder(border);

        JLabel lblMerchant = new JLabel("Merchant Name");
        lblMerchant.setForeground(nepalBlue);
        lblMerchant.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblMerchant.setBounds(100, 240, 150, 25);

        merchantNameField = new JTextField();
        merchantNameField.setBounds(100, 270, 500, 40);
        merchantNameField.setBorder(border);

        JLabel lblPan = new JLabel("PAN Number");
        lblPan.setForeground(nepalBlue);
        lblPan.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblPan.setBounds(100, 330, 150, 25);

        panField = new JTextField();
        panField.setBounds(100, 360, 500, 40);
        panField.setBorder(border);

        JLabel lblLocation = new JLabel("Location");
        lblLocation.setForeground(nepalBlue);
        lblLocation.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblLocation.setBounds(100, 420, 150, 25);

        locationField = new JTextField();
        locationField.setBounds(100, 450, 500, 40);
        locationField.setBorder(border);

        JLabel lblPassword = new JLabel("Password");
        lblPassword.setForeground(nepalBlue);
        lblPassword.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblPassword.setBounds(100, 510, 150, 25);

        passwordField = new JPasswordField();
        passwordField.setBounds(100, 540, 240, 40);
        passwordField.setBorder(border);

        JLabel lblConfirm = new JLabel("Confirm Password");
        lblConfirm.setForeground(nepalBlue);
        lblConfirm.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblConfirm.setBounds(360, 510, 150, 25);

        confirmPasswordField = new JPasswordField();
        confirmPasswordField.setBounds(360, 540, 240, 40);
        confirmPasswordField.setBorder(border);

        registerButton = new JButton("Register");
        registerButton.setBounds(200, 620, 300, 45);
        registerButton.setBackground(nepalRed);
        registerButton.setForeground(Color.WHITE);
        registerButton.setFont(new Font("Segoe UI", Font.BOLD, 15));
        registerButton.setFocusPainted(false);
        registerButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        registerButton.addActionListener(e -> {

            String shop = shopNameField.getText().trim();
            String merchant = merchantNameField.getText().trim();
            String pan = panField.getText().trim();
            String loc = locationField.getText().trim();
            String pass = new String(passwordField.getPassword());
            String confirm = new String(confirmPasswordField.getPassword());

            // Check for empty fields
            if (shop.isEmpty() || merchant.isEmpty() || pan.isEmpty()
                    || loc.isEmpty() || pass.isEmpty() || confirm.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please fill all the fields.",
                        "Registration Error",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            // Check password confirmation
            if (!pass.equals(confirm)) {

                JOptionPane.showMessageDialog(
                        this,
                        "Passwords do not match.",
                        "Registration Error",
                        JOptionPane.ERROR_MESSAGE
                );

                passwordField.setText("");
                confirmPasswordField.setText("");
                return;
            }

            // Password length validation
            if (pass.length() < 6) {

                JOptionPane.showMessageDialog(
                        this,
                        "Password must be at least 6 characters long.",
                        "Registration Error",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            // Create User object
            User user = new User();
            user.setShopName(shop);
            user.setMerchantName(merchant);
            user.setPanNumber(pan);
            user.setLocation(loc);
            user.setPassword(pass);

            // Save user
            UserDAO dao = new UserDAO();

            if (dao.registerUser(user)) {

                JOptionPane.showMessageDialog(
                        this,
                        "Registration Successful!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );

                dispose();
                new LoginFrame();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Registration Failed!\nMerchant Name or PAN Number may already exist.",
                        "Database Error",
                        JOptionPane.ERROR_MESSAGE
                );

                passwordField.setText("");
                confirmPasswordField.setText("");
            }

        });
        panel.add(header);
        panel.add(accentLine);

        panel.add(lblShop);
        panel.add(shopNameField);

        panel.add(lblMerchant);
        panel.add(merchantNameField);

        panel.add(lblPan);
        panel.add(panField);

        panel.add(lblLocation);
        panel.add(locationField);

        panel.add(lblPassword);
        panel.add(passwordField);

        panel.add(lblConfirm);
        panel.add(confirmPasswordField);

        panel.add(registerButton);

        add(panel);
        setVisible(true);
    }

}

