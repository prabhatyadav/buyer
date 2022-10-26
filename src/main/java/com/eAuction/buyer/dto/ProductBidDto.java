package com.eAuction.buyer.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@NoArgsConstructor
public class ProductBidDto {
    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String state;
    private String pin;
    private String phone;
    private String email;
    private Long productId;
    private BigDecimal bidAmount;
}
