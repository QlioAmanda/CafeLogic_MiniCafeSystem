package cafe.model;

public class BaseItem implements MenuItem {
    protected String name;
    protected double price;
    protected int stock;

    public BaseItem(String name, double price, int stock) {
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public int getStock() {
        return stock;
    }

    @Override
    public void setStock(int stock) {
        this.stock = stock;
    }
}
