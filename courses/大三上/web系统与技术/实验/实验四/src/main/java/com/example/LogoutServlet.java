package com.example;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * 登出Servlet - 销毁Session
 */
@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // 销毁Session
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        // 跳转到登录页面
        response.sendRedirect("login.jsp?msg=logout");
    }
}
