package ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;

public class HelpPanel extends JPanel {

    // Modern Refined Theme
    private final Color PRIMARY_BLUE = new Color(14, 82, 184);
    private final Color DARK_SLATE = new Color(30, 38, 50);
    private final Color BG_LIGHT = new Color(245, 247, 250);
    private final Color CARD_BG = Color.WHITE;
    private final Color TEXT_MUTED = new Color(110, 120, 135);
    private final Color BORDER_COLOR = new Color(225, 230, 238);

    // Brand Colors
    private final Color WHATSAPP_GREEN = new Color(37, 211, 102);
    private final Color INSTAGRAM_PINK = new Color(225, 48, 108);

    public HelpPanel() {
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout(0, 20));
        setBackground(BG_LIGHT);
        setBorder(new EmptyBorder(25, 35, 25, 35));

        // ======================================================
        // HEADER SECTION
        // ======================================================
        JPanel headerPanel = new JPanel(new BorderLayout(0, 6));
        headerPanel.setBackground(BG_LIGHT);

        JLabel titleLabel = new JLabel("Help Desk & Support Center");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titleLabel.setForeground(PRIMARY_BLUE);

        JLabel subTitleLabel = new JLabel("Have questions or run into technical issues? Get in touch with our dedicated support team.");
        subTitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subTitleLabel.setForeground(TEXT_MUTED);

        headerPanel.add(titleLabel, BorderLayout.NORTH);
        headerPanel.add(subTitleLabel, BorderLayout.SOUTH);

        add(headerPanel, BorderLayout.NORTH);

        // ======================================================
        // MAIN BODY SPLIT (LEFT: Contact Cards | RIGHT: FAQ)
        // ======================================================
        JPanel mainContent = new JPanel(new GridLayout(1, 2, 25, 0));
        mainContent.setBackground(BG_LIGHT);

        // LEFT COLUMN: Interactive Contact Methods
        JPanel contactContainer = new JPanel();
        contactContainer.setLayout(new BoxLayout(contactContainer, BoxLayout.Y_AXIS));
        contactContainer.setBackground(BG_LIGHT);

        contactContainer.add(createActionCard(
                "📞 Direct Help Desk",
                "Call our 24/7 technical help line for immediate POS support.",
                "9765563957",
                "Call Now",
                PRIMARY_BLUE,
                e -> copyToClipboard("9765563957", "Help Desk number copied to clipboard!")
        ));
        contactContainer.add(Box.createVerticalStrut(15));

        contactContainer.add(createActionCard(
                "💬 WhatsApp Support",
                "Chat live for rapid assistance, product updates, and system guides.",
                "+977 9765563957",
                "Open WhatsApp",
                WHATSAPP_GREEN,
                e -> openWebLink("https://wa.me/9779765563957")
        ));
        contactContainer.add(Box.createVerticalStrut(15));

        contactContainer.add(createActionCard(
                "✉️ Email Support",
                "Send detailed bug reports or feature requests directly to developers.",
                "lonewarrior7677@gmail.com",
                "Send Email",
                DARK_SLATE,
                e -> openWebLink("mailto:lonewarrior7677@gmail.com?cc=raviydvg75@gmail.com")
        ));
        contactContainer.add(Box.createVerticalStrut(15));

        contactContainer.add(createActionCard(
                "📸 Instagram / Developer",
                "Follow developer updates and direct message on social media.",
                "@raviydv_0 (Ravi Yadav)",
                "Visit Profile",
                INSTAGRAM_PINK,
                e -> openWebLink("https://instagram.com/raviydv_0")
        ));

        // RIGHT COLUMN: Frequently Asked Questions
        JPanel faqContainer = createFAQCard();

        mainContent.add(contactContainer);
        mainContent.add(faqContainer);

        JScrollPane scrollPane = new JScrollPane(mainContent);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(BG_LIGHT);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        add(scrollPane, BorderLayout.CENTER);

        // ======================================================
        // FOOTER
        // ======================================================
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footerPanel.setBackground(BG_LIGHT);

        JLabel footerText = new JLabel("Mero Pasal Support System • Version 1.0 • Built with Java Swing & MySQL");
        footerText.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        footerText.setForeground(TEXT_MUTED);

