package org.example.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.entity.Token;
import org.example.service.AuthService;
import org.example.util.JspHelper;
import org.example.util.UrlPath;

import java.io.IOException;

@WebServlet(UrlPath.LOGIN)
public class LoginServlet extends HttpServlet {
    private static final ObjectMapper mapper = new ObjectMapper();
    private final AuthService authService = AuthService.getINSTANCE();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(JspHelper.getPath(UrlPath.LOGIN)).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        if(authService.isAuthenticateUser(email, password)){
            String token = authService.getTokenFromEmail(email);
            resp.getWriter().write(mapper.writeValueAsString(new Token(token)));
            resp.setStatus(HttpServletResponse.SC_OK);
        } else {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid credentials");
        }
    }
}
