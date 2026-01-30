<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<header class="header">
    <div class="header-container">
        <div class="logo">
            <a href="${pageContext.request.contextPath}/">简易论坛</a>
        </div>
        <nav class="nav">
            <ul class="nav-list">
                <li><a href="${pageContext.request.contextPath}/">首页</a></li>
                <c:choose>
                    <c:when test="${not empty sessionScope.user}">
                        <li class="user-info">
                            <span class="welcome">欢迎，${sessionScope.nickname}</span>
                        </li>
                        <li><a href="${pageContext.request.contextPath}/article/post">发帖</a></li>
                        <li><a href="${pageContext.request.contextPath}/logout" class="logout">退出</a></li>
                    </c:when>
                    <c:otherwise>
                        <li><a href="${pageContext.request.contextPath}/login">登录</a></li>
                        <li><a href="${pageContext.request.contextPath}/register">注册</a></li>
                    </c:otherwise>
                </c:choose>
            </ul>
        </nav>
    </div>
</header>
