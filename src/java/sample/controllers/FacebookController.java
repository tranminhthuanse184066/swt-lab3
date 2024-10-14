package sample.controllers;

import com.restfb.types.User;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import sample.users.UserDAO;
import sample.users.UserDTO;
import sample.utils.RestFB;

/**
 *
 * @author Hieu Phi Trinh
 */
@WebServlet(name = "FacebookController", urlPatterns = {"/FacebookController"})
public class FacebookController extends HttpServlet {

    private static final String ERROR = "login.jsp";
    private static final String SUCCESS = "user.jsp";

    private static final String FB_USER_ROLE = "US";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String code = request.getParameter("code");
        String url = ERROR;
        try {
            if (code != null) {
                String accessToken = RestFB.getToken(code);
                User fbUser = RestFB.getUserInfo(accessToken);
                String userID = fbUser.getId();
                String fullName = fbUser.getName();
                UserDTO user = new UserDTO(userID, fullName, FB_USER_ROLE, "***");
                UserDAO dao = new UserDAO();

                boolean checkDuplicate = dao.checkDuplicate(userID);
                if (!checkDuplicate) {
                    boolean checkInsert = dao.insert(user);
                    if (!checkInsert) {
                        request.setAttribute("ERROR", "Fail to create new User!");
                    }
                }
                
                HttpSession session = request.getSession();
                session.setAttribute("LOGIN_USER", user);
                url = SUCCESS;
            } else {
                request.setAttribute("ERROR", "Fail to Login with Facebook");
            }
        } catch (Exception e) {
            log("Error at FacebookController " + e.toString());
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
