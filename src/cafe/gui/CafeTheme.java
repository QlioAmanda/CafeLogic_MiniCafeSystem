package cafe.gui;

import cafe.gui.components.RoundedButton;
import java.awt.Color;
import java.awt.Font;

public class CafeTheme {
    // --- WARNA UTAMA (Desain Baru) ---
    public static final Color BG_COLOR = new Color(240, 230, 210); // Krem background
    public static final Color BUTTON_COLOR = new Color(198, 169, 146); // Coklat Muda
    public static final Color BUTTON_TEXT = new Color(40, 20, 10); // Hampir Hitam
    public static final Color EXIT_BTN_COLOR = new Color(139, 94, 60); // Coklat Tua
    public static final Color TEXT_COLOR = new Color(60, 42, 33); // Coklat Kopi

    // --- WARNA KOMPATIBILITAS (Agar Admin & Customer tidak error) ---
    // Kita samakan warnanya dengan palet baru agar seragam
    public static final Color DARK_TEXT = TEXT_COLOR;       // Mapping ke TEXT_COLOR
    public static final Color PRIMARY_BTN = BUTTON_COLOR;   // Mapping ke BUTTON_COLOR
    public static final Color ACCENT_COLOR = EXIT_BTN_COLOR;// Mapping ke warna coklat tua
    
    // --- FONT ---
    // Font Utama (Login)
    public static final Font FONT_BRAND = new Font("Serif", Font.ITALIC | Font.BOLD, 48); 
    public static final Font FONT_SUBTITLE = new Font("SansSerif", Font.BOLD, 16);
    public static final Font FONT_BUTTON = new Font("SansSerif", Font.BOLD, 18);
    
    // Font Tambahan (Admin/Customer)
    public static final Font FONT_TITLE = new Font("Serif", Font.BOLD | Font.ITALIC, 36);
    public static final Font FONT_REGULAR = new Font("SansSerif", Font.PLAIN, 14);
}