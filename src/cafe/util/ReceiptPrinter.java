package cafe.util;

import cafe.model.MenuItem;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class ReceiptPrinter {
    
    public static void printToTxt(List<MenuItem> items, double total, double bayar) {
        // Lokasi file: struk.txt di folder proyek utama
        try (FileWriter fw = new FileWriter("struk.txt")) {
            StringBuilder sb = new StringBuilder();
            sb.append("========================================\n");
            sb.append("           CAFE LOGIC RECEIPT           \n");
            sb.append("========================================\n");
            sb.append("Tanggal : ").append(LocalDate.now()).append("\n\n");
            sb.append(String.format("%-25s %13s\n", "ITEM", "HARGA"));
            sb.append("----------------------------------------\n");
            
            for (MenuItem item : items) {
                // Potong nama jika kepanjangan biar rapi
                String name = item.getName().length() > 24 ? item.getName().substring(0, 21) + "..." : item.getName();
                sb.append(String.format("%-25s Rp %,10.0f\n", name, item.getPrice()));
            }
            
            sb.append("\n----------------------------------------\n");
            sb.append(String.format("TOTAL   : Rp %,13.0f\n", total));
            sb.append(String.format("BAYAR   : Rp %,13.0f\n", bayar));
            sb.append(String.format("KEMBALI : Rp %,13.0f\n", (bayar - total)));
            sb.append("========================================\n");
            sb.append("          Terima Kasih :)               \n");

            fw.write(sb.toString());
            
            // Fitur asli: File dihapus otomatis saat aplikasi tutup
            new File("struk.txt").deleteOnExit();
            
            System.out.println("[INFO] Struk fisik berhasil dicetak ke 'struk.txt'");
            
        } catch (IOException e) {
            System.err.println("Gagal mencetak struk: " + e.getMessage());
            e.printStackTrace();
        }
    }
}