package jm.task.core.jdbc.util;


import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Util {
    // реализуйте настройку соеденения с БД


    private static final String URL = "jdbc:mysql://localhost:3306/my_db?useSSL=false&serverTimezone=UTC";
    private static final String USERNAME = "bestuser";
    private static final String PASSWORD = "bestuser";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";


    public Util(){


    }

    private static Connection connection=null;

    public static Connection getConnection() throws SQLException {

        try  {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            Class.forName(DRIVER);
            System.out.println("Connection successful!");
            //connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Connection failed!");
            e.printStackTrace();
        } finally {
            //connection.close();
        }

        return connection;
    }






}
