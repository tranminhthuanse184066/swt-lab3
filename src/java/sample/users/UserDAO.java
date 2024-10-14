package sample.users;

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
public class UserDAO {

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
    private static final String LOGIN_QUERY = "SELECT fullName, roleID FROM tblUsers WHERE userID=? AND password=?";
    private static final String SEARCH_QUERY = "SELECT userID, fullName, roleID FROM tblUsers WHERE fullName LIKE ?";
    private static final String DELETE_QUERY = "DELETE tblUsers WHERE userID=?";
    private static final String UPDATE_QUERY = "UPDATE tblUsers SET fullName=?, roleID=? WHERE userID=?";
    private static final String CHECK_DUPLICATE_QUERY = "SELECT fullName FROM tblUsers WHERE userID=?";
    private static final String INSERT_QUERY = "INSERT INTO tblUsers(userID, fullName, roleID, password) VALUES(?,?,?,?)";
    private static final String GET_USER_QUERY = "SELECT TOP 1 userID, fullName, roleID FROM tblUsers WHERE 1=1";

    public UserDTO checkLogin(String userID, String password) throws SQLException, ClassNotFoundException, NamingException {
        UserDTO loginUser = null;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;

        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(LOGIN_QUERY);
                ptm.setString(1, userID);
                ptm.setString(2, password);

                //get table of data
                rs = ptm.executeQuery();
                if (rs.next()) {
                    String fullName = rs.getString("fullName");
                    String roleID = rs.getString("roleID");
                    loginUser = new UserDTO(userID, fullName, roleID, "***");
                }
            }
        } finally {
            closeResources(rs, ptm, conn);
        }
        return loginUser;
    }

    public List<UserDTO> getListUser(String search) throws SQLException, ClassNotFoundException, NamingException {
        List<UserDTO> listUser = new ArrayList<UserDTO>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;

        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(SEARCH_QUERY);
                ptm.setString(1, "%" + search + "%");
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String userID = rs.getString("userID");
                    String fullName = rs.getString("fullName");
                    String roleID = rs.getString("roleID");
                    String password = "***";
                    listUser.add(new UserDTO(userID, fullName, roleID, password));
                }

            }
        } finally {
            closeResources(rs, ptm, conn);
        }
        return listUser;
    }

    public boolean delete(String userID) throws SQLException, ClassNotFoundException, NamingException {
        boolean checkDelete = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(DELETE_QUERY);
                ptm.setString(1, userID);

                checkDelete = ptm.executeUpdate() > 0 ? true : false;
            }
        } finally {
            closeResources(ptm, conn);
        }
        return checkDelete;
    }

    public boolean update(UserDTO user) throws SQLException, ClassNotFoundException, NamingException {
        boolean checkUpdate = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(UPDATE_QUERY);
                ptm.setString(1, user.getFullName());
                ptm.setString(2, user.getRoleID());
                ptm.setString(3, user.getUserID());

                checkUpdate = ptm.executeUpdate() > 0 ? true : false;
            }
        } finally {
            closeResources(ptm, conn);
        }
        return checkUpdate;
    }

    public boolean checkDuplicate(String userID) throws SQLException, ClassNotFoundException, NamingException {
        boolean checkDup = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;

        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(CHECK_DUPLICATE_QUERY);
                ptm.setString(1, userID);

                //get table of data
                rs = ptm.executeQuery();
                if (rs.next()) {
                    checkDup = true;
                }
            }
        } finally {
            closeResources(rs, ptm, conn);
        }
        return checkDup;
    }

    public boolean insert(UserDTO user) throws SQLException, ClassNotFoundException, NamingException {
        boolean checkIns = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(INSERT_QUERY);
                ptm.setString(1, user.getUserID());
                ptm.setString(2, user.getFullName());
                ptm.setString(3, user.getRoleID());
                ptm.setString(4, user.getPassword());

                checkIns = ptm.executeUpdate() > 0 ? true : false;
            }
        } finally {
            closeResources(ptm, conn);
        }
        return checkIns;
    }

    public UserDTO getUser() throws SQLException {
        UserDTO user = null;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(GET_USER_QUERY);
                rs = ptm.executeQuery();
                
                if (rs.next()) {
                    String userID = rs.getString("userID");
                    String fullName = rs.getString("fullName");
                    String roleID = rs.getString("roleID");
                    user = new UserDTO(userID, fullName, roleID, "");
                }
            }
        } catch (Exception e) {
        } finally {
            closeResources(rs, ptm, conn);
        }
        return user;
    }

}
