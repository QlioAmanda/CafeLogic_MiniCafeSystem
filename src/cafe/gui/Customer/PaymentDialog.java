package cafe.gui.customer; 

import cafe.gui.CafeTheme;
import cafe.gui.components.RoundedButton;
import cafe.gui.common.ErrorDialog; 
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

import javax.swing.SwingConstants; 

public class PaymentDialog extends JDialog {
    
    private double grandTotal;
    private double paymentAmount = -1;
    private boolean confirmed = false;
    private JTextField txtCash;
    
    private static final String FONT_NAME = "SansSerif";

    public PaymentDialog(Frame owner, double subtotal, double ppn, double total) {
        super(owner, true);
        setUndecorated(true);
        setSize(350, 450);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout());
        this.grandTotal = total;

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(CafeTheme.BG_COLOR);
        mainPanel.setBorder(new LineBorder(CafeTheme.EXIT_BTN_COLOR, 3));

        JPanel header = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 15));
        header.setBackground(CafeTheme.EXIT_BTN_COLOR);
        JLabel title = new JLabel("PEMBAYARAN");
        
        title.setFont(new Font(FONT_NAME, Font.BOLD, 18));
        title.setForeground(Color.WHITE);
        header.add(title);
        mainPanel.add(header, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(CafeTheme.BG_COLOR);
        contentPanel.setBorder(new EmptyBorder(20, 30, 20, 30));

        addDetailRow(contentPanel, "Subtotal:", subtotal, false);
        addDetailRow(contentPanel, "PPN (10%):", ppn, false);
        contentPanel.add(Box.createVerticalStrut(10));
        JSeparator sep = new JSeparator();
        sep.setForeground(CafeTheme.EXIT_BTN_COLOR);
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 2));
        contentPanel.add(sep);
        contentPanel.add(Box.createVerticalStrut(10));
        addDetailRow(contentPanel, "TOTAL TAGIHAN:", total, true);
        contentPanel.add(Box.createVerticalStrut(40));

        JLabel lblInput = new JLabel("Masukkan Uang Pembayaran:");
        
        lblInput.setFont(new Font(FONT_NAME, Font.BOLD, 14));
        lblInput.setForeground(CafeTheme.TEXT_COLOR);
        
        lblInput.setAlignmentX(Component.CENTER_ALIGNMENT); 
        contentPanel.add(lblInput);
        contentPanel.add(Box.createVerticalStrut(10));

        txtCash = new JTextField();
        
        txtCash.setFont(new Font(FONT_NAME, Font.BOLD, 22));
        
        txtCash.setHorizontalAlignment(SwingConstants.CENTER); 
        
        txtCash.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(CafeTheme.EXIT_BTN_COLOR, 2), 
            new EmptyBorder(5, 5, 5, 5)
        ));
        txtCash.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50)); 
        txtCash.setAlignmentX(Component.CENTER_ALIGNMENT); 
        contentPanel.add(txtCash);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        footer.setBackground(CafeTheme.BG_COLOR);
        RoundedButton btnCancel = new RoundedButton("Batal");
        btnCancel.setBackground(new Color(200, 80, 80));
        btnCancel.setForeground(Color.WHITE);
        btnCancel.setPreferredSize(new Dimension(120, 45));
        RoundedButton btnPay = new RoundedButton("BAYAR");
        btnPay.setBackground(new Color(46, 125, 50));
        btnPay.setForeground(Color.WHITE);
        btnPay.setPreferredSize(new Dimension(120, 45));
        footer.add(btnCancel); footer.add(btnPay);
        mainPanel.add(footer, BorderLayout.SOUTH);
        add(mainPanel);

        btnCancel.addActionListener(e -> dispose());
        btnPay.addActionListener(e -> handlePayment());
    }

    private void handlePayment() {
        try {
            double inputMoney = Double.parseDouble(txtCash.getText());
            if (inputMoney < grandTotal) {
                String msg = "Uang kurang!<br>Kurang: Rp " + String.format("%,.0f", (grandTotal - inputMoney));
                new ErrorDialog(this, msg).setVisible(true);
            } else {
                paymentAmount = inputMoney;
                confirmed = true;
                dispose();
            }
        } catch (NumberFormatException e) {
            new ErrorDialog(this, "Masukkan angka yang valid!").setVisible(true);
        }
    }
    public boolean isConfirmed() { return confirmed; }
    public double getPaymentAmount() { return paymentAmount; }
    
    private void addDetailRow(JPanel panel, String label, double value, boolean isBold) {
        JPanel row = new JPanel(new BorderLayout());
        row.setBackground(CafeTheme.BG_COLOR);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        JLabel lbl = new JLabel(label);
        JLabel val = new JLabel("Rp " + String.format("%,.0f", value));
        if (isBold) {
            
            lbl.setFont(new Font(FONT_NAME, Font.BOLD, 16));
            lbl.setForeground(CafeTheme.EXIT_BTN_COLOR);
            
            val.setFont(new Font(FONT_NAME, Font.BOLD, 18));
            val.setForeground(CafeTheme.EXIT_BTN_COLOR);
        } else {
            
            lbl.setFont(new Font(FONT_NAME, Font.PLAIN, 14));
            
            val.setFont(new Font(FONT_NAME, Font.BOLD, 14));
        }
        row.add(lbl, BorderLayout.WEST);
        row.add(val, BorderLayout.EAST);
        panel.add(row);
        
        panel.add(Box.createVerticalStrut(5)); 
    }
}