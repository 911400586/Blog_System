package api;

import Model.Blog;
import Model.BlogDao;
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

@WebServlet("/user")
public class UserHttp extends HttpServlet {
    private ObjectMapper mapper = new ObjectMapper();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession(false);
        if(session==null)
        {
            //还没有登录
           resp.setContentType("text/html");
           resp.getWriter().write("请先登录!");
            return;
        }
        User user = (User)session.getAttribute("user");
        if(user==null)
        {
            //还没有登录
            resp.setContentType("text/html");
            resp.getWriter().write("请先登录!");
            return;
        }
        int blogid = Integer.parseInt(req.getParameter("blogid"));
        Blog blog = BlogDao.GetBolgByid(blogid);
        if(blog==null)
        {
            resp.setContentType("text/html");
            resp.getWriter().write("不存在该文章!");
            return;
        }
        User use = UserDao.GetUserById(blog.getUserid());
        if(use==null)
        {
            resp.setContentType("text/html");
            resp.getWriter().write("不存在该用户!");
            return;
        }
        resp.setContentType("Application/json");
        mapper.writeValue(resp.getWriter(),use);
    }

//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        req.setCharacterEncoding("UTF-8");
//        resp.setCharacterEncoding("UTF-8");
//        HttpSession session = req.getSession(false);
//        if(session==null)
//        {
//            //还没有登录
//            resp.setContentType("text/html");
//            resp.getWriter().write("请先登录!");
//            return;
//        }
//        User user = (User)session.getAttribute("user");
//        if(user==null)
//        {
//            //还没有登录
//            resp.setContentType("text/html");
//            resp.getWriter().write("请先登录!");
//            return;
//        }
//        //确保登录状态
//
//    }
}
