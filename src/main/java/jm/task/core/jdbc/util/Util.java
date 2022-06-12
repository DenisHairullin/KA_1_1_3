package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/ka_1_1_3";
    private static final String DB_USERNAME = "hdr";
    private static final String DB_PASSWORD = "hdrUser1";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
    }
}
