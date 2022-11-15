package com.invop.iobackend.service;

import com.invop.iobackend.exceptions.InsufficientStockException;
import com.invop.iobackend.exceptions.ProductNotExistsException;
import com.invop.iobackend.model.Detail;
import com.invop.iobackend.model.Product;
import com.invop.iobackend.model.Sale;
import com.invop.iobackend.repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SaleService {

    private final SaleRepository saleRepository;
    private final ProductService productService;
    private final DetailService detailService;

    @Autowired
    public SaleService(SaleRepository saleRepository, ProductService productService, DetailService detailService) {
        this.saleRepository = saleRepository;
        this.productService = productService;
        this.detailService = detailService;
    }


    public Sale addSale(List<Detail> detailList)
            throws InsufficientStockException, ProductNotExistsException {
        List<Detail> details = new ArrayList<>();
        for (Detail detail : detailList) {
            Product product = productService.getByBarcode(detail.getProduct().getBarcode());
            Integer stockProduct = product.getStock();
            stockProduct -= detail.getLot();
            if (stockProduct < 0)
                throw new InsufficientStockException();
        }
        for (Detail detail : detailList) {
            Product product = productService.getByBarcode(detail.getProduct().getBarcode());
            Integer stockProduct = product.getStock();
            stockProduct -= detail.getLot();
            product.setStock(stockProduct);
            detail.setProduct(product);
            Detail newDetail = detailService.addDetail(detail);
            details.add(newDetail);

//            detail = detailService.addDetail(detail);
//            product.getDetails().add(detail);
        }
//        Sale newSale = saleRepository.save(new Sale());
//        for (Detail d : details) {
//            d.setSale(newSale);
//        }
//        newSale.setDetails(details);
//        saleRepository.save(newSale);

        Sale newSale = Sale.builder().details(details).build();
        newSale.CalculateTotal();
        newSale = saleRepository.save(newSale);

//        Sale newSale = new Sale();
//        newSale.setDetails(details);
//        newSale.CalculateTotal();
//        newSale = saleRepository.save(newSale);

//        for (Detail d : newSale.getDetails()) {
//            d.setSale(newSale);
//        }
        return newSale;
    }

    public List<Sale> getAll() {
        return saleRepository.findAll();
    }
}
