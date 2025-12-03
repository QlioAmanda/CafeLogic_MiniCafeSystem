package cafe.gui.customer;

import cafe.gui.CafeTheme;
import cafe.model.MenuItem;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.List;

public class ReceiptDialog extends JDialog {
    public ReceiptDialog(Frame owner, List<MenuItem> items, double sub, double ppn, double tot, double cash) {
        super(owner, true);
        setUndecorated(true); setSize(340, 500); setLocationRelativeTo(owner);
        setLayout(new BorderLayout());

        JPanel main = new JPanel(new BorderLayout());
        main.setBorder(new LineBorder(CafeTheme.EXIT_BTN_COLOR, 2));
        
        // Panggil Panel Konten Struk
        main.add(new ReceiptPanel(items, sub, ppn, tot, cash), BorderLayout.CENTER);

        JButton btnClose = new JButton("Tutup Struk");
        btnClose.setBackground(CafeTheme.EXIT_BTN_COLOR);
        btnClose.setForeground(Color.WHITE);
        btnClose.setFocusPainted(false);
        btnClose.setPreferredSize(new Dimension(340, 45));
        btnClose.addActionListener(e -> dispose());

        main.add(btnClose, BorderLayout.SOUTH);
        add(main);
    }
}