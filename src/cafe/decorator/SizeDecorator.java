package cafe.decorator;

import cafe.model.MenuItem;

public class SizeDecorator extends DrinkDecorator {
    private String sizeName;
    private double extraCost;

    public SizeDecorator(MenuItem drink, String sizeName, double extraCost) {
        super(drink);
        this.sizeName = sizeName;
        this.extraCost = extraCost;
    }

    @Override
    public String getName() {
        return drink.getName() + " (" + sizeName + ")";
    }

    @Override
    public double getPrice() {
        return drink.getPrice() + extraCost;
    }
}