        footerPanel.add(footerText);
        add(footerPanel, BorderLayout.SOUTH);
    }

    private JPanel createActionCard(String title, String desc, String contactInfo, String btnText, Color accentColor, java.awt.event.ActionListener action) {
        JPanel card = new JPanel(new BorderLayout(15, 0));
        card.setBackground(CARD_BG);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1),
                new EmptyBorder(15, 20, 15, 20)
        ));

        // Left info container
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(CARD_BG);

        JLabel titleLbl = new JLabel(title);
        titleLbl.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLbl.setForeground(accentColor);

        JLabel descLbl = new JLabel("<html>" + desc + "</html>");
        descLbl.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        descLbl.setForeground(TEXT_MUTED);

        JLabel infoLbl = new JLabel(contactInfo);
        infoLbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
        infoLbl.setForeground(DARK_SLATE);

        infoPanel.add(titleLbl);
        infoPanel.add(Box.createVerticalStrut(4));
        infoPanel.add(descLbl);
        infoPanel.add(Box.createVerticalStrut(8));
        infoPanel.add(infoLbl);

        // Right Action Button
        JButton actionBtn = new JButton(btnText);
        actionBtn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        actionBtn.setBackground(accentColor);
        actionBtn.setForeground(Color.WHITE);
        actionBtn.setFocusPainted(false);
        actionBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        actionBtn.setPreferredSize(new Dimension(120, 36));
        actionBtn.addActionListener(action);

        JPanel btnWrapper = new JPanel(new GridBagLayout());
        btnWrapper.setBackground(CARD_BG);
        btnWrapper.add(actionBtn);

        card.add(infoPanel, BorderLayout.CENTER);
        card.add(btnWrapper, BorderLayout.EAST);

        // Hover Effect
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                card.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(accentColor, 1),
                        new EmptyBorder(15, 20, 15, 20)
                ));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                card.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(BORDER_COLOR, 1),
                        new EmptyBorder(15, 20, 15, 20)
                ));
            }
        });

        return card;
    }

    private JPanel createFAQCard() {
        JPanel card = new JPanel(new BorderLayout(0, 15));
        card.setBackground(CARD_BG);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR, 1),
                new EmptyBorder(20, 20, 20, 20)
        ));

        JLabel faqTitle = new JLabel("💡 Frequently Asked Questions");
        faqTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        faqTitle.setForeground(DARK_SLATE);

        card.add(faqTitle, BorderLayout.NORTH);

        JPanel accordionPanel = new JPanel();
        accordionPanel.setLayout(new BoxLayout(accordionPanel, BoxLayout.Y_AXIS));
        accordionPanel.setBackground(CARD_BG);

        accordionPanel.add(createFAQItem("How do I complete a transaction?", "Go to 'Add Transactions', enter product details, click '+ Add Item', then click 'Complete Transaction' to record it in MySQL."));
        accordionPanel.add(Box.createVerticalStrut(12));

        accordionPanel.add(createFAQItem("Where can I see total sales?", "Navigate to the 'Sales' page on the left sidebar to view real-time total revenue and complete transaction history."));
        accordionPanel.add(Box.createVerticalStrut(12));

        accordionPanel.add(createFAQItem("What if database connection fails?", "Check if MySQL Service is running in XAMPP or Services, and ensure database credentials in 'DBConnection.java' are correct."));
        accordionPanel.add(Box.createVerticalStrut(12));

        accordionPanel.add(createFAQItem("How do I update merchant details?", "Click the 'Profile' button on the top-right navigation bar to view and update logged-in merchant details."));

        card.add(accordionPanel, BorderLayout.CENTER);

        return card;
    }

    private JPanel createFAQItem(String question, String answer) {
        JPanel panel = new JPanel(new BorderLayout(0, 4));
        panel.setBackground(new Color(248, 250, 252));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 235, 242), 1),
                new EmptyBorder(12, 12, 12, 12)
        ));

        JLabel qLbl = new JLabel("Q: " + question);
        qLbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
        qLbl.setForeground(PRIMARY_BLUE);

        JLabel aLbl = new JLabel("<html>" + answer + "</html>");
        aLbl.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        aLbl.setForeground(DARK_SLATE);

        panel.add(qLbl, BorderLayout.NORTH);
        panel.add(aLbl, BorderLayout.CENTER);

        return panel;
    }

    private void openWebLink(String url) {
        try {
            Desktop.getDesktop().browse(new URI(url));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Could not open link automatically.\nURL: " + url, "Browser Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void copyToClipboard(String text, String message) {
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new java.awt.datatransfer.StringSelection(text), null);
        JOptionPane.showMessageDialog(this, message, "Copied", JOptionPane.INFORMATION_MESSAGE);
    }
}