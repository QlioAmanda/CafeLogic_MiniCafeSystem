package cafe.gui;

import cafe.gui.admin.AdminPanel;
import cafe.gui.customer.CustomerPanel;
import cafe.gui.common.ConfirmDialog;
import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    
    private LoginPanel loginPanel;
    private AdminPanel adminPanel;
    private CustomerPanel customerPanel;

    public MainFrame() {
        setTitle("Cafe Logic App");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);

        initLayout();
        initPanels(); // <--- Pastikan ini dipanggil di sini!
        
        cardLayout.show(mainPanel, "LOGIN"); 
        
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                confirmExit();
            }
        });
    }

    private void initLayout() {
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        add(mainPanel);
    }

    private void initPanels() {
        // [PENTING] Inisialisasi variabel DULUan
        loginPanel = new LoginPanel(this);
        adminPanel = new AdminPanel(this);
        customerPanel = new CustomerPanel(this);

        // [PENTING] Baru ditambahkan ke panel SETELAH diinisialisasi
        // Jika urutannya terbalik, akan error NullPointerException "comp is null"
        if (loginPanel != null) mainPanel.add(loginPanel, "LOGIN");
        if (adminPanel != null) mainPanel.add(adminPanel, "ADMIN");
        if (customerPanel != null) mainPanel.add(customerPanel, "CUSTOMER");
    }

    public void showCard(String cardName) {
        if (cardName.equals("ADMIN")) adminPanel.refreshTable();
        if (cardName.equals("CUSTOMER")) customerPanel.refreshData();
        cardLayout.show(mainPanel, cardName);
    }
    
    private void confirmExit() {
        ConfirmDialog dialog = new ConfirmDialog(this, "KELUAR", "Yakin ingin menutup aplikasi?");
        dialog.setVisible(true);
        if (dialog.isConfirmed()) System.exit(0);
    }
}