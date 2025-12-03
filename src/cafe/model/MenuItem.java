package cafe.model;

public interface MenuItem {
    String getName();
    double getPrice();
    int getStock();
    void setStock(int stock);
}