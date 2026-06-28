package ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class Dashboard extends JFrame {

    // Nepal Flag Colors
    private final Color NEPAL_BLUE = new Color(0, 56, 147);
    private final Color NEPAL_RED = new Color(220, 20, 60);
    private JPanel centerPanel;
    public Dashboard() {

        setTitle("Mero Pasal - Admin Panel");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Full Screen
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        setLayout(new BorderLayout());

        // ======================================================
        // TOP NAVIGATION BAR
        // ======================================================


// ============================
// TOP NAVIGATION BAR
// ============================
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(NEPAL_BLUE);
        topPanel.setPreferredSize(new Dimension(0, 70));
        topPanel.setBorder(new EmptyBorder(10, 20, 10, 20));

// ----------------------------
// LEFT : Logo + App Name
// ----------------------------
        ImageIcon logo = new ImageIcon(getClass().getResource("/images/meropasal2.png"));
        Image logoImage = logo.getImage().getScaledInstance(45, 45, Image.SCALE_SMOOTH);

        JLabel title = new JLabel("  Mero Pasal", new ImageIcon(logoImage), JLabel.LEFT);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 28));

// ----------------------------
// CENTER : Welcome Message
// ----------------------------
        JLabel welcomeLabel = new JLabel("Welcome to Mero Pasal Dashboard", SwingConstants.CENTER);
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));

// ----------------------------
// RIGHT : Profile Button
// ----------------------------
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.setOpaque(false);

        ImageIcon profileIcon = new ImageIcon(getClass().getResource("/images/profile.png"));
        Image profileImage = profileIcon.getImage().getScaledInstance(22, 22, Image.SCALE_SMOOTH);

        JButton profileBtn = new JButton("Profile", new ImageIcon(profileImage));
        profileBtn.setFocusPainted(false);
        profileBtn.setBackground(Color.WHITE);
        profileBtn.setForeground(NEPAL_BLUE);
        profileBtn.setPreferredSize(new Dimension(130, 40));
        profileBtn.setIconTextGap(10);

        rightPanel.add(profileBtn);

        topPanel.add(title, BorderLayout.WEST);
        topPanel.add(welcomeLabel, BorderLayout.CENTER);
        topPanel.add(rightPanel, BorderLayout.EAST);

// ============================
// SIDEBAR
// ============================
        JPanel sidePanel = new JPanel();
        sidePanel.setBackground(NEPAL_RED);
        sidePanel.setPreferredSize(new Dimension(230, 0));
        sidePanel.setBorder(new EmptyBorder(20, 15, 20, 15));
        sidePanel.setLayout(new GridLayout(9, 1, 12, 12));

        String[] names = {
                "Dashboard",
                "Add Transactions",
                "Products",
                "Sales",
                "Customers",
                "Suppliers",
                "Reports",
                "Settings",
                "Help"
        };

        String[] icons = {
                "/images/dashboard.png",
                "/images/transaction.png",
                "/images/product.png",
                "/images/sales.png",
                "/images/customers.png",
                "/images/suppliers.png",
                "/images/report.png",
                "/images/setting.png",
                "/images/help.png"
        };

        for (int i = 0; i < names.length; i++) {

            ImageIcon icon = new ImageIcon(getClass().getResource(icons[i]));
            Image img = icon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);

            JButton button = new JButton(names[i], new ImageIcon(img));

            button.setHorizontalAlignment(SwingConstants.LEFT);
            button.setHorizontalTextPosition(SwingConstants.RIGHT);
            button.setIconTextGap(15);

            button.setBackground(Color.WHITE);
            button.setForeground(NEPAL_RED);
            button.setFont(new Font("Arial", Font.BOLD, 15));
            button.setFocusPainted(false);
            button.setCursor(new Cursor(Cursor.HAND_CURSOR));
            button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 8));
            String page = names[i];

            button.addActionListener(e -> showPage(page));

            sidePanel.add(button);
        }

// ============================
// CENTER PANEL
// ============================
        centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(Color.WHITE);

        showPage("Dashboard");
// ============================
// STATUS BAR
// ============================
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setPreferredSize(new Dimension(0, 35));
        bottomPanel.setBackground(NEPAL_BLUE);
        bottomPanel.setBorder(new EmptyBorder(5, 15, 5, 15));

        JLabel status = new JLabel("Status : Ready");
        status.setForeground(Color.WHITE);

        JLabel copyright = new JLabel("© Mero Pasal");
        copyright.setForeground(Color.WHITE);

        bottomPanel.add(status, BorderLayout.WEST);
        bottomPanel.add(copyright, BorderLayout.EAST);

// ============================
// ADD COMPONENTS
// ============================
        add(topPanel, BorderLayout.NORTH);
        add(sidePanel, BorderLayout.WEST);
        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);


        setVisible(true);
    }
    private void showPage(String pageName) {

        // Remove old contents
        centerPanel.removeAll();
        centerPanel.setLayout(new BorderLayout());
        centerPanel.setBackground(Color.WHITE);

        // Page Heading
        JLabel heading = new JLabel(pageName, SwingConstants.CENTER);
        heading.setFont(new Font("Arial", Font.BOLD, 32));
        heading.setForeground(NEPAL_BLUE);
        heading.setBorder(new EmptyBorder(30, 0, 20, 0));

        // Description
        JLabel description = new JLabel("", SwingConstants.CENTER);
        description.setFont(new Font("Arial", Font.PLAIN, 18));
        description.setForeground(Color.DARK_GRAY);

        switch (pageName) {

            case "Dashboard":
                description.setText("Welcome to Mero Pasal Dashboard.");
                break;

            case "Add Transactions":
                description.setText("Record sales and purchase transactions.");
                break;

            case "Products":
                description.setText("Manage your product inventory.");
                break;

            case "Sales":
                description.setText("View daily, weekly and monthly sales.");
                break;

            case "Customers":
                description.setText("Manage customer information.");
                break;

            case "Suppliers":
                description.setText("Manage supplier details.");
                break;

            case "Reports":
                description.setText("Generate business reports.");
                break;

            case "Settings":
                description.setText("Configure application settings.");
                break;

            case "Help":
                description.setText("Need help? Find documentation here.");
                break;

            default:
                description.setText("Page not found.");
        }

        JPanel content = new JPanel();
        content.setBackground(Color.WHITE);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        heading.setAlignmentX(Component.CENTER_ALIGNMENT);
        description.setAlignmentX(Component.CENTER_ALIGNMENT);

        content.add(Box.createVerticalStrut(80));
        content.add(heading);
        content.add(Box.createVerticalStrut(20));
        content.add(description);

        centerPanel.add(content, BorderLayout.CENTER);

        centerPanel.revalidate();
        centerPanel.repaint();
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> new Dashboard());

    }
}

