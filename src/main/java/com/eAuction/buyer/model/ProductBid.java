package com.eAuction.buyer.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "Product_Bid")
@NoArgsConstructor
public class ProductBid {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String phone;
    private String email;
    private Long productId;
    private BigDecimal bidAmount;

}
