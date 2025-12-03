package cafe.gui.customer;

import cafe.model.MenuItem;
import cafe.gui.components.DashedSeparator;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReceiptPanel extends JPanel {
    public ReceiptPanel(List<MenuItem> items, double sub, double ppn, double tot, double cash) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(10, 15, 10, 15));

        addText("CAFE LOGIC", 22, true);
        addText("Selamat Menikmati :)", 11, false);
        add(Box.createVerticalStrut(10));
        add(new DashedSeparator());
        add(Box.createVerticalStrut(5));

        // List Item Logic
        Map<String, List<MenuItem>> grouped = items.stream().collect(Collectors.groupingBy(MenuItem::getName));
        for (var entry : grouped.entrySet()) {
            double price = entry.getValue().get(0).getPrice() * entry.getValue().size();
            addDetail(entry.getKey(), "x" + entry.getValue().size(), price);
        }

        add(Box.createVerticalStrut(5));
        add(new DashedSeparator());
        addTotal("Subtotal", sub, false);
        addTotal("PPN (10%)", ppn, false);
        add(new DashedSeparator());
        addTotal("TOTAL", tot, true);
        addTotal("Tunai", cash, false);
        addTotal("Kembali", cash - tot, false);
    }

    private void addText(String txt, int size, boolean bold) {
        JLabel l = new JLabel(txt);
        l.setFont(new Font("SansSerif", bold ? Font.BOLD : Font.PLAIN, size));
        l.setAlignmentX(CENTER_ALIGNMENT);
        add(l);
    }

    private void addDetail(String name, String qty, double price) {
        JPanel p = new JPanel(new BorderLayout()); p.setBackground(Color.WHITE);
        p.add(new JLabel(name), BorderLayout.WEST);
        p.add(new JLabel(qty + "  Rp" + (int)price), BorderLayout.EAST);
        p.setMaximumSize(new Dimension(300, 20));
        add(p);
    }

    private void addTotal(String label, double val, boolean bold) {
        JPanel p = new JPanel(new BorderLayout()); p.setBackground(Color.WHITE);
        JLabel l = new JLabel(label);
        JLabel v = new JLabel("Rp " + String.format("%,.0f", val));
        if (bold) { Font f = new Font("SansSerif", Font.BOLD, 14); l.setFont(f); v.setFont(f); }
        p.add(l, BorderLayout.WEST); p.add(v, BorderLayout.EAST);
        p.setMaximumSize(new Dimension(300, 20));
        add(p);
    }
}