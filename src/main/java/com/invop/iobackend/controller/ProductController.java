package com.invop.iobackend.controller;

import com.invop.iobackend.config.Conf;
import com.invop.iobackend.exceptions.ProductExistsException;
import com.invop.iobackend.exceptions.ProductNotExistsException;
import com.invop.iobackend.model.Product;
import com.invop.iobackend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/products")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    /// Add New Product ///
    @PostMapping(path = "/", consumes = "application/json")
    public ResponseEntity addProduct (@RequestBody @Validated final Product product)
            throws ProductExistsException {
        Product newProduct = productService.addProduct(product);
        return ResponseEntity.created(Conf.getLocation(newProduct)).build();
    }

    /// Get All Products ///
    @GetMapping(path = "/", produces = "application/json")
    public ResponseEntity<List<Product>> getAllProducts () {
        List<Product> allProducts = productService.getAll();
        HttpStatus httpStatus = allProducts.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return ResponseEntity
                .status(httpStatus)
                .body(allProducts);
    }

    /// Get Product By Barcode ///
    @GetMapping(value = "/{barcode}", produces = "application/json")
    public ResponseEntity getProductByBarcode(@PathVariable(value = "barcode") String barcode)
            throws ProductNotExistsException {
        Product product = productService.getByBarcode(barcode);
        return ResponseEntity.
                status(HttpStatus.OK)
                .body(product);
    }

    ///// Delete Product By Barcode /////
    @DeleteMapping(value = "/{barcode}", produces = "application/json")
    public ResponseEntity deleteProductByBarcode(@PathVariable(value = "barcode") String barcode)
            throws ProductNotExistsException {
        productService.deleteByBarcode(barcode);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
