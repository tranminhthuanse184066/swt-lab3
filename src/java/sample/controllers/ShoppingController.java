package sample.controllers;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sample.clothing.ClothesDAO;
import sample.clothing.ClothesDTO;

/**
 *
 * @author Hieu Phi Trinh
 */
public class ShoppingController extends HttpServlet {

    private static final String SUCCESS = "shopping.jsp";
    private static final String ERROR = "shopping.jsp";

    private static final int PRODUCTS_PER_PAGE = 4;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = ERROR;
        try {
            ClothesDAO dao = new ClothesDAO();
            int page = 1;
            if (request.getParameter("page") != null) {
                page = Integer.parseInt(request.getParameter("page"));
            }

            //get list of products
            int offset = (page - 1) * PRODUCTS_PER_PAGE;
            List<ClothesDTO> productList = dao.getProductByPage(offset, PRODUCTS_PER_PAGE);

            //cal number of pages
            int totalProducts = dao.getAllProduct().size();
            int totalPages = (int) Math.ceil(totalProducts * 1.0 / PRODUCTS_PER_PAGE);

            if (productList != null) {
                request.setAttribute("LIST_PRODUCT", productList);
                request.setAttribute("TOTAL_PAGES", totalPages);
                request.setAttribute("CURRENT_PAGE", page);
                url = SUCCESS;
            } else {
                request.setAttribute("ERROR", "Failed to retrieve products from the database.");
            }
        } catch (Exception e) {
            log("Error at ShoppingController: " + e.toString());
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
