package com.eAuction.buyer.service;

import com.eAuction.buyer.dto.ProductBidDto;
import com.eAuction.buyer.exception.AlreadyExistingBidException;
import com.eAuction.buyer.exception.InvalidProductBidDetailException;
import com.eAuction.buyer.model.ProductBid;
import com.eAuction.buyer.repository.ProductBidRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BidService {

    @Autowired
    private ProductBidRepository productBidRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ProductBid getProductBid(String email, Long productId) {
        return productBidRepository.findByEmailAndProductId(email, productId);
    }

    public Boolean isBidAlreadyPlacedByBidder(String email, Long productId) {
        if (this.getProductBid(email, productId) != null) {
            throw new AlreadyExistingBidException("Already Bid is done for ProductId : " + productId + " By User : " + email);
        } else {
            return false;
        }
    }

    public ProductBid placeBidForProductDto(ProductBidDto productBidDto) {
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

    public List<ProductBidDto> getAllProductBid(Long productId) {
        List<ProductBidDto> productBidDtoList = new ArrayList<>(0);
        List<ProductBid> productBids = productBidRepository.findByProductId(productId);

        if (productBids == null || productBids.isEmpty()) {
            return productBidDtoList;
        } else {
            productBids.forEach((productBid) -> log.info(productBid.toString()));
            productBidDtoList = productBids.stream().map((productBid) -> modelMapper.map(productBid, ProductBidDto.class)).collect(Collectors.toList());

        }
        return productBidDtoList;
    }
}
