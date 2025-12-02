package cafe.factory;

import cafe.model.*;

public class MenuFactory {

    public MenuItem createFood(String name, double price, int stock) {
        return new Food(name, price, stock);
    }

    public MenuItem createDrink(String name, double price, int stock) {
        return new Drink(name, price, stock);
    }
}
