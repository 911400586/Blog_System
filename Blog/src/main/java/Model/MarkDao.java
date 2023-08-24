package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MarkDao {
    public static void Add(Mark mark)
    {
        Connection connection = DButil.GetConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
//        +----------+--------------+------+-----+---------+-------+
//                | Field    | Type         | Null | Key | Default | Extra |
//                +----------+--------------+------+-----+---------+-------+
//                | username | varchar(20)  | YES  | UNI | NULL    |       |
//| blogid   | int(11)      | YES  |     | NULL    |       |
//| content  | varchar(150) | YES  |     | NULL    |       |
//| posttime | datetime     | YES  |     | NULL    |       |
//        +----------+--------------+------+-----+---------+-------+
        if(connection!=null)
        {
            String sql = "insert into mark values(?,?,?,?)";
            try {
                statement = connection.prepareStatement(sql);
                statement.setString(1,mark.getUsername());
                statement.setInt(2,mark.getBlogid());
                statement.setString(3,mark.getContent());
                statement.setString(4,mark.getPosttime());
                statement.executeUpdate();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }finally {
                DButil.Close(connection,statement,resultSet);
            }

        }

    }
    public static List<Mark> GetMarkList(int blogid)
    {
        Connection connection = DButil.GetConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Mark> list = new ArrayList<>();
        if(connection!=null)
        {
            String sql = "select * from mark where blogid=? order by posttime desc";
            try {
                statement = connection.prepareStatement(sql);
                statement.setInt(1,blogid);
                resultSet = statement.executeQuery();
                Mark mark = null;
                while(resultSet.next())
                {
                    mark = new Mark();
                    mark.setUsername(resultSet.getString("username"));
                    mark.setPosttime(resultSet.getTimestamp("posttime"));
                    mark.setBlogid(resultSet.getInt("blogid"));
                    mark.setContent(resultSet.getString("content"));
                    list.add(mark);
                }
                return list;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }finally {
                DButil.Close(connection,statement,resultSet);
            }

        }
        return null;
    }
}
