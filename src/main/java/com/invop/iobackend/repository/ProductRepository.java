package com.invop.iobackend.repository;

import com.invop.iobackend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Boolean existsByBarcode(String barcode);

    Product findByBarcode(String barcode);

    void deleteByBarcode(String barcode);
}
