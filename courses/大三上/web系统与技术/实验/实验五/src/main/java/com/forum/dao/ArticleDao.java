package com.forum.dao;

import com.forum.entity.Article;
import com.forum.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 文章数据访问对象
 */
public class ArticleDao {
    
    public boolean addArticle(Article article) {
        String sql = "INSERT INTO article (title, content, author_id) VALUES (?, ?, ?)";
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, article.getTitle());
            pstmt.setString(2, article.getContent());
            pstmt.setInt(3, article.getAuthorId());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DBUtil.close(conn, pstmt);
        }
    }
    
    public Article findById(int id) {
        String sql = "SELECT a.*, u.username as author_name, u.nickname as author_nickname " +
                     "FROM article a LEFT JOIN user u ON a.author_id = u.id WHERE a.id = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToArticle(rs);
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }
    }
    
    public List<Article> findAll() {
        String sql = "SELECT a.*, u.username as author_name, u.nickname as author_nickname " +
                     "FROM article a LEFT JOIN user u ON a.author_id = u.id " +
                     "ORDER BY a.create_time DESC";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Article> articles = new ArrayList<>();
        
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                articles.add(mapResultSetToArticle(rs));
            }
            return articles;
        } catch (SQLException e) {
            e.printStackTrace();
            return articles;
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }
    }

    /**
     * 分页查询文章列表。
     */
    public List<Article> findAll(int page, int pageSize) {
        String sql = "SELECT a.*, u.username as author_name, u.nickname as author_nickname " +
                     "FROM article a LEFT JOIN user u ON a.author_id = u.id " +
                     "ORDER BY a.create_time DESC LIMIT ? OFFSET ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Article> articles = new ArrayList<>();

        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, pageSize);
            pstmt.setInt(2, Math.max(0, (page - 1) * pageSize));
            rs = pstmt.executeQuery();

            while (rs.next()) {
                articles.add(mapResultSetToArticle(rs));
            }
            return articles;
        } catch (SQLException e) {
            e.printStackTrace();
            return articles;
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }
    }
    
    public List<Article> findByAuthorId(int authorId) {
        String sql = "SELECT a.*, u.username as author_name, u.nickname as author_nickname " +
                     "FROM article a LEFT JOIN user u ON a.author_id = u.id " +
                     "WHERE a.author_id = ? ORDER BY a.create_time DESC";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Article> articles = new ArrayList<>();
        
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, authorId);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                articles.add(mapResultSetToArticle(rs));
            }
            return articles;
        } catch (SQLException e) {
            e.printStackTrace();
            return articles;
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }
    }
    
    public boolean incrementViewCount(int id) {
        String sql = "UPDATE article SET view_count = view_count + 1 WHERE id = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DBUtil.close(conn, pstmt);
        }
    }

    /**
     * 兼容调用：浏览量 +1。
     */
    public boolean increaseViewCount(int id) {
        return incrementViewCount(id);
    }
    
    public boolean updateCommentCount(int id) {
        String sql = "UPDATE article SET comment_count = (SELECT COUNT(*) FROM comment WHERE article_id = ?) WHERE id = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.setInt(2, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DBUtil.close(conn, pstmt);
        }
    }

    /**
     * 兼容调用：刷新评论数。
     */
    public boolean increaseCommentCount(int id) {
        return updateCommentCount(id);
    }
    
    public boolean deleteById(int id) {
        String sql = "DELETE FROM article WHERE id = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DBUtil.close(conn, pstmt);
        }
    }
    
    public int getTotalCount() {
        String sql = "SELECT COUNT(*) FROM article";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }
    }

    /**
     * 兼容调用：文章总数。
     */
    public int countAll() {
        return getTotalCount();
    }

    /**
     * 创建文章并返回生成的主键ID。
     */
    public int create(Article article) {
        String sql = "INSERT INTO article (title, content, author_id) VALUES (?, ?, ?)";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, article.getTitle());
            pstmt.setString(2, article.getContent());
            pstmt.setInt(3, article.getAuthorId());
            int affected = pstmt.executeUpdate();
            if (affected > 0) {
                rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
            return -1;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }
    }
    
    private Article mapResultSetToArticle(ResultSet rs) throws SQLException {
        Article article = new Article();
        article.setId(rs.getInt("id"));
        article.setTitle(rs.getString("title"));
        article.setContent(rs.getString("content"));
        article.setAuthorId(rs.getInt("author_id"));
        article.setAuthorName(rs.getString("author_name"));
        article.setAuthorNickname(rs.getString("author_nickname"));
        // 前端显示统一使用 author 字段
        if (article.getAuthorNickname() != null) {
            article.setAuthor(article.getAuthorNickname());
        } else {
            article.setAuthor(article.getAuthorName());
        }
        article.setViewCount(rs.getInt("view_count"));
        article.setCommentCount(rs.getInt("comment_count"));
        article.setCreateTime(rs.getTimestamp("create_time"));
        article.setUpdateTime(rs.getTimestamp("update_time"));
        return article;
    }
}
