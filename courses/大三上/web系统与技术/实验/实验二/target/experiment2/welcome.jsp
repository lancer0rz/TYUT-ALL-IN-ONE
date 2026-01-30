<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>欢迎页面</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        body {
            font-family: 'Microsoft YaHei', Arial, sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
        }
        .container {
            background: white;
            padding: 40px;
            border-radius: 10px;
            box-shadow: 0 15px 35px rgba(0, 0, 0, 0.2);
            min-width: 500px;
            text-align: center;
        }
        h1 {
            color: #333;
            margin-bottom: 30px;
        }
        .user-info {
            text-align: left;
            margin: 20px 0;
        }
        .info-item {
            padding: 15px;
            margin: 10px 0;
            background: #f8f9fa;
            border-radius: 5px;
            border-left: 4px solid #667eea;
        }
        .info-item strong {
            color: #333;
        }
        .info-item span {
            color: #666;
        }
        .btn {
            display: inline-block;
            padding: 12px 30px;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            text-decoration: none;
            border-radius: 5px;
            margin: 10px;
            transition: all 0.3s;
        }
        .btn:hover {
            transform: translateY(-2px);
            box-shadow: 0 5px 20px rgba(102, 126, 234, 0.4);
        }
        .btn.logout {
            background: #e74c3c;
        }
        .btn.logout:hover {
            box-shadow: 0 5px 20px rgba(231, 76, 60, 0.4);
        }
        .not-login {
            color: #e74c3c;
            font-size: 18px;
            margin: 20px 0;
        }
    </style>
</head>
<body>
    <div class="container">
        <%
            // 检查用户是否已登录
            String username = (String) session.getAttribute("username");
            String email = (String) session.getAttribute("email");
            Long loginTime = (Long) session.getAttribute("loginTime");
            
            if (username == null) {
        %>
            <h1>⚠️ 未登录</h1>
            <p class="not-login">您还没有登录，请先登录！</p>
            <a href="login.html" class="btn">去登录</a>
            <a href="register.html" class="btn">去注册</a>
        <%
            } else {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String loginTimeStr = loginTime != null ? sdf.format(new Date(loginTime)) : "未知";
        %>
            <h1>🎉 欢迎您，<%= username %>！</h1>
            <div class="user-info">
                <div class="info-item">
                    <strong>用户名：</strong>
                    <span><%= username %></span>
                </div>
                <div class="info-item">
                    <strong>邮箱：</strong>
                    <span><%= email != null ? email : "未设置" %></span>
                </div>
                <div class="info-item">
                    <strong>登录时间：</strong>
                    <span><%= loginTimeStr %></span>
                </div>
                <div class="info-item">
                    <strong>Session ID：</strong>
                    <span><%= session.getId() %></span>
                </div>
                <div class="info-item">
                    <strong>Session有效期：</strong>
                    <span><%= session.getMaxInactiveInterval() / 60 %> 分钟</span>
                </div>
            </div>
            <a href="LogoutServlet" class="btn logout">退出登录</a>
        <%
            }
        %>
    </div>
</body>
</html>
