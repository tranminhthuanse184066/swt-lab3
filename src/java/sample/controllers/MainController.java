package sample.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Hieu Phi Trinh
 */
public class MainController extends HttpServlet {

    private static final String WELCOME = "home.jsp";

    private static final String LOGIN = "Login";
    private static final String LOGIN_CONTROLLER = "LoginController";

    private static final String LOGOUT = "Logout";
    private static final String LOGOUT_CONTROLLER = "LogoutController";

    private static final String SEARCH = "Search";
    private static final String SEARCH_CONTROLLER = "SearchController";

    private static final String SEARCH_PRODUCT = "SearchProduct";
    private static final String SEARCH_PRODUCT_CONTROLLER = "SearchProductController";

    private static final String DELETE = "Delete";
    private static final String DELETE_CONTROLLER = "DeleteController";

    private static final String DELETE_PRODUCT = "DeleteProduct";
    private static final String DELETE_PRODUCT_CONTROLLER = "DeleteProductController";

    private static final String REMOVE = "Remove";
    private static final String REMOVE_CONTROLLER = "RemoveController";

    private static final String UPDATE = "Update";
    private static final String UPDATE_CONTROLLER = "UpdateController";

    private static final String UPDATE_PRODUCT = "UpdateProduct";
    private static final String UPDATE_PRODUCT_CONTROLLER = "UpdateProductController";

    private static final String EDIT = "Edit";
    private static final String EDIT_CONTROLLER = "EditController";

    private static final String CREATE_PAGE = "Create_Page";
    private static final String CREATE_PAGE_VIEW = "create.html";

    private static final String CREATE = "Create";
    private static final String CREATE_CONTROLLER = "CreateController";

    private static final String ADD_PRODUCT_PAGE = "AddProduct_Page";
    private static final String ADD_PRODUCT_PAGE_VIEW = "addProduct.jsp";

    private static final String ADD_PRODUCT = "AddProduct";
    private static final String ADD_PRODUCT_CONTROLLER = "AddProductController";

    private static final String ADD = "Add";
    private static final String ADD_CONTROLLER = "AddController";

    private static final String SHOPPING_PAGE = "Shopping_Page";
    private static final String SHOPPING_PAGE_CONTROLLER = "ShoppingController";

    private static final String VIEW = "View";
    private static final String VIEW_PAGE = "viewCart.jsp";

    private static final String CHECKOUT = "CheckOut";
    private static final String CHECKOUT_CONTROLLER = "CheckOutController";

    private static final String EXCEL = "Excel";
    private static final String EXCEL_CONTROLLER = "ExcelController";
    
    private static final String ORDER_LIST = "OrderList";
    private static final String ORDER_LIST_CONTROLLER = "OrderListController";
    
    private static final String REGISTER = "Register";
    private static final String REGISTER_PAGE = "register.jsp";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = WELCOME;
        try {
            String action = request.getParameter("action");
            if (LOGIN.equals(action)) {
                url = LOGIN_CONTROLLER;
            } else if (LOGOUT.equals(action)) {
                url = LOGOUT_CONTROLLER;
            } else if (SEARCH.equals(action)) {
                url = SEARCH_CONTROLLER;
            } else if (SEARCH_PRODUCT.equals(action)) {
                url = SEARCH_PRODUCT_CONTROLLER;
            } else if (DELETE.equals(action)) {
                url = DELETE_CONTROLLER;
            } else if (DELETE_PRODUCT.equals(action)) {
                url = DELETE_PRODUCT_CONTROLLER;
            } else if (REMOVE.equals(action)) {
                url = REMOVE_CONTROLLER;
            } else if (UPDATE.equals(action)) {
                url = UPDATE_CONTROLLER;
            } else if (UPDATE_PRODUCT.equals(action)) {
                url = UPDATE_PRODUCT_CONTROLLER;
            } else if (EDIT.equals(action)) {
                url = EDIT_CONTROLLER;
            } else if (CREATE_PAGE.equals(action)) {
                url = CREATE_PAGE_VIEW;
            } else if (CREATE.equals(action)) {
                url = CREATE_CONTROLLER;
            } else if (ADD_PRODUCT_PAGE.equals(action)) {
                url = ADD_PRODUCT_PAGE_VIEW;
            } else if (ADD_PRODUCT.equals(action)) {
                url = ADD_PRODUCT_CONTROLLER;
            } else if (ADD.equals(action)) {
                url = ADD_CONTROLLER;
            } else if (SHOPPING_PAGE.equals(action)) {
                url = SHOPPING_PAGE_CONTROLLER;
            } else if (VIEW.equals(action)) {
                url = VIEW_PAGE;
            } else if (CHECKOUT.equals(action)) {
                url = CHECKOUT_CONTROLLER;
            } else if (EXCEL.equals(action)) {
                url = EXCEL_CONTROLLER;
            } else if (ORDER_LIST.equals(action)) {
                url = ORDER_LIST_CONTROLLER;
            } else if (REGISTER.equals(action)) {
                url = REGISTER_PAGE;
            }
        } catch (Exception e) {
            log("Error at MainController " + e.toString());
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