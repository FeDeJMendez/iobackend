package com.invop.iobackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @Column
    private Double price;

    @Column
    private Integer stock;

    @Column(name = "class_product")
    @Enumerated(EnumType.STRING)
    private ClassProduct classProduct;


//    @JsonIgnore
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
//    private List<Detail> details;
}
