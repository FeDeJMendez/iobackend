package com.invop.iobackend.service;

import com.invop.iobackend.exceptions.ProductExistsException;
import com.invop.iobackend.exceptions.ProductNotExistsException;
import com.invop.iobackend.model.ClassProduct;
import com.invop.iobackend.model.Product;
import com.invop.iobackend.repository.DetailRepository;
import com.invop.iobackend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final DetailRepository detailRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, DetailRepository detailRepository) {
        this.productRepository = productRepository;
        this.detailRepository = detailRepository;
    }

    public Product addProduct(Product product)
            throws ProductExistsException {
        if (productRepository.existsByBarcode(product.getBarcode()))
            throw new ProductExistsException();
        if (product.getStock() == null)
            product.setStock(0);
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

    public void recategorize() {
        List<Product> products = this.getAll();
        Integer totalSold = detailRepository.totalSold();
        for (Product p : products) {
            Integer totalProductSold = detailRepository.totalSoldByProduct(p.getId());
            Double limitCB = totalSold * 0.03;
            Double limitBA = totalSold * 0.04;
            if (totalProductSold > limitCB) {
                if (totalProductSold > limitBA) {
                    p.setClassProduct(ClassProduct.A);
                    productRepository.save(p);
                }
                else {
                    p.setClassProduct(ClassProduct.B);
                    productRepository.save(p);
                }
            }
        }
    }

    public Product editStock(String barcode, Integer stock)
            throws ProductNotExistsException {
        Product product = this.getByBarcode(barcode);
        product.setStock(stock);
        productRepository.save(product);
        return product;
    }
}
