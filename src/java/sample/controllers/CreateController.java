package sample.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import sample.users.UserDAO;
import sample.users.UserDTO;
import sample.users.UserError;

/**
 *
 * @author Hieu Phi Trinh
 */
public class CreateController extends HttpServlet {

    private static final String REGISTER_ERROR = "register.jsp";
    private static final String CREATE_ERROR = "create.jsp";
    private static final String SUCCESS = "login.jsp";

    private static final String CREATEBYAD = "CreateByAD";
    private static final String CREATEBYUS = "CreateByUS";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = "";
        String newAccountType = request.getParameter("newAccountType");
        if (CREATEBYAD.equals(newAccountType)) {
            url = CREATE_ERROR;
        } else if (CREATEBYUS.equals(newAccountType)) {
            url = REGISTER_ERROR;
        }
        boolean checkValid = true;
        UserDAO dao = new UserDAO();
        UserError userError = new UserError();
        try {
            String userID = request.getParameter("userID");
            String fullName = request.getParameter("fullName");
            String roleID = request.getParameter("roleID");
            String password = request.getParameter("password");
            String confirm = request.getParameter("confirm");

            HttpSession session = request.getSession();
            UserDTO loginUser = (UserDTO) session.getAttribute("LOGIN_USER");

            if (userID.length() < 2 || userID.length() > 20) {
                userError.setUserIDError("User ID must be in [2-20] characters!!!");
                checkValid = false;
            }
            if (fullName.length() < 5 || fullName.length() > 50) {
                userError.setFullNameError("Full Name must be in [5-50] characters!!!");
                checkValid = false;
            }

            boolean checkDuplicate = dao.checkDuplicate(userID);
            if (checkDuplicate) {
                userError.setUserIDError("This User already exists!!!");
                checkValid = false;
            }
            if (!confirm.equals(password)) {
                userError.setConfirmError("Confirm must be the same as Password!!!");
                checkValid = false;
            }

            if (loginUser == null || !loginUser.getRoleID().equals("AD")) {
                // If the logged-in user is not an admin or not logged in, restrict role creation to "US"
                if (!roleID.equals("US")) {
                    userError.setRoleIDError("You can only create a user with role 'US'.");
                    checkValid = false;
                }
            }

            if (checkValid) {
                UserDTO user = new UserDTO(userID, fullName, roleID, password);
                boolean checkInsert = dao.insert(user);
                if (!checkInsert) {
                    userError.setError("Unknown Error!");
                }
                url = SUCCESS;
            } else {
                request.setAttribute("USER_ERROR", userError);
            }
        } catch (Exception e) {
            log("Error at CreateController " + e.toString());
            if (e.toString().contains("duplicate")) {
                userError.setUserIDError("This User already exist");
                request.setAttribute("USER_ERROR", userError);
            }

        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
