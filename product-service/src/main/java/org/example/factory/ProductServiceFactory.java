package org.example.factory;

import org.example.service.ProductService;

public class ProductServiceFactory {
    public static ProductService createProductService() {
        return new ProductService();
    }
}
