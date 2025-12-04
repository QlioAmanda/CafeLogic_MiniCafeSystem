package cafe.util;

import cafe.model.MenuItem;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;

public class ReceiptPrinter {

    private static final Logger LOGGER = Logger.getLogger(ReceiptPrinter.class.getName());

    private static final String SEPARATOR_FULL = "========================================";
    private static final String SEPARATOR_DASH = "----------------------------------------";
    
    private ReceiptPrinter() {
        // Mencegah instansiasi (utility class)
    }

    public static void printToTxt(List<MenuItem> items, double total, double bayar) {
        
        String newLine = System.lineSeparator();
        
        try (FileWriter fw = new FileWriter("struk.txt")) {
            StringBuilder sb = new StringBuilder();
            sb.append(SEPARATOR_FULL).append(newLine);
            sb.append("          CAFE LOGIC RECEIPT          ").append(newLine);
            sb.append(SEPARATOR_FULL).append(newLine);
            sb.append("Tanggal : ").append(LocalDate.now()).append(newLine).append(newLine);
            
            sb.append(String.format("%-25s %13s%n", "ITEM", "HARGA"));
            sb.append(SEPARATOR_DASH).append(newLine);
            
            for (MenuItem item : items) {
                // Potong nama jika kepanjangan biar rapi
                String name = item.getName().length() > 24
                        ? item.getName().substring(0, 21) + "..."
                        : item.getName();
                sb.append(String.format("%-25s Rp %,10.0f%n", name, item.getPrice()));
            }
            
            sb.append(newLine).append(SEPARATOR_DASH).append(newLine);
            sb.append(String.format("TOTAL   : Rp %,13.0f%n", total));
            sb.append(String.format("BAYAR   : Rp %,13.0f%n", bayar));
            sb.append(String.format("KEMBALI : Rp %,13.0f%n", (bayar - total)));
            sb.append(SEPARATOR_FULL).append(newLine);
            sb.append("          Terima Kasih :)              ").append(newLine);

            fw.write(sb.toString());
            
            // File dihapus otomatis saat aplikasi tutup
            new File("struk.txt").deleteOnExit();
            
            LOGGER.info("[INFO] Struk fisik berhasil dicetak ke 'struk.txt'");
            
        } catch (IOException e) {
            LOGGER.severe("Gagal mencetak struk: " + e.getMessage());
            LOGGER.severe("Stack Trace:\n" + e);
        }
    }
}
