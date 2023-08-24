package api;

import Model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/logout")
public class LogoutHttp extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if(session==null)
        {
            //还没有登录
           resp.sendRedirect("blog_login.html");
            return;
        }
        User user = (User)session.getAttribute("user");
        if(user==null)
        {

            resp.sendRedirect("blog_login.html");
            return;
        }
        //将user移除
        session.removeAttribute("user");
        resp.sendRedirect("blog_login.html");
    }
}
