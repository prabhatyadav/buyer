package com.eAuction.buyer.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "product_bid")
@NoArgsConstructor
public class ProductBid {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min=5, max=30, message="First name should be 5 to 30 character")
    private String firstName;

    @NotNull
    @Size(min=3, max=25 , message="Last name should be 3 to 25 character")
    private String lastName;
    private String address;
    private String city;
    private String state;
    private String pin;
    @Column(name = "phone", nullable = false)
    private String phone;
    @Column(name = "email", nullable = false)
    private String email;
    private Long productId;
    private BigDecimal bidAmount;

}
