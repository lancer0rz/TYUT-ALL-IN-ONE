-- 实验五 简易论坛开发
-- 数据库初始化脚本

-- 创建数据库
CREATE DATABASE IF NOT EXISTS forum_db 
    DEFAULT CHARACTER SET utf8mb4 
    COLLATE utf8mb4_unicode_ci;

USE forum_db;

-- 创建用户表
CREATE TABLE IF NOT EXISTS user (
    id INT PRIMARY KEY AUTO_INCREMENT COMMENT '用户ID',
    username VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
    password VARCHAR(100) NOT NULL COMMENT '密码',
    email VARCHAR(100) COMMENT '邮箱',
    nickname VARCHAR(50) COMMENT '昵称',
    avatar VARCHAR(200) DEFAULT '/images/default-avatar.png' COMMENT '头像',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 创建文章表
CREATE TABLE IF NOT EXISTS article (
    id INT PRIMARY KEY AUTO_INCREMENT COMMENT '文章ID',
    title VARCHAR(200) NOT NULL COMMENT '文章标题',
    content TEXT NOT NULL COMMENT '文章内容',
    author_id INT NOT NULL COMMENT '作者ID',
    view_count INT DEFAULT 0 COMMENT '浏览次数',
    comment_count INT DEFAULT 0 COMMENT '评论数',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    FOREIGN KEY (author_id) REFERENCES user(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文章表';

-- 创建评论表
CREATE TABLE IF NOT EXISTS comment (
    id INT PRIMARY KEY AUTO_INCREMENT COMMENT '评论ID',
    content TEXT NOT NULL COMMENT '评论内容',
    article_id INT NOT NULL COMMENT '文章ID',
    user_id INT NOT NULL COMMENT '用户ID',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    FOREIGN KEY (article_id) REFERENCES article(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='评论表';

-- 插入测试用户
INSERT INTO user (username, password, email, nickname) VALUES 
    ('admin', '123456', 'admin@forum.com', '管理员'),
    ('zhangsan', '123456', 'zhangsan@forum.com', '张三'),
    ('lisi', '123456', 'lisi@forum.com', '李四');

-- 插入测试文章
INSERT INTO article (title, content, author_id) VALUES 
    ('欢迎来到论坛', '这是论坛的第一篇文章，欢迎大家来交流学习！\n\n论坛功能包括：\n1. 用户注册和登录\n2. 发表文章\n3. 查看文章详情\n4. 发表评论', 1),
    ('Java Web开发入门', 'Java Web开发是一个非常有趣的领域。本文将介绍Java Web开发的基础知识，包括Servlet、JSP、JDBC等技术。\n\n## Servlet\nServlet是Java Web开发的核心技术...\n\n## JSP\nJSP是一种动态网页技术...\n\n## JDBC\nJDBC是Java数据库连接技术...', 2),
    ('MySQL数据库学习笔记', '今天学习了MySQL数据库的基本操作，包括增删改查等。\n\n## 创建数据库\nCREATE DATABASE mydb;\n\n## 创建表\nCREATE TABLE users (id INT PRIMARY KEY);', 3);

-- 插入测试评论
INSERT INTO comment (content, article_id, user_id) VALUES 
    ('写得很好，期待更多文章！', 1, 2),
    ('论坛不错，支持一下！', 1, 3),
    ('讲解得很清楚，学到了很多！', 2, 1),
    ('MySQL确实很重要', 3, 2);

-- 更新文章评论数
UPDATE article SET comment_count = (SELECT COUNT(*) FROM comment WHERE article_id = article.id);

-- 查看数据
SELECT * FROM user;
SELECT * FROM article;
SELECT * FROM comment;
