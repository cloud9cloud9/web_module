package org.example.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.entity.Product;
import org.example.factory.ProductServiceFactory;
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
        productService = ProductServiceFactory.createProductService();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        try {
            if (request.getPathInfo() != null) {
                String stringId = request.getPathInfo().substring(1);
                Product product = productService.getProductById(Integer.parseInt(stringId));
                response.getWriter().println(mapper.writeValueAsString(product));
            } else {
                List<Product> allProduct = productService.getAllProduct();
                response.getWriter().println(mapper.writeValueAsString(allProduct));
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println("Internal Server Error: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        StringBuffer sf = new StringBuffer();
        String line = null;
        try {
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null) {
                sf.append(line);
            }
            Product product = mapper.readValue(sf.toString(), Product.class);
            productService.saveProduct(product);
            out.write(mapper.writeValueAsString(product));
            response.setStatus(HttpServletResponse.SC_CREATED);

        } catch (Exception e) {
            out.write("{\"error\" : \"" + e.getMessage() + "\"}");
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            if (request.getPathInfo() != null) {
                String stringId = request.getPathInfo().substring(1);
                BufferedReader reader = request.getReader();
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                Product product = mapper.readValue(sb.toString(), Product.class);
                productService.update(Integer.parseInt(stringId), product);
                response.getWriter().append("Product is updated");
            }
        } catch (Exception e) {
            out.write("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        String contextPath = request.getPathInfo();
        try {
            if (contextPath != null) {
                productService.delete(Integer.parseInt(contextPath.substring(1)));
                out.write("{\"report\": \"deleted\"}");
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.write("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
}
