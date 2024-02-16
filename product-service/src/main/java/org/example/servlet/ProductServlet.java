package org.example.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.entity.Product;
import org.example.service.ProductService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/products/*")
public class ProductServlet extends HttpServlet {
    private ProductService productService;
    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void init() throws ServletException {
        productService = new ProductService();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            if (request.getPathInfo() != null) {
                String stringId = request.getPathInfo().substring(1);
                Product product = productService.getProductById(Integer.parseInt(stringId));
                response.getWriter().write(mapper.writeValueAsString(product));
            } else {
                List<Product> allProduct = productService.getAllProduct();
                response.getWriter().write(mapper.writeValueAsString(allProduct));
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Internal Server Error: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (BufferedReader reader = request.getReader();
             PrintWriter out = response.getWriter()) {
            StringBuilder sf = new StringBuilder();
            reader.lines().forEach(sf::append);
            Product product = mapper.readValue(sf.toString(), Product.class);
            productService.saveProduct(product);
            out.write(mapper.writeValueAsString(product));
            response.setStatus(HttpServletResponse.SC_CREATED);
        } catch (Exception e) {
            response.getWriter().write("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String stringId = request.getPathInfo().substring(1);
            Product product = new ObjectMapper().readValue(request.getReader(), Product.class);
            productService.update(Integer.parseInt(stringId), product);
            response.getWriter().append("Product is updated");
        } catch (Exception e) {
            response.getWriter().write("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String contextPath = request.getPathInfo();
        try (PrintWriter out = response.getWriter()) {
            if (contextPath != null) {
                int id = Integer.parseInt(contextPath.substring(1));
                productService.delete(id);
                out.write("{\"report\": \"deleted\"}");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
}
