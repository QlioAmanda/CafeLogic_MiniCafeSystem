package cafe.test;

import cafe.model.MenuItem;
import cafe.model.Food;
import cafe.exception.InsufficientStockException;
import cafe.service.MenuService;
import cafe.service.OrderService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class OrderServiceTest {
    private OrderService orderService;
    private MenuItem kopiSusu;
    private MenuItem kentang;

    @BeforeEach
    void setUp() {
        // Deklarasikan menuService sebagai variabel lokal di sini
        MenuService menuService = MenuService.getInstance(); 
        
        orderService = OrderService.getInstance();
        orderService.clear();

        menuService.getAllMenu().clear();

        kopiSusu = new Food("Kopi Susu", 15000, 3);
        kentang = new Food("Kentang", 10000, 5);

        menuService.addMenu(kopiSusu);
        menuService.addMenu(kentang);
    }

    @Test
    void testAddToCartInsufficientStock() {
        orderService.addToCart(kopiSusu);
        orderService.addToCart(kopiSusu);
        orderService.addToCart(kopiSusu);

        assertThrows(InsufficientStockException.class, () -> 
            orderService.addToCart(kopiSusu)
        );
    }

    @Test
    void testCheckoutAndStockUpdate() {
        orderService.addToCart(kopiSusu);
        orderService.addToCart(kopiSusu);
        orderService.addToCart(kentang);

        int initialStockKopi = kopiSusu.getStock();
        int initialStockKentang = kentang.getStock();

        orderService.checkout();

        assertTrue(orderService.getCart().isEmpty());

        assertEquals(initialStockKopi - 2, kopiSusu.getStock());
        assertEquals(initialStockKentang - 1, kentang.getStock());
    }
}