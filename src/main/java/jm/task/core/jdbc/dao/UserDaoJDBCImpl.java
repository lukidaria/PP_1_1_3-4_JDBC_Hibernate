package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    String query;

    PreparedStatement preparedStatement;

    User user = new User();


    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        query = "CREATE TABLE IF NOT EXISTS user (id int PRIMARY KEY AUTO_INCREMENT,\n" +
                "  name varchar(15),\n" +
                "  lastname varchar(20),\n" +
                "  age int)";
        try {
            preparedStatement = Util.getConnection().prepareStatement(query);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void dropUsersTable() {
        query = "TRUNCATE TABLE user";
        try {
            preparedStatement = Util.getConnection().prepareStatement(query);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        query = "INSERT INTO user (name, lastname, age) VALUES (?, ?, ?)";


        try {
            preparedStatement = Util.getConnection().prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("User с именем " + name + " добавлен в таблицу!");


    }

    public void removeUserById(long id) {
        query = "DELETE FROM user WHERE id = ?";
        try {
            preparedStatement = Util.getConnection().prepareStatement(query);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public List<User> getAllUsers() {
        query = "SELECT * FROM user";

        List<User> userList = new ArrayList<>();
        try {
            preparedStatement = Util.getConnection().prepareStatement(query);
            preparedStatement.execute(query);
            ResultSet resultSet = preparedStatement.getResultSet();

            while (resultSet.next()) {
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastname"));
                user.setAge(resultSet.getByte("age"));
                userList.add(user);

            }
            preparedStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userList;
    }

    public void cleanUsersTable() {
        query = "DROP TABLE IF EXISTS user ";
        try {
            preparedStatement = Util.getConnection().prepareStatement(query);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
