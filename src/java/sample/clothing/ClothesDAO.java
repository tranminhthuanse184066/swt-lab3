package sample.clothing;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import sample.utils.DBUtils;

/**
 *
 * @author Hieu Phi Trinh
 */
public class ClothesDAO {

    private static final String SEARCH_PRODUCT_QUERY = "SELECT productID, productName, price, quantity, image, status FROM tblProducts WHERE productName LIKE ?";
    private static final String SEARCH_BY_PAGE_QUERY = "SELECT productID, productName, price, quantity, image, status FROM tblProducts WHERE productName LIKE ? ORDER BY productID OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
    private static final String SEARCH_ALL_QUERY = "SELECT productID, productName, price, quantity, image, status FROM tblProducts WHERE 1=1";
    private static final String SEARCH_ALL_BY_PAGE_QUERY = "SELECT productID, productName, price, quantity, image, status FROM tblProducts ORDER BY productID OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
    private static final String ADD_NEW_CLOTHES_QUERY = "INSERT INTO tblProducts (productID, productName, price, quantity, image, status) VALUES (?,?,?,?,?,?)";
    private static final String CHECK_DUPLICATE_QUERY = "SELECT productName FROM tblProducts WHERE productID=?";
    private static final String DELETE_PRODUCT_QUERY = "DELETE tblProducts WHERE productID=?";
    private static final String UPDATE_PRODUCT_QUERY = "UPDATE tblProducts SET productName=?, price=?, quantity=?, image=?, status=? WHERE productID=?";
    private static final String CHECK_QUANTITY_INSTOCK_QUERY = "SELECT productName FROM tblProducts WHERE productID=? AND quantity>=?";
    private static final String UPDATE_QUANTITY_INSTOCK_QUERY = "UPDATE tblProducts SET quantity=quantity-? WHERE productID=?";
    private static final String GET_ORDERID_NUMBER_QUERY = "SELECT TOP 1 orderID FROM tblOrders ORDER BY orderID DESC";
    private static final String INSERT_NEW_ORDER_QUERY = "INSERT INTO tblOrders(userID, orderDate, total) VALUES(?,GETDATE(),?)";
    private static final String CREATE_NEW_DETAIL_QUERY = "INSERT INTO tblOrderDetails(orderID, productID, price, quantity, status) VALUES(?,?,?,?,?)";

    private void closeResources(ResultSet rs, PreparedStatement ptm, Connection conn) throws SQLException {
        if (rs != null) {
            rs.close();
        }
        if (ptm != null) {
            ptm.close();
        }
        if (conn != null) {
            conn.close();
        }
    }

    private void closeResources(PreparedStatement ptm, Connection conn) throws SQLException {
        if (ptm != null) {
            ptm.close();
        }
        if (conn != null) {
            conn.close();
        }
    }

