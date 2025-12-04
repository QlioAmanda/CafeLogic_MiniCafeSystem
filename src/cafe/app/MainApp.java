package cafe.app;

import cafe.factory.MenuFactory;
import cafe.service.MenuService;
import cafe.gui.MainFrame; 
import javax.swing.SwingUtilities; 
import java.util.logging.Logger; 

public class MainApp {
    
    // Deklarasi logger statis untuk kelas ini
    private static final Logger LOGGER = Logger.getLogger(MainApp.class.getName());

    public static void main(String[] args) {
        // 1. Load Data Awal (agar menu tidak kosong)
        initializeData();

        // 2. Tampilkan GUI
        SwingUtilities.invokeLater(() -> {
            MainFrame app = new MainFrame();
            app.setVisible(true);
            
            LOGGER.info("GUI Berhasil Dijalankan. Cek jendela baru yang muncul.");
        });
    }

    private static void initializeData() {
        MenuService menuService = MenuService.getInstance();
        MenuFactory menuFactory = new MenuFactory();

        // Data Dummy (hanya ditambahkan jika list kosong)
        if (menuService.getAllMenu().isEmpty()) {
            menuService.addMenu(menuFactory.createFood("French Fries", 12000, 20));
            menuService.addMenu(menuFactory.createFood("Sandwich", 20000, 15));
            menuService.addMenu(menuFactory.createFood("Chicken Popcorn", 18000, 15));

            menuService.addMenu(menuFactory.createDrink("Coffee", 10000, 50));
            menuService.addMenu(menuFactory.createDrink("Latte", 15000, 40));
            menuService.addMenu(menuFactory.createDrink("Matcha Latte", 18000, 30));
           
            LOGGER.info("System: Data awal berhasil dimuat.");
        }
    }
}