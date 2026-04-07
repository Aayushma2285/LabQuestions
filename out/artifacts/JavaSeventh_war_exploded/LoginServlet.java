package labQuestions.servletAndJSP;
//commit
import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
public class LoginServlet extends HttpServlet{
    private final String URL = "jdbc:postgresql://localhost:5432/userdb";
    private final String USER = "postgres";
    private final String PASS = "Aa2023@#";


    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Cookie[] cookies = request.getCookies();
        String username = null;

        if(cookies != null){
            for(Cookie c : cookies){
                if(c.getName().equals("username")){
                    username = c.getValue();
                    break;
                }
            }
        }

        if(username != null){
            // User has a cookie → redirect to dashboard
            response.sendRedirect("dashboard.jsp");
        } else {
            // Show login page
            response.sendRedirect("login.jsp"); // or login.html
        }
    }

    // 2️⃣ Handle POST request - process login form
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            Class.forName("org.postgresql.Driver");
            Connection con = DriverManager.getConnection(URL, USER, PASS);

            String sql = "SELECT * FROM users WHERE username=? AND password=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // Create session
                HttpSession session = request.getSession();
                session.setAttribute("username", username);

                // Create cookie
                Cookie cookie = new Cookie("username", username);
                cookie.setMaxAge(60*60); // 1 hour
                response.addCookie(cookie);

                response.sendRedirect("dashboard.jsp"); // Redirect after successful login
            } else {
                out.println("<h2>Login Failed</h2>");
                out.println("<a href='login.jsp'>Try Again</a>");
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
            out.println("Error: " + e.getMessage());
        }
    }
}
