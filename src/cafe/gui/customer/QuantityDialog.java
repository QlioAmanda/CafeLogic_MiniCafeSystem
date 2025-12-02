package cafe.gui.customer; 

import cafe.gui.CafeTheme;
import cafe.gui.components.RoundedButton;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class QuantityDialog extends JDialog {
    private int quantity = -1; 
    private JSpinner spinner;

    public QuantityDialog(Frame owner) {
        super(owner, true);
        setUndecorated(true);
        setSize(300, 200);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(CafeTheme.BG_COLOR);
        mainPanel.setBorder(new LineBorder(CafeTheme.EXIT_BTN_COLOR, 2));

        JPanel header = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10));
        header.setBackground(CafeTheme.EXIT_BTN_COLOR);
        JLabel title = new JLabel("MASUKKAN JUMLAH");
        title.setFont(new Font("SansSerif", Font.BOLD, 14));
        title.setForeground(Color.WHITE);
        header.add(title);
        mainPanel.add(header, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(CafeTheme.BG_COLOR);
        spinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        spinner.setFont(new Font("SansSerif", Font.BOLD, 24)); 
        spinner.setPreferredSize(new Dimension(100, 50));
        JComponent editor = spinner.getEditor();
        JFormattedTextField tf = ((JSpinner.DefaultEditor) editor).getTextField();
        tf.setHorizontalAlignment(SwingConstants.CENTER);
        tf.setBackground(Color.WHITE);
        centerPanel.add(spinner);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        footer.setBackground(CafeTheme.BG_COLOR);
        RoundedButton btnBatal = new RoundedButton("Batal");
        btnBatal.setBackground(new Color(200, 80, 80)); 
        btnBatal.setForeground(Color.WHITE);
        btnBatal.setPreferredSize(new Dimension(110, 40)); 
        RoundedButton btnOk = new RoundedButton("OK");
        btnOk.setBackground(CafeTheme.BUTTON_COLOR); 
        btnOk.setPreferredSize(new Dimension(110, 40));
        footer.add(btnBatal); footer.add(btnOk);
        mainPanel.add(footer, BorderLayout.SOUTH);
        add(mainPanel);

        btnBatal.addActionListener(e -> { quantity = -1; dispose(); });
        btnOk.addActionListener(e -> { quantity = (int) spinner.getValue(); dispose(); });
    }
    public int getQuantity() { return quantity; }
}