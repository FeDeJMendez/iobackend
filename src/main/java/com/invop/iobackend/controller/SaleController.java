package com.invop.iobackend.controller;

import com.invop.iobackend.config.Conf;
import com.invop.iobackend.exceptions.InsufficientStockException;
import com.invop.iobackend.exceptions.ProductNotExistsException;
import com.invop.iobackend.model.Detail;
import com.invop.iobackend.model.Product;
import com.invop.iobackend.model.Sale;
import com.invop.iobackend.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/sales")
public class SaleController {

    private final SaleService saleService;

    @Autowired
    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }

    /// Add New Sale ///
    @PostMapping(path = "/", consumes = "application/json")
    public ResponseEntity addSale (@RequestBody @Validated final List<Detail> detailList)
            throws InsufficientStockException, ProductNotExistsException {
        Sale newSale = saleService.addSale(detailList);
        return ResponseEntity.created(Conf.getLocation(newSale)).build();
    }

    /// Get All Sales ///
    @GetMapping(path = "/", produces = "application/json")
    public ResponseEntity<List<Sale>> getAllSales () {
        List<Sale> allSales = saleService.getAll();
        HttpStatus httpStatus = allSales.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return ResponseEntity
                .status(httpStatus)
                .body(allSales);
    }
}
