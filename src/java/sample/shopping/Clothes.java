package sample.shopping;

/**
 *
 * @author Hieu Phi Trinh
 */
public class Clothes {

    private String clothesID;
    private String clothesName;
    private int quantity;
    private double price;

    public Clothes() {
    }

    public Clothes(String productID, String name, int quantity, double price) {
        this.clothesID = productID;
        this.clothesName = name;
        this.quantity = quantity;
        this.price = price;
    }

    public String getClothesID() {
        return clothesID;
    }

    public void setClothesID(String productID) {
        this.clothesID = productID;
    }

    public String getClothesName() {
        return clothesName;
    }

    public void setClothesName(String name) {
        this.clothesName = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
