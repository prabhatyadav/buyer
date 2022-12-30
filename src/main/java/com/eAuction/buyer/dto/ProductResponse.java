package com.eAuction.buyer.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ProductResponse {
    private long id;
    private String name;
    private LocalDateTime bidEndDate;
    private LocalDateTime bidStartDate;
    private Boolean isDeleted;
    private double startingPrice;

}
