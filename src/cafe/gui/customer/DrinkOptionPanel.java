package cafe.gui.customer;

import cafe.gui.CafeTheme;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class DrinkOptionPanel extends JPanel {
    public JRadioButton rbIce, rbHot;
    public JComboBox<String> cbSize;
    public JCheckBox chkBoba, chkCream, chkCheese, chkSugar;
    public JSpinner spinQty;

    public DrinkOptionPanel(String drinkName, double basePrice) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(CafeTheme.BG_COLOR);
        setBorder(new EmptyBorder(10, 30, 10, 30));

        JLabel lblName = new JLabel(drinkName);
        lblName.setFont(new Font("SansSerif", Font.BOLD, 20));
        lblName.setForeground(CafeTheme.EXIT_BTN_COLOR);
        add(lblName);
        
        JLabel lblPrice = new JLabel("Harga Dasar: Rp " + String.format("%,.0f", basePrice));
        lblPrice.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblPrice.setForeground(Color.GRAY);
        add(lblPrice);
        add(Box.createVerticalStrut(15)); 

        addLabel("Suhu");
        JPanel temp = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));
        temp.setBackground(CafeTheme.BG_COLOR);
        rbIce = new JRadioButton("Dingin"); rbHot = new JRadioButton("Panas");
        rbIce.setBackground(CafeTheme.BG_COLOR); rbHot.setBackground(CafeTheme.BG_COLOR);
        rbIce.setSelected(true);
        ButtonGroup bg = new ButtonGroup(); bg.add(rbIce); bg.add(rbHot);
        temp.add(rbIce); temp.add(rbHot);
        add(temp); add(Box.createVerticalStrut(10));

        addLabel("Ukuran Gelas");
        cbSize = new JComboBox<>(new String[]{"Kecil (+0)", "Sedang (+3.000)", "Besar (+5.000)"});
        cbSize.setBackground(Color.WHITE);
        cbSize.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        add(cbSize); add(Box.createVerticalStrut(10));

        addLabel("Topping Tambahan");
        chkBoba = mkCheck("Boba (+3.000)"); chkCream = mkCheck("Krim (+4.000)");
        chkCheese = mkCheck("Keju (+5.000)"); chkSugar = mkCheck("Gula (+2.000)");
        add(chkBoba); add(chkCream); add(chkCheese); add(chkSugar);
        add(Box.createVerticalStrut(10));

        addLabel("Jumlah");
        spinQty = new JSpinner(new SpinnerNumberModel(1, 1, 50, 1));
        spinQty.setMaximumSize(new Dimension(100, 35));
        add(spinQty);
    }

    private void addLabel(String t) {
        JLabel l = new JLabel(t);
        l.setFont(new Font("SansSerif", Font.BOLD, 14));
        l.setForeground(CafeTheme.TEXT_COLOR);
        add(l); add(Box.createVerticalStrut(5));
    }
    private JCheckBox mkCheck(String t) {
        JCheckBox c = new JCheckBox(t); c.setBackground(CafeTheme.BG_COLOR); return c;
    }
}