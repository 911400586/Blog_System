package api;

import Model.Mark;
import Model.MarkDao;
import Model.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
@WebServlet("/mark")
public class MarkHttp extends HttpServlet {
    ObjectMapper mapper = new ObjectMapper();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        //resp.setContentType("Application/json");
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
        String content = req.getParameter("content");
        int userid = user.getUserid();
        String blogid = req.getParameter("blogid");
       Mark mark = new Mark();
        mark.setPosttime(new Timestamp(System.currentTimeMillis()));
        mark.setContent(content);
        mark.setUsername(user.getUsername());
        mark.setBlogid(Integer.parseInt(blogid));
        MarkDao.Add(mark);
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html");
        resp.getWriter().write("<h>评论成功,请刷新获取最新评论!!!</h>");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        resp.setContentType("Application/json");
        resp.setCharacterEncoding("UTF-8");
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
        //获取blogid从query
        String query = req.getParameter("blogid");
        List<Mark>  list = MarkDao.GetMarkList(Integer.parseInt(query));
        mapper.writeValue(resp.getWriter(),list);
    }
}
