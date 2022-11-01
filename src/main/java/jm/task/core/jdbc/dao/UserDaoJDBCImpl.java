package jm.task.core.jdbc.dao;

import com.sun.xml.bind.v2.runtime.output.SAXOutput;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private static String query;

    private static PreparedStatement preparedStatement;

    private static Connection connection;

    User user = new User();


    public UserDaoJDBCImpl() {

        try {
            connection = Util.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void createUsersTable() {
        query = "CREATE TABLE IF NOT EXISTS user (id int PRIMARY KEY AUTO_INCREMENT,\n" +
                "  name varchar(15),\n" +
                "  lastname varchar(20),\n" +
                "  age int)";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (Exception e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        }

    }

    public void dropUsersTable() {
        query = "TRUNCATE TABLE user";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (Exception e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        query = "INSERT INTO user (name, lastname, age) VALUES (?, ?, ?)";


        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (Exception e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        }
        System.out.println("User с именем " + name + " добавлен в таблицу!");
    }

    public void removeUserById(long id) {
        String query1 = "SELECT id FROM user";
        query = "DELETE FROM user WHERE id = ?";

        try {
            preparedStatement = connection.prepareStatement(query1);
            preparedStatement.executeQuery(query1);
            ResultSet resultSet = preparedStatement.getResultSet();
            List<Long> list = new ArrayList<>();
            while (resultSet.next()) {
                list.add(resultSet.getLong("id"));
            }
            if (!list.contains(id)) {
                System.out.println("Пользователя с id = " + id + " не существует!");
                preparedStatement.close();
            } else {
                preparedStatement.close();
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setLong(1, id);
                preparedStatement.executeUpdate();
                connection.commit();
            }
        } catch (Exception e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        }


    }

    public List<User> getAllUsers() {
        query = "SELECT * FROM user";

        List<User> userList = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute(query);
            ResultSet resultSet = preparedStatement.getResultSet();

            while (resultSet.next()) {
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastname"));
                user.setAge(resultSet.getByte("age"));
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            System.out.println(userList);
        }

        return userList;
    }

    public void cleanUsersTable() {
        query = "DROP TABLE IF EXISTS user ";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.commit();
        } catch (Exception e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        }

    }
}
