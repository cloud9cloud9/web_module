package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.entity.Role;
import org.example.service.AuthService;
import org.example.util.JspHelper;
import org.example.util.UrlPath;

import java.io.IOException;

@WebServlet(UrlPath.REGISTRATION)
public class RegistrationServlet extends HttpServlet {
    AuthService authService = AuthService.getINSTANCE();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("roles", Role.values());

        req.getRequestDispatcher(JspHelper.getPath(UrlPath.REGISTRATION))
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Role role = req.getParameter("role") != null ? Role.valueOf(req.getParameter("role")) : Role.USER;
        authService.registerUser(req.getParameter("email"), req.getParameter("password"), role);
    }
}
