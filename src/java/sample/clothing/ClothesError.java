package sample.clothing;

public class ClothesError {

    private String productID;
    private String productName;
    private String price;
    private String quantity;
    private String image;
    private String status;
    private String errorMessage;

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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public ClothesError(String productID, String productName, String price, String quantity, String image, String status, String errorMessage) {
        this.productID = productID;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.image = image;
        this.status = status;
        this.errorMessage = errorMessage;
    }

    public ClothesError() {
        this.productID = "";
        this.productName = "";
        this.price = "";
        this.quantity = "";
        this.image = "";
        this.status = "";
        this.errorMessage = "";
    }

}
