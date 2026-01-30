package com.forum.entity;

import java.util.Date;

/**
 * 文章实体类
 */
public class Article {
    private Integer id;
    private String title;
    private String content;
    private Integer authorId;
    private String author;         // 前端显示用
    private String authorName;      // 作者名称（关联查询）
    private String authorNickname;  // 作者昵称（关联查询）
    private Integer viewCount;
    private Integer commentCount;
    private Date createTime;
    private Date updateTime;
    
    public Article() {}
    
    public Article(String title, String content, Integer authorId) {
        this.title = title;
        this.content = content;
        this.authorId = authorId;
    }
    
    // Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public Integer getAuthorId() { return authorId; }
    public void setAuthorId(Integer authorId) { this.authorId = authorId; }
    public Integer getUserId() { return authorId; }
    public void setUserId(Integer userId) { this.authorId = userId; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public String getAuthorName() { return authorName; }
    public void setAuthorName(String authorName) { this.authorName = authorName; }
    public String getAuthorNickname() { return authorNickname; }
    public void setAuthorNickname(String authorNickname) { this.authorNickname = authorNickname; }
    public Integer getViewCount() { return viewCount; }
    public void setViewCount(Integer viewCount) { this.viewCount = viewCount; }
    public Integer getCommentCount() { return commentCount; }
    public void setCommentCount(Integer commentCount) { this.commentCount = commentCount; }
    public Date getCreateTime() { return createTime; }
    public void setCreateTime(Date createTime) { this.createTime = createTime; }
    public Date getUpdateTime() { return updateTime; }
    public void setUpdateTime(Date updateTime) { this.updateTime = updateTime; }
    
    // 获取内容摘要
    public String getSummary() {
        if (content == null) return "";
        String plainText = content.replaceAll("\\n", " ").replaceAll("\\s+", " ");
        return plainText.length() > 100 ? plainText.substring(0, 100) + "..." : plainText;
    }
}
