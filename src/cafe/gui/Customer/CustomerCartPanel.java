package cafe.gui.customer;

import cafe.gui.CafeTheme;
import cafe.gui.components.RoundedButton;
import cafe.model.MenuItem;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class CustomerCartPanel extends JPanel {

    private DefaultListModel<String> cartModel;
    private JLabel totalLabel;
    private CustomerPanel parentPanel; 

    public CustomerCartPanel(CustomerPanel parentPanel) {
        this.parentPanel = parentPanel;
        setLayout(new BorderLayout(0, 10));
        setPreferredSize(new Dimension(320, 0));
        setOpaque(false);
        initUI();
    }

    private void initUI() {
        JLabel header = new JLabel("KERANJANG ANDA", SwingConstants.CENTER);
        header.setFont(CafeTheme.FONT_BUTTON);
        add(header, BorderLayout.NORTH);

        cartModel = new DefaultListModel<>();
        JList<String> cartList = new JList<>(cartModel);
        cartList.setFont(new Font("Monospaced", Font.PLAIN, 14));
        cartList.setFixedCellHeight(30);
        add(new JScrollPane(cartList), BorderLayout.CENTER);

        add(initActionPanel(), BorderLayout.SOUTH);
    }

    private JPanel initActionPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);

        totalLabel = new JLabel("Total: Rp 0", SwingConstants.CENTER);
        totalLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        totalLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        RoundedButton btnCheckout = new RoundedButton("Bayar & Struk");
        btnCheckout.setBackground(new Color(60, 140, 60));
        btnCheckout.setPreferredSize(new Dimension(300, 50));
        btnCheckout.setMaximumSize(new Dimension(320, 50));
        btnCheckout.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnCheckout.addActionListener(e -> parentPanel.checkoutAction());

        RoundedButton btnLogout = new RoundedButton("Keluar");
        btnLogout.setBackground(new Color(180, 60, 60));
        btnLogout.setPreferredSize(new Dimension(300, 40));
        btnLogout.setMaximumSize(new Dimension(320, 40));
        btnLogout.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnLogout.addActionListener(e -> parentPanel.logoutAction());

        panel.add(totalLabel);
        panel.add(Box.createVerticalStrut(15));
        panel.add(btnCheckout);
        panel.add(Box.createVerticalStrut(10));
        panel.add(btnLogout);

        return panel;
    }

    public void updateDisplay(Map<String, List<MenuItem>> grouped) {
        cartModel.clear();
        double grandTotal = 0;

        for (Map.Entry<String, List<MenuItem>> entry : grouped.entrySet()) {
            double price = entry.getValue().get(0).getPrice();
            int qty = entry.getValue().size();
            double sub = price * qty;
            cartModel.addElement(String.format("%s x%d - %,.0f", entry.getKey(), qty, sub));
            grandTotal += sub;
        }

        totalLabel.setText(String.format("Total: Rp %,.0f", grandTotal));
    }
}
