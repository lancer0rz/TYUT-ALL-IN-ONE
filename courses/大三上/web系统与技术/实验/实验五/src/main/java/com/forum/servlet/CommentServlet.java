package com.forum.servlet;

import com.forum.dao.ArticleDao;
import com.forum.dao.CommentDao;
import com.forum.entity.Comment;
import com.forum.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * 评论管理Servlet
 */
@WebServlet("/comment/*")
public class CommentServlet extends HttpServlet {
    
    private CommentDao commentDao = new CommentDao();
    private ArticleDao articleDao = new ArticleDao();
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        
        String pathInfo = request.getPathInfo();
        
        if ("/add".equals(pathInfo)) {
            addComment(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
    
    /**
     * 添加评论
     */
    private void addComment(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // 检查登录
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        User user = (User) session.getAttribute("user");
        
        String articleIdStr = request.getParameter("articleId");
        String content = request.getParameter("content");
        
        // 验证文章ID
        int articleId;
        try {
            articleId = Integer.parseInt(articleIdStr);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        
        // 验证评论内容
        if (content == null || content.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/article/view/" + articleId + "?error=empty");
            return;
        }
        
        if (content.length() > 1000) {
            response.sendRedirect(request.getContextPath() + "/article/view/" + articleId + "?error=toolong");
            return;
        }
        
        // 创建评论
        Comment comment = new Comment();
        comment.setArticleId(articleId);
        comment.setUserId(user.getId());
        comment.setAuthor(user.getNickname());
        comment.setContent(content.trim());
        
        boolean success = commentDao.create(comment);
        
        if (success) {
            // 更新文章评论数
            articleDao.increaseCommentCount(articleId);
        }
        
        response.sendRedirect(request.getContextPath() + "/article/view/" + articleId);
    }
}
