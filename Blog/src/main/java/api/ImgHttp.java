package api;

import Model.Blog;
import Model.BlogDao;
import Model.User;
import Model.UserDao;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.List;
import java.util.Stack;

@WebServlet("/img")
public class ImgHttp extends HttpServlet {
    //图片存储位置
    //private static String imgroot = "D:/JAVA_CODE/servlet/Blog/src/IMG/";
    //服务器上的路径
    private static String imgroot = "/root/apache-tomcat-8.5.92/webapps/source/img";

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
        User newuser =  UserDao.GetUserById(user.getUserid());
        //设置工厂
        DiskFileItemFactory factory=new DiskFileItemFactory();
        //设置缓冲区大小5M
        //factory.setSizeThreshold(1024*1024*5);
        //设置临时文件
        //factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
        //设置解析器
        ServletFileUpload sUpload=new ServletFileUpload(factory);
        try {
            //解析结果放list
            List<FileItem> list=sUpload.parseRequest(req);
            System.out.println("表单数据项数："+list.size());
            for(FileItem item:list){
                String name=item.getFieldName();
                System.out.println("数据项名："+name);
                InputStream in=item.getInputStream();
                //提交的表单中，文件键值为aFile
                if(name.equals("v1")){

                    //保存文件
                    //最好保存在绝对路径
                    System.out.println(item.getName());
                    //String path=this.getServletConfig().getServletContext().getRealPath("/");
                    //path=path+"\\images\\test2.png";
                    String img = item.getName();
                    String tail = img.substring(img.lastIndexOf('.'));
                    String imgname = newuser.getUserid()+tail;
                    String path=imgroot+imgname;
                    System.out.println(path);
                    File file=new File(path);
                    BufferedInputStream fi = new BufferedInputStream(in);
                    FileOutputStream fo = new FileOutputStream(file);
                    int f;
                    while ((f=fi.read())!=-1) {
                        fo.write(f);
                    }
                    fo.flush();
                    fo.close();
                    fi.close();
                    //上传完了 更该数据库中的img路径
                    UserDao.UpdateImg(user.getUserid(),path);
                }
                else {
                    System.out.println("一般表单"+item.getName()+","
                            +item.getString());
                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    public byte[] readfile(String path)
    {
        File file = new File(path);
        byte[] bt = new byte[1024*1024*2*10];
        try {
            InputStream in = new FileInputStream(file);
            in.read(bt);
            in.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return bt;
    }
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
        String bid = req.getParameter("blogid");
        if(bid!=null)
        {
            //不是当前用户的头像
            Blog blog = BlogDao.GetBolgByid(Integer.parseInt(bid));
            int userid = blog.getUserid();
            String img = UserDao.GetUserById(userid).getImg();
            String tail = img.substring(img.lastIndexOf('.')+1);//不需要.
            resp.setContentType("image/"+tail);
            resp.getOutputStream().write(readfile(img));
        }
        else{
            User newuser =  UserDao.GetUserById(user.getUserid());
            String img = newuser.getImg();
            String tail = img.substring(img.lastIndexOf('.')+1);//不需要.
            resp.setContentType("image/"+tail);
            resp.getOutputStream().write(readfile(img));
        }

    }
}
