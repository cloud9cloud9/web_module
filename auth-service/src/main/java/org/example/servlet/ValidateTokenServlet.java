package org.example.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.service.AuthService;
import org.example.validator.ValidationResult;

import java.io.IOException;

@WebServlet("/validate")
public class ValidateTokenServlet extends HttpServlet {
    private final AuthService authService = AuthService.getINSTANCE();
    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String token = req.getHeader("Authorization");
        boolean isValid = authService.isValidateToken(token);
        ValidationResult result = new ValidationResult(isValid);
        String json = mapper.writeValueAsString(result);
        resp.setContentType("application/json");
        resp.getWriter().println(json);
        resp.getWriter().flush();
    }
}
