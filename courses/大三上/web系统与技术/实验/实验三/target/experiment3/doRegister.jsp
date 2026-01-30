<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.net.URLEncoder" %>
<%
    request.setCharacterEncoding("UTF-8");
    String username = request.getParameter("username");
    String password = request.getParameter("password");
    String confirmPassword = request.getParameter("confirmPassword");
    String email = request.getParameter("email");
    
    // 验证参数不为空
    if (username == null || username.trim().isEmpty() || 
        password == null || password.trim().isEmpty()) {
        response.sendRedirect("register.jsp?error=empty");
        return;
    }
    
    // 验证两次密码一致
    if (!password.equals(confirmPassword)) {
        response.sendRedirect("register.jsp?error=password");
        return;
    }
    
    // 检查用户名是否已存在
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
        for (Cookie c : cookies) {
            if (c.getName().equals("user_" + username)) {
                response.sendRedirect("register.jsp?error=exists");
                return;
            }
        }
    }
    
    // 保存用户信息到Cookie，格式：user_用户名 = 密码|邮箱
    String userInfo = password + "|" + (email != null ? email : "");
    Cookie userCookie = new Cookie("user_" + username, URLEncoder.encode(userInfo, "UTF-8"));
    userCookie.setMaxAge(60 * 60 * 24 * 30); // 30天有效期
    userCookie.setPath("/");
    response.addCookie(userCookie);
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>注册成功</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body { font-family: 'Microsoft YaHei'; background: linear-gradient(135deg, #667eea, #764ba2); 
               min-height: 100vh; display: flex; justify-content: center; align-items: center; }
        .container { background: white; padding: 30px; border-radius: 10px; width: 360px; 
                     text-align: center; box-shadow: 0 10px 30px rgba(0,0,0,0.2); }
        h2 { color: #27ae60; margin-bottom: 20px; }
        p { margin: 10px 0; color: #555; }
        .btn { display: inline-block; margin-top: 20px; padding: 12px 30px; 
               background: linear-gradient(135deg, #667eea, #764ba2); color: white; 
               text-decoration: none; border-radius: 5px; }
    </style>
</head>
<body>
<div class="container">
    <h2>🎉 注册成功！</h2>
    <p><strong>用户名：</strong><%= username %></p>
    <p><strong>邮箱：</strong><%= email != null && !email.isEmpty() ? email : "未设置" %></p>
    <p style="font-size:12px;color:#999;">您的信息已保存到Cookie中</p>
    <a href="login.jsp" class="btn">立即登录</a>
</div>
</body>
</html>
