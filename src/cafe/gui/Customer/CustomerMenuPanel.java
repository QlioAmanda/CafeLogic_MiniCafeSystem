package cafe.gui.customer; 

import cafe.gui.CafeTheme;
import cafe.gui.components.RoundedButton;
import cafe.model.*;
import cafe.model.MenuItem;
import cafe.service.MenuService;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

public class CustomerMenuPanel extends JPanel {
    private JTable menuTable;
    private DefaultTableModel menuModel;
    private MenuService menuService = MenuService.getInstance();
    private CustomerPanel parent;

    public CustomerMenuPanel(CustomerPanel parent) {
        this.parent = parent;
        setLayout(new BorderLayout(0, 10));
        setOpaque(false);
        initUI();
    }

    private void initUI() {
        JPanel filterPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        filterPanel.setOpaque(false);
        JButton btnFood = createCategoryBtn("MAKANAN", "FOOD");
        JButton btnDrink = createCategoryBtn("MINUMAN", "DRINK");
        filterPanel.add(btnFood); filterPanel.add(btnDrink);
        add(filterPanel, BorderLayout.NORTH);

        initTable();
        JScrollPane scrollPane = new JScrollPane(menuTable);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBorder(BorderFactory.createLineBorder(CafeTheme.BUTTON_COLOR, 2));
        add(scrollPane, BorderLayout.CENTER);

        RoundedButton btnAdd = new RoundedButton("Tambah ke Keranjang (+)");
        btnAdd.setPreferredSize(new Dimension(0, 50));
        btnAdd.addActionListener(e -> parent.addToCartAction(menuTable, menuModel));
        add(btnAdd, BorderLayout.SOUTH);
    }

    private void initTable() {
        String[] cols = {"Nama", "Harga", "Stok"};
        menuModel = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        menuTable = new JTable(menuModel);
        menuTable.setRowHeight(35);
        menuTable.setShowVerticalLines(false);
        JTableHeader header = menuTable.getTableHeader();
        header.setBackground(CafeTheme.EXIT_BTN_COLOR);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("SansSerif", Font.BOLD, 14));
        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(JLabel.CENTER);
        menuTable.getColumnModel().getColumn(1).setCellRenderer(center);
        menuTable.getColumnModel().getColumn(2).setCellRenderer(center);
    }

    private JButton createCategoryBtn(String label, String catCode) {
        JButton btn = new JButton(label);
        btn.setFont(new Font("SansSerif", Font.BOLD, 16));
        btn.setFocusPainted(false);
        btn.setBackground(CafeTheme.BUTTON_COLOR);
        btn.addActionListener(e -> parent.switchCategory(catCode));
        return btn;
    }

    public void refreshData(String category) {
        menuModel.setRowCount(0);
        for (MenuItem m : menuService.getAllMenu()) {
            boolean match = (category.equals("FOOD") && m instanceof Food) ||
                            (category.equals("DRINK") && m instanceof Drink);
            if (match) menuModel.addRow(new Object[]{m.getName(), m.getPrice(), m.getStock()});
        }
    }
}