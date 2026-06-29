package ui;
import dao.UserDAO;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class LoginFrame extends JFrame {

    private JTextField merchantNameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;

    public LoginFrame() {

        Color nepalRed = new Color(220, 20, 60);
        Color nepalBlue = new Color(0, 56, 147);
        Color lightBg = new Color(245, 247, 250);

        setTitle("Mero Pasal - Merchant Login");
        setSize(600, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel(null);
        panel.setBackground(lightBg);

        JPanel header = new JPanel(null);
        header.setBackground(nepalBlue);
        header.setBounds(0, 0, 600, 120);

        ImageIcon icon = new ImageIcon("src/resources/meropasal1.png");
        Image img = icon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);

        JLabel logoLabel = new JLabel(new ImageIcon(img));
        logoLabel.setBounds(20, 20, 80, 80);

        JLabel headerText = new JLabel("MERO PASAL");
        headerText.setForeground(Color.WHITE);
        headerText.setFont(new Font("Segoe UI", Font.BOLD, 28));
        headerText.setHorizontalAlignment(JLabel.CENTER);
        headerText.setBounds(100, 15, 450, 40);

        JLabel subHeader = new JLabel("Merchant Login");
        subHeader.setForeground(Color.WHITE);
        subHeader.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subHeader.setHorizontalAlignment(JLabel.CENTER);
        subHeader.setBounds(100, 55, 450, 30);

        header.add(logoLabel);
        header.add(headerText);
        header.add(subHeader);

        JPanel accentLine = new JPanel();
        accentLine.setBackground(nepalRed);
        accentLine.setBounds(0, 120, 600, 5);

        Border border = BorderFactory.createLineBorder(nepalBlue, 2);

        JLabel merchantLabel = new JLabel("Merchant Name");
        merchantLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        merchantLabel.setForeground(nepalBlue);
        merchantLabel.setBounds(100, 155, 150, 25);

        merchantNameField = new JTextField();
        merchantNameField.setBounds(100, 185, 400, 40);
        merchantNameField.setBorder(border);
        merchantNameField.setToolTipText("Enter Merchant Name");

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        passwordLabel.setForeground(nepalBlue);
        passwordLabel.setBounds(100, 245, 150, 25);

        passwordField = new JPasswordField();
        passwordField.setBounds(100, 275, 400, 40);
        passwordField.setBorder(border);
        passwordField.setToolTipText("Enter Password");

        loginButton = new JButton("Login");
        loginButton.setBounds(100, 345, 400, 45);
        loginButton.setBackground(nepalRed);
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 15));
        loginButton.setFocusPainted(false);
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel registerLabel = new JLabel("Don't have an account?");
        registerLabel.setForeground(nepalRed);
        registerLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        registerLabel.setBounds(140, 420, 170, 25);

        registerButton = new JButton("Register Here");
        registerButton.setBounds(300, 417, 150, 32);
        registerButton.setBackground(nepalBlue);
        registerButton.setForeground(Color.WHITE);
        registerButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        registerButton.setFocusPainted(false);
        registerButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel footer = new JLabel("© 2026 Mero Pasal");
        footer.setForeground(Color.GRAY);
        footer.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        footer.setBounds(245, 485, 120, 20);

        loginButton.addActionListener(e -> {

            String merchantName = merchantNameField.getText().trim();
            String password = String.valueOf(passwordField.getPassword());

            if (merchantName.isEmpty() || password.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please fill all fields.",
                        "Login Error",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            UserDAO userDAO = new UserDAO();

            if (userDAO.loginUser(merchantName, password)) {

                JOptionPane.showMessageDialog(
                        this,
                        "Login Successful!"
                );

                new Dashboard();
                dispose();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Invalid Merchant Name or Password.",
                        "Login Failed",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        });



        registerButton.addActionListener(e -> {
            new RegisterFrame();
            dispose();
        });

        panel.add(header);
        panel.add(accentLine);
        panel.add(merchantLabel);
        panel.add(merchantNameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(loginButton);
        panel.add(registerLabel);
        panel.add(registerButton);
        panel.add(footer);

        add(panel);
        setVisible(true);
    }

}
