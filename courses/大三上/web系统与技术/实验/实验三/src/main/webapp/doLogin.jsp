<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.net.URLEncoder, java.net.URLDecoder" %>
<%
    request.setCharacterEncoding("UTF-8");
    String username = request.getParameter("username");
    String password = request.getParameter("password");
    String remember = request.getParameter("remember");
    
    // 验证参数不为空
    if (username == null || username.trim().isEmpty() || 
        password == null || password.trim().isEmpty()) {
        response.sendRedirect("login.jsp?error=empty");
        return;
    }
    
    // 从Cookie查找用户信息
    Cookie[] cookies = request.getCookies();
    String storedInfo = null;
    if (cookies != null) {
        for (Cookie c : cookies) {
            if (c.getName().equals("user_" + username)) {
                storedInfo = URLDecoder.decode(c.getValue(), "UTF-8");
                break;
            }
        }
    }
    
    // 验证用户是否存在
    if (storedInfo == null) {
        response.sendRedirect("login.jsp?error=notfound");
        return;
    }
    
    // 解析存储的信息：密码|邮箱
    String[] parts = storedInfo.split("\\|");
    String storedPassword = parts[0];
    String storedEmail = parts.length > 1 ? parts[1] : "";
    
    // 验证密码
    if (!password.equals(storedPassword)) {
        response.sendRedirect("login.jsp?error=password");
        return;
    }
    
    // 登录成功，保存用户信息到Session
    session.setAttribute("username", username);
    session.setAttribute("email", storedEmail);
    session.setAttribute("loginTime", System.currentTimeMillis());
    session.setMaxInactiveInterval(30 * 60); // 30分钟有效期
    
    // 如果勾选"记住我"，保存Cookie
    if ("true".equals(remember)) {
        Cookie savedCookie = new Cookie("savedUsername", URLEncoder.encode(username, "UTF-8"));
        savedCookie.setMaxAge(60 * 60 * 24 * 7); // 7天
        savedCookie.setPath("/");
        response.addCookie(savedCookie);
    }
    
    // 跳转到主页面
    response.sendRedirect("index.jsp");
%>
