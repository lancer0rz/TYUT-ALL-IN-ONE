package com.example.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;

/**
 * 用户注册Servlet
 * 实验二：Servlet与会话技术
 * 使用Cookie保存注册信息
 */
@WebServlet("/RegServlet")
public class RegServlet extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 设置请求和响应的编码
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        
        // 获取表单参数
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        
        PrintWriter out = response.getWriter();
        
        // 验证参数
        if (username == null || username.trim().isEmpty() ||
            password == null || password.trim().isEmpty() ||
            email == null || email.trim().isEmpty()) {
            
            out.println("<!DOCTYPE html>");
            out.println("<html><head><meta charset='UTF-8'><title>注册失败</title>");
            out.println("<style>body{font-family:'Microsoft YaHei';display:flex;justify-content:center;align-items:center;height:100vh;background:#f5f5f5;}");
            out.println(".msg{background:white;padding:40px;border-radius:10px;text-align:center;box-shadow:0 5px 20px rgba(0,0,0,0.1);}");
            out.println(".error{color:#e74c3c;font-size:24px;margin-bottom:20px;}");
            out.println("a{color:#667eea;text-decoration:none;}</style></head>");
            out.println("<body><div class='msg'><p class='error'>注册失败：请填写完整信息！</p>");
            out.println("<a href='register.html'>返回注册</a></div></body></html>");
            return;
        }
        
        // 检查用户名是否已存在（通过Cookie）
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("user_" + username)) {
                    out.println("<!DOCTYPE html>");
                    out.println("<html><head><meta charset='UTF-8'><title>注册失败</title>");
                    out.println("<style>body{font-family:'Microsoft YaHei';display:flex;justify-content:center;align-items:center;height:100vh;background:#f5f5f5;}");
                    out.println(".msg{background:white;padding:40px;border-radius:10px;text-align:center;box-shadow:0 5px 20px rgba(0,0,0,0.1);}");
                    out.println(".error{color:#e74c3c;font-size:24px;margin-bottom:20px;}");
                    out.println("a{color:#667eea;text-decoration:none;}</style></head>");
                    out.println("<body><div class='msg'><p class='error'>注册失败：用户名已存在！</p>");
                    out.println("<a href='register.html'>返回注册</a></div></body></html>");
                    return;
                }
            }
        }
        
        // 创建Cookie保存用户信息
        // 格式：user_用户名 = 密码|邮箱
        String userInfo = password + "|" + email;
        Cookie userCookie = new Cookie("user_" + username, URLEncoder.encode(userInfo, "UTF-8"));
        userCookie.setMaxAge(60 * 60 * 24 * 30); // 30天有效期
        userCookie.setPath("/");
        response.addCookie(userCookie);
        
        // 注册成功，输出结果页面
        out.println("<!DOCTYPE html>");
        out.println("<html><head><meta charset='UTF-8'><title>注册成功</title>");
        out.println("<style>");
        out.println("body{font-family:'Microsoft YaHei';display:flex;justify-content:center;align-items:center;height:100vh;background:linear-gradient(135deg, #667eea 0%, #764ba2 100%);}");
        out.println(".msg{background:white;padding:40px;border-radius:10px;text-align:center;box-shadow:0 15px 35px rgba(0,0,0,0.2);}");
        out.println(".success{color:#27ae60;font-size:24px;margin-bottom:20px;}");
        out.println(".info{color:#666;margin:10px 0;}");
        out.println("a{display:inline-block;margin-top:20px;padding:10px 30px;background:linear-gradient(135deg, #667eea 0%, #764ba2 100%);color:white;text-decoration:none;border-radius:5px;}");
        out.println("a:hover{transform:translateY(-2px);box-shadow:0 5px 20px rgba(102,126,234,0.4);}");
        out.println("</style></head>");
        out.println("<body>");
        out.println("<div class='msg'>");
        out.println("<p class='success'>🎉 注册成功！</p>");
        out.println("<p class='info'>用户名：" + username + "</p>");
        out.println("<p class='info'>邮箱：" + email + "</p>");
        out.println("<p class='info'>您的信息已保存到Cookie中</p>");
        out.println("<a href='login.html'>立即登录</a>");
        out.println("</div>");
        out.println("</body></html>");
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // GET请求重定向到注册页面
        response.sendRedirect("register.html");
    }
}
