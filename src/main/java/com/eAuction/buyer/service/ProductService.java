package com.eAuction.buyer.service;

import com.eAuction.buyer.dto.ProductBidDto;
import com.eAuction.buyer.dto.ProductResponse;
import com.eAuction.buyer.exception.InvalidProductBidDetailException;
import com.eAuction.buyer.exception.InvalidProductDetailException;
import com.eAuction.buyer.feignclient.SellerFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class ProductService {

    @Value("${product.api.url}")
    String productApiURL;

    @Autowired
    SellerFeignClient sellerFeignClient;

    public Boolean isProductBidIsValid(Long productId, Double productBidAmount) {
        String errorMessage = null;
        if (productId != null) {
            //1. check the productId present in database or  not.
            //RestTemplate
            //ResponseEntity<ProductResponse> responseEntity = restTemplate.exchange(productApiURL + "/" + productId, HttpMethod.GET, null, ProductResponse.class);

            //feign client
            ResponseEntity<ProductResponse> responseEntity = sellerFeignClient.getProductByProductId(productId);
            if (responseEntity == null || responseEntity.getBody() == null) {
                throw new InvalidProductDetailException("Product Not Found with Product Id : " + productId);
            } else {
                log.info("response : " + responseEntity.getBody());
                //2. product Bid place date should be in range of start date and end date of bid.- throw exception
                ProductResponse productResponse = responseEntity.getBody();

                if (productResponse.getBidEndDate().isBefore(LocalDateTime.now())) {
                    throw new InvalidProductDetailException("Product Bid not allowed as Bid End date  : " + productResponse.getBidEndDate() + " over");
                }
                if (productResponse.getBidStartDate().isAfter(LocalDateTime.now())) {
                    throw new InvalidProductDetailException("Product Bid not allowed as Bid Start date  : " + productResponse.getBidStartDate() + " Not Coming");
                }
                if (productResponse.getIsDeleted()) {
                    throw new InvalidProductDetailException("Product Bid not allowed as Product id  : " + productResponse.getId() + " Not allowed As Deleted");
                }
                if (productResponse.getStartingPrice() > productBidAmount) {
                    throw new InvalidProductBidDetailException("Product Bid amount can not be  zero or less than starting price : " + productResponse.getStartingPrice());
                }
            }

            return true;
        } else {
            errorMessage = "productId Null not allowed";
            throw new InvalidProductDetailException(errorMessage);
        }
    }
}
