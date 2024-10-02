package Mon331;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import Mon.Clothes;
import connection.DBConnection;

@WebServlet(urlPatterns = {"/MonZa"})
public class MonZa extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
            HttpSession session = request.getSession(); // Ensure session exists
            Clothes clothes = (Clothes) session.getAttribute("clothes");
            
            if (clothes == null) {
                clothes = new Clothes();
                clothes.setName(request.getParameter("name"));
                clothes.setEmail(request.getParameter("email"));
                clothes.setClothesType(request.getParameter("clothesType"));
                clothes.setClothesColor(request.getParameter("clothesColor"));
                clothes.setPaymentType(request.getParameter("paymentType")); // Fixed: changed to paymentType
                clothes.setClothesPrice(new BigDecimal(request.getParameter("clothesPrice"))); // Convert to BigDecimal
                session.setAttribute("clothes", clothes);
                
                DBConnection dbConnection = new DBConnection();
                if (!dbConnection.insertNewClothes(clothes)) {
                    out.println("<h1>เกิดข้อผิดพลาดในการเพิ่มข้อมูล</h1>");
                } else {
                    out.println("<h1>ข้อมูลที่บันทึก</h1>");
                    out.println("<p>ชื่อผู้ซื้อ: " + clothes.getName() + "</p>");
                    out.println("<p>อีเมล: " + clothes.getEmail() + "</p>");
                    out.println("<p>ประเภทเสื้อผ้า: " + clothes.getClothesType() + "</p>");
                    out.println("<p>สีเสื้อผ้า: " + clothes.getClothesColor() + "</p>");
                    out.println("<p>การเลือกซื้อ: " + (clothes.getPaymentType().equals("cash") ? "สด" : "ผ่อนชำระ") + "</p>");
                    out.println("<p>ราคา: " + clothes.getClothesPrice() + "</p>");
                    out.println("<br/><a href='MonZa'>แก้ไขข้อมูล</a>");
                }

                // Redirect to the same page for showing the updated data
                response.sendRedirect("Showupdataclothes.jsp");

            } else {
                out.println("<html><body><b>ข้อมูลเดิม:</b><br/>"
                        + "ชื่อผู้ซื้อ: " + clothes.getName() + "<br/>"
                        + "อีเมล: " + clothes.getEmail() + "<br/>"
                        + "ประเภทเสื้อผ้า: " + clothes.getClothesType() + "<br/>"
                        + "สีเสื้อผ้า: " + clothes.getClothesColor() + "<br/>"
                        + "การเลือกซื้อ: " + (clothes.getPaymentType().equals("cash") ? "สด" : "ผ่อนชำระ") + "<br/>"
                        + "ราคา: " + clothes.getClothesPrice()
                        + "<br/><br/><br/>");
                
                // Form for editing existing data
                out.println("<h1>แก้ไขข้อมูล</h1>");
                out.println("<form action='MonZa' method='post'>");
                out.println("ชื่อ: <input type='text' name='name' value='" + clothes.getName() + "'><br/>");
                out.println("อีเมล: <input type='text' name='email' value='" + clothes.getEmail() + "'><br/>");
                out.println("ประเภทเสื้อผ้า: <select name='clothesType'>");
                out.println("<option value=\"long-sleeved shirt\" " + ("long-sleeved shirt".equals(clothes.getClothesType()) ? "selected" : "") + ">long-sleeved shirt</option>");
                out.println("<option value=\"Short sleeve shirt\" " + ("Short sleeve shirt".equals(clothes.getClothesType()) ? "selected" : "") + ">Short sleeve shirt</option>");
                out.println("<option value=\"trousers\" " + ("trousers".equals(clothes.getClothesType()) ? "selected" : "") + ">trousers</option>");
                out.println("<option value=\"shorts\" " + ("shorts".equals(clothes.getClothesType()) ? "selected" : "") + ">shorts</option>");
                out.println("<option value=\"coat\" " + ("coat".equals(clothes.getClothesType()) ? "selected" : "") + ">coat</option></select><br/>");
                out.println("สีเสื้อผ้า: <select name='clothesColor'>");
                out.println("<option value=\"White\" " + ("White".equals(clothes.getClothesColor()) ? "selected" : "") + ">White</option>");
                out.println("<option value=\"Orange\" " + ("Orange".equals(clothes.getClothesColor()) ? "selected" : "") + ">Orange</option>");
                out.println("<option value=\"Gray\" " + ("Gray".equals(clothes.getClothesColor()) ? "selected" : "") + ">Gray</option>");
                out.println("<option value=\"Cream\" " + ("Cream".equals(clothes.getClothesColor()) ? "selected" : "") + ">Cream</option>");
                out.println("<option value=\"Black\" " + ("Black".equals(clothes.getClothesColor()) ? "selected" : "") + ">Black</option></select><br/>");
                out.println("การเลือกซื้อ: ");
                out.println("<input type='radio' name='paymentType' value='cash' " + (clothes.getPaymentType().equals("cash") ? "checked" : "") + "> สด ");
                out.println("<input type='radio' name='paymentType' value='credit' " + (clothes.getPaymentType().equals("credit") ? "checked" : "") + "> ผ่อนชำระ <br/>");
                out.println("ราคา: <input type='text' name='clothesPrice' value='" + clothes.getClothesPrice() + "'><br/>");
                out.println("<input type='submit' value='บันทึก'>");
                out.println("</form>");
                session.removeAttribute("clothes");
            }

        } catch (Exception e) {
            response.setContentType("text/html;charset=UTF-8");
            try (PrintWriter out = response.getWriter()) {
                out.print("<html><body>");
                out.print("Error: " + e.getMessage());
                out.print("</body></html>");
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Servlet for handling clothes orders";
    }
}
