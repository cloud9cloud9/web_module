package org.example.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.service.AuthService;

import java.io.IOException;

@WebFilter("/products/*")
public class AuthorizationFilter implements Filter{
    private final AuthService authService = new AuthService();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        String token = httpRequest.getHeader("Authorization");

        if (token == null || !authService.isTokenValid(token)) {
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.getWriter().write("Unauthorized access");
            return;
        }
        if (authService.isAdminUser(token) || httpRequest.getMethod().equals("POST")) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
            httpResponse.getWriter().write("Permission denied for non-POST requests");
        }
    }
}
