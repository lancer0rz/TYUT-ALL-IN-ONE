<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    // 判断用户是否已登录（从Session中获取）
    String username = (String) session.getAttribute("username");
    if (username == null) {
%>
<!DOCTYPE html>
<html><head><meta charset="UTF-8"><title>提示</title>
<style>body{font-family:'Microsoft YaHei';display:flex;justify-content:center;align-items:center;height:100vh;background:#f5f5f5;}
.box{background:white;padding:30px;border-radius:8px;text-align:center;box-shadow:0 2px 10px rgba(0,0,0,0.1);}
.btn{display:inline-block;margin-top:15px;padding:10px 20px;background:#0066cc;color:white;text-decoration:none;border-radius:4px;}</style>
</head><body>
<div class="box">
    <h2>⚠️ 您还未登录</h2>
    <p>请先登录后再访问主页面</p>
    <a href="login.jsp" class="btn">去登录</a>
</div>
</body></html>
<%
        return;
    }
    String email = (String) session.getAttribute("email");
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>技术论坛 - 首页</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body { font-family: 'Microsoft YaHei', Arial, sans-serif; background: #f5f5f5; }
        .header { background: #333; color: white; padding: 15px 20px; }
        .header h1 { display: inline; }
        .nav { float: right; }
        .nav a { color: white; margin-left: 15px; text-decoration: none; }
        .container { max-width: 900px; margin: 20px auto; padding: 0 20px; }
        .content { background: white; padding: 20px; border-radius: 5px; margin-bottom: 20px; }
        .content h2 { border-bottom: 2px solid #0066cc; padding-bottom: 10px; margin-bottom: 15px; }
        .welcome { background: linear-gradient(135deg, #667eea, #764ba2); color: white; }
        .welcome h2 { color: white; border-bottom-color: white; }
        table { width: 100%; border-collapse: collapse; }
        th, td { border: 1px solid #ddd; padding: 10px; text-align: left; }
        th { background: #f0f0f0; }
        .badge { display: inline-block; background: #27ae60; color: white; padding: 3px 10px; border-radius: 10px; font-size: 12px; }
    </style>
</head>
<body>
    <div class="header">
        <h1>技术论坛</h1>
        <div class="nav">
            <a href="LogoutServlet">退出登录</a>
        </div>
    </div>
    
    <div class="container">
        <div class="content welcome">
            <h2>👋 欢迎回来！</h2>
            <p><span class="badge">💾 数据来自MySQL数据库</span></p>
            <p style="font-size:18px;margin-top:10px;">当前登录用户：<strong><%= username %></strong></p>
            <p>邮箱：<%= email != null && !email.isEmpty() ? email : "未设置" %></p>
            <p style="font-size:11px;margin-top:10px;">Session ID：<%= session.getId() %></p>
        </div>
        
        <div class="content">
            <h2>🔥 热门话题</h2>
            <table>
                <tr><th>话题</th><th>作者</th><th>浏览</th></tr>
                <tr><td>如何学好Java Web开发？</td><td>张三</td><td>1230</td></tr>
                <tr><td>MySQL性能优化经验</td><td>李四</td><td>980</td></tr>
            </table>
        </div>
    </div>
</body>
</html>
