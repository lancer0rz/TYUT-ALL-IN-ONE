<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>页面未找到 - 简易论坛</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
        .error-page {
            text-align: center;
            padding: 100px 20px;
        }
        .error-code {
            font-size: 6rem;
            color: #ddd;
            margin-bottom: 20px;
        }
        .error-message {
            font-size: 1.5rem;
            color: #666;
            margin-bottom: 30px;
        }
    </style>
</head>
<body>
    <jsp:include page="/WEB-INF/views/includes/header.jsp" />
    
    <main class="container">
        <div class="error-page">
            <div class="error-code">404</div>
            <p class="error-message">抱歉，您访问的页面不存在</p>
            <a href="${pageContext.request.contextPath}/" class="btn btn-primary">返回首页</a>
        </div>
    </main>
    
    <jsp:include page="/WEB-INF/views/includes/footer.jsp" />
</body>
</html>
