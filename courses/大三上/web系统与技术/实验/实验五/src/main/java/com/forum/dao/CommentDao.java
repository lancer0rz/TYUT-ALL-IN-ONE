package com.forum.dao;

import com.forum.entity.Comment;
import com.forum.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 评论数据访问对象
 */
public class CommentDao {
    
    public boolean addComment(Comment comment) {
        String sql = "INSERT INTO comment (content, article_id, user_id) VALUES (?, ?, ?)";
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, comment.getContent());
            pstmt.setInt(2, comment.getArticleId());
            pstmt.setInt(3, comment.getUserId());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DBUtil.close(conn, pstmt);
        }
    }
    
    public List<Comment> findByArticleId(int articleId) {
        String sql = "SELECT c.*, u.username as user_name, u.nickname as user_nickname " +
                     "FROM comment c LEFT JOIN user u ON c.user_id = u.id " +
                     "WHERE c.article_id = ? ORDER BY c.create_time ASC";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Comment> comments = new ArrayList<>();
        
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, articleId);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                comments.add(mapResultSetToComment(rs));
            }
            return comments;
        } catch (SQLException e) {
            e.printStackTrace();
            return comments;
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }
    }

    /**
     * 兼容调用：创建评论。
     */
    public boolean create(Comment comment) {
        return addComment(comment);
    }
    
    public int getCountByArticleId(int articleId) {
        String sql = "SELECT COUNT(*) FROM comment WHERE article_id = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, articleId);
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
    
    public boolean deleteById(int id) {
        String sql = "DELETE FROM comment WHERE id = ?";
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
    
    private Comment mapResultSetToComment(ResultSet rs) throws SQLException {
        Comment comment = new Comment();
        comment.setId(rs.getInt("id"));
        comment.setContent(rs.getString("content"));
        comment.setArticleId(rs.getInt("article_id"));
        comment.setUserId(rs.getInt("user_id"));
        comment.setUserName(rs.getString("user_name"));
        comment.setUserNickname(rs.getString("user_nickname"));
        // 前端显示统一使用 author 字段
        if (comment.getUserNickname() != null) {
            comment.setAuthor(comment.getUserNickname());
        } else {
            comment.setAuthor(comment.getUserName());
        }
        comment.setCreateTime(rs.getTimestamp("create_time"));
        return comment;
    }
}
