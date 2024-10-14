package sample.controllers;

import java.io.IOException;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import sample.clothing.ClothesDAO;
import sample.shopping.Cart;
import sample.shopping.Clothes;
import sample.users.UserDTO;
import sample.utils.MailUtils;

/**
 *
 * @author Hieu Phi Trinh
 */
public class CheckOutController extends HttpServlet {

    private static final String ERROR = "viewCart.jsp";
    private static final String SUCCESS = "viewCart.jsp";
    private static final String EMAIL_PATTERN = "^[A-Za-z0-9]*@[A-Za-z0-9]*+(\\.[A-Za-z0-9]{2,})*$";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        boolean proceedToCheckout = true;
        boolean allQuantitiesAvailable = true;
        boolean allUpdatesSuccessful = true;
        boolean checkCreateNewOrder = true;
        boolean checkCreateNewDetail = true;

        try {
            HttpSession session = request.getSession();
            Cart cart = (Cart) session.getAttribute("CART");

            if (cart != null) {
                ClothesDAO dao = new ClothesDAO();
                UserDTO loginUser = (UserDTO) session.getAttribute("LOGIN_USER");

                String userEmail = request.getParameter("userEmail");

                // Check valid email
                if (!Pattern.matches(EMAIL_PATTERN, userEmail)) {
                    request.setAttribute("CART_MESSAGE", "Please enter a valid email to check out!");
                    proceedToCheckout = false;
                }

                // Check quantity
                if (proceedToCheckout) {
                    for (Clothes clothes : cart.getCart().values()) {
                        String clothesID = clothes.getClothesID();
                        int quantity = clothes.getQuantity();
                        boolean checkQuantityInStock = dao.checkQuantityInStock(clothesID, quantity);
                        if (!checkQuantityInStock) {
                            request.setAttribute("CART_MESSAGE", clothes.getClothesName() + " is currently out of stock.");
                            allQuantitiesAvailable = false;
                            proceedToCheckout = false;
                        }
                    }
                }

                // Update quantity
                if (proceedToCheckout && allQuantitiesAvailable) {
                    for (Clothes clothes : cart.getCart().values()) {
                        String clothesID = clothes.getClothesID();
                        int quantity = clothes.getQuantity();
                        boolean checkUpdateQuantity = dao.updateQuantityInStock(clothesID, quantity);
                        if (!checkUpdateQuantity) {
                            request.setAttribute("CART_MESSAGE", "Fail to update stock quantity");
                            allUpdatesSuccessful = false;
                        }
                    }
                }

                // Create new order
                if (proceedToCheckout && allQuantitiesAvailable && allUpdatesSuccessful) {
                    double total = Double.parseDouble(request.getParameter("total"));
                    String userID = request.getParameter("userID");
                    checkCreateNewOrder = dao.createNewOrder(total, userID);
                    if (!checkCreateNewOrder) {
                        request.setAttribute("CART_MESSAGE", "Fail to create new Order!");
                    }
                }

                // Create new order details
                if (proceedToCheckout && allQuantitiesAvailable && allUpdatesSuccessful && checkCreateNewOrder) {
                    int newOrderID = dao.getNewOrderID();
                    for (Clothes clothes : cart.getCart().values()) {
                        String clothesID = clothes.getClothesID();
                        int quantity = clothes.getQuantity();
                        double tmpTotal = clothes.getPrice() * quantity;
                        boolean status = allUpdatesSuccessful;
                        checkCreateNewDetail = dao.createNewDetail(newOrderID, clothesID, tmpTotal, quantity, status);

                        if (!checkCreateNewDetail) {
                            request.setAttribute("CART_MESSAGE", "Fail to create new OrderDetail!");
                        }
                    }
                }

                // Confirm message and remove cart
                if (proceedToCheckout && allQuantitiesAvailable && allUpdatesSuccessful && checkCreateNewOrder && checkCreateNewDetail) {
                    request.setAttribute("CART_MESSAGE", "Ordered successfully! Your Order number is: " + dao.getNewOrderID());
                    String userName = loginUser.getFullName();
                    MailUtils.sendMail(userEmail, userName, cart);
                    session.removeAttribute("CART");
                    url = SUCCESS;
                }

            } else {
                request.setAttribute("CART_MESSAGE", "Your cart is empty, buy something!!");
            }
        } catch (Exception e) {
            log("Error at CheckOutController: " + e.toString());
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
     * @param response servlet response)
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
