package com.invop.iobackend.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "modelconfigs")
public class ModelConfig {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "REVIEW_PERIOD")
    private int reviewPeriod;

    @Column(name = "DELIVERY_TIME")
    private int deliveryTime;

    @Column(name = "LAST_DELIVERY")
    private LocalDate lastDelivery;

    @Column(name = "SUPPLIER")
    private String supplier;

    @Column(name = "SATISFACTION")
    private double satisfaction;

    @Column(name = "STORAGE_COST")
    private double storageCost;

    @Column(name = "DELIVERY_COST")
    private double deliveryCost;
}
