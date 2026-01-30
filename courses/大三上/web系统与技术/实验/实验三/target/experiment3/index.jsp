<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    // 判断用户是否已登录（从Session中获取）
    String username = (String) session.getAttribute("username");
    if (username == null) {
        // 未登录，提示并跳转到登录页面
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
        table { width: 100%; border-collapse: collapse; }
        th, td { border: 1px solid #ddd; padding: 10px; text-align: left; }
        th { background: #f0f0f0; }
    </style>
</head>
<body>
    <div class="header">
        <h1>技术论坛</h1>
        <div class="nav">
            <a href="logout.jsp">退出登录</a>
        </div>
    </div>
    
    <div class="container">
        <!-- 包含欢迎页面 -->
        <jsp:include page="welcome.jsp" />
        
        <div class="content">
            <h2>🔥 热门话题</h2>
            <table>
                <tr><th>话题</th><th>作者</th><th>浏览</th></tr>
                <tr><td>如何学好Java Web开发？</td><td>张三</td><td>1230</td></tr>
                <tr><td>MySQL性能优化经验</td><td>李四</td><td>980</td></tr>
            </table>
        </div>
        
        <div class="content">
            <h2>📝 最新帖子</h2>
            <table>
                <tr><th>内容</th><th>发布者</th><th>时间</th></tr>
                <tr><td>学习了Servlet和JSP</td><td>赵六</td><td>刚刚</td></tr>
                <tr><td>分享编程学习网站</td><td>孙七</td><td>10分钟前</td></tr>
            </table>
        </div>
    </div>
</body>
</html>