    public List<ClothesDTO> getAllProduct() throws Exception {
        List<ClothesDTO> productList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(SEARCH_ALL_QUERY);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String productID = rs.getString("productID");
                    String productName = rs.getString("productName");
                    double price = rs.getDouble("price");
                    int quantity = rs.getInt("quantity");
                    String image = rs.getString("image");
                    boolean status = rs.getBoolean("status");
                    productList.add(new ClothesDTO(productID, productName, price, quantity, image, status));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, ptm, conn);
        }
        return productList;
    }

    public List<ClothesDTO> getProductByPage(int offset, int noOfRecords) throws SQLException {
        List<ClothesDTO> productList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(SEARCH_ALL_BY_PAGE_QUERY);
                ptm.setInt(1, offset);
                ptm.setInt(2, noOfRecords);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String productID = rs.getString("productID");
                    String productName = rs.getString("productName");
                    double price = rs.getDouble("price");
                    int quantity = rs.getInt("quantity");
                    String image = rs.getString("image");
                    boolean status = rs.getBoolean("status");
                    productList.add(new ClothesDTO(productID, productName, price, quantity, image, status));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, ptm, conn);
        }
        return productList;
    }

    public List<ClothesDTO> getListProductByPage(String searchProduct, int offset, int noOfRecords) throws SQLException {
        List<ClothesDTO> productList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                pst = conn.prepareStatement(SEARCH_BY_PAGE_QUERY);
                pst.setString(1, "%" + searchProduct + "%");
                pst.setInt(2, offset);
                pst.setInt(3, noOfRecords);
                rs = pst.executeQuery();
                while (rs.next()) {
                    String productID = rs.getString("productID");
                    String productName = rs.getString("productName");
                    double price = rs.getDouble("price");
                    int quantity = rs.getInt("quantity");
                    String image = rs.getString("image");
                    boolean status = rs.getBoolean("status");
                    productList.add(new ClothesDTO(productID, productName, price, quantity, image, status));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, pst, conn);
        }
        return productList;
    }

    public List<ClothesDTO> getListProduct(String search) throws SQLException {
        List<ClothesDTO> productList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;

        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(SEARCH_PRODUCT_QUERY);
                ptm.setString(1, "%" + search + "%");
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String productID = rs.getString("productID");
                    String productName = rs.getString("productName");
                    double price = rs.getDouble("price");
                    int quantity = rs.getInt("quantity");
                    String image = rs.getString("image");
                    boolean status = rs.getBoolean("status");
                    productList.add(new ClothesDTO(productID, productName, price, quantity, image, status));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, ptm, conn);
        }

        return productList;
    }

    public boolean checkDuplicate(String productID) throws SQLException {
        boolean checkDupl = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;

        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(CHECK_DUPLICATE_QUERY);
                ptm.setString(1, productID);
                rs = ptm.executeQuery();
                if (rs.next()) {
                    checkDupl = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, ptm, conn);
        }
        return checkDupl;
    }

    public boolean addNewClothes(ClothesDTO clothes) throws SQLException, ClassNotFoundException, NamingException {
        boolean check = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(ADD_NEW_CLOTHES_QUERY);
                ptm.setString(1, clothes.getProductID());
                ptm.setString(2, clothes.getProductName());
                ptm.setDouble(3, clothes.getPrice());
                ptm.setInt(4, clothes.getQuantity());
                ptm.setString(5, clothes.getImage());
                ptm.setBoolean(6, clothes.isStatus());
                check = ptm.executeUpdate() > 0;
            }
        } finally {
            closeResources(ptm, conn);
        }
        return check;
    }

    public boolean deleteProduct(String productID) throws SQLException {
        boolean checkDelete = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(DELETE_PRODUCT_QUERY);
                ptm.setString(1, productID);

                checkDelete = ptm.executeUpdate() > 0;
            }
        } catch (Exception e) {
        } finally {
            closeResources(ptm, conn);
        }
        return checkDelete;
    }

    public boolean updateProduct(ClothesDTO clothes) throws SQLException {
        boolean checkUpdate = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(UPDATE_PRODUCT_QUERY);
                ptm.setString(1, clothes.getProductName());
                ptm.setDouble(2, clothes.getPrice());
                ptm.setInt(3, clothes.getQuantity());
                ptm.setString(4, clothes.getImage());
                ptm.setBoolean(5, clothes.isStatus());
                ptm.setString(6, clothes.getProductID());

                checkUpdate = ptm.executeUpdate() > 0;
            }
        } catch (Exception e) {
        } finally {
            closeResources(ptm, conn);
        }
        return checkUpdate;
    }

    public boolean checkQuantityInStock(String clothesID, int quantity) throws SQLException {
        boolean check = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;

        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(CHECK_QUANTITY_INSTOCK_QUERY);
                ptm.setString(1, clothesID);
                ptm.setInt(2, quantity);
                rs = ptm.executeQuery();
                if (rs.next()) {
                    check = true;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, ptm, conn);
        }

        return check;
    }

    public boolean updateQuantityInStock(String clothesID, int quantity) throws SQLException {
        boolean check = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(UPDATE_QUANTITY_INSTOCK_QUERY);
                ptm.setInt(1, quantity);
                ptm.setString(2, clothesID);

                check = ptm.executeUpdate() > 0;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources(ptm, conn);
        }
        return check;
    }

    public int getNewOrderID() throws SQLException {
        int orderID = 0;

        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;

        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(GET_ORDERID_NUMBER_QUERY);
                rs = ptm.executeQuery();
                while (rs.next()) {
                    orderID = rs.getInt("orderID");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, ptm, conn);
        }

        return orderID;
    }

    public boolean createNewOrder(Double total, String userID) throws SQLException {
        boolean check = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(INSERT_NEW_ORDER_QUERY);
//                ptm.setInt(1, newOrderID);
                ptm.setString(1, userID);
                ptm.setDouble(2, total);
                check = ptm.executeUpdate() > 0;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources(ptm, conn);
        }

        return check;
    }

    public boolean createNewDetail(int newOrderID, String clothesID, double tmpTotal, int quantity, boolean status) throws SQLException {
        boolean check = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(CREATE_NEW_DETAIL_QUERY);
                ptm.setInt(1, newOrderID);
                ptm.setString(2, clothesID);
                ptm.setDouble(3, tmpTotal);
                ptm.setInt(4, quantity);
                ptm.setBoolean(5, status);

                check = ptm.executeUpdate() > 0;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources(ptm, conn);
        }

        return check;
    }

}
