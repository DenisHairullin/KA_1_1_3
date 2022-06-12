package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Connection connection;

    public UserDaoJDBCImpl() {
        try {
            connection = Util.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createUsersTable() {
        try (ResultSet rs = connection.getMetaData().getTables(connection.getCatalog(), null, "users", null);
                Statement st = connection.createStatement()) {

            if (!rs.next()) {
                st.execute("CREATE TABLE users "
                    + "(id BIGINT AUTO_INCREMENT PRIMARY KEY, "
                    + "name VARCHAR(60), "
                    + "lastName VARCHAR(60), "
                    + "age TINYINT)");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        try (ResultSet rs = connection.getMetaData().getTables(connection.getCatalog(), null, "users", null);
                Statement sta = connection.createStatement()) {

            if (rs.next()) {
                sta.execute("DROP TABLE users");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement st = connection.prepareStatement("INSERT INTO users " +
                "(name, lastName, age) VALUES (?, ?, ?)")) {

            st.setString(1, name);
            st.setString(2, lastName);
            st.setByte(3, age);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement st = connection.prepareStatement("DELETE FROM users WHERE id = ?")) {
            st.setLong(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> l = new ArrayList<>();

        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery("SELECT name, lastName, age FROM users")) {

            while (rs.next()) {
                l.add(new User(rs.getString(1), rs.getString(2), rs.getByte(3)));
            }
            return l;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void cleanUsersTable() {
        try (Statement st = connection.createStatement()) {
            st.executeUpdate("TRUNCATE TABLE users");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
