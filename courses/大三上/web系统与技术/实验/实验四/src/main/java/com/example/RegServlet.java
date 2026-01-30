package com.example;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;

/**
 * 注册Servlet - 将用户信息保存到数据库
 */
@WebServlet("/RegServlet")
public class RegServlet extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        
        // 验证参数
        if (username == null || username.trim().isEmpty() || 
            password == null || password.trim().isEmpty()) {
            response.sendRedirect("register.jsp?error=empty");
            return;
        }
        
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            conn = DBUtil.getConnection();
            
            // 检查用户名是否已存在
            ps = conn.prepareStatement("SELECT id FROM users WHERE username = ?");
            ps.setString(1, username);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                // 用户名已被占用
                response.sendRedirect("register.jsp?error=exists");
                return;
            }
            
            DBUtil.close(rs, ps);
            
            // 插入新用户
            ps = conn.prepareStatement("INSERT INTO users (username, password, email) VALUES (?, ?, ?)");
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, email);
            ps.executeUpdate();
            
            // 注册成功，跳转到登录页面
            response.sendRedirect("login.jsp?msg=register");
            
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("register.jsp?error=error");
        } finally {
            DBUtil.close(rs, ps, conn);
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.sendRedirect("register.jsp");
    }
}
