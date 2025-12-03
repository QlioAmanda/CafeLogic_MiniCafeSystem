package cafe.decorator;

import cafe.model.MenuItem;

public abstract class DrinkDecorator implements MenuItem {
    protected MenuItem drink;

    public DrinkDecorator(MenuItem drink) {
        this.drink = drink;
    }

    @Override
    public String getName() { return drink.getName(); }

    @Override
    public double getPrice() { return drink.getPrice(); }

    @Override
    public int getStock() { return drink.getStock(); }

    @Override
    public void setStock(int stock) { drink.setStock(stock); }
}