<%--
  Created by IntelliJ IDEA.
  User: ACER
  Date: 3/21/2026
  Time: 12:38 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="java.sql.*" contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<%
    String name = request.getParameter("name");
    String email = request.getParameter("email");
    String feedback = request.getParameter("feedback");

    if (name != null && email != null) {
        String url = "jdbc:postgresql://localhost:5432/userdb";
        String user = "postgres";
        String pass = "your_password";

        try {
            Class.forName("org.postgresql.Driver");
            Connection con = DriverManager.getConnection(url, user, pass);

            String sql = "INSERT INTO feedbacks(name,email,message) VALUES(?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, feedback);

            ps.executeUpdate();
            con.close();
%>
<h3>Thank you, <%= name %>! Your feedback has been submitted.</h3>
<%
} catch (Exception e) {
%>
<h3>Error: <%= e.getMessage() %></h3>
<%
        }
    }
%>
</body>
</html>