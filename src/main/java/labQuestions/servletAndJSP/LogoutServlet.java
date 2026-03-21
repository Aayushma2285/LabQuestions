package labQuestions.servletAndJSP;
//commit
import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
public class LogoutServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null) session.invalidate();

        // Remove cookie
        Cookie cookie = new Cookie("username", "");
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        response.sendRedirect("login.html");
    }
}
