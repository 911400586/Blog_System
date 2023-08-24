package Model;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DButil {
   // private static final String URL = "jdbc:mysql://127.0.0.1:3306/blog_system?characterEncoding=utf8&SSL=false";
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/Blog_System?characterEncoding=utf8&SSL=false";
    private static final String USER = "root";
//    private static final String PASSWORD = "";
    private static final String PASSWORD = "root";
    private static DataSource dataSource = null;
    private static DataSource GetDataSource()
    {
        if(dataSource==null)
        {
            synchronized (DButil.class)
            {
                if(dataSource==null)
                {
                    dataSource = new MysqlDataSource();
                    ((MysqlDataSource)dataSource).setURL(URL);
                    ((MysqlDataSource)dataSource).setUser(USER);
                    ((MysqlDataSource)dataSource).setPassword(PASSWORD);
                }
            }
        }
        return dataSource;
    }
    public static Connection GetConnection()
    {
        DataSource source = GetDataSource();
        if(source!=null)
        {
            try {
                return source.getConnection();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }
    public static void Close(Connection connection, PreparedStatement statement, ResultSet resultSet)
    {
        if(resultSet!=null)
        {
            try {
                resultSet.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if(statement!=null)
        {
            try {
                statement.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if(connection!=null)
        {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

