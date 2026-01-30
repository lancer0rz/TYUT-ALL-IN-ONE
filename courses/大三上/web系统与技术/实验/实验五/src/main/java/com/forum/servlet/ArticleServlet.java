package com.forum.servlet;

import com.forum.dao.ArticleDao;
import com.forum.dao.CommentDao;
import com.forum.entity.Article;
import com.forum.entity.Comment;
import com.forum.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

/**
 * 文章管理Servlet
 */
@WebServlet("/article/*")
public class ArticleServlet extends HttpServlet {
    
    private ArticleDao articleDao = new ArticleDao();
    private CommentDao commentDao = new CommentDao();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        
        String pathInfo = request.getPathInfo();
        
        if (pathInfo == null || "/".equals(pathInfo) || "/list".equals(pathInfo)) {
            // 文章列表
            list(request, response);
        } else if (pathInfo.startsWith("/view/")) {
            // 文章详情
            view(request, response);
        } else if ("/post".equals(pathInfo)) {
            // 发表文章页面
            post(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        
        String pathInfo = request.getPathInfo();
        
        if ("/post".equals(pathInfo)) {
            // 发表文章
            doPost(request, response, true);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
    
    /**
     * 文章列表
     */
    private void list(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String pageStr = request.getParameter("page");
        int page = 1;
        if (pageStr != null && !pageStr.isEmpty()) {
            try {
                page = Integer.parseInt(pageStr);
            } catch (NumberFormatException e) {
                page = 1;
            }
        }
        if (page < 1) page = 1;
        
        int pageSize = 10;
        
        List<Article> articles = articleDao.findAll(page, pageSize);
        int totalCount = articleDao.countAll();
        int totalPages = (int) Math.ceil((double) totalCount / pageSize);
        
        request.setAttribute("articles", articles);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("totalCount", totalCount);
        
        request.getRequestDispatcher("/WEB-INF/views/index.jsp").forward(request, response);
    }
    
    /**
     * 文章详情
     */
    private void view(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        String idStr = pathInfo.substring("/view/".length());
        
        int id;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        
        Article article = articleDao.findById(id);
        if (article == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        
        // 增加浏览量
        articleDao.increaseViewCount(id);
        article.setViewCount(article.getViewCount() + 1);
        
        // 获取评论列表
        List<Comment> comments = commentDao.findByArticleId(id);
        
        request.setAttribute("article", article);
        request.setAttribute("comments", comments);
        
        request.getRequestDispatcher("/WEB-INF/views/article.jsp").forward(request, response);
    }
    
    /**
     * 发表文章页面
     */
    private void post(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // 检查登录
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        request.getRequestDispatcher("/WEB-INF/views/post.jsp").forward(request, response);
    }
    
    /**
     * 发表文章处理
     */
    private void doPost(HttpServletRequest request, HttpServletResponse response, boolean isPost) 
            throws ServletException, IOException {
        // 检查登录
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        User user = (User) session.getAttribute("user");
        
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        
        // 验证参数
        if (title == null || title.trim().isEmpty()) {
            request.setAttribute("error", "请输入文章标题！");
            request.getRequestDispatcher("/WEB-INF/views/post.jsp").forward(request, response);
            return;
        }
        
        if (content == null || content.trim().isEmpty()) {
            request.setAttribute("error", "请输入文章内容！");
            request.getRequestDispatcher("/WEB-INF/views/post.jsp").forward(request, response);
            return;
        }
        
        if (title.length() > 100) {
            request.setAttribute("error", "标题不能超过100个字符！");
            request.getRequestDispatcher("/WEB-INF/views/post.jsp").forward(request, response);
            return;
        }
        
        // 创建文章
        Article article = new Article();
        article.setTitle(title.trim());
        article.setContent(content.trim());
        article.setUserId(user.getId());
        article.setAuthor(user.getNickname());
        
        int articleId = articleDao.create(article);
        
        if (articleId > 0) {
            response.sendRedirect(request.getContextPath() + "/article/view/" + articleId);
        } else {
            request.setAttribute("error", "发表文章失败，请重试！");
            request.getRequestDispatcher("/WEB-INF/views/post.jsp").forward(request, response);
        }
    }
}
