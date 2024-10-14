package sample.clothing;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import sample.utils.DBUtils;

/**
 *
 * @author Hieu Phi Trinh
 */
public class OrderDAO {

    private static final String GET_ALL_QUERY = "SELECT orderID, userID, orderDate, total FROM tblOrders WHERE 1=1";

    public List<OrderDTO> getAllOrders() throws SQLException {
        List<OrderDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;

        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(GET_ALL_QUERY);

                rs = ptm.executeQuery();
                while (rs.next()) {
                    int orderID = rs.getInt("orderID");
                    String userID = rs.getString("userID");
                    Date orderDate = rs.getDate("orderDate");
                    double total = rs.getDouble("total");
                    list.add((new OrderDTO(orderID, userID, orderDate, total)));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (rs != null) {
                rs.close();
            }
        }
        return list;
    }

}
