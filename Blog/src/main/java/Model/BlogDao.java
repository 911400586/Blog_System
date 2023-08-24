package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BlogDao {
//    操作数据库的一些方法
    public static Blog GetBolgByid(int blogid)
    {
        Connection connection = DButil.GetConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        if(connection!=null)
        {
            String sql = "select * from blog where blogid=?";
            try {
                statement = connection.prepareStatement(sql);
                statement.setString(1,String.valueOf(blogid));
                 resultSet =  statement.executeQuery();
                Blog blog = null;
                if(resultSet.next())
                {
                    blog = new Blog();
                    blog.setUserid(resultSet.getInt("userid"));
                    blog.setBlogid(resultSet.getInt("blogid"));
                    blog.setContent(resultSet.getString("content"));
                    blog.setTitle(resultSet.getString("title"));
                    blog.setPosttime(resultSet.getTimestamp("posttime"));
                }
                return blog;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }finally {
                DButil.Close(connection,statement,resultSet);
            }
        }
        return null;
    }
    //主要用于构造博客列表页
    public static List<Blog> GetBolgList()
    {
        Connection connection = DButil.GetConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Blog> list = new ArrayList<>();
        if(connection!=null)
        {
            String sql = "select * from blog order by posttime desc";
            try {
                statement = connection.prepareStatement(sql);
                resultSet =  statement.executeQuery();
                Blog blog = null;
                while(resultSet.next())
                {
                    blog = new Blog();
                    blog.setUserid(resultSet.getInt("userid"));
                    blog.setBlogid(resultSet.getInt("blogid"));
                    String content = resultSet.getString("content");
                    if(content.length()>100)
                    {
                        content = content.substring(0,100);
                    }
                    blog.setContent(content);
                    blog.setTitle(resultSet.getString("title"));
                    blog.setPosttime(resultSet.getTimestamp("posttime"));
                    list.add(blog);
                }
                return list;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
        return null;
    }
    public static boolean Add(Blog blog)
    {
        Connection connection = DButil.GetConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        if(connection!=null)
        {
            String sql = "insert into blog values(?,?,?,?,?)";
            try {
                statement = connection.prepareStatement(sql);
                statement.setInt(1,blog.getBlogid());
                statement.setString(2,blog.getTitle());
                statement.setString(3,blog.getContent());
                statement.setString(4,blog.getPosttime());
                statement.setInt(5,blog.getUserid());
                int a = statement.executeUpdate();
                if(a>0)
                {
                    return true;
                }
                else{
                    return false;
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }

    public void Delete(int blogid)
    {

    }
}
