<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.text.SimpleDateFormat, java.util.Date" %>
<%
    // 从Session获取用户信息
    String username = (String) session.getAttribute("username");
    String email = (String) session.getAttribute("email");
    Long loginTime = (Long) session.getAttribute("loginTime");
    
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String loginTimeStr = loginTime != null ? sdf.format(new Date(loginTime)) : "未知";
%>
<div class="content" style="background:linear-gradient(135deg,#667eea,#764ba2);color:white;">
    <h2 style="color:white;border-bottom-color:white;">👋 欢迎回来！</h2>
    <p style="font-size:18px;">当前登录用户：<strong><%= username %></strong></p>
    <p>邮箱：<%= email != null && !email.isEmpty() ? email : "未设置" %></p>
    <p>登录时间：<%= loginTimeStr %></p>
    <p>Session ID：<span style="font-size:11px;"><%= session.getId() %></span></p>
</div>
