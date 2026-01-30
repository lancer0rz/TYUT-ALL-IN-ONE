package com.forum.servlet;

import com.forum.dao.UserDao;
import com.forum.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * 用户登录Servlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    
    private UserDao userDao = new UserDao();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String remember = request.getParameter("remember");
        
        // 验证参数
        if (username == null || username.trim().isEmpty() ||
            password == null || password.trim().isEmpty()) {
            request.setAttribute("error", "请输入用户名和密码！");
            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
            return;
        }
        
        // 验证用户
        User user = userDao.findByUsernameAndPassword(username, password);
        
        if (user == null) {
            if (!userDao.isUsernameExists(username)) {
                request.setAttribute("error", "用户不存在！");
            } else {
                request.setAttribute("error", "密码错误！");
            }
            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
            return;
        }
        
        // 登录成功，创建Session
        HttpSession session = request.getSession();
        session.setAttribute("user", user);
        session.setAttribute("userId", user.getId());
        session.setAttribute("username", user.getUsername());
        session.setAttribute("nickname", user.getNickname());
        session.setMaxInactiveInterval(30 * 60);
        
        // 记住我
        if ("true".equals(remember)) {
            Cookie cookie = new Cookie("savedUsername", URLEncoder.encode(username, "UTF-8"));
            cookie.setMaxAge(60 * 60 * 24 * 7);
            cookie.setPath("/");
            response.addCookie(cookie);
        }
        
        response.sendRedirect(request.getContextPath() + "/");
    }
}
