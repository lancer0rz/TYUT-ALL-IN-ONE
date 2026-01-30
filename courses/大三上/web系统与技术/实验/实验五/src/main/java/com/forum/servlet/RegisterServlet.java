package com.forum.servlet;

import com.forum.dao.UserDao;
import com.forum.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * 用户注册Servlet
 */
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    
    private UserDao userDao = new UserDao();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String nickname = request.getParameter("nickname");
        
        // 验证参数
        if (username == null || username.trim().isEmpty() ||
            password == null || password.trim().isEmpty()) {
            request.setAttribute("error", "请填写完整信息！");
            request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
            return;
        }
        
        // 检查用户名是否存在
        if (userDao.isUsernameExists(username)) {
            request.setAttribute("error", "用户名已存在！");
            request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
            return;
        }
        
        // 处理昵称
        if (nickname == null || nickname.trim().isEmpty()) {
            nickname = username;
        }
        
        // 保存用户
        User user = new User(username, password, email, nickname);
        boolean success = userDao.addUser(user);
        
        if (success) {
            response.sendRedirect(request.getContextPath() + "/login?msg=registered");
        } else {
            request.setAttribute("error", "注册失败，请稍后重试！");
            request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
        }
    }
}
