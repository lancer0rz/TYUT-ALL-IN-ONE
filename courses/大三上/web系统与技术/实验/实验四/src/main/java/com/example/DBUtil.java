package com.example;

import java.sql.*;

/**
 * 数据库工具类 - 使用H2内嵌数据库
 */
public class DBUtil {
    private static final String URL = "jdbc:h2:./data/experiment4;MODE=MySQL;AUTO_SERVER=TRUE";
    private static final String USER = "sa";
    private static final String PASS = "";

    static {
        try {
            Class.forName("org.h2.Driver");
            initDB();
            System.out.println("数据库初始化成功！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    // 初始化数据库表
    private static void initDB() throws SQLException {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            // 创建users表（避免使用user保留字）
            stmt.execute("CREATE TABLE IF NOT EXISTS users (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "username VARCHAR(50) UNIQUE NOT NULL," +
                "password VARCHAR(100) NOT NULL," +
                "email VARCHAR(100)," +
                "create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP)");
            
            // 插入测试用户（如果不存在）
            stmt.execute("MERGE INTO users (username, password, email) KEY(username) " +
                "VALUES ('admin', '123456', 'admin@test.com')");
        }
    }

    public static void close(AutoCloseable... resources) {
        for (AutoCloseable r : resources) {
            if (r != null) try { r.close(); } catch (Exception ignored) {}
        }
    }
}
