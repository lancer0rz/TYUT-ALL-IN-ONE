<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>用户注册 - 简易论坛</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <jsp:include page="/WEB-INF/views/includes/header.jsp" />
    
    <main class="container">
        <div class="form-container">
            <h2>用户注册</h2>
            
            <c:if test="${not empty error}">
                <div class="alert alert-error">${error}</div>
            </c:if>
            
            <c:if test="${not empty success}">
                <div class="alert alert-success">${success}</div>
            </c:if>
            
            <form action="${pageContext.request.contextPath}/register" method="post" class="form">
                <div class="form-group">
                    <label for="username">用户名 <span class="required">*</span></label>
                    <input type="text" id="username" name="username" required 
                           placeholder="请输入用户名（3-20个字符）"
                           minlength="3" maxlength="20"
                           value="${param.username}">
                </div>
                
                <div class="form-group">
                    <label for="password">密码 <span class="required">*</span></label>
                    <input type="password" id="password" name="password" required 
                           placeholder="请输入密码（6-20个字符）"
                           minlength="6" maxlength="20">
                </div>
                
                <div class="form-group">
                    <label for="confirmPassword">确认密码 <span class="required">*</span></label>
                    <input type="password" id="confirmPassword" name="confirmPassword" required 
                           placeholder="请再次输入密码">
                </div>
                
                <div class="form-group">
                    <label for="nickname">昵称 <span class="required">*</span></label>
                    <input type="text" id="nickname" name="nickname" required 
                           placeholder="请输入昵称"
                           maxlength="50"
                           value="${param.nickname}">
                </div>
                
                <div class="form-group">
                    <label for="email">邮箱</label>
                    <input type="email" id="email" name="email" 
                           placeholder="请输入邮箱（选填）"
                           value="${param.email}">
                </div>
                
                <div class="form-actions">
                    <button type="submit" class="btn btn-primary">注册</button>
                    <a href="${pageContext.request.contextPath}/login" class="btn btn-link">已有账号？去登录</a>
                </div>
            </form>
        </div>
    </main>
    
    <jsp:include page="/WEB-INF/views/includes/footer.jsp" />
    
    <script>
        document.querySelector('form').addEventListener('submit', function(e) {
            var password = document.getElementById('password').value;
            var confirmPassword = document.getElementById('confirmPassword').value;
            
            if (password !== confirmPassword) {
                e.preventDefault();
                alert('两次输入的密码不一致！');
            }
        });
    </script>
</body>
</html>
