<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Mon.Clothes"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Show Update Clothes</title>
</head>
<body>
    <%
        Clothes clothes = (Clothes) session.getAttribute("clothes");
        if (clothes == null) {
            response.sendRedirect("MonZa");
        }
    %>
    <div>ข้อมูลที่เพิ่ม</div>
    ชื่อผู้ซื้อ: <%= clothes.getName() %><br/>
    อีเมล: <%= clothes.getEmail() %><br/>
    ประเภทเสื้อผ้า: <%= clothes.getClothesType() %><br/>
    สีเสื้อผ้า: <%= clothes.getClothesColor() %><br/>
    การเลือกซื้อ: <%= (clothes.getPaymentType().equals("cash") ? "สด" : "ผ่อนชำระ") %><br/>
    ราคา: <%= clothes.getClothesPrice() %><br/>
    <a href='MonZa'>แก้ไขข้อมูล</a>
</body>
</html>
