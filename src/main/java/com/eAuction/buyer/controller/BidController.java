package com.eAuction.buyer.controller;

import com.eAuction.buyer.dto.ProductBidDto;
import com.eAuction.buyer.exception.InvalidProductBidDetailException;
import com.eAuction.buyer.model.ProductBid;
import com.eAuction.buyer.service.BidService;
import com.eAuction.buyer.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@RestController
@RequestMapping("/buyer")
public class BidController {

    @Autowired
    private ProductService productService;

    @Autowired
    private BidService bidService;

    @RequestMapping(method = RequestMethod.POST, value = "/place-bid")
    public ProductBid placeBid(@RequestBody @NotNull ProductBidDto productBidDto) {

        if (productBidDto != null) {
            Long productId = productBidDto.getProductId();
            if (productService.isProductIdValid(productId)) {
                String email = productBidDto.getEmail();
                if (!bidService.isBidAlreadyPlacedByBidder(email, productId)) {
                  return  bidService.placeBidForProduct(productBidDto);
                }

            }
        }
        return null;
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/update-bid/{productId}/{buyerEmailId}/{newBidAmount}")
    public ProductBid updateBid(@PathVariable("productId") @NotNull Long productId,
                          @PathVariable("buyerEmailId") @NotNull String buyerEmailId,
                          @PathVariable("newBidAmount") @NotNull BigDecimal newBidAmount) {


        //1. get the bid- entry form the productbid table
        ProductBid foundProductBid = bidService.getProductBid(buyerEmailId, productId);

        //2. and update that with  new amount and updated date.
         if(foundProductBid!=null){
             foundProductBid.setBidAmount(newBidAmount);
             ProductBid updatedProductBid = bidService.placeBidForProduct(foundProductBid);
             return updatedProductBid;
         }else{
             throw new InvalidProductBidDetailException(" No Bid is placed for the Product code : "+productId +" By user : "+buyerEmailId);
         }


    }

    @GetMapping("/demo")
    public String demo() {
        return "Hello This is the  ";
    }
}
