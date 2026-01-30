package com.forum.entity;

import java.util.Date;

/**
 * 评论实体类
 */
public class Comment {
    private Integer id;
    private String content;
    private Integer articleId;
    private Integer userId;
    private String author;         // 前端显示用
    private String userName;      // 用户名（关联查询）
    private String userNickname;  // 用户昵称（关联查询）
    private Date createTime;
    
    public Comment() {}
    
    public Comment(String content, Integer articleId, Integer userId) {
        this.content = content;
        this.articleId = articleId;
        this.userId = userId;
    }
    
    // Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public Integer getArticleId() { return articleId; }
    public void setArticleId(Integer articleId) { this.articleId = articleId; }
    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    public String getUserNickname() { return userNickname; }
    public void setUserNickname(String userNickname) { this.userNickname = userNickname; }
    public Date getCreateTime() { return createTime; }
    public void setCreateTime(Date createTime) { this.createTime = createTime; }
}
