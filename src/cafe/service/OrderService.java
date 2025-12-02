package cafe.service;

import cafe.exception.InsufficientStockException;
import cafe.model.MenuItem;
import cafe.util.TransactionLogger; 
import java.util.ArrayList;
import java.util.List;

public class OrderService {
    private static OrderService instance;
    private List<MenuItem> cart;

    private OrderService() {
        cart = new ArrayList<>();
    }

    public static OrderService getInstance() {
        if (instance == null) instance = new OrderService();
        return instance;
    }

    public void addToCart(MenuItem item) {
        int currentStock = item.getStock();
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

        // 1. Kurangi Stok
        for (MenuItem item : cart) {
            if (item.getStock() > 0) item.setStock(item.getStock() - 1);
        }
        // 2. Simpan Log (Delegasi ke util)
        TransactionLogger.saveTransaction(cart);
        // 3. Bersihkan
        clear();
    }
}