package cafe.gui.common;

import cafe.gui.CafeTheme;
import cafe.gui.components.RoundedButton;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class ConfirmDialog extends JDialog {
    private boolean confirmed = false;

    public ConfirmDialog(Window owner, String title, String message) {
        super(owner, ModalityType.APPLICATION_MODAL);
        setUndecorated(true);
        setSize(350, 220); 
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

        // CONTENT
        JPanel content = new JPanel(new GridBagLayout());
        content.setBackground(CafeTheme.BG_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        
        // 1. IKON (ATAS)
        gbc.gridx = 0; 
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(15, 0, 10, 0); 
        content.add(new QuestionIcon(), gbc);

        // 2. TEKS (BAWAH)
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 20, 10, 20);
        
        JLabel msgLabel = new JLabel("<html><div style='text-align: center; width: 250px;'>" + message + "</div></html>");
        msgLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        msgLabel.setForeground(CafeTheme.TEXT_COLOR);
        msgLabel.setHorizontalAlignment(SwingConstants.CENTER);
        content.add(msgLabel, gbc);

        mainPanel.add(content, BorderLayout.CENTER);

        // FOOTER
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        footer.setBackground(CafeTheme.BG_COLOR);
        
        RoundedButton btnYes = new RoundedButton("Ya");
        btnYes.setBackground(new Color(200, 80, 80)); 
        btnYes.setForeground(Color.WHITE);
        btnYes.setPreferredSize(new Dimension(80, 35));
        
        RoundedButton btnNo = new RoundedButton("Tidak");
        btnNo.setBackground(CafeTheme.BUTTON_COLOR); 
        btnNo.setPreferredSize(new Dimension(80, 35));
        
        footer.add(btnYes); footer.add(btnNo);
        mainPanel.add(footer, BorderLayout.SOUTH);
        
        add(mainPanel);

        btnYes.addActionListener(e -> { confirmed = true; dispose(); });
        btnNo.addActionListener(e -> { confirmed = false; dispose(); });
    }

    public boolean isConfirmed() { return confirmed; }

    class QuestionIcon extends JPanel {
        public QuestionIcon() { 
            setOpaque(false); 
            setPreferredSize(new Dimension(60, 60)); 
            setMinimumSize(new Dimension(60, 60));
        }
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // 1. Lingkaran Emas
            int circleSize = 50;
            int xCircle = (getWidth() - circleSize) / 2; // Tengah panel secara horizontal
            int yCircle = (getHeight() - circleSize) / 2;
            
            g2.setColor(new Color(220, 160, 50));
            g2.fillOval(xCircle, yCircle, circleSize, circleSize);
            
            // 2. Tanda Tanya Putih
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("SansSerif", Font.BOLD, 32));
            FontMetrics fm = g2.getFontMetrics();
            
            String txt = "?";
            // Hitung posisi X biar di tengah lingkaran
            int xText = xCircle + (circleSize - fm.stringWidth(txt)) / 2;
         
            // Rumus: yCircle + (circleSize/2) + (Ascent/2) - sedikit offset visual
            int yText = yCircle + (circleSize / 2) + (fm.getAscent() / 2) - 2;
            
            g2.drawString(txt, xText, yText);
            g2.dispose();
        }
    }
}