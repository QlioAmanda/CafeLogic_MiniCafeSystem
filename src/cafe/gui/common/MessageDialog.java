package cafe.gui.common;

import cafe.gui.CafeTheme;
import cafe.gui.components.RoundedButton;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class MessageDialog extends JDialog {

    public MessageDialog(Window owner, String title, String message) {
        super(owner, ModalityType.APPLICATION_MODAL);
        setUndecorated(true);
        setSize(320, 220); // Tinggi disesuaikan untuk layout vertikal
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(CafeTheme.BG_COLOR);
        mainPanel.setBorder(new LineBorder(CafeTheme.EXIT_BTN_COLOR, 3)); 

        // HEADER
        JPanel header = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10));
        header.setBackground(CafeTheme.EXIT_BTN_COLOR); 
        JLabel titleLabel = new JLabel(title.toUpperCase());
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        titleLabel.setForeground(Color.WHITE);
        header.add(titleLabel);
        mainPanel.add(header, BorderLayout.NORTH);

        // CONTENT (VERTIKAL: IKON ATAS, TEKS BAWAH)
        JPanel content = new JPanel(new GridBagLayout());
        content.setBackground(CafeTheme.BG_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        
        // 1. IKON (Posisi: Baris 0)
        gbc.gridx = 0; 
        gbc.gridy = 0; 
        gbc.insets = new Insets(15, 0, 10, 0); // Jarak bawah ke teks
        gbc.anchor = GridBagConstraints.CENTER;
        
        boolean isSuccess = title.equalsIgnoreCase("SUKSES");
        content.add(new InfoIcon(isSuccess), gbc);

        // 2. TEKS (Posisi: Baris 1)
        gbc.gridy = 1; 
        gbc.insets = new Insets(0, 20, 10, 20); // Padding kiri kanan
        
        JLabel msgLabel = new JLabel("<html><div style='text-align: center; width: 220px;'>" + message + "</div></html>");
        msgLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        msgLabel.setForeground(CafeTheme.TEXT_COLOR);
        msgLabel.setHorizontalAlignment(SwingConstants.CENTER); // Pastikan teks rata tengah
        content.add(msgLabel, gbc);

        mainPanel.add(content, BorderLayout.CENTER);

        // FOOTER
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 15));
        footer.setBackground(CafeTheme.BG_COLOR);
        RoundedButton btnOk = new RoundedButton("OK");
        btnOk.setBackground(CafeTheme.BUTTON_COLOR);
        btnOk.setPreferredSize(new Dimension(100, 35));
        btnOk.addActionListener(e -> dispose());
        footer.add(btnOk);
        mainPanel.add(footer, BorderLayout.SOUTH);

        add(mainPanel);
    }

    // --- IKON MANUAL (Dipastikan Muncul) ---
    class InfoIcon extends JPanel {
        private boolean isSuccess;
        public InfoIcon(boolean isSuccess) {
            this.isSuccess = isSuccess;
            setOpaque(false); // Transparan agar warna background panel terlihat
            // Paksa ukuran agar tidak hilang
            setPreferredSize(new Dimension(60, 60)); 
            setMinimumSize(new Dimension(60, 60));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            if (isSuccess) {
                g2.setColor(new Color(46, 125, 50)); // Hijau Sukses
            } else {
                g2.setColor(CafeTheme.EXIT_BTN_COLOR); // Coklat Info
            }
            g2.fillOval(5, 5, 50, 50); // Lingkaran Dasar

            g2.setColor(Color.WHITE);
            g2.setStroke(new BasicStroke(5, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

            if (isSuccess) {
                // Gambar Ceklis
                g2.drawLine(18, 30, 26, 38); 
                g2.drawLine(26, 38, 42, 22); 
            } else {
                // Gambar Huruf 'i'
                g2.fillOval(27, 15, 6, 6); 
                g2.fillRoundRect(27, 25, 6, 20, 2, 2); 
            }
            g2.dispose();
        }
    }
}