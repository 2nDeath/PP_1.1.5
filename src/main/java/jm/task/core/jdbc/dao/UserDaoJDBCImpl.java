package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try(Connection connection = Util.getConnection();
            Statement stmt = connection.createStatement()) {
            String quary = "CREATE TABLE IF NOT EXISTS users ("
                    + "id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,"
                    + "name VARCHAR(127) NOT NULL,"
                    + "lastName VARCHAR(127) NOT NULL,"
                    + "age TINYINT NOT NULL" + ");";
            stmt.executeUpdate(quary);
            System.out.println("Таблица создана");
        } catch(SQLException e) {
            System.out.println("Ошибка при создании таблицы " + e.getMessage());
        }
    }

    public void dropUsersTable() {
        try(Connection connection = Util.getConnection();
            Statement stmt = connection.createStatement()) {
            String quary = "DROP TABLE IF EXISTS users";
            stmt.executeUpdate(quary);
            System.out.println("Таблица была удалена");
        } catch(SQLException e) {
            System.out.println("Ошибка при удалении таблицы" + e.getMessage());
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String quary = "INSERT INTO users (name, lastName, age) VALUES (?,?,?)";
        try(Connection connection = Util.getConnection();
            PreparedStatement stmt = connection.prepareStatement(quary)) {
            stmt.setString(1, name);
            stmt.setString(2, lastName);
            stmt.setByte(3, age);
            stmt.executeUpdate();
            System.out.println("User " + name + " " + lastName + " добавлен");
        } catch(SQLException e) {
            System.out.println("Ошибка во время добавления User " + name + lastName + e.getMessage());
        }
    }

    public void removeUserById(long id) {
        String quary = "DELETE FROM users WHERE id = " + id;
        try(Connection connection = Util.getConnection();
            PreparedStatement stmt = connection.prepareStatement(quary)) {
//            stmt.setLong(1, id);
            stmt.executeUpdate(quary);
            System.out.println("Удален User с id = " + id);
        } catch(SQLException e) {
            System.out.println("Ошибка при удалении User с id = " + id + e.getMessage());
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList();
        try(Connection connection = Util.getConnection();
            Statement stmt = connection.createStatement()) {
            String quary = "SELECT * FROM users";
            ResultSet rs = stmt.executeQuery(quary);
            while(rs.next()) {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("name"));
                user.setLastName(rs.getString("lastName"));
                user.setAge(rs.getByte("age"));
                users.add(user);
            }
            System.out.println("Список пользователей получен");
            return users;
        } catch (SQLException e) {
            System.out.println("Ошибка при получении списка пользователей " + e.getMessage());
        }
        return null;
    }

    public void cleanUsersTable() {
        try(Connection connection = Util.getConnection();
            Statement stmt = connection.createStatement()) {
            String quary = "TRUNCATE TABLE users";
            stmt.executeUpdate(quary);
            System.out.println("Очистка таблицы произведена");
        } catch (SQLException e) {
            System.out.println("Ошибка при очистке таблицы " + e.getMessage());
        }
    }
}
