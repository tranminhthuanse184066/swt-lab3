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
public class SearchProductController extends HttpServlet {

    private static final String ERROR = "admin.jsp";

    private static final String AD = "AD";
    private static final String AD_ERROR = "admin.jsp";
    private static final String AD_SUCCESS = "admin.jsp";

    private static final String US = "US";
    private static final String US_ERROR = "shopping.jsp";
    private static final String US_SUCCESS = "shopping.jsp";

    private static final int PRODUCTS_PER_PAGE = 4;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        try {
            String searchProduct = request.getParameter("searchProduct");
            ClothesDAO dao = new ClothesDAO();
            String roleID = request.getParameter("roleID");

            if (AD.equals(roleID)) {
                List<ClothesDTO> listProduct = dao.getListProduct(searchProduct);
                if (listProduct.size() > 0) {
                    request.setAttribute("LIST_PRODUCT", listProduct);
                    url = AD_SUCCESS;
                } else {
                    url = AD_ERROR;
                }
            } else if (US.equals(roleID)) {
                int page = 1;
                if (request.getParameter("page") != null) {
                    page = Integer.parseInt(request.getParameter("page"));
                }
                int totalProducts = dao.getListProduct(searchProduct).size();
                int totalPages = (int) Math.ceil((double) totalProducts / PRODUCTS_PER_PAGE);

                //find the starting index of the product (in list) for the current page.
                int offset = (page - 1) * PRODUCTS_PER_PAGE;
                List<ClothesDTO> listProduct = dao.getListProductByPage(searchProduct, offset, PRODUCTS_PER_PAGE);

                if (listProduct.size() > 0) {
                    request.setAttribute("LIST_PRODUCT", listProduct);
                    request.setAttribute("TOTAL_PAGES", totalPages);
                    request.setAttribute("CURRENT_PAGE", page);
                    url = US_SUCCESS;
                } else {
                    request.setAttribute("MESSAGE", "Product not found!");
                    url = US_ERROR;
                }
            }

        } catch (Exception e) {
            log("Error at SearchProductController: " + e.toString());
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
