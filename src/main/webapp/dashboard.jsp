<%
    response.setHeader("Cache-Control","no-cache, no-store, must-revalidate");
    response.setHeader("Pragma","no-cache");
    response.setDateHeader("Expires", 0);
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    // Get session
    String username = (String) session.getAttribute("username");

    // If not logged in, redirect to login page
    if (username == null) {
        response.sendRedirect("login.jsp");
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Dashboard</title>
</head>
<body>

<h2>Welcome to Dashboard</h2>

<p>Hello, <b><%= username %></b>!</p>

<a href="LogoutServlet">Logout</a>

</body>
</html>