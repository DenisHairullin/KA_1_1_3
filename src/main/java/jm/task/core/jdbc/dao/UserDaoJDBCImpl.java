package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Connection connection;

    public UserDaoJDBCImpl() {
        connection = Util.getConnection();
    }

    @Override
    public void createUsersTable() {
        try (Statement st = connection.createStatement()) {
            st.execute("CREATE TABLE users "
                    + "(id BIGINT AUTO_INCREMENT PRIMARY KEY, "
                    + "name VARCHAR(60), "
                    + "lastName VARCHAR(60), "
                    + "age TINYINT)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Statement sta = connection.createStatement()) {
            sta.execute("DROP TABLE users");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement st = connection.prepareStatement("INSERT INTO users " +
                "(name, lastName, age) VALUES (?, ?, ?)")) {

            connection.setAutoCommit(false);
            st.setString(1, name);
            st.setString(2, lastName);
            st.setByte(3, age);
            st.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException r) {
                r.printStackTrace();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        try (PreparedStatement st = connection.prepareStatement("DELETE FROM users WHERE id = ?")) {
            connection.setAutoCommit(false);
            st.setLong(1, id);
            st.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException r) {
                r.printStackTrace();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> l = new ArrayList<>();

        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery("SELECT name, lastName, age FROM users")) {

            while (rs.next()) {
                l.add(new User(rs.getString(1), rs.getString(2), rs.getByte(3)));
            }
            return l;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void cleanUsersTable() {
        try (Statement st = connection.createStatement()) {
            st.executeUpdate("TRUNCATE TABLE users");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
