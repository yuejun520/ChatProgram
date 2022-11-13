package com.hbnu.server.jdbc.dbc;


import java.io.FileInputStream;
import java.sql.*;
import java.util.Properties;

/**
 * @author luanhao
 * @date 2022年11月01日 23:47
 */
public class ConnectionManager {
    public static Connection getConnection() throws Exception {
        Properties prop = new Properties();
        prop.load(new FileInputStream("src/druid.properties"));

        Connection conn = null;

        Class.forName(prop.getProperty("driverClassName"));
        conn = DriverManager.getConnection(prop.getProperty("url"), prop.getProperty("username"), prop.getProperty("password"));

        return conn;
    }

    public static void release(PreparedStatement pstmt, Connection conn) throws Exception {
        pstmt.close();
        conn.close();
    }

    public static void release(ResultSet rs, PreparedStatement pstmt, Connection conn) throws Exception {
        rs.close();
        pstmt.close();
        conn.close();
    }

    public static void release(Statement stmt, Connection conn) throws Exception {
        stmt.close();
        conn.close();
    }
}
