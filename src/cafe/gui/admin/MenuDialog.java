package cafe.gui.admin;

import cafe.gui.CafeTheme;
import cafe.gui.components.RoundedButton;
import cafe.gui.common.ErrorDialog; 
import cafe.factory.MenuFactory;
import cafe.model.Food;
import cafe.model.MenuItem;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class MenuDialog extends JDialog {
    private boolean confirmed = false;
    private MenuItem resultItem;
    private MenuFactory menuFactory = new MenuFactory();
    private JComboBox<String> comboType;
    private JTextField txtName, txtPrice, txtStock;

    public MenuDialog(Frame owner, MenuItem itemToEdit) {
        super(owner, true);
        setUndecorated(true);
        setSize(350, 480);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout());

        boolean isEdit = (itemToEdit != null);
        String titleText = isEdit ? "UBAH MENU" : "TAMBAH MENU BARU";

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(CafeTheme.BG_COLOR);
        mainPanel.setBorder(new LineBorder(CafeTheme.EXIT_BTN_COLOR, 3));

        JPanel header = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 15));
        header.setBackground(CafeTheme.EXIT_BTN_COLOR);
        JLabel title = new JLabel(titleText);
        title.setFont(new Font("SansSerif", Font.BOLD, 16));
        title.setForeground(Color.WHITE);
        header.add(title);
        mainPanel.add(header, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(CafeTheme.BG_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 25, 2, 25);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        addLabel(formPanel, "Tipe:", 0, gbc);
        String[] types = {"Makanan", "Minuman"};
        comboType = new JComboBox<>(types);
        comboType.setFont(new Font("SansSerif", Font.PLAIN, 14));
        comboType.setBackground(Color.WHITE);
        if (isEdit) comboType.setSelectedItem(itemToEdit instanceof Food ? "Makanan" : "Minuman");
        gbc.gridy = 1; formPanel.add(comboType, gbc);

        addLabel(formPanel, "Nama:", 2, gbc);
        txtName = createTextField();
        if (isEdit) txtName.setText(itemToEdit.getName());
        gbc.gridy = 3; formPanel.add(txtName, gbc);

        addLabel(formPanel, "Harga:", 4, gbc);
        txtPrice = createTextField();
        if (isEdit) txtPrice.setText(String.valueOf((long)itemToEdit.getPrice()));
        gbc.gridy = 5; formPanel.add(txtPrice, gbc);

        addLabel(formPanel, "Stok:", 6, gbc);
        txtStock = createTextField();
        if (isEdit) txtStock.setText(String.valueOf(itemToEdit.getStock()));
        gbc.gridy = 7; formPanel.add(txtStock, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        JPanel footer = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        footer.setBackground(CafeTheme.BG_COLOR);
        RoundedButton btnCancel = new RoundedButton("Batal");
        btnCancel.setBackground(new Color(200, 80, 80));
        btnCancel.setForeground(Color.WHITE);
        btnCancel.setPreferredSize(new Dimension(110, 45));
        RoundedButton btnSave = new RoundedButton(isEdit ? "Perbarui" : "Simpan");
        btnSave.setBackground(CafeTheme.BUTTON_COLOR);
        btnSave.setPreferredSize(new Dimension(110, 45));
        footer.add(btnCancel); footer.add(btnSave);
        mainPanel.add(footer, BorderLayout.SOUTH);
        add(mainPanel);

        btnCancel.addActionListener(e -> dispose());
        btnSave.addActionListener(e -> handleSave());
    }

    private void handleSave() {
        try {
            String type = (String) comboType.getSelectedItem();
            String name = txtName.getText().trim();
            if (name.isEmpty()) {
                new ErrorDialog(this, "Nama tidak boleh kosong!").setVisible(true);
                return;
            }
            double price = Double.parseDouble(txtPrice.getText());
            int stock = Integer.parseInt(txtStock.getText());
            if (price < 0 || stock < 0) {
                new ErrorDialog(this, "Harga/Stok tidak boleh minus!").setVisible(true);
                return;
            }
            if (type.equals("Makanan")) resultItem = menuFactory.createFood(name, price, stock);
            else resultItem = menuFactory.createDrink(name, price, stock);
            confirmed = true;
            dispose();
        } catch (NumberFormatException e) {
            new ErrorDialog(this, "Harga dan Stok harus angka!").setVisible(true);
        }
    }
    public boolean isConfirmed() { return confirmed; }
    public MenuItem getResultItem() { return resultItem; }
    private void addLabel(JPanel p, String text, int y, GridBagConstraints gbc) {
        gbc.gridx = 0; gbc.gridy = y;
        JLabel l = new JLabel(text);
        l.setFont(new Font("SansSerif", Font.BOLD, 14));
        l.setForeground(CafeTheme.TEXT_COLOR);
        p.add(l, gbc);
    }
    private JTextField createTextField() {
        JTextField t = new JTextField();
        t.setFont(new Font("SansSerif", Font.PLAIN, 14));
        t.setBorder(BorderFactory.createCompoundBorder(new LineBorder(CafeTheme.EXIT_BTN_COLOR, 1), new EmptyBorder(5, 5, 5, 5)));
        t.setPreferredSize(new Dimension(200, 35));
        return t;
    }
}