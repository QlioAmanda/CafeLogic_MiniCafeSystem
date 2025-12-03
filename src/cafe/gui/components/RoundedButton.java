package cafe.gui.components;

import cafe.gui.CafeTheme;
import javax.swing.*;
import java.awt.*;

public class RoundedButton extends JButton {
    private int arcSize = 20; // Default lengkungan

    public RoundedButton(String label) {
        super(label);
        // Default style
        setFont(new Font("SansSerif", Font.BOLD, 14));
        setBackground(CafeTheme.BUTTON_COLOR);
        setForeground(Color.WHITE);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
    }

    // FITUR PENTING: Method ini dibutuhkan agar LoginPanel bisa bikin tombol lebih bulat
    public void setArc(int arc) {
        this.arcSize = arc;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Efek Bayangan Halus
        g2.setColor(new Color(0, 0, 0, 30));
        g2.fillRoundRect(2, 2, getWidth(), getHeight(), arcSize, arcSize);

        // Warna Tombol
        if (getModel().isPressed()) {
            g2.setColor(getBackground().darker());
        } else if (getModel().isRollover()) {
            g2.setColor(getBackground().brighter());
        } else {
            g2.setColor(getBackground());
        }
        
        g2.fillRoundRect(0, 0, getWidth() - 2, getHeight() - 2, arcSize, arcSize);
        super.paintComponent(g);
        g2.dispose();
    }
}