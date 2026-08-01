package ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.FilteredImageSource;
import java.awt.image.RGBImageFilter;

public class Dashboard extends JFrame {
    private JLabel totalLabel;
    private double grandTotal = 0.0;

    // Active merchant tracking
    private String loggedInMerchant = "Sample Merchant";

    // Modern Refined Color Palette
    private final Color NEPAL_BLUE = new Color(14, 82, 184);     // Deep Modern Royal Blue
    private final Color NEPAL_RED = new Color(30, 38, 50);        // Sleek Dark Slate (Sidebar)
    private final Color SIDEBAR_HOVER = new Color(45, 55, 72);
    private final Color SIDEBAR_TEXT = new Color(205, 215, 225);
    private final Color BG_LIGHT = new Color(245, 247, 250);

    private JPanel centerPanel;

    // Primary constructor taking logged-in merchant name
    public Dashboard(String merchantName) {
        if (merchantName != null && !merchantName.trim().isEmpty()) {
            this.loggedInMerchant = merchantName;
        }
        initDashboard();
    }

    // Default constructor
    public Dashboard() {
        this("Sample Merchant");
    }

    private void initDashboard() {
        setTitle("Mero Pasal - Admin Panel");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Full Screen
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(1000, 700));

        setLayout(new BorderLayout());

        // ======================================================
        // TOP NAVIGATION BAR
        // ======================================================
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(NEPAL_BLUE);
        topPanel.setPreferredSize(new Dimension(0, 65));
        topPanel.setBorder(new EmptyBorder(10, 20, 10, 20));

