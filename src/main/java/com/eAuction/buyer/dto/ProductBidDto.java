package com.eAuction.buyer.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
@Data
@NoArgsConstructor
@ToString
public class ProductBidDto {
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

    @NotNull
    @Size(min=10, max=10 , message="mobile number should be 10 digit only number")
    @Pattern(regexp = "^(\\+91[\\-\\s]?)?[0]?(91)?[789]\\d{9}$")
    private String phone;  // phone should allow only number

    @NotNull
    private String email;

    private Long productId;
    private BigDecimal bidAmount;
}
