package sample.clothing;

import java.util.Date;

/**
 *
 * @author Hieu Phi Trinh
 */
public class OrderDTO {

    private int orderID;
    private String userID;
    private Date orderDate;
    private double total;

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public OrderDTO(int orderID, String userID, Date orderDate, double total) {
        this.orderID = orderID;
        this.userID = userID;
        this.orderDate = orderDate;
        this.total = total;
    }

    public OrderDTO() {
        this.orderID = 0;
        this.userID = "";
        this.orderDate = new Date();
        this.total = 0.0;
    }
}
