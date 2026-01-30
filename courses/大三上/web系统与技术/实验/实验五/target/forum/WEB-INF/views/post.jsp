<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>发表帖子 - 简易论坛</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <jsp:include page="/WEB-INF/views/includes/header.jsp" />
    
    <main class="container">
        <div class="form-container post-form-container">
            <h2>发表新帖</h2>
            
            <c:if test="${not empty error}">
                <div class="alert alert-error">${error}</div>
            </c:if>
            
            <form action="${pageContext.request.contextPath}/article/post" method="post" class="form">
                <div class="form-group">
                    <label for="title">标题 <span class="required">*</span></label>
                    <input type="text" id="title" name="title" required 
                           placeholder="请输入帖子标题"
                           maxlength="100"
                           value="${param.title}">
                </div>
                
                <div class="form-group">
                    <label for="content">内容 <span class="required">*</span></label>
                    <textarea id="content" name="content" rows="15" required 
                              placeholder="请输入帖子内容...">${param.content}</textarea>
                </div>
                
                <div class="form-actions">
                    <button type="submit" class="btn btn-primary">发表</button>
                    <a href="${pageContext.request.contextPath}/" class="btn btn-secondary">取消</a>
                </div>
            </form>
        </div>
    </main>
    
    <jsp:include page="/WEB-INF/views/includes/footer.jsp" />
</body>
</html>
