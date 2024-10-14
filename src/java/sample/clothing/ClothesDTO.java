package sample.clothing;

/**
 *
 * @author Hieu Phi Trinh
 */
public class ClothesDTO {

    private String productID;
    private String productName;
    private double price;
    private int quantity;
    private String image;
    private boolean status;

    public ClothesDTO() {
        this.productID = "";
        this.productName = "";
        this.price = 0;
        this.quantity = 0;
        this.image = "";
        this.status = false;
    }

    public ClothesDTO(String productID, String productName, double price, int quantity, String image, boolean status) {
        this.productID = productID;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.image = image;
        this.status = status;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

}
