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
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * 用户登录Servlet
 * 实验二：Servlet与会话技术
 * 使用Cookie验证登录，使用Session保存登录状态
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 设置请求和响应的编码
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        
        // 获取表单参数
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String remember = request.getParameter("remember");
        
        PrintWriter out = response.getWriter();
        
        // 验证参数
        if (username == null || username.trim().isEmpty() ||
            password == null || password.trim().isEmpty()) {
            
            outputError(out, "登录失败：请输入用户名和密码！");
            return;
        }
        
        // 从Cookie中查找用户信息
        Cookie[] cookies = request.getCookies();
        String storedInfo = null;
        
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("user_" + username)) {
                    storedInfo = URLDecoder.decode(cookie.getValue(), "UTF-8");
                    break;
                }
            }
        }
        
        // 验证用户是否存在
        if (storedInfo == null) {
            outputError(out, "登录失败：用户不存在，请先注册！");
            return;
        }
        
        // 解析存储的信息：密码|邮箱
        String[] parts = storedInfo.split("\\|");
        String storedPassword = parts[0];
        String storedEmail = parts.length > 1 ? parts[1] : "";
        
        // 验证密码
        if (!password.equals(storedPassword)) {
            outputError(out, "登录失败：密码错误！");
            return;
        }
        
        // 登录成功，创建Session保存用户信息
        HttpSession session = request.getSession();
        session.setAttribute("username", username);
        session.setAttribute("email", storedEmail);
        session.setAttribute("loginTime", System.currentTimeMillis());
        session.setMaxInactiveInterval(30 * 60); // Session有效期30分钟
        
        // 如果选择"记住我"，保存Cookie
        if ("true".equals(remember)) {
            Cookie savedUsername = new Cookie("savedUsername", URLEncoder.encode(username, "UTF-8"));
            savedUsername.setMaxAge(60 * 60 * 24 * 7); // 7天有效期
            savedUsername.setPath("/");
            response.addCookie(savedUsername);
        } else {
            // 清除保存的用户名Cookie
            Cookie savedUsername = new Cookie("savedUsername", "");
            savedUsername.setMaxAge(0);
            savedUsername.setPath("/");
            response.addCookie(savedUsername);
        }
        
        // 登录成功，输出结果页面
        out.println("<!DOCTYPE html>");
        out.println("<html><head><meta charset='UTF-8'><title>登录成功</title>");
        out.println("<style>");
        out.println("body{font-family:'Microsoft YaHei';display:flex;justify-content:center;align-items:center;height:100vh;background:linear-gradient(135deg, #667eea 0%, #764ba2 100%);}");
        out.println(".msg{background:white;padding:40px;border-radius:10px;text-align:center;box-shadow:0 15px 35px rgba(0,0,0,0.2);min-width:400px;}");
        out.println(".success{color:#27ae60;font-size:24px;margin-bottom:20px;}");
        out.println(".info{color:#666;margin:10px 0;text-align:left;padding:10px;background:#f8f9fa;border-radius:5px;}");
        out.println(".info strong{color:#333;}");
        out.println("a{display:inline-block;margin-top:20px;padding:10px 30px;background:linear-gradient(135deg, #667eea 0%, #764ba2 100%);color:white;text-decoration:none;border-radius:5px;margin-right:10px;}");
        out.println("a:hover{transform:translateY(-2px);box-shadow:0 5px 20px rgba(102,126,234,0.4);}");
        out.println("a.logout{background:#e74c3c;}");
        out.println("</style></head>");
        out.println("<body>");
        out.println("<div class='msg'>");
        out.println("<p class='success'>🎉 登录成功！</p>");
        out.println("<div class='info'><strong>用户名：</strong>" + username + "</div>");
        out.println("<div class='info'><strong>邮箱：</strong>" + storedEmail + "</div>");
        out.println("<div class='info'><strong>Session ID：</strong>" + session.getId() + "</div>");
        out.println("<div class='info'><strong>登录时间：</strong>" + new java.util.Date() + "</div>");
        if ("true".equals(remember)) {
            out.println("<div class='info'><strong>记住登录：</strong>已启用（7天内免登录）</div>");
        }
        out.println("<a href='welcome.jsp'>进入主页</a>");
        out.println("<a href='LogoutServlet' class='logout'>退出登录</a>");
        out.println("</div>");
        out.println("</body></html>");
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // GET请求重定向到登录页面
        response.sendRedirect("login.html");
    }
    
    /**
     * 输出错误信息页面
     */
    private void outputError(PrintWriter out, String message) {
        out.println("<!DOCTYPE html>");
        out.println("<html><head><meta charset='UTF-8'><title>登录失败</title>");
        out.println("<style>body{font-family:'Microsoft YaHei';display:flex;justify-content:center;align-items:center;height:100vh;background:#f5f5f5;}");
        out.println(".msg{background:white;padding:40px;border-radius:10px;text-align:center;box-shadow:0 5px 20px rgba(0,0,0,0.1);}");
        out.println(".error{color:#e74c3c;font-size:24px;margin-bottom:20px;}");
        out.println("a{color:#667eea;text-decoration:none;display:block;margin-top:10px;}</style></head>");
        out.println("<body><div class='msg'><p class='error'>" + message + "</p>");
        out.println("<a href='login.html'>返回登录</a>");
        out.println("<a href='register.html'>去注册</a></div></body></html>");
    }
}
