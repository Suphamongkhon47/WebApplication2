package connection;

import Mon.Clothes;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;



@WebServlet(name = "DBConnection", urlPatterns = {"/DBConnection"})
public class DBConnection extends HttpServlet {

    public static Connection getConnection() {
        Connection conn = null;
        try {
            // โหลด JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // เชื่อมต่อกับฐานข้อมูล
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/com?zeroDateTimeBehavior=CONVERT_TO_NULL", "root", "Mon1234");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public boolean insertNewClothes(Clothes clothes) {
        boolean result = false;
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = getConnection();
            String sql = "INSERT INTO clothes_orders (name, email, clothesType, clothesColor, paymentType, clothesPrice) VALUES (?, ?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, clothes.getName());
            pstmt.setString(2, clothes.getEmail());
            pstmt.setString(3, clothes.getClothesType());
            pstmt.setString(4, clothes.getClothesColor());
            pstmt.setString(5, clothes.getPaymentType());
            pstmt.setBigDecimal(6, clothes.getClothesPrice());

            int rowsAffected = pstmt.executeUpdate();
            result = rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
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

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            // Getting data from the request
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String clothesType = request.getParameter("clothesType");
            String clothesColor = request.getParameter("clothesColor");
            String paymentType = request.getParameter("paymentType");
            BigDecimal clothesPrice = new BigDecimal(request.getParameter("clothesPrice"));
            
            // Creating Clothes object
            Clothes clothes = new Clothes();
            clothes.setName(name);
            clothes.setEmail(email);
            clothes.setClothesType(clothesType);
            clothes.setClothesColor(clothesColor);
            clothes.setPaymentType(paymentType);
            clothes.setClothesPrice(clothesPrice);
            
            // Inserting data into database
            DBConnection dbConnection = new DBConnection();
            if (dbConnection.insertNewClothes(clothes)) {
                out.println("<html><body>");
                out.println("<h1>ข้อมูลถูกบันทึกเรียบร้อยแล้ว</h1>");
                out.println("<p>ชื่อผู้ซื้อ: " + clothes.getName() + "</p>");
                out.println("<p>อีเมล: " + clothes.getEmail() + "</p>");
                out.println("<p>ประเภทเสื้อผ้า: " + clothes.getClothesType() + "</p>");
                out.println("<p>สีเสื้อผ้า: " + clothes.getClothesColor() + "</p>");
                out.println("<p>การเลือกซื้อ: " + (clothes.getPaymentType().equals("cash") ? "สด" : "ผ่อนชำระ") + "</p>");
                out.println("<p>ราคา: " + clothes.getClothesPrice() + "</p>");
                out.println("</body></html>");
            } else {
                out.println("<html><body><h1>เกิดข้อผิดพลาดในการบันทึกข้อมูล</h1></body></html>");
            }
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
