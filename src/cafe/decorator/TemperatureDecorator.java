package cafe.decorator;

import cafe.model.MenuItem;

public class TemperatureDecorator extends DrinkDecorator {
    private String type; // Hot / Ice

    public TemperatureDecorator(MenuItem drink, String type) {
        super(drink);
        this.type = type;
    }

    @Override
    public String getName() {
        return drink.getName() + " - " + type;
    }

    @Override
    public double getPrice() {
        return drink.getPrice();
    }
}
