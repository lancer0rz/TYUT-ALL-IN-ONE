<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    // 使Session失效
    session.invalidate();
    
    // 清除"记住我"的Cookie
    Cookie savedCookie = new Cookie("savedUsername", "");
    savedCookie.setMaxAge(0);
    savedCookie.setPath("/");
    response.addCookie(savedCookie);
    
    // 跳转到登录页面
    response.sendRedirect("login.jsp?msg=logout");
%>
