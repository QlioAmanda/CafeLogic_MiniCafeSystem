package cafe.gui.components;

import cafe.gui.CafeTheme;
import javax.swing.*;
import java.awt.*;

public class CustomIcon extends JPanel {
    public enum IconType { INFO, SUCCESS, WARNING, QUESTION }
    private IconType type;

    public CustomIcon(IconType type) {
        this.type = type;
        setOpaque(false);
        setPreferredSize(new Dimension(50, 50));
        setMinimumSize(new Dimension(50, 50));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();
        int size = 46;

        // Pilih Warna & Gambar Background
        if (type == IconType.WARNING) g2.setColor(new Color(200, 60, 60)); // Merah
        else if (type == IconType.QUESTION) g2.setColor(new Color(220, 160, 50)); // Kuning
        else if (type == IconType.SUCCESS) g2.setColor(new Color(46, 125, 50)); // Hijau
        else g2.setColor(CafeTheme.EXIT_BTN_COLOR); // Coklat (Info)

        if (type == IconType.WARNING) {
            g2.fillPolygon(new int[]{w/2, w-2, 2}, new int[]{2, size, size}, 3); // Segitiga
        } else {
            g2.fillOval(2, 2, 46, 46); // Lingkaran
        }

        // Gambar Simbol Putih
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(4, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

        if (type == IconType.SUCCESS) {
            g2.drawLine(12, 25, 20, 34); g2.drawLine(20, 34, 36, 16); // Ceklis
        } else if (type == IconType.WARNING) {
            g2.fillRoundRect((w/2)-3, 14, 6, 16, 2, 2); g2.fillOval((w/2)-3, 34, 6, 6); // Tanda Seru !
        } else {
            g2.setFont(new Font("SansSerif", Font.BOLD, 30));
            FontMetrics fm = g2.getFontMetrics();
            String txt = (type == IconType.QUESTION) ? "?" : "i";
            int txtY = (type == IconType.QUESTION) ? -2 : 0;
            g2.drawString(txt, 2 + (46 - fm.stringWidth(txt))/2, 2 + (46 - fm.getHeight())/2 + fm.getAscent() + txtY);
        }
        g2.dispose();
    }
}