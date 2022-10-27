package com.eAuction.buyer.service;

import com.eAuction.buyer.dto.ProductResponse;
import com.eAuction.buyer.exception.InvalidProductDetailException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Service
@Slf4j
public class ProductService {

    @Autowired(required = true)
    RestTemplate restTemplate;

    @Value("${product.api.url}")
    String productApiURL;

    public Boolean isProductIdValid(Long productId) {
        String errorMessage = null;
        if (productId != null) {
            //1. check the productId present in database or  not.

            ResponseEntity<ProductResponse> responseEntity = restTemplate.exchange(productApiURL + "/" + productId, HttpMethod.GET, null, ProductResponse.class);

            if (responseEntity == null || responseEntity.getBody() == null) {
                throw new InvalidProductDetailException("Product Not Found with Product Id : " + productId);
            } else {
                log.info("response : " + responseEntity.getBody());
                //2. product Bid place date should be in range of start date and end date of bid.- throw exception
                ProductResponse productResponse = responseEntity.getBody();
                if (productResponse.getBidEndDate().isBefore(LocalDateTime.now())) {
                    throw new InvalidProductDetailException("Product Bid not allowed as Bid End date  : " + productResponse.getBidEndDate() + " over");
                }
            }

            return true;
        } else {
            errorMessage = "productId Null not allowed";
            throw new InvalidProductDetailException(errorMessage);
        }
    }
}
