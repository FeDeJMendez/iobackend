package com.invop.iobackend.model;

import com.invop.iobackend.utils.URIInterface;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.bytebuddy.implementation.bind.annotation.Default;

import javax.persistence.*;

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

    @Column(name = "class_product")
    @Enumerated(EnumType.STRING)
    private ClassProduct classProduct;
}
