package cafe.gui.customer;

import cafe.gui.CafeTheme;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class DrinkInputPanel extends JPanel {
    
    private JRadioButton rbIce;
    private JRadioButton rbHot;
    
    private JComboBox<String> cbSize;
    
    private JCheckBox chkBoba; 
    private JCheckBox chkCream; 
    private JCheckBox chkCheese; 
    private JCheckBox chkSugar;
    
    private JSpinner spinQty;

    public DrinkInputPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(CafeTheme.BG_COLOR);
    
        setBorder(new EmptyBorder(10, 30, 0, 30)); 
        
        initSuhu();
        initUkuran();
        initTopping();
        initQty();
    }

    private void initSuhu() {
        addLabel("Suhu");
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));
        p.setBackground(CafeTheme.BG_COLOR); 
        p.setAlignmentX(LEFT_ALIGNMENT);
        
        p.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        
        rbIce = new JRadioButton("Dingin"); 
        rbHot = new JRadioButton("Panas");
        
        rbIce.setBackground(CafeTheme.BG_COLOR);
        rbHot.setBackground(CafeTheme.BG_COLOR);
        rbIce.setFocusPainted(false);
        rbHot.setFocusPainted(false);
        rbIce.setSelected(true);

        ButtonGroup bg = new ButtonGroup(); bg.add(rbIce); bg.add(rbHot);
        p.add(rbIce); p.add(rbHot);
        add(p);
    }

    private void initUkuran() {
        addLabel("Ukuran Gelas");
        String[] s = {"Kecil (+0)", "Sedang (+3.000)", "Besar (+5.000)"};
        cbSize = new JComboBox<>(s);
        cbSize.setAlignmentX(LEFT_ALIGNMENT);
        
        // Lebar 150px
        cbSize.setMaximumSize(new Dimension(150, 35)); 
        
        cbSize.setBackground(Color.WHITE); 
        add(cbSize);
    }

    private void initTopping() {
        addLabel("Topping Tambahan");
        chkBoba = createCheck("Boba (+3.000)");
        chkCream = createCheck("Krim (+4.000)");
        chkCheese = createCheck("Keju (+5.000)");
        chkSugar = createCheck("Gula (+2.000)");
        
        add(chkBoba); add(chkCream); add(chkCheese); add(chkSugar);
    }

    private JCheckBox createCheck(String text) {
        JCheckBox c = new JCheckBox(text);
        c.setAlignmentX(LEFT_ALIGNMENT);
        c.setBackground(CafeTheme.BG_COLOR); 
        c.setFocusPainted(false);
        return c;
    }
    
    private void initQty() {
        addLabel("Jumlah");
        spinQty = new JSpinner(new SpinnerNumberModel(1, 1, 50, 1));
        spinQty.setMaximumSize(new Dimension(100, 35));
        spinQty.setAlignmentX(LEFT_ALIGNMENT);
        add(spinQty);
    }

    private void addLabel(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("SansSerif", Font.BOLD, 14));
        l.setAlignmentX(LEFT_ALIGNMENT);
        l.setForeground(CafeTheme.TEXT_COLOR); 
        add(l);
    }
    
    // --- Accessor (Getter) Methods untuk mengakses komponen private ---
    
    public JRadioButton getRbIce() { return rbIce; }
    public JRadioButton getRbHot() { return rbHot; }
    public JComboBox<String> getCbSize() { return cbSize; }
    public JCheckBox getChkBoba() { return chkBoba; }
    public JCheckBox getChkCream() { return chkCream; }
    public JCheckBox getChkCheese() { return chkCheese; }
    public JCheckBox getChkSugar() { return chkSugar; }
    public JSpinner getSpinQty() { return spinQty; }
}