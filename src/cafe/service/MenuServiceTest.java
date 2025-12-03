package cafe.service;

import cafe.model.MenuItem;
import cafe.model.Food;
import cafe.exception.MenuNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MenuServiceTest {
    private MenuService menuService;
    private MenuItem nasiGoreng;

    @BeforeEach
    void setUp() {
        menuService = MenuService.getInstance();
        menuService.getAllMenu().clear();

        nasiGoreng = new Food("Nasi Goreng", 25000, 10);
    }

    @Test
    void testSingletonInstance() {
        MenuService anotherInstance = MenuService.getInstance();
        assertSame(menuService, anotherInstance, 
            "Harusnya mengembalikan instance yang sama (Singleton)");
    }

    @Test
    void testAddAndGetAllMenu() {
        menuService.addMenu(nasiGoreng);

        assertEquals(1, menuService.getAllMenu().size());
        assertEquals("Nasi Goreng", menuService.getAllMenu().get(0).getName());
    }

    @Test
    void testDeleteMenuSuccess() {
        menuService.addMenu(nasiGoreng);

        menuService.deleteMenu(0);

        assertTrue(menuService.getAllMenu().isEmpty());
    }

    @Test
    void testDeleteMenuNotFound() {
        assertThrows(MenuNotFoundException.class, () -> 
            menuService.deleteMenu(5)
        );
    }

    @Test
    void testEditMenu() {
        menuService.addMenu(nasiGoreng);

        MenuItem sateAyam = new Food("Sate Ayam", 30000, 5);

        menuService.editMenu(0, sateAyam);

        assertEquals(1, menuService.getAllMenu().size());
        assertEquals("Sate Ayam", menuService.getAllMenu().get(0).getName());
    }
}