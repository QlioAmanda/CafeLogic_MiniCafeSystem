package cafe.service;

import cafe.exception.InsufficientStockException;
import cafe.model.MenuItem;
import cafe.util.TransactionLogger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OrderService {
    private static OrderService instance;
    private List<MenuItem> cart;

    private OrderService() {
        cart = new ArrayList<>();
    }

    // Design Pattern: Singleton
    public static OrderService getInstance() {
        if (instance == null) instance = new OrderService();
        return instance;
    }

    public void addToCart(MenuItem item) {
        int currentStock = item.getStock();

        // Hitung jumlah item yang sama di cart
        long itemInCart = cart.stream()
                .filter(i -> i.getName().equals(item.getName()))
                .count();

        if (itemInCart + 1 > currentStock) {
            throw new InsufficientStockException("Stok tidak cukup: " + item.getName());
        }

        cart.add(item);
    }

    public List<MenuItem> getCart() { return cart; }

    public void clear() { cart.clear(); }

    public void checkout() {
        if (cart.isEmpty()) return;

        // Grouping item untuk pengurangan stok
        Map<String, List<MenuItem>> grouped = cart.stream()
                .collect(Collectors.groupingBy(MenuItem::getName));

        for (List<MenuItem> items : grouped.values()) {
            MenuItem item = items.get(0); 
            int qtySold = items.size();
            int currentStock = item.getStock();

            item.setStock(currentStock - qtySold);
        }

        // Simpan log transaksi
        TransactionLogger.saveTransaction(cart);

        // Bersihkan keranjang
        clear();
    }
}
