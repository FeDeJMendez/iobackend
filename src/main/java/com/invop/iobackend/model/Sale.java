package com.invop.iobackend.model;

import com.invop.iobackend.utils.URIInterface;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import java.text.DecimalFormat;
import java.util.List;

@Entity
@Table(name = "sales")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Sale implements URIInterface {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sale")
    private List<Detail> details;

    @Column
    @NumberFormat(style = NumberFormat.Style.CURRENCY)
    private Double total;

    public void CalculateTotal () {
        Double sum = 0.0;
        for (Detail detail : details) {
            sum += detail.CalculateSubtotal();
        }
        sum = Math.round(sum*100.0)/100.0;
        this.total = sum;
    }
}
