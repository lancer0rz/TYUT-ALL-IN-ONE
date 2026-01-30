package com.forum.servlet;

import com.forum.dao.ArticleDao;
import com.forum.entity.Article;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

/**
 * 首页Servlet - 作为论坛入口
 */
@WebServlet(urlPatterns = {"", "/index"})
public class IndexServlet extends HttpServlet {
    
    private ArticleDao articleDao = new ArticleDao();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        
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
}
