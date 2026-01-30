package com.example.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 用户登出Servlet
 * 实验二：Servlet与会话技术
 * 清除Session和Cookie
 */
@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        
        // 获取当前Session
        HttpSession session = request.getSession(false);
        String username = "";
        
        if (session != null) {
            username = (String) session.getAttribute("username");
            // 使Session失效
            session.invalidate();
        }
        
        // 清除"记住我"的Cookie
        Cookie savedUsername = new Cookie("savedUsername", "");
        savedUsername.setMaxAge(0);
        savedUsername.setPath("/");
        response.addCookie(savedUsername);
        
        // 输出退出成功页面
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html><head><meta charset='UTF-8'><title>退出成功</title>");
        out.println("<meta http-equiv='refresh' content='3;url=login.html'>");
        out.println("<style>");
        out.println("body{font-family:'Microsoft YaHei';display:flex;justify-content:center;align-items:center;height:100vh;background:linear-gradient(135deg, #667eea 0%, #764ba2 100%);}");
        out.println(".msg{background:white;padding:40px;border-radius:10px;text-align:center;box-shadow:0 15px 35px rgba(0,0,0,0.2);}");
        out.println(".success{color:#27ae60;font-size:24px;margin-bottom:20px;}");
        out.println(".info{color:#666;margin:10px 0;}");
        out.println("a{color:#667eea;text-decoration:none;}</style></head>");
        out.println("<body>");
        out.println("<div class='msg'>");
        out.println("<p class='success'>👋 已成功退出登录</p>");
        if (username != null && !username.isEmpty()) {
            out.println("<p class='info'>再见，" + username + "！</p>");
        }
        out.println("<p class='info'>3秒后自动跳转到登录页面...</p>");
        out.println("<p><a href='login.html'>立即跳转</a></p>");
        out.println("</div>");
        out.println("</body></html>");
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }
}
