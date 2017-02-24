package util;

import com.mysql.jdbc.Driver;

import java.sql.*;

/**
 * Created by mingfei.net@gmail.com
 * 2/24/17 14:07
 */
public class DB {

    private static final String URL = "jdbc:mysql:///dictionary?user=root&password=system";

    public static Connection getConnection() {
        try {
            new Driver();
            return DriverManager.getConnection(URL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void close(ResultSet resultSet, PreparedStatement preparedStatement, Connection connection) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
