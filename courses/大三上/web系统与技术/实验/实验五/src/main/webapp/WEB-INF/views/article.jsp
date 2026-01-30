<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${article.title} - 简易论坛</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <jsp:include page="/WEB-INF/views/includes/header.jsp" />
    
    <main class="container">
        <article class="article-detail">
            <header class="article-header">
                <h1>${article.title}</h1>
                <div class="article-meta">
                    <span class="author">
                        <i class="icon-user"></i> ${article.author}
                    </span>
                    <span class="time">
                        <i class="icon-time"></i>
                        发表于 <fmt:formatDate value="${article.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                    </span>
                    <span class="views">
                        <i class="icon-eye"></i> 浏览 ${article.viewCount}
                    </span>
                    <span class="comments">
                        <i class="icon-comment"></i> 评论 ${article.commentCount}
                    </span>
                </div>
            </header>
            
            <div class="article-content">
                ${fn:replace(article.content, newLine, '<br/>')}
                <c:set var="newLine" value="
"/>
                <c:out value="${article.content}" escapeXml="true" />
            </div>
            
            <footer class="article-footer">
                <a href="${pageContext.request.contextPath}/" class="btn btn-secondary">返回列表</a>
            </footer>
        </article>
        
        <!-- 评论区 -->
        <section class="comments-section">
            <h2>评论 (${fn:length(comments)})</h2>
            
            <c:if test="${not empty param.error}">
                <div class="alert alert-error">
                    <c:choose>
                        <c:when test="${param.error == 'empty'}">评论内容不能为空！</c:when>
                        <c:when test="${param.error == 'toolong'}">评论内容不能超过1000个字符！</c:when>
                        <c:otherwise>发表评论失败！</c:otherwise>
                    </c:choose>
                </div>
            </c:if>
            
            <!-- 发表评论表单 -->
            <c:choose>
                <c:when test="${not empty sessionScope.user}">
                    <form action="${pageContext.request.contextPath}/comment/add" method="post" class="comment-form">
                        <input type="hidden" name="articleId" value="${article.id}">
                        <div class="form-group">
                            <label for="content">发表评论</label>
                            <textarea id="content" name="content" rows="4" 
                                      placeholder="请输入评论内容..." required
                                      maxlength="1000"></textarea>
                        </div>
                        <button type="submit" class="btn btn-primary">发表评论</button>
                    </form>
                </c:when>
                <c:otherwise>
                    <div class="login-prompt">
                        <a href="${pageContext.request.contextPath}/login">登录</a> 后才能发表评论
                    </div>
                </c:otherwise>
            </c:choose>
            
            <!-- 评论列表 -->
            <div class="comment-list">
                <c:choose>
                    <c:when test="${empty comments}">
                        <div class="empty-message">暂无评论，快来抢沙发吧！</div>
                    </c:when>
                    <c:otherwise>
                        <c:forEach var="comment" items="${comments}" varStatus="status">
                            <div class="comment-item">
                                <div class="comment-header">
                                    <span class="comment-author">${comment.author}</span>
                                    <span class="comment-floor">#${status.index + 1}楼</span>
                                    <span class="comment-time">
                                        <fmt:formatDate value="${comment.createTime}" pattern="yyyy-MM-dd HH:mm"/>
                                    </span>
                                </div>
                                <div class="comment-content">
                                    <c:out value="${comment.content}" escapeXml="true" />
                                </div>
                            </div>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
            </div>
        </section>
    </main>
    
    <jsp:include page="/WEB-INF/views/includes/footer.jsp" />
</body>
</html>
