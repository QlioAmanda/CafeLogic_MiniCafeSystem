package cafe.gui;

import java.awt.Color;
import java.awt.Font;

public class CafeTheme {

    private CafeTheme() {
        // Prevent instantiation
    }

    private static final String FONT_SANS = "SansSerif";

    // --- WARNA UTAMA (Desain Baru) ---
    public static final Color BG_COLOR = new Color(240, 230, 210);
    public static final Color BUTTON_COLOR = new Color(198, 169, 146);
    public static final Color BUTTON_TEXT = new Color(40, 20, 10);
    public static final Color EXIT_BTN_COLOR = new Color(139, 94, 60);
    public static final Color TEXT_COLOR = new Color(60, 42, 33);

    // --- WARNA KOMPATIBILITAS ---
    public static final Color DARK_TEXT = TEXT_COLOR;
    public static final Color PRIMARY_BTN = BUTTON_COLOR;
    public static final Color ACCENT_COLOR = EXIT_BTN_COLOR;

    // --- FONT ---
    public static final Font FONT_BRAND = new Font("Serif", Font.ITALIC | Font.BOLD, 48);
    public static final Font FONT_SUBTITLE = new Font(FONT_SANS, Font.BOLD, 16);
    public static final Font FONT_BUTTON = new Font(FONT_SANS, Font.BOLD, 18);
    public static final Font FONT_TITLE = new Font("Serif", Font.BOLD | Font.ITALIC, 36);
    public static final Font FONT_REGULAR = new Font(FONT_SANS, Font.PLAIN, 14);
}
