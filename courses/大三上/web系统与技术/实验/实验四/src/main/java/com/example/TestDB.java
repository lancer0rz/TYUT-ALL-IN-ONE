package com.example;

import java.sql.*;

/**
 * 数据库测试脚本 - 验证数据库连接和数据
 */
public class TestDB {
    public static void main(String[] args) {
        System.out.println("========== 数据库连接测试 ==========\n");
        
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        
        try {
            // 获取连接
            conn = DBUtil.getConnection();
            System.out.println("✓ 数据库连接成功！\n");
            
            stmt = conn.createStatement();
            
            // 测试1: 查询users表的所有数据
            System.out.println("--- 测试1: 查询所有用户 ---");
            rs = stmt.executeQuery("SELECT * FROM users");
            
            int count = 0;
            System.out.println(String.format("%-5s %-15s %-15s %-20s %-20s", 
                "ID", "用户名", "密码", "邮箱", "创建时间"));
            System.out.println("-".repeat(80));
            
            while (rs.next()) {
                count++;
                System.out.println(String.format("%-5d %-15s %-15s %-20s %-20s",
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("email") != null ? rs.getString("email") : "N/A",
                    rs.getTimestamp("create_time")));
            }
            System.out.println("-".repeat(80));
            System.out.println("✓ 共找到 " + count + " 个用户\n");
            
            // 测试2: 查询表结构
            System.out.println("--- 测试2: 表结构 ---");
            DatabaseMetaData meta = conn.getMetaData();
            rs = meta.getColumns(null, null, "USERS", null);
            
            System.out.println(String.format("%-15s %-15s %-15s %-10s", 
                "列名", "类型", "可空", "备注"));
            System.out.println("-".repeat(60));
            
            while (rs.next()) {
                String colName = rs.getString("COLUMN_NAME");
                String colType = rs.getString("TYPE_NAME");
                String nullable = rs.getInt("NULLABLE") == DatabaseMetaData.columnNullable ? "是" : "否";
                String remarks = rs.getString("REMARKS") != null ? rs.getString("REMARKS") : "";
                
                System.out.println(String.format("%-15s %-15s %-15s %-10s",
                    colName, colType, nullable, remarks));
            }
            System.out.println("-".repeat(60) + "\n");
            
            // 测试3: 查询统计信息
            System.out.println("--- 测试3: 统计信息 ---");
            rs = stmt.executeQuery("SELECT COUNT(*) as total FROM users");
            if (rs.next()) {
                System.out.println("✓ users表总记录数: " + rs.getInt("total"));
            }
            
            System.out.println("\n========== 测试完成！数据库正常工作 ==========");
            
        } catch (SQLException e) {
            System.out.println("✗ 数据库错误: " + e.getMessage());
            e.printStackTrace();
        } finally {
            DBUtil.close(rs, stmt, conn);
        }
    }
}
