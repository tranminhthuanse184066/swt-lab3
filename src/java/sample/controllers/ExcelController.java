package sample.controllers;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import sample.clothing.OrderDAO;
import sample.clothing.OrderDTO;

@WebServlet(name = "ExcelController", urlPatterns = {"/ExcelController"})
public class ExcelController extends HttpServlet {

    private static final String ERROR = "admin.jsp";
    private static final String SUCCESS = "admin.jsp";
    private static final String FILENAME = "orderData.xlsx";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=" + FILENAME);
        String url = ERROR;

        try {
            OrderDAO dao = new OrderDAO();
            List<OrderDTO> ordersList = dao.getAllOrders();
            if (ordersList.size() > 0) {
                Workbook workbook = new XSSFWorkbook();
                Sheet sheet = workbook.createSheet("Order data");
                String[] headerTitle = {"orderID", "userID", "orderDate", "total ($)"};
                Row headerRow = sheet.createRow(0);
                for (int i = 0; i < headerTitle.length; i++) {
                    headerRow.createCell(i).setCellValue(headerTitle[i]);
                }

                int rowNum = 1;
                for (OrderDTO order : ordersList) {
                    Row row = sheet.createRow(rowNum);
                    row.createCell(0).setCellValue(order.getOrderID());
                    row.createCell(1).setCellValue(order.getUserID());
                    row.createCell(2).setCellValue(order.getOrderDate().toString());
                    row.createCell(3).setCellValue(order.getTotal());
                    rowNum++;
                }

                workbook.write(response.getOutputStream());
                workbook.close();
                url = SUCCESS;
            } else {
                request.setAttribute("ERROR", "Your shop has no orders!");
            }
        } catch (Exception e) {
            log("Error at ExcelController " + e.toString());
            request.setAttribute("ERROR", "Failed to export orders.");
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
