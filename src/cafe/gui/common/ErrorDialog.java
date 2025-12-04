package cafe.gui.common;

import cafe.gui.CafeTheme;
import cafe.gui.components.RoundedButton;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class ErrorDialog extends JDialog {

    public ErrorDialog(Window owner, String message) {
        super(owner, ModalityType.APPLICATION_MODAL);
        setUndecorated(true);
        // [FIX] Ukuran diperbesar agar muat 2 baris teks + ikon
        setSize(320, 240); 
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(CafeTheme.BG_COLOR);
        mainPanel.setBorder(new LineBorder(new Color(200, 60, 60), 3)); 

        // HEADER
        JPanel header = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10));
        header.setBackground(new Color(200, 60, 60)); 
        JLabel title = new JLabel("PERINGATAN");
        title.setFont(new Font("SansSerif", Font.BOLD, 16));
        title.setForeground(Color.WHITE);
        header.add(title);
        mainPanel.add(header, BorderLayout.NORTH);

        // CONTENT (Vertikal)
        JPanel content = new JPanel(new GridBagLayout());
        content.setBackground(CafeTheme.BG_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        
        // 1. IKON (Baris 0)
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 0, 10, 0); // Jarak atas/bawah ikon
        content.add(new WarningIcon(), gbc);

        // 2. TEKS (Baris 1)
        gbc.gridy = 1;
        // [FIX] Tambahkan insets atas dan bawah lebih besar agar HTML punya ruang
        gbc.insets = new Insets(5, 20, 20, 20); 
        
        JLabel msgLabel = new JLabel("<html><div style='text-align: center; width: 220px;'>" + message + "</div></html>");
        msgLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        msgLabel.setForeground(CafeTheme.TEXT_COLOR);
        msgLabel.setHorizontalAlignment(SwingConstants.CENTER);
        content.add(msgLabel, gbc);

        mainPanel.add(content, BorderLayout.CENTER);

        // FOOTER
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 15));
        footer.setBackground(CafeTheme.BG_COLOR);
        RoundedButton btnOk = new RoundedButton("OK");
        btnOk.setBackground(CafeTheme.BUTTON_COLOR);
        btnOk.setPreferredSize(new Dimension(90, 35));
        btnOk.addActionListener(e -> dispose());
        footer.add(btnOk);
        mainPanel.add(footer, BorderLayout.SOUTH);

        add(mainPanel);
    }

    class WarningIcon extends JPanel {
        public WarningIcon() { setOpaque(false); setPreferredSize(new Dimension(60, 60)); setMinimumSize(new Dimension(60, 60)); }
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int w = getWidth();     
            int size = 50;
            
            g2.setColor(new Color(200, 60, 60));
            int[] xPoints = {w/2, w-5, 5};
            int[] yPoints = {5, size, size};
            g2.fillPolygon(xPoints, yPoints, 3);

            g2.setColor(Color.WHITE);
            g2.fillRoundRect((w/2)-3, 18, 6, 18, 2, 2);
            g2.fillOval((w/2)-3, 40, 6, 6);

            g2.dispose();
        }
    }
}