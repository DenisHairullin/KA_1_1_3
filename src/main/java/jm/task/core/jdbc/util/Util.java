package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/ka_1_1_3";
    private static final String DB_USERNAME = "hdr";
    private static final String DB_PASSWORD = "hdrUser1";

    private static Connection connection = null;

    private static SessionFactory sessionFactory = null;

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null || sessionFactory.isClosed()) {
            Configuration config = new Configuration()
                    .setProperty("hibernate.connection.url", DB_URL)
                    .setProperty("hibernate.connection.username", DB_USERNAME)
                    .setProperty("hibernate.connection.password", DB_PASSWORD)
                    .setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver")
                    .setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect")
                    .addAnnotatedClass(User.class);

            try {
                sessionFactory = config.buildSessionFactory();
            } catch (HibernateException e) {
                e.printStackTrace();
            }
        }

        return sessionFactory;
    }
}
