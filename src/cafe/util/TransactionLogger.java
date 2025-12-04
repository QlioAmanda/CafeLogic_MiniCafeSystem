package cafe.util;

import cafe.model.MenuItem;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Logger; 

public class TransactionLogger {
    
    private static final Logger LOGGER = Logger.getLogger(TransactionLogger.class.getName());
    private static final String HISTORY_FILE = "database_transaksi.jsonl";

    private TransactionLogger() {
        // Mencegah instansiasi
    }

    public static void saveTransaction(List<MenuItem> cart) {
        // Deklarasikan sebagai final
        final double total = cart.stream().mapToDouble(MenuItem::getPrice).sum() * 1.10;
        final String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        StringBuilder json = new StringBuilder();
        json.append("{")
            .append("\"date\":\"").append(timestamp).append("\",")
            .append("\"total\":").append((long) total).append(",")
            .append("\"items\":[");

        for (int i = 0; i < cart.size(); i++) {
            // Menambahkan item ke JSON
            json.append("\"").append(cart.get(i).getName()).append("\"");
            if (i < cart.size() - 1) json.append(",");
        }
        json.append("]}");

        try (FileWriter fw = new FileWriter(HISTORY_FILE, true);
             PrintWriter writer = new PrintWriter(fw)) {
            writer.println(json.toString());
            
            LOGGER.info("[INFO] Data tersimpan: " + new File(HISTORY_FILE).getAbsolutePath());
            
        } catch (IOException e) {
            LOGGER.severe("Gagal simpan database: " + e.getMessage());
            LOGGER.severe("Stack Trace:\n" + e);
        }
    }
}