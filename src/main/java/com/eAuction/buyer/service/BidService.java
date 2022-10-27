package com.eAuction.buyer.service;

import com.eAuction.buyer.dto.ProductBidDto;
import com.eAuction.buyer.exception.AlreadyExistingBidException;
import com.eAuction.buyer.exception.InvalidProductBidDetailException;
import com.eAuction.buyer.model.ProductBid;
import com.eAuction.buyer.repository.ProductBidRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BidService {

    @Autowired
    private ProductBidRepository productBidRepository;

    @Autowired
    ModelMapper modelMapper;

    public ProductBid getProductBid(String email, Long productId) {
        ProductBid foundProductBid = productBidRepository.findByEmailAndProductId(email, productId);
        return foundProductBid;
    }

    public Boolean isBidAlreadyPlacedByBidder(String email, Long productId) {
        if (this.getProductBid(email, productId) != null) {
            throw new AlreadyExistingBidException("Already Bid is done for ProductId : " + productId + "By User :" + email);
        } else {
            return false;
        }
    }

    public ProductBid placeBidForProduct(ProductBidDto productBidDto) {
        if (productBidDto != null) {
            ProductBid productBid = modelMapper.map(productBidDto, ProductBid.class);
            ProductBid savedProductBid = productBidRepository.save(productBid);
            return savedProductBid;
        } else {
            throw new InvalidProductBidDetailException("Product Bid  is Null");
        }
    }

    public ProductBid placeBidForProduct(ProductBid productBid) {
        if (productBid != null) {
            ProductBid savedProductBid = productBidRepository.save(productBid);
            return savedProductBid;
        } else {
            throw new InvalidProductBidDetailException("ProductBid  is Null");
        }
    }
}
