package cafe.gui.components;

import javax.swing.*;
import java.awt.*;

public class DashedSeparator extends JComponent {
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(Color.LIGHT_GRAY);
        Stroke dashed = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 
                                      0, new float[]{5}, 0);
        g2.setStroke(dashed);
        g2.drawLine(0, getHeight()/2, getWidth(), getHeight()/2);
        g2.dispose();
    }
    
    @Override
    public Dimension getMaximumSize() {
        return new Dimension(Integer.MAX_VALUE, 8); 
    }
}