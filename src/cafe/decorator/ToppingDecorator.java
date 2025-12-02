package cafe.decorator;

import cafe.model.MenuItem;

public class ToppingDecorator extends DrinkDecorator {
    private String toppingName;
    private double extraCost;

    public ToppingDecorator(MenuItem drink, String toppingName, double extraCost) {
        super(drink);
        this.toppingName = toppingName;
        this.extraCost = extraCost;
    }

    @Override
    public String getName() {
        return drink.getName() + " + " + toppingName;
    }

    @Override
    public double getPrice() {
        return drink.getPrice() + extraCost;
    }
}
