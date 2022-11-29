package com.invop.iobackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.invop.iobackend.utils.NormalizedTableEz;
import com.invop.iobackend.utils.URIInterface;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.bytebuddy.implementation.bind.annotation.Default;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product implements URIInterface {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String barcode;

    @Column(unique = true)
    private String description;

    @Column
    private Double price;

    @Column 
    private Integer stock;

    @Column(name = "class_product")
    @Enumerated(EnumType.STRING)
    private ClassProduct classProduct;

    private String model;
    private Double avgDemand;      //Average demand
    private Double disDemand;      //Dispersion of demand
    private Double reorderPoint;
    private Float costOfPreparing;
    private Float storageCost;
    private String supplier;
    private Double ReviewPeriod;
    private Double serviceLevel;
    private Integer leadTime;



    public static Double calculateReorderPoint(Product product) {
        if(product.model.equals(ModelType.Q_MODEL)) {
            Double Q = Math.sqrt(((2*product.getAvgDemand()* product.getCostOfPreparing())/product.getStorageCost()));
            System.out.println("Q: "+Q);
            Double oL = Math.sqrt(product.getReviewPeriod()) * product.getDisDemand();
            System.out.println("oL: "+oL);
            Double eX = (1 - product.getServiceLevel())*(Q/oL);
            System.out.println("EX: "+eX);
            NormalizedTableEz brownTable = new NormalizedTableEz();
            Double z = brownTable.calculateZeta(eX);
            return (product.getAvgDemand()*product.getLeadTime()) + (z*oL);
        }
        return null;
    }


//    @JsonIgnore
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
//    private List<Detail> details;
}
