package ui;


import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    private JTextField merchantNameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;

    public LoginFrame() {

        setTitle("Mero Pasal - Merchant Login");
        setSize(500, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(35, 35, 35));

        // Title
        JLabel title = new JLabel("MERO PASAL");
        title.setFont(new Font("Arial", Font.BOLD, 30));
        title.setForeground(Color.WHITE);
        title.setBounds(140, 30, 250, 40);

        // Subtitle
        JLabel subtitle = new JLabel("Merchant Login");
        subtitle.setFont(new Font("Arial", Font.PLAIN, 18));
        subtitle.setForeground(Color.LIGHT_GRAY);
        subtitle.setBounds(175, 75, 150, 25);

        // Merchant Name
        JLabel merchantLabel = new JLabel("Merchant's Name");
        merchantLabel.setForeground(Color.WHITE);
        merchantLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        merchantLabel.setBounds(70, 130, 150, 25);

        merchantNameField = new JTextField();
        merchantNameField.setBounds(70, 160, 350, 40);
        merchantNameField.setFont(new Font("Arial", Font.PLAIN, 14));

        // Password
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setForeground(Color.WHITE);
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordLabel.setBounds(70, 220, 100, 25);

        passwordField = new JPasswordField();
        passwordField.setBounds(70, 250, 350, 40);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));

        // Login Button
        loginButton = new JButton("Login");
        loginButton.setBounds(70, 320, 350, 45);
        loginButton.setFont(new Font("Arial", Font.BOLD, 15));

        // Register Section
        JLabel registerLabel = new JLabel("Don't have an account?");
        registerLabel.setForeground(Color.LIGHT_GRAY);
        registerLabel.setBounds(110, 380, 150, 25);

        registerButton = new JButton("Register");
        registerButton.setBounds(260, 377, 100, 30);

        // Events
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

            JOptionPane.showMessageDialog(
                    this,
                    "Welcome, " + merchantName + "!"
            );


        });

        registerButton.addActionListener(e -> {
            RegisterFrame r = new  RegisterFrame();
            dispose();

        });

        panel.add(title);
        panel.add(subtitle);
        panel.add(merchantLabel);
        panel.add(merchantNameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(loginButton);
        panel.add(registerLabel);
        panel.add(registerButton);

        add(panel);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginFrame::new);
    }
}