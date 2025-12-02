package cafe.gui.customer; 

import cafe.gui.MainFrame;
import cafe.gui.CafeTheme;
import cafe.gui.common.MessageDialog; 
import cafe.service.MenuService;
import cafe.service.OrderService;
import cafe.model.MenuItem;
import cafe.model.Drink;
import cafe.exception.InsufficientStockException;
import cafe.util.ReceiptPrinter;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class CustomerPanel extends JPanel {
    private MenuService menuService = MenuService.getInstance();
    private OrderService orderService = OrderService.getInstance();
    private MainFrame mainFrame;
    
    // Komponen yang dipisah ke file lain
    private CustomerMenuPanel menuPanel;
    private CustomerCartPanel cartPanel;
    
    private String currentCategory = "FOOD"; 

    public CustomerPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout(15, 15));
        setBackground(CafeTheme.BG_COLOR);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Inisialisasi Panel Kiri (Menu)
        menuPanel = new CustomerMenuPanel(this);
        add(menuPanel, BorderLayout.CENTER);
        
        // Inisialisasi Panel Kanan (Keranjang)
        cartPanel = new CustomerCartPanel(this);
        add(cartPanel, BorderLayout.EAST);

        refreshData(); 
    }

    // --- LOGIKA UTAMA ---

    /**
     * Mengganti kategori menu yang ditampilkan (Makanan/Minuman).
     */
    public void switchCategory(String cat) {
        this.currentCategory = cat;
        refreshData();
    }

    /**
     * Memperbarui tampilan tabel menu dan list keranjang.
     */
    public void refreshData() {
        if (menuPanel != null) menuPanel.refreshData(currentCategory);
        updateCartDisplay();
    }

    private void updateCartDisplay() {
        if (cartPanel != null) {
            // Grouping item keranjang agar item sama tidak muncul berkali-kali
            Map<String, List<MenuItem>> grouped = orderService.getCart().stream()
                .collect(Collectors.groupingBy(MenuItem::getName, LinkedHashMap::new, Collectors.toList()));
            cartPanel.updateDisplay(grouped);
        }
    }

    /**
     * Logika saat tombol "Tambah" ditekan.
     * Dipanggil oleh CustomerMenuPanel.
     */
    public void addToCartAction(JTable table, TableModel model) {
        int row = table.getSelectedRow();
        if (row == -1) {
            new MessageDialog(mainFrame, "INFO", "Pilih menu dulu!").setVisible(true);
            return;
        }
        
        String name = (String) model.getValueAt(row, 0);
        // Cari object MenuItem asli berdasarkan nama
        MenuItem item = menuService.getAllMenu().stream()
                .filter(m -> m.getName().equals(name)).findFirst().orElse(null);
        
        if (item != null) processAddItem(item);
    }

    private void processAddItem(MenuItem item) {
        try {
            // Jika minuman, tampilkan opsi kustomisasi (Gula/Es/Toping)
            if (item instanceof Drink) {
                DrinkOptionDialog d = new DrinkOptionDialog(mainFrame, item);
                d.setVisible(true);
                if (d.getResultItem() != null) addMultiple(d.getResultItem(), d.getQuantity());
            } else {
                // Jika makanan, langsung tanya jumlah
                QuantityDialog d = new QuantityDialog(mainFrame);
                d.setVisible(true);
                if (d.getQuantity() > 0) addMultiple(item, d.getQuantity());
            }
        } catch (InsufficientStockException ex) {
            new MessageDialog(mainFrame, "STOK HABIS", "Stok tidak cukup!").setVisible(true);
        }
    }

    private void addMultiple(MenuItem item, int qty) {
        for(int i=0; i<qty; i++) orderService.addToCart(item);
        updateCartDisplay();
    }

    /**
     * Logika Checkout (Pembayaran & Struk).
     * Dipanggil oleh CustomerCartPanel.
     */
    public void checkoutAction() {
        if (orderService.getCart().isEmpty()) {
            new MessageDialog(mainFrame, "INFO", "Keranjang kosong!").setVisible(true); 
            return;
        }
        
        List<MenuItem> items = new ArrayList<>(orderService.getCart());
        double sub = items.stream().mapToDouble(MenuItem::getPrice).sum();
        double ppn = sub * 0.1;
        
        PaymentDialog pd = new PaymentDialog(mainFrame, sub, ppn, sub + ppn);
        pd.setVisible(true);
        
        if (pd.isConfirmed()) {
            orderService.checkout(); // Simpan Transaksi & Kosongkan Keranjang
            
            // Tampilkan Struk di Layar
            new ReceiptDialog(mainFrame, items, sub, ppn, sub+ppn, pd.getPaymentAmount()).setVisible(true);
            
            // Cetak Struk ke TXT
            ReceiptPrinter.printToTxt(items, sub+ppn, pd.getPaymentAmount());
            
            refreshData();
        }
    }

    public void logoutAction() {
        orderService.clear();
        mainFrame.showCard("LOGIN");
    }
}