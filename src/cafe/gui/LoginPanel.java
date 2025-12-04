package cafe.gui;

import cafe.gui.components.RoundedButton;
import cafe.gui.admin.AdminLoginDialog; 
import cafe.gui.common.ConfirmDialog; 
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

            double cupW = 70.0; 
            double cupH = 50.0; 
            
            // Variabel posisi cangkir
            double cupX = (w - cupW) / 2.0; 
            double cupY = (h - cupH) / 2.0 + 15.0; 

            // Piringan 
            g2.setColor(shadowColor);
            g2.fillOval((int) (cupX - 15.0), (int) (cupY + cupH - 8.0), (int) (cupW + 30.0), 12); 
            g2.setColor(baseColor);
            g2.fillOval((int) (cupX - 12.0), (int) (cupY + cupH - 10.0), (int) (cupW + 24.0), 10);

            // Gagang
            g2.setStroke(new BasicStroke(6, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g2.setColor(baseColor);
            Path2D handle = new Path2D.Double();
            handle.moveTo(cupX + cupW - 5.0, cupY + 10.0);
            handle.curveTo(cupX + cupW + 25.0, cupY + 5.0, cupX + cupW + 25.0, cupY + 35.0, cupX + cupW - 10.0, cupY + 40.0);
            g2.draw(handle);

            // Badan
            GradientPaint gradient = new GradientPaint((float)cupX, (float)cupY, baseColor.brighter(), (float)(cupX + cupW), (float)cupY, shadowColor);
            g2.setPaint(gradient);
            RoundRectangle2D cupBody = new RoundRectangle2D.Double(cupX, cupY, cupW, cupH, 20.0, 30.0);
            g2.fill(cupBody);

            // Cairan
            g2.setColor(liquidColor);
            g2.fillOval((int) (cupX + 3.0), (int) (cupY + 2.0), (int) (cupW - 6.0), 14);
            g2.setStroke(new BasicStroke(2));
            g2.setColor(baseColor.brighter());
            g2.drawOval((int) (cupX + 2.0), (int) (cupY + 1.0), (int) (cupW - 4.0), 16);

            // Uap
            g2.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g2.setColor(new Color(baseColor.getRed(), baseColor.getGreen(), baseColor.getBlue(), 180));
    
            double steamStartX = cupX + 20.0;
            double steamStartY = cupY - 5.0;
            
            Path2D steam1 = new Path2D.Double();
            steam1.moveTo(steamStartX, steamStartY);
            steam1.curveTo(steamStartX - 10.0, steamStartY - 15.0, steamStartX + 5.0, steamStartY - 25.0, steamStartX - 5.0, steamStartY - 35.0);
            g2.draw(steam1);

            Path2D steam2 = new Path2D.Double();
            steam2.moveTo(steamStartX + 15.0, steamStartY - 5.0);
            steam2.curveTo(steamStartX + 5.0, steamStartY - 20.0, steamStartX + 25.0, steamStartY - 30.0, steamStartX + 10.0, steamStartY - 45.0);
            g2.draw(steam2);

            Path2D steam3 = new Path2D.Double();
            steam3.moveTo(steamStartX + 30.0, steamStartY);
            // Kode ini sekarang bersih karena semua operasi adalah double + double
            steam3.curveTo(steamStartX + 20.0, steamStartY - 15.0, steamStartX + 35.0, steamStartY - 25.0, steamStartX + 25.0, steamStartY - 35.0);
            g2.draw(steam3);

            g2.dispose();
        }
    }
}