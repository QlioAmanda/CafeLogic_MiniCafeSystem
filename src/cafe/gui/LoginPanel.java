package cafe.gui;

import cafe.gui.components.RoundedButton;
import cafe.gui.admin.AdminLoginDialog; 
import cafe.gui.common.ConfirmDialog;   
import cafe.service.LoginService;
import javax.swing.*;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.awt.geom.*;
import java.util.Map;

public class LoginPanel extends JPanel {
    private MainFrame mainFrame;

    @SuppressWarnings("unchecked")
    public LoginPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new GridBagLayout());
        setBackground(CafeTheme.BG_COLOR);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;

        // 1. LOGO KOPI
        LogoPanel logo = new LogoPanel();
        logo.setPreferredSize(new Dimension(120, 120));
        logo.setBackground(CafeTheme.BG_COLOR);
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 0, 5, 0);
        add(logo, gbc);

        // 2. TEXT: SELAMAT DATANG
        JLabel welcomeLabel = new JLabel("S E L A M A T   D A T A N G");
        welcomeLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        welcomeLabel.setForeground(CafeTheme.TEXT_COLOR);
        
        Font font = welcomeLabel.getFont();
        Map<TextAttribute, Object> attributes = (Map<TextAttribute, Object>) font.getAttributes();
        attributes.put(TextAttribute.TRACKING, 0.3);
        welcomeLabel.setFont(font.deriveFont(attributes));

        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 5, 0);
        add(welcomeLabel, gbc);

        // 3. TEXT: Cafe Logic
        JLabel brandLabel = new JLabel("Cafe Logic");
        Font scriptFont = new Font("Segoe Script", Font.BOLD, 48);
        if (scriptFont.getFamily().equals("Dialog")) { 
            scriptFont = new Font("Brush Script MT", Font.ITALIC, 48); 
        }
        brandLabel.setFont(scriptFont);
        brandLabel.setForeground(CafeTheme.TEXT_COLOR);
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 40, 0);
        add(brandLabel, gbc);

        // 4. CONTAINER TOMBOL LOGIN
        JPanel buttonContainer = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 0));
        buttonContainer.setBackground(CafeTheme.BG_COLOR);

        // Tombol Login
        RoundedButton btnAdmin = new RoundedButton("Masuk sebagai Admin");
        btnAdmin.setPreferredSize(new Dimension(280, 60)); 
        btnAdmin.setBackground(CafeTheme.BUTTON_COLOR);
        btnAdmin.setFont(CafeTheme.FONT_BUTTON);
        
        RoundedButton btnCustomer = new RoundedButton("Masuk sebagai Pelanggan");
        btnCustomer.setPreferredSize(new Dimension(280, 60));
        btnCustomer.setBackground(CafeTheme.BUTTON_COLOR);
        btnCustomer.setFont(CafeTheme.FONT_BUTTON);

        buttonContainer.add(btnAdmin);
        buttonContainer.add(btnCustomer);

        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 25, 0);
        add(buttonContainer, gbc);

        // 5. TOMBOL KELUAR
        RoundedButton btnExit = new RoundedButton("Keluar Aplikasi");
        btnExit.setPreferredSize(new Dimension(150, 35));
        btnExit.setBackground(CafeTheme.EXIT_BTN_COLOR);
        btnExit.setForeground(Color.WHITE);
        btnExit.setFont(new Font("SansSerif", Font.BOLD, 12));
        btnExit.setArc(35);

        gbc.gridy = 4;
        gbc.insets = new Insets(0, 0, 20, 0);
        add(btnExit, gbc);

        // --- EVENT LISTENERS ---
        btnAdmin.addActionListener(e -> handleAdminLogin());
        btnCustomer.addActionListener(e -> mainFrame.showCard("CUSTOMER"));
        
        btnExit.addActionListener(e -> {
             ConfirmDialog dialog = new ConfirmDialog(mainFrame, "KELUAR", "Apakah Anda yakin ingin menutup aplikasi?");
             dialog.setVisible(true);
             if (dialog.isConfirmed()) System.exit(0);
        });
    }

    private void handleAdminLogin() {
        AdminLoginDialog loginDialog = new AdminLoginDialog(mainFrame);
        loginDialog.setVisible(true);

        if (loginDialog.isAuthenticated()) {
            mainFrame.showCard("ADMIN");
        }
    }

    // --- LOGO KOPI ---
    class LogoPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int w = getWidth();
            int h = getHeight();
            Color baseColor = CafeTheme.EXIT_BTN_COLOR;
            Color shadowColor = baseColor.darker();
            Color liquidColor = new Color(101, 67, 33);

            int cupW = 70;
            int cupH = 50;
            int cupX = (w - cupW) / 2;
            int cupY = (h - cupH) / 2 + 15;

            // Piringan
            g2.setColor(shadowColor);
            g2.fillOval(cupX - 15, cupY + cupH - 8, cupW + 30, 12);
            g2.setColor(baseColor);
            g2.fillOval(cupX - 12, cupY + cupH - 10, cupW + 24, 10);

            // Gagang
            g2.setStroke(new BasicStroke(6, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g2.setColor(baseColor);
            Path2D handle = new Path2D.Double();
            handle.moveTo(cupX + cupW - 5, cupY + 10);
            handle.curveTo(cupX + cupW + 25, cupY + 5, cupX + cupW + 25, cupY + 35, cupX + cupW - 10, cupY + 40);
            g2.draw(handle);

            // Badan
            GradientPaint gradient = new GradientPaint(cupX, cupY, baseColor.brighter(), cupX + cupW, cupY, shadowColor);
            g2.setPaint(gradient);
            RoundRectangle2D cupBody = new RoundRectangle2D.Double(cupX, cupY, cupW, cupH, 20, 30);
            g2.fill(cupBody);

            // Cairan
            g2.setColor(liquidColor);
            g2.fillOval(cupX + 3, cupY + 2, cupW - 6, 14);
            g2.setStroke(new BasicStroke(2));
            g2.setColor(baseColor.brighter());
            g2.drawOval(cupX + 2, cupY + 1, cupW - 4, 16);

            // Uap
            g2.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g2.setColor(new Color(baseColor.getRed(), baseColor.getGreen(), baseColor.getBlue(), 180));
            int steamStartX = cupX + 20;
            int steamStartY = cupY - 5;
            
            Path2D steam1 = new Path2D.Double();
            steam1.moveTo(steamStartX, steamStartY);
            steam1.curveTo(steamStartX - 10, steamStartY - 15, steamStartX + 5, steamStartY - 25, steamStartX - 5, steamStartY - 35);
            g2.draw(steam1);

            Path2D steam2 = new Path2D.Double();
            steam2.moveTo(steamStartX + 15, steamStartY - 5);
            steam2.curveTo(steamStartX + 5, steamStartY - 20, steamStartX + 25, steamStartY - 30, steamStartX + 10, steamStartY - 45);
            g2.draw(steam2);

            Path2D steam3 = new Path2D.Double();
            steam3.moveTo(steamStartX + 30, steamStartY);
            steam3.curveTo(steamStartX + 20, steamStartY - 15, steamStartX + 35, steamStartY - 25, steamStartX + 25, steamStartY - 35);
            g2.draw(steam3);

            g2.dispose();
        }
    }
}