package com.eAuction.buyer.repository;

import com.eAuction.buyer.model.ProductBid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductBidRepository  extends JpaRepository<ProductBid, Long> {

    public  List<ProductBid> findByProductId(Long productId) ;


    public ProductBid findByEmailAndProductId(String email, Long productId);

}
