package com.eAuction.buyer.service;

import com.eAuction.buyer.exception.InvalidProductDetailException;
import org.springframework.stereotype.Service;

@Service
public class ProductService {


    public  Boolean isProductIdValid(Long productId) {
        String errorMessage =null;
        if (productId != null) {
            //1. check the productId present in database or  not.
            //2. product Bid place date should be in range of start date and end date of bid.- throw exception

            return true;
        } else {
            throw new InvalidProductDetailException(errorMessage);
        }
    }
}
