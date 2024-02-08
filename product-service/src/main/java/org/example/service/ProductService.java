package org.example.service;

import org.example.dao.ProductDao;
import org.example.entity.Product;

import java.util.List;

public class ProductService {
    private final ProductDao productDao =ProductDao.getInstance();

    public void saveProduct(Product product){
        productDao.saveProduct(product);
    }
    public Product getProductById(int id){
        return productDao.getProductById(id);
    }
    public List<Product> getAllProduct(){
        return productDao.getAllProduct();
    }
    public void update(int id, Product updateProd){
        productDao.update(id, updateProd);
    }
    public Product delete(int id){
        return productDao.delete(id);
    }
}
