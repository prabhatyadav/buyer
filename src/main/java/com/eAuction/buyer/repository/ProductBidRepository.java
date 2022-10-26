package com.eAuction.buyer.repository;

import com.eAuction.buyer.model.ProductBid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductBidRepository  extends JpaRepository<ProductBid, Long> {

    public ProductBid findByEmailAndProductId(String email, Long productId);

}