        // ----------------------------
        // LEFT : Logo + App Name
        // ----------------------------
        ImageIcon logo = new ImageIcon(getClass().getResource("/images/meropasal2.png"));
        Image logoImage = logo.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);

        JLabel title = new JLabel("  Mero Pasal", new ImageIcon(logoImage), JLabel.LEFT);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));

        // ----------------------------
        // CENTER : Welcome Message
        // ----------------------------
        JLabel welcomeLabel = new JLabel("Welcome to Mero Pasal Dashboard", SwingConstants.CENTER);
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));

        // ----------------------------
        // RIGHT : Profile Button
        // ----------------------------
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        rightPanel.setOpaque(false);

        ImageIcon profileIcon = new ImageIcon(getClass().getResource("/images/profile.png"));
        Image profileImage = profileIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);

        JButton profileBtn = new JButton("Profile", new ImageIcon(profileImage));
        profileBtn.setFocusPainted(false);
        profileBtn.setBackground(Color.WHITE);
        profileBtn.setForeground(NEPAL_BLUE);
        profileBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        profileBtn.setPreferredSize(new Dimension(120, 36));
        profileBtn.setIconTextGap(8);
        profileBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        profileBtn.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));

        rightPanel.add(profileBtn);

        // Open Profile panel inside centerPanel when clicked
        profileBtn.addActionListener(e -> showPage("Profile"));

        topPanel.add(title, BorderLayout.WEST);
        topPanel.add(welcomeLabel, BorderLayout.CENTER);
        topPanel.add(rightPanel, BorderLayout.EAST);

        // ============================
        // SIDEBAR
        // ============================
        JPanel sidePanel = new JPanel();
        sidePanel.setBackground(NEPAL_RED);
        sidePanel.setPreferredSize(new Dimension(230, 0));
        sidePanel.setBorder(new EmptyBorder(15, 10, 15, 10));
        sidePanel.setLayout(new GridLayout(9, 1, 0, 8));

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
            Image img = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);

            // Lighten the icon dynamically so dark icons stand out clearly against the dark sidebar
            Image whiteImg = createWhiteIcon(img);

            JButton button = new JButton(names[i], new ImageIcon(whiteImg));

            button.setHorizontalAlignment(SwingConstants.LEFT);
            button.setHorizontalTextPosition(SwingConstants.RIGHT);
            button.setIconTextGap(12);

            button.setBackground(NEPAL_RED);
            button.setForeground(SIDEBAR_TEXT);
            button.setFont(new Font("Segoe UI", Font.BOLD, 14));
            button.setFocusPainted(false);
            button.setCursor(new Cursor(Cursor.HAND_CURSOR));
            button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 10));

            // Hover effects for modern look
            button.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    button.setBackground(SIDEBAR_HOVER);
                    button.setForeground(Color.WHITE);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    button.setBackground(NEPAL_RED);
                    button.setForeground(SIDEBAR_TEXT);
                }
            });

            String page = names[i];
            button.addActionListener(e -> showPage(page));

            sidePanel.add(button);
        }

        // ============================
        // CENTER PANEL
        // ============================
        centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(BG_LIGHT);

        showPage("Dashboard");

        // ============================
        // STATUS BAR
        // ============================
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setPreferredSize(new Dimension(0, 32));
        bottomPanel.setBackground(NEPAL_RED);
        bottomPanel.setBorder(new EmptyBorder(5, 20, 5, 20));

        JLabel status = new JLabel("Status : Ready");
        status.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        status.setForeground(new Color(170, 185, 200));

        JLabel copyright = new JLabel("© Mero Pasal");
        copyright.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        copyright.setForeground(new Color(170, 185, 200));

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

    /**
     * Converts any image icon pixels to bright white while preserving transparency,
     * ensuring icons remain high-contrast on dark sidebars.
     */
    private Image createWhiteIcon(Image sourceImage) {
        RGBImageFilter filter = new RGBImageFilter() {
            @Override
            public int filterRGB(int x, int y, int rgb) {
                int alpha = (rgb >> 24) & 0xff;
                if (alpha == 0) return rgb; // Preserve fully transparent pixels
                // Set RGB channels to bright white (255, 255, 255) while keeping original alpha transparency
                return (alpha << 24) | (255 << 16) | (255 << 8) | 255;
            }
        };

        FilteredImageSource producer = new FilteredImageSource(sourceImage.getSource(), filter);
        return Toolkit.getDefaultToolkit().createImage(producer);
    }

    private void showPage(String pageName) {

        centerPanel.removeAll();
        centerPanel.setLayout(new BorderLayout());
        centerPanel.setBackground(BG_LIGHT);

        if (pageName.equals("Add Transactions")) {
            centerPanel.add(new TransactionPanel(), BorderLayout.CENTER);
            centerPanel.revalidate();
            centerPanel.repaint();
            return;
        }

        if (pageName.equals("Profile")) {
            centerPanel.add(new Profile(loggedInMerchant), BorderLayout.CENTER);
            centerPanel.revalidate();
            centerPanel.repaint();
            return;
        }

        JLabel heading = new JLabel(pageName, SwingConstants.CENTER);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 30));
        heading.setForeground(NEPAL_BLUE);

        JLabel description = new JLabel("", SwingConstants.CENTER);
        description.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        description.setForeground(new Color(100, 110, 120));

        switch (pageName) {

            case "Dashboard":
                description.setText("Welcome to Mero Pasal Dashboard.");
                break;

            case "Products":
                description.setText("Manage your product inventory.");
                break;

            case "Sales":
                centerPanel.add(new SalesPanel(loggedInMerchant), BorderLayout.CENTER);
                centerPanel.revalidate();
                centerPanel.repaint();
                return;


            case "Customers":
                description.setText("Manage customers.");
                break;

            case "Suppliers":
                description.setText("Manage suppliers.");
                break;

            case "Reports":
                description.setText("Generate reports.");
                break;

            case "Settings":
                description.setText("Configure application settings.");
                break;

            case "Help":
                centerPanel.add(new HelpPanel(), BorderLayout.CENTER);
                centerPanel.revalidate();
                centerPanel.repaint();
                return;
        }

        JPanel content = new JPanel();
        content.setBackground(BG_LIGHT);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        heading.setAlignmentX(Component.CENTER_ALIGNMENT);
        description.setAlignmentX(Component.CENTER_ALIGNMENT);

        content.add(Box.createVerticalStrut(100));
        content.add(heading);
        content.add(Box.createVerticalStrut(15));
        content.add(description);

        centerPanel.add(content, BorderLayout.CENTER);

        centerPanel.revalidate();
        centerPanel.repaint();
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        new Dashboard();
    }
}