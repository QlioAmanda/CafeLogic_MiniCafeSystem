package cafe.gui.admin; 

import cafe.gui.CafeTheme; 
import cafe.model.*;
import cafe.model.MenuItem;
import cafe.service.MenuService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.List;

public class AdminTablePanel extends JPanel {
    private MenuService menuService = MenuService.getInstance();
    private DefaultTableModel tableModel;
    private JTable table;

    public AdminTablePanel() {
        setLayout(new BorderLayout());
        setOpaque(false);
        
        String[] cols = {"ID", "Tipe", "Nama", "Harga", "Stok"};
        tableModel = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(tableModel);
        styleTable();

        JScrollPane scroll = new JScrollPane(table);
        scroll.getViewport().setBackground(Color.WHITE);
        add(scroll, BorderLayout.CENTER);
    }

    private void styleTable() {
        table.setRowHeight(40);
        table.getTableHeader().setBackground(CafeTheme.BUTTON_COLOR);
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        
        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(center);
        table.getColumnModel().getColumn(3).setCellRenderer(center);
        table.getColumnModel().getColumn(4).setCellRenderer(center);
    }

    public void refreshTable() {
        tableModel.setRowCount(0);
        List<MenuItem> menus = menuService.getAllMenu();
        for (int i = 0; i < menus.size(); i++) {
            MenuItem m = menus.get(i);
            String type = (m instanceof Food) ? "Makanan" : "Minuman";
            tableModel.addRow(new Object[]{i + 1, type, m.getName(), m.getPrice(), m.getStock()});
        }
    }

    public int getSelectedRow() {
        return table.getSelectedRow();
    }
}