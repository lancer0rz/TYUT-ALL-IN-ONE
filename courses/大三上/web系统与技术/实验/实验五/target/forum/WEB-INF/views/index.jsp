<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>简易论坛 - 首页</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <jsp:include page="/WEB-INF/views/includes/header.jsp" />
    
    <main class="container">
        <div class="page-header">
            <h1>帖子列表</h1>
            <c:if test="${not empty sessionScope.user}">
                <a href="${pageContext.request.contextPath}/article/post" class="btn btn-primary">发表新帖</a>
            </c:if>
        </div>
        
        <div class="article-list">
            <c:choose>
                <c:when test="${empty articles}">
                    <div class="empty-message">
                        <p>暂无帖子</p>
                        <c:if test="${not empty sessionScope.user}">
                            <a href="${pageContext.request.contextPath}/article/post" class="btn btn-primary">发表第一篇帖子</a>
                        </c:if>
                    </div>
                </c:when>
                <c:otherwise>
                    <c:forEach var="article" items="${articles}">
                        <div class="article-item">
                            <div class="article-info">
                                <h3 class="article-title">
                                    <a href="${pageContext.request.contextPath}/article/view/${article.id}">${article.title}</a>
                                </h3>
                                <p class="article-summary">${article.summary}</p>
                                <div class="article-meta">
                                    <span class="author">
                                        <i class="icon-user"></i> ${article.author}
                                    </span>
                                    <span class="time">
                                        <i class="icon-time"></i>
                                        <fmt:formatDate value="${article.createTime}" pattern="yyyy-MM-dd HH:mm"/>
                                    </span>
                                </div>
                            </div>
                            <div class="article-stats">
                                <span class="views" title="浏览量">
                                    <i class="icon-eye"></i> ${article.viewCount}
                                </span>
                                <span class="comments" title="评论数">
                                    <i class="icon-comment"></i> ${article.commentCount}
                                </span>
                            </div>
                        </div>
                    </c:forEach>
                    
                    <!-- 分页 -->
                    <c:if test="${totalPages > 1}">
                        <div class="pagination">
                            <c:if test="${currentPage > 1}">
                                <a href="${pageContext.request.contextPath}/?page=${currentPage - 1}" class="page-link">&laquo; 上一页</a>
                            </c:if>
                            
                            <c:forEach begin="1" end="${totalPages}" var="i">
                                <c:choose>
                                    <c:when test="${i == currentPage}">
                                        <span class="page-link current">${i}</span>
                                    </c:when>
                                    <c:otherwise>
                                        <a href="${pageContext.request.contextPath}/?page=${i}" class="page-link">${i}</a>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                            
                            <c:if test="${currentPage < totalPages}">
                                <a href="${pageContext.request.contextPath}/?page=${currentPage + 1}" class="page-link">下一页 &raquo;</a>
                            </c:if>
                        </div>
                    </c:if>
                    
                    <div class="total-info">
                        共 ${totalCount} 篇帖子，第 ${currentPage}/${totalPages} 页
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </main>
    
    <jsp:include page="/WEB-INF/views/includes/footer.jsp" />
</body>
</html>
