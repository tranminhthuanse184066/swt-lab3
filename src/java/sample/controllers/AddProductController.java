package sample.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sample.clothing.ClothesDAO;
import sample.clothing.ClothesDTO;
import sample.clothing.ClothesError;

/**
 *
 * @author Hieu Phi Trinh
 */
public class AddProductController extends HttpServlet {

    private static final String ERROR = "addProduct.jsp";
    private static final String SUCCESS = "SearchProductController";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        boolean checkValidation = true;
        ClothesDAO dao = new ClothesDAO();
        ClothesError clothesError = new ClothesError();
        try {
            String productID = request.getParameter("productID");
            String productName = request.getParameter("productName");
            double price = Double.parseDouble(request.getParameter("price"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            String image = request.getParameter("image");
            boolean status = (quantity > 0);

            boolean checkDuplicate = dao.checkDuplicate(productID);
            if (checkDuplicate) {
                clothesError.setProductID("This Product already exist!");
                checkValidation = false;
            }

            if (productName.length() < 2 || productName.length() > 50) {
                clothesError.setProductName("Product Name must be in [2-50] characters!");
                checkValidation = false;
            }

            if (image.length() < 5 || image.length() > 250) {
                clothesError.setImage("Image Link must be in [5-250] characters!");
                checkValidation = false;
            }

            if (price < 0) {
                clothesError.setPrice("Price must be > 0");
                checkValidation = false;
            }

            if (quantity < 0) {
                clothesError.setQuantity("Quantity must be > 0");
                checkValidation = false;
            }

            if (checkValidation) {
                ClothesDTO clothes = new ClothesDTO(productID, productName, price, quantity, image, status);
                boolean checkAdd = dao.addNewClothes(clothes);
                if (!checkAdd) {
                    clothesError.setErrorMessage("Unknown Error");
                }
                url = SUCCESS;
            } else {
                request.setAttribute("CLOTHES_ERROR", clothesError);
            }
        } catch (Exception e) {
            log("Error at AddProductController " + e.toString());
            if (e.toString().contains("duplicate")) {
                clothesError.setProductID("Duplicate ProductID!");
                request.setAttribute("CLOTHES_ERROR", clothesError);
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
