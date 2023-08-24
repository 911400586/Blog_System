package api;

import Model.Blog;
import Model.BlogDao;
import Model.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.jws.WebService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
@WebServlet("/blog")
public class BlogHttp extends HttpServlet {
    private static ObjectMapper jackson = new ObjectMapper();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String query = req.getParameter("blogid");
        if(query==null)
        {
            List<Blog> list = BlogDao.GetBolgList();
            resp.setContentType("Application/json");
            resp.setCharacterEncoding("UTF-8");
            if(list==null)
            {
                return;
            }
            jackson.writeValue(resp.getWriter(),list);
            return;
        }
        else if(query=="mark"){

        }
        else{
            Blog blog = BlogDao.GetBolgByid(Integer.parseInt(query));
            resp.setContentType("Application/json");
            resp.setCharacterEncoding("UTF-8");

            jackson.writeValue(resp.getWriter(),blog);
            return;
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
        String title = req.getParameter("title");
        String content = req.getParameter("content");
        if(title.equals("") || content.equals(""))
        {
            resp.setContentType("text/html");
            resp.getWriter().write("标题和内容不能为空！！");
            return;
        }
        //确保可以获取user相关信息
        Blog blog = new Blog();
        blog.setTitle(req.getParameter("title"));
        blog.setContent(req.getParameter("content"));
        blog.setUserid(user.getUserid());
        blog.setPosttime(new Timestamp(System.currentTimeMillis()));
       boolean t =  BlogDao.Add(blog);
       if(t==true)
       {
           resp.setContentType("text/html");
           resp.getWriter().write("文章发布成功!");
       }
       else{
           resp.setContentType("text/html");
           resp.getWriter().write("文章发布失败!");
       }
    }
}
