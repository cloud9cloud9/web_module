package org.example.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.entity.Discount;
import org.example.service.DiscountService;

import java.io.IOException;

@WebServlet("/discount")
public class DiscountServlet extends HttpServlet {
    private final DiscountService discountService = new DiscountService();
    private static final ObjectMapper mapper = new ObjectMapper();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        mapper.writeValue(resp.getWriter(), new Discount(discountService.getDiscount()));
    }
}
