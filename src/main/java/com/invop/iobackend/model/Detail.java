package com.invop.iobackend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "details")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Detail {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column()
    private Integer lot;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
//    @JsonBackReference
    @JoinColumn(name = "sale_id")
    private Sale sale;


    public Double CalculateSubtotal(){
        Double unitPrice = product.getPrice();
        return unitPrice * lot;
    }
}
