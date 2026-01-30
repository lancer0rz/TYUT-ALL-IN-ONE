<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String error = request.getParameter("error");
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>用户注册</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body { font-family: 'Microsoft YaHei'; background: linear-gradient(135deg, #667eea, #764ba2); 
               min-height: 100vh; display: flex; justify-content: center; align-items: center; }
        .container { background: white; padding: 30px; border-radius: 10px; width: 360px; 
                     box-shadow: 0 10px 30px rgba(0,0,0,0.2); }
        h2 { text-align: center; margin-bottom: 20px; color: #333; }
        .badge { display: block; text-align: center; margin-bottom: 15px; }
        .badge span { background: #27ae60; color: white; padding: 5px 12px; border-radius: 15px; font-size: 12px; }
        .form-group { margin-bottom: 15px; }
        label { display: block; margin-bottom: 5px; color: #555; }
        input[type="text"], input[type="password"], input[type="email"] { 
            width: 100%; padding: 10px; border: 1px solid #ddd; border-radius: 5px; }
        input:focus { outline: none; border-color: #667eea; }
        .btn { width: 100%; padding: 12px; background: linear-gradient(135deg, #667eea, #764ba2); 
               color: white; border: none; border-radius: 5px; cursor: pointer; font-size: 16px; }
        .link { text-align: center; margin-top: 15px; }
        .link a { color: #667eea; text-decoration: none; }
        .msg { padding: 10px; border-radius: 5px; margin-bottom: 15px; text-align: center; }
        .error { background: #f8d7da; color: #721c24; }
    </style>
</head>
<body>
<div class="container">
    <h2>用户注册</h2>
    <p class="badge"><span>💾 数据库存储</span></p>
    
    <% if ("empty".equals(error)) { %><div class="msg error">请填写用户名和密码！</div>
    <% } else if ("exists".equals(error)) { %><div class="msg error">用户名已被占用！</div>
    <% } else if ("password".equals(error)) { %><div class="msg error">两次密码不一致！</div>
    <% } else if ("error".equals(error)) { %><div class="msg error">系统错误，请稍后重试！</div><% } %>
    
    <form action="RegServlet" method="post" id="regForm">
        <div class="form-group">
            <label>用户名：</label>
            <input type="text" name="username" required>
        </div>
        <div class="form-group">
            <label>密码：</label>
            <input type="password" name="password" id="pwd" required>
        </div>
        <div class="form-group">
            <label>确认密码：</label>
            <input type="password" id="cpwd" required>
        </div>
        <div class="form-group">
            <label>邮箱：</label>
            <input type="email" name="email">
        </div>
        <button type="submit" class="btn">注 册</button>
    </form>
    <div class="link">已有账号？<a href="login.jsp">立即登录</a></div>
</div>
<script>
document.getElementById('regForm').onsubmit = function() {
    if (document.getElementById('pwd').value !== document.getElementById('cpwd').value) {
        alert('两次密码不一致！'); return false;
    }
    return true;
};
</script>
</body>
</html>
