package cafe.gui.customer;

import cafe.gui.CafeTheme;
import cafe.gui.components.RoundedButton;
import cafe.decorator.*; 
import cafe.model.MenuItem;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class DrinkOptionDialog extends JDialog {
    private MenuItem resultItem;
    private int quantity = 1; 
    private boolean confirmed = false;
    private DrinkInputPanel inputPanel; 

    public DrinkOptionDialog(Frame owner, MenuItem baseDrink) {
        super(owner, true);
        // [FIX 2]: Tinggi dialog dikurangi dari 550 menjadi 480
        setUndecorated(true); setSize(400, 480); setLocationRelativeTo(owner);
        setLayout(new BorderLayout());

        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(CafeTheme.BG_COLOR);
        main.setBorder(new LineBorder(CafeTheme.EXIT_BTN_COLOR, 3));

        // Header
        JPanel head = new JPanel(); head.setBackground(CafeTheme.EXIT_BTN_COLOR);
        JLabel title = new JLabel("KUSTOMISASI MINUMAN");
        title.setForeground(Color.WHITE); title.setFont(new Font("SansSerif", Font.BOLD, 16));
        head.add(title);
        main.add(head, BorderLayout.NORTH);

        // --- CONTENT WRAPPER ---
        JPanel contentWrapper = new JPanel(new BorderLayout());
        contentWrapper.setBackground(CafeTheme.BG_COLOR);
        
        // Nama Item
        JLabel lblName = new JLabel(baseDrink.getName());
        lblName.setFont(new Font("SansSerif", Font.BOLD, 20));
        lblName.setForeground(CafeTheme.EXIT_BTN_COLOR);
        lblName.setBorder(new EmptyBorder(15, 30, 0, 30)); 
        
        // Harga Dasar
        JLabel lblPrice = new JLabel("Harga Dasar: Rp " + String.format("%,.0f", baseDrink.getPrice())); 
        lblPrice.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblPrice.setForeground(Color.GRAY);
        lblPrice.setBorder(new EmptyBorder(0, 30, 0, 30));

        JPanel namePanel = new JPanel();
        namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.Y_AXIS));
        namePanel.setBackground(CafeTheme.BG_COLOR);
        namePanel.add(lblName);
        namePanel.add(lblPrice);
        
        contentWrapper.add(namePanel, BorderLayout.NORTH);

        // Input Panel (Form Kustomisasi)
        inputPanel = new DrinkInputPanel();
        contentWrapper.add(inputPanel, BorderLayout.CENTER);
        
        main.add(contentWrapper, BorderLayout.CENTER);
        // --- END CONTENT WRAPPER ---


        // Footer
        JPanel foot = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        foot.setBackground(CafeTheme.BG_COLOR);
        RoundedButton btnCancel = new RoundedButton("Batal");
        btnCancel.setBackground(new Color(200, 100, 100)); btnCancel.setForeground(Color.WHITE);
        RoundedButton btnAdd = new RoundedButton("Tambah");
        btnAdd.setBackground(CafeTheme.BUTTON_COLOR);
        
        btnCancel.setPreferredSize(new Dimension(100, 40));
        btnAdd.setPreferredSize(new Dimension(100, 40));
        
        btnCancel.addActionListener(e -> dispose());
        btnAdd.addActionListener(e -> processOrder(baseDrink));
        foot.add(btnCancel); foot.add(btnAdd);
        main.add(foot, BorderLayout.SOUTH);
        add(main);
    }

    private void processOrder(MenuItem item) {
        MenuItem temp = item;
        String suhu = inputPanel.rbIce.isSelected() ? "Dingin" : "Panas";
        temp = new TemperatureDecorator(temp, suhu);

        int idx = inputPanel.cbSize.getSelectedIndex();
        if (idx == 1) temp = new SizeDecorator(temp, "Sedang", 3000);
        else if (idx == 2) temp = new SizeDecorator(temp, "Besar", 5000);
        else temp = new SizeDecorator(temp, "Kecil", 0);

        if (inputPanel.chkBoba.isSelected()) temp = new ToppingDecorator(temp, "Boba", 3000);
        if (inputPanel.chkCream.isSelected()) temp = new ToppingDecorator(temp, "Krim", 4000);
        if (inputPanel.chkCheese.isSelected()) temp = new ToppingDecorator(temp, "Keju", 5000);
        if (inputPanel.chkSugar.isSelected()) temp = new ToppingDecorator(temp, "Gula", 2000);
        
        this.resultItem = temp;
        this.quantity = (int) inputPanel.spinQty.getValue();
        this.confirmed = true;
        dispose();
    }

    public MenuItem getResultItem() { return confirmed ? resultItem : null; }
    public int getQuantity() { return confirmed ? quantity : 0; }
}