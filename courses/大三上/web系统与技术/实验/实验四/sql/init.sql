-- 实验四 JDBC访问数据库
-- 数据库初始化脚本

-- 创建数据库
CREATE DATABASE IF NOT EXISTS web_experiment 
    DEFAULT CHARACTER SET utf8mb4 
    COLLATE utf8mb4_unicode_ci;

USE web_experiment;

-- 创建用户表
CREATE TABLE IF NOT EXISTS user (
    id INT PRIMARY KEY AUTO_INCREMENT COMMENT '用户ID',
    username VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
    password VARCHAR(100) NOT NULL COMMENT '密码',
    email VARCHAR(100) COMMENT '邮箱',
    nickname VARCHAR(50) COMMENT '昵称',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 插入测试数据
INSERT INTO user (username, password, email, nickname) VALUES 
    ('admin', '123456', 'admin@example.com', '管理员'),
    ('test', '123456', 'test@example.com', '测试用户');

-- 查看表结构
DESC user;

-- 查看测试数据
SELECT * FROM user;
