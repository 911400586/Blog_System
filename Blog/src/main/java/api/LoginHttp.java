package api;

import Model.BlogDao;
import Model.DButil;
import Model.User;
import Model.UserDao;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginHttp extends HttpServlet {
    private ObjectMapper mapper = new ObjectMapper();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        if(username.equals("") || password.equals("") || username.length()>20 || password.length()>20)
        {
            //登录失败

            resp.setContentType("text/html");
            resp.getWriter().write("<h>404：账号或者密码为空！</h>");
            return;
        }
        User user = UserDao.GetUserByName(username);
        if(user==null)
        {

            resp.setContentType("text/html");
            resp.getWriter().write("<h>404：账户或者密码错误！</h>");
            return;
        }
        //存在该用户进行密码验证
        if(!password.equals(user.getPassword()))
        {
            //验证失败

            resp.setContentType("text/html");
            resp.getWriter().write("<h>404：账户或者密码错误！</h>");
            return;
        }
        //登录成功 建立会话
        HttpSession session = req.getSession(true);
        //如果注销后,在登录,该session id 继续使用即便用户不一样了
            //1.存储该会话用户相关信息

            session.setAttribute("user",user);
            resp.sendRedirect("blog_list.html");


    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //强制用户登录,禁止重复登录
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("Application/json");
        //resp.setStatus(200);
        HttpSession session = req.getSession(false);
        if(session==null)
        {
            //还没有登录
            User user = new User();
            mapper.writeValue(resp.getWriter(),user);
            return;
        }
        User user = (User)session.getAttribute("user");
        if(user==null)
        {
            //还没有登录
            User user1 = new User();
            mapper.writeValue(resp.getWriter(),user1);
            return;
        }
        else{
            mapper.writeValue(resp.getWriter(),user);
            return;
        }

    }
}

