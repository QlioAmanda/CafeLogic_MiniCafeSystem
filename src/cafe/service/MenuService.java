package cafe.service;

import cafe.model.MenuItem;
import cafe.exception.MenuNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class MenuService {
    private List<MenuItem> menuList;
    
    // 1. Private Constructor
    private MenuService() {
        menuList = new ArrayList<>();
    }

    // 2. Static Instance Holder
    private static class Holder {
        private static final MenuService INSTANCE = new MenuService();
    }

    // 3. Global Access Point
    public static MenuService getInstance() {
        return Holder.INSTANCE;
    }

    public void addMenu(MenuItem item) { menuList.add(item); }
    public List<MenuItem> getAllMenu() { return menuList; }

    public void deleteMenu(int index) {
        if (index < 0 || index >= menuList.size()) 
            throw new MenuNotFoundException("ID tidak valid");
        menuList.remove(index);
    }

    public void editMenu(int index, MenuItem item) {
        if (index < 0 || index >= menuList.size())
            throw new MenuNotFoundException("ID tidak valid");
        menuList.set(index, item);
    }
}