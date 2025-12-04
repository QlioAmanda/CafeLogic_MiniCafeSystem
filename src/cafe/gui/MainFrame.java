package cafe.gui;

import cafe.gui.admin.AdminPanel;
import cafe.gui.customer.CustomerPanel;
import cafe.gui.common.ConfirmDialog;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class MainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;

    // Map untuk menyimpan aksi refresh tiap panel
    private final Map<String, Runnable> refreshActions = new HashMap<>();

    public MainFrame() {
        setTitle("Cafe Logic App");
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);

        initLayout();
        initPanels();

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
        // Panel sebagai variabel lokal (tidak jadi field)
        LoginPanel loginPanel = new LoginPanel(this);
        AdminPanel adminPanel = new AdminPanel(this);
        CustomerPanel customerPanel = new CustomerPanel(this);

        // Tambahkan ke mainPanel
        mainPanel.add(loginPanel, "LOGIN");
        mainPanel.add(adminPanel, "ADMIN");
        mainPanel.add(customerPanel, "CUSTOMER");

        // Daftarkan fungsi refresh
        refreshActions.put("ADMIN", adminPanel::refreshTable);
        refreshActions.put("CUSTOMER", customerPanel::refreshData);
    }

    public void showCard(String cardName) {
        // Jalankan fungsi refresh jika ada
        Runnable action = refreshActions.get(cardName);
        if (action != null) action.run();

        cardLayout.show(mainPanel, cardName);
    }

    private void confirmExit() {
        ConfirmDialog dialog = new ConfirmDialog(this, "KELUAR", "Yakin ingin menutup aplikasi?");
        dialog.setVisible(true);
        if (dialog.isConfirmed()) System.exit(0);
    }
}
