package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {
    public static User GetUserById(int userid) {

        Connection connection = DButil.GetConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        if (connection != null) {
            String sql = "select * from user where userid=?";
            try {
                statement = connection.prepareStatement(sql);
                statement.setInt(1, userid);
                resultSet = statement.executeQuery();

                User user = new User();
                if (resultSet.next()) {
                    user.setUserid(resultSet.getInt("userid"));
                    user.setUsername(resultSet.getString("username"));
                    user.setPassword(resultSet.getString("password"));
                    user.setImg(resultSet.getString("img"));
                    return user;
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                DButil.Close(connection, statement, resultSet);
            }
        }
        return null;
    }
    public static void UpdateImg(int userid,String path)
    {
        Connection connection = DButil.GetConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        if (connection != null) {
            String sql = "update user SET img = ? where userid = ?";
            try {
                statement = connection.prepareStatement(sql);
                statement.setString(1, path);
                statement.setInt(2, userid);
                statement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                DButil.Close(connection, statement, resultSet);
            }
        }
        return ;
    }
    public static User GetUserByName(String name) {
        Connection connection = DButil.GetConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        if (connection != null) {
            String sql = "select * from user where username=?";
            try {
                statement = connection.prepareStatement(sql);
                statement.setString(1,name);
                resultSet = statement.executeQuery();

                User user = new User();
                if (resultSet.next()) {
                    user.setUserid(resultSet.getInt("userid"));
                    user.setUsername(resultSet.getString("username"));
                    user.setPassword(resultSet.getString("password"));
                    user.setImg(resultSet.getString("img"));
                    return user;
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                DButil.Close(connection, statement, resultSet);
            }
        }
        return null;
    }
}
