package com.dzirt.spring.services;

import com.dzirt.spring.entities.Product;
import com.dzirt.spring.repositories.ProductDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private ProductDAO productDAO;

    @Autowired
    public void setProductRepository(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    public List<Product> getAllProducts() {
        return productDAO.findAll();
    }

    public Product getByID(Long id) {
        return productDAO.findById(id);
    }

    public void remove(Long id) {
        productDAO.remove(id);
    }

    public void add(Product product) {
        productDAO.add(product);
    }
    public void update(Product product) {
        productDAO.update(product);
    }
}
