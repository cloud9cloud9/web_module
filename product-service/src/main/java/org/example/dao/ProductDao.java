package org.example.dao;

import org.example.entity.Product;
import org.example.service.DiscountService;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class ProductDao {
    private static final ProductDao INSTANCE = new ProductDao();
    private final List<Product> productList = new ArrayList<>();
    private final DiscountService discountService = new DiscountService();
    private AtomicInteger key = new AtomicInteger();

    public ProductDao() {
        saveProduct(Product.builder().name("coca").price(20.0).build());
        saveProduct(Product.builder().name("pizza").price(100.0).build());
        saveProduct(Product.builder().name("chizzburger").price(56.0).build());
    }

    public void saveProduct(Product product) {
        product.setId(key.incrementAndGet());
        productList.add(product);
    }

    public Product getProductById(int id) {
        return productList.stream()
                .filter(p -> p.getId() == id)
                .map(p -> {
                    Product productCopy = new Product(p.getId(), p.getName(), p.getPrice());
                    setProductPriceWithDiscount(productCopy);
                    return productCopy;
                })
                .findFirst()
                .orElse(null);
    }

    public List<Product> getAllProduct() {
        return productList.stream()
                .map(product -> {
                    Product discountedProduct = new Product(product.getId(), product.getName(), product.getPrice());
                    setProductPriceWithDiscount(discountedProduct);
                    return discountedProduct;
                })
                .collect(Collectors.toList());
    }

    public void update(int id, Product updateProd) {
        Product product = getProductById(id);
        if (updateProd != null && product != null) {
            product.setName(updateProd.getName());
            product.setPrice(updateProd.getPrice());
            product.setId(key.incrementAndGet());
            delete(id);
        }
        productList.add(product);
    }

    public Product delete(int id) {
        Iterator<Product> iterator = productList.iterator();
        while (iterator.hasNext()) {
            Product product = iterator.next();
            if (product.getId() == id) {
                iterator.remove();
                return product;
            }
        }
        System.out.println("product not found");
        return null;
    }

    public static ProductDao getInstance() {
        return INSTANCE;
    }

    private void setProductPriceWithDiscount(Product product) {
        product.setPrice(product.getPrice() - (product.getPrice() * getPriceWithDiscount()));
    }

    private double getPriceWithDiscount() {
        return discountService.getPriceWithDiscount();
    }
}
