package com.invop.iobackend.service;

import com.invop.iobackend.exceptions.ProductExistsException;
import com.invop.iobackend.exceptions.ProductNotExistsException;
import com.invop.iobackend.model.Product;
import com.invop.iobackend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product addProduct(Product product)
            throws ProductExistsException {
        if (productRepository.existsByBarcode(product.getBarcode()))
            throw new ProductExistsException();
        return productRepository.save(product);
    }

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public Product getByBarcode(String barcode)
            throws ProductNotExistsException {
        if (!productRepository.existsByBarcode(barcode))
            throw new ProductNotExistsException();
        return productRepository.findByBarcode(barcode);
    }

    public void deleteByBarcode(String barcode)
            throws ProductNotExistsException {
        Product product = this.getByBarcode(barcode);
        productRepository.deleteById(product.getId());
    }
}
