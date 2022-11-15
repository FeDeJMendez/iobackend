package com.invop.iobackend.service;

import com.invop.iobackend.exceptions.ProductNotExistsException;
import com.invop.iobackend.model.Detail;
import com.invop.iobackend.model.Product;
import com.invop.iobackend.model.Sale;
import com.invop.iobackend.repository.DetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DetailService {

    private final DetailRepository detailRepository;
    private final ProductService productService;

    @Autowired
    public DetailService(DetailRepository detailRepository, ProductService productService) {
        this.detailRepository = detailRepository;
        this.productService = productService;
    }

    public Detail addDetail(Detail detail)
            throws ProductNotExistsException {
        Product product = productService.getByBarcode(detail.getProduct().getBarcode());
        detail.setProduct(product);
        return detailRepository.save(detail);
    }

    public Integer getTotalSold () {
        return detailRepository.totalSold();
    }

    public Integer getTotalSoldByProduct (Integer idProduct) {
        return detailRepository.totalSoldByProduct(idProduct);
    }
}
