package cafe.gui.admin; 

import cafe.gui.MainFrame;
import cafe.gui.CafeTheme;
import cafe.gui.components.RoundedButton;
import cafe.gui.common.ConfirmDialog; 
import cafe.gui.common.ErrorDialog;
import cafe.gui.common.MessageDialog; 
import cafe.service.MenuService;
import cafe.model.MenuItem;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class AdminPanel extends JPanel {
    
    // Perbaikan 2: Definisikan konstanta untuk literal yang berulang
    private static final String SUCCESS_TITLE = "SUKSES";
    
    // Perbaikan 1: Tambahkan 'transient' untuk mematuhi S1948 (Non-serializable field)
    private transient MenuService menuService = MenuService.getInstance(); 
    private AdminTablePanel tablePanel; 
    private MainFrame mainFrame;

    public AdminPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout());
        setBackground(CafeTheme.BG_COLOR);

        add(initNavbar(), BorderLayout.NORTH);
        
        JPanel content = new JPanel(new BorderLayout(0, 20));
        content.setBackground(CafeTheme.BG_COLOR);
        content.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        tablePanel = new AdminTablePanel();
        content.add(tablePanel, BorderLayout.CENTER);
        content.add(initButtons(), BorderLayout.SOUTH);
        
        add(content, BorderLayout.CENTER);
    }

    public void refreshTable() {
        if (tablePanel != null) tablePanel.refreshTable();
    }

    private JPanel initNavbar() {
        JPanel navbar = new JPanel(new BorderLayout());
        navbar.setBackground(CafeTheme.EXIT_BTN_COLOR);
        navbar.setPreferredSize(new Dimension(0, 60));
        navbar.setBorder(new EmptyBorder(0, 20, 0, 20));

        JLabel title = new JLabel("DASHBOARD ADMIN");
        title.setFont(CafeTheme.FONT_BUTTON);
        title.setForeground(Color.WHITE);
        navbar.add(title, BorderLayout.WEST);

        RoundedButton btnLogout = new RoundedButton("Keluar");
        btnLogout.setBackground(new Color(200, 60, 60)); 
        btnLogout.setPreferredSize(new Dimension(100, 35));
        btnLogout.addActionListener(e -> mainFrame.showCard("LOGIN"));
        
        JPanel ctr = new JPanel(new GridBagLayout()); 
        ctr.setOpaque(false); 
        ctr.add(btnLogout);
        navbar.add(ctr, BorderLayout.EAST);
        
        return navbar;
    }

    private JPanel initButtons() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        p.setBackground(CafeTheme.BG_COLOR);

        // [PERBAIKAN] Tambahkan Ikon Unicode di teks tombol
        addButton(p, "+ Tambah", new Color(46, 125, 50), this::showAddDialog);
        addButton(p, "\u270E Ubah", new Color(255, 143, 0), this::showEditDialog); // Ikon Pensil
        addButton(p, "\uD83D\uDDD1 Hapus", new Color(198, 40, 40), this::handleDelete); // Ikon Sampah

        return p;
    }

    private void addButton(JPanel p, String text, Color bg, Runnable action) {
        RoundedButton btn = new RoundedButton(text);
        btn.setBackground(bg);
        btn.setPreferredSize(new Dimension(140, 45));
        btn.addActionListener(e -> action.run());
        p.add(btn);
    }

    // --- LOGIC dengan POPUP SUKSES ---

    private void showAddDialog() {
        MenuDialog d = new MenuDialog(mainFrame, null);
        d.setVisible(true);
        
        if (d.isConfirmed()) {
            menuService.addMenu(d.getResultItem());
            refreshTable();
            // Menggunakan konstanta SUCCESS_TITLE
            new MessageDialog(mainFrame, SUCCESS_TITLE, "Menu berhasil ditambahkan!").setVisible(true);
        }
    }

    private void showEditDialog() {
        int r = tablePanel.getSelectedRow();
        if (r == -1) { 
            new ErrorDialog(mainFrame, "Pilih baris dulu!").setVisible(true); 
            return; 
        }
        
        MenuItem item = menuService.getAllMenu().get(r);
        MenuDialog d = new MenuDialog(mainFrame, item);
        d.setVisible(true);
        
        if (d.isConfirmed()) {
            menuService.editMenu(r, d.getResultItem());
            refreshTable();
            // Menggunakan konstanta SUCCESS_TITLE
            new MessageDialog(mainFrame, SUCCESS_TITLE, "Menu berhasil diperbarui!").setVisible(true);
        }
    }

    private void handleDelete() {
        int r = tablePanel.getSelectedRow();
        if (r == -1) { 
            new ErrorDialog(mainFrame, "Pilih baris dulu!").setVisible(true); 
            return; 
        }
        
        ConfirmDialog d = new ConfirmDialog(mainFrame, "HAPUS", "Yakin hapus menu ini?");
        d.setVisible(true);
        
        if (d.isConfirmed()) {
            menuService.deleteMenu(r);
            refreshTable();
            // Menggunakan konstanta SUCCESS_TITLE
            new MessageDialog(mainFrame, SUCCESS_TITLE, "Menu berhasil dihapus!").setVisible(true);
        }
    }
}