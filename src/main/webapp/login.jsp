<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
</head>
<body>

<h2>Login</h2>

<form action="LoginServlet" method="post">
    Username: <input type="text" name="username" required><br><br>
    Password: <input type="password" name="password" required><br><br>

    <input type="submit" value="Login">
</form>

<%
    String error = request.getParameter("error");
    if ("1".equals(error)) {
%>
<p style="color:red;">Invalid username or password</p>
<%
    }
%>

</body>
</html>