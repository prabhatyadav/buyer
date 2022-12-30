package com.eAuction.buyer.feignclient;

import com.eAuction.buyer.dto.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="seller-feign-client", url="http://localhost:5000/e-auction/api/v1/seller")
public interface SellerFeignClient {

    @GetMapping("/product/{productId}")
    ResponseEntity<ProductResponse> getProductByProductId(@PathVariable Long productId);
}
