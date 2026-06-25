package ui;

import javax.swing.*;
import java.awt.*;

public class RegisterFrame extends JFrame {
    private static String shopName;
    private static String merchantName;
    private static String panNumber;
    private static String location;
    private static String password;


    private JTextField shopNameField;
    private JTextField merchantNameField;
    private JTextField panField;
    private JTextField locationField;

    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;

    private JButton registerButton;

    public static String getShopName() {
        return shopName;
    }

    public static String getMerchantName() {
        return merchantName;
    }

    public static String getPanNumber() {
        return panNumber;
    }


    public static String getPassword() {
        return password;
    }

    public RegisterFrame() {

        setTitle("Mero Pasal - Register");
        setSize(650, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(35, 35, 35));

        // Heading
        JLabel heading = new JLabel("Let's make shopping digital and secure!");
        heading.setForeground(Color.WHITE);
        heading.setFont(new Font("Arial", Font.BOLD, 22));
        heading.setBounds(90, 30, 500, 30);

        // Shop Name
        JLabel lblShop = new JLabel("Shop Name");
        lblShop.setForeground(Color.WHITE);
        lblShop.setBounds(70, 100, 150, 25);

        shopNameField = new JTextField();
        shopNameField.setBounds(70, 130, 500, 35);

        // Merchant Name
        JLabel lblMerchant = new JLabel("Merchant Name");
        lblMerchant.setForeground(Color.WHITE);
        lblMerchant.setBounds(70, 180, 150, 25);

        merchantNameField = new JTextField();
        merchantNameField.setBounds(70, 210, 500, 35);

        // PAN Number
        JLabel lblPan = new JLabel("PAN Number");
        lblPan.setForeground(Color.WHITE);
        lblPan.setBounds(70, 260, 150, 25);

        panField = new JTextField();
        panField.setBounds(70, 290, 500, 35);

        // Location
        JLabel lblLocation = new JLabel("Location");
        lblLocation.setForeground(Color.WHITE);
        lblLocation.setBounds(70, 340, 150, 25);

        locationField = new JTextField();
        locationField.setBounds(70, 370, 500, 35);

        // Password
        JLabel lblPassword = new JLabel("Password");
        lblPassword.setForeground(Color.WHITE);
        lblPassword.setBounds(70, 420, 150, 25);

        passwordField = new JPasswordField();
        passwordField.setBounds(70, 450, 240, 35);

        // Confirm Password
        JLabel lblConfirmPass = new JLabel("Confirm Password");
        lblConfirmPass.setForeground(Color.WHITE);
        lblConfirmPass.setBounds(330, 420, 150, 25);

        confirmPasswordField = new JPasswordField();
        confirmPasswordField.setBounds(330, 450, 240, 35);

        // Register Button
        registerButton = new JButton("Register");
        registerButton.setBounds(220, 530, 180, 45);
        registerButton.addActionListener(e -> {

            String shop = shopNameField.getText().trim();
            String merchant = merchantNameField.getText().trim();
            String pan = panField.getText().trim();
            String loc = locationField.getText().trim();
            String pass = String.valueOf(passwordField.getPassword());
            String confirm = String.valueOf(confirmPasswordField.getPassword());

            // Check empty fields
            if (shop.isEmpty() || merchant.isEmpty() || pan.isEmpty()
                    || loc.isEmpty() || pass.isEmpty() || confirm.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please fill all fields.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            // Check password match
            if (!pass.equals(confirm)) {

                JOptionPane.showMessageDialog(
                        this,
                        "Passwords do not match.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            // Store data in class variables
            shopName = shop;
            merchantName = merchant;
            panNumber = pan;
            location = loc;
            password = pass;

            JOptionPane.showMessageDialog(
                    this,
                    "Registration Successful!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

        });


        panel.add(heading);

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

        panel.add(lblConfirmPass);
        panel.add(confirmPasswordField);

        panel.add(registerButton);

        add(panel);

        setVisible(true);
    }

}