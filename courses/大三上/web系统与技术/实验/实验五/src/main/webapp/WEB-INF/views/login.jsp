<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>用户登录 - 简易论坛</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <jsp:include page="/WEB-INF/views/includes/header.jsp" />
    
    <main class="container">
        <div class="form-container">
            <h2>用户登录</h2>
            
            <c:if test="${not empty error}">
                <div class="alert alert-error">${error}</div>
            </c:if>
            
            <c:if test="${not empty param.registered}">
                <div class="alert alert-success">注册成功，请登录！</div>
            </c:if>
            
            <form action="${pageContext.request.contextPath}/login" method="post" class="form">
                <div class="form-group">
                    <label for="username">用户名</label>
                    <input type="text" id="username" name="username" required 
                           placeholder="请输入用户名"
                           value="${cookie.savedUsername.value}">
                </div>
                
                <div class="form-group">
                    <label for="password">密码</label>
                    <input type="password" id="password" name="password" required 
                           placeholder="请输入密码">
                </div>
                
                <div class="form-group checkbox-group">
                    <label>
                        <input type="checkbox" name="remember" value="true" 
                               ${not empty cookie.savedUsername ? 'checked' : ''}>
                        记住用户名
                    </label>
                </div>
                
                <div class="form-actions">
                    <button type="submit" class="btn btn-primary">登录</button>
                    <a href="${pageContext.request.contextPath}/register" class="btn btn-link">没有账号？去注册</a>
                </div>
            </form>
        </div>
    </main>
    
    <jsp:include page="/WEB-INF/views/includes/footer.jsp" />
</body>
</html>
