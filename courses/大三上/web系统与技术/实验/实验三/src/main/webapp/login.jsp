<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.net.URLDecoder" %>
<%
    // 检查Cookie中保存的用户名（记住我功能）
    String savedUser = "";
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
        for (Cookie c : cookies) {
            if ("savedUsername".equals(c.getName())) {
                savedUser = URLDecoder.decode(c.getValue(), "UTF-8");
            }
        }
    }
    String error = request.getParameter("error");
    String msg = request.getParameter("msg");
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>用户登录</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body { font-family: 'Microsoft YaHei'; background: linear-gradient(135deg, #667eea, #764ba2); 
               min-height: 100vh; display: flex; justify-content: center; align-items: center; }
        .container { background: white; padding: 30px; border-radius: 10px; width: 360px; 
                     box-shadow: 0 10px 30px rgba(0,0,0,0.2); }
        h2 { text-align: center; margin-bottom: 20px; color: #333; }
        .form-group { margin-bottom: 15px; }
        label { display: block; margin-bottom: 5px; color: #555; }
        input[type="text"], input[type="password"] { width: 100%; padding: 10px; border: 1px solid #ddd; border-radius: 5px; }
        input:focus { outline: none; border-color: #667eea; }
        .btn { width: 100%; padding: 12px; background: linear-gradient(135deg, #667eea, #764ba2); 
               color: white; border: none; border-radius: 5px; cursor: pointer; font-size: 16px; }
        .link { text-align: center; margin-top: 15px; }
        .link a { color: #667eea; text-decoration: none; }
        .msg { padding: 10px; border-radius: 5px; margin-bottom: 15px; text-align: center; }
        .success { background: #d4edda; color: #155724; }
        .error { background: #f8d7da; color: #721c24; }
    </style>
</head>
<body>
<div class="container">
    <h2>用户登录</h2>
    <% if ("logout".equals(msg)) { %><div class="msg success">您已成功退出登录！</div><% } %>
    <% if ("empty".equals(error)) { %><div class="msg error">请输入用户名和密码！</div>
    <% } else if ("notfound".equals(error)) { %><div class="msg error">用户不存在，请先注册！</div>
    <% } else if ("password".equals(error)) { %><div class="msg error">密码错误！</div><% } %>
    
    <form action="doLogin.jsp" method="post">
        <div class="form-group">
            <label>用户名：</label>
            <input type="text" name="username" value="<%= savedUser %>" required>
        </div>
        <div class="form-group">
            <label>密码：</label>
            <input type="password" name="password" required>
        </div>
        <div class="form-group">
            <input type="checkbox" name="remember" value="true" <%= !savedUser.isEmpty() ? "checked" : "" %>> 记住我
        </div>
        <button type="submit" class="btn">登 录</button>
    </form>
    <div class="link">没有账号？<a href="register.jsp">立即注册</a></div>
</div>
</body>
</html>
