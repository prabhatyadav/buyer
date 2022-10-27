package com.eAuction.buyer.controller;

import com.eAuction.buyer.dto.ProductBidDto;
import com.eAuction.buyer.exception.AlreadyExistingBidException;
import com.eAuction.buyer.exception.InvalidProductDetailException;
import com.eAuction.buyer.model.ProductBid;
import com.eAuction.buyer.service.BidService;
import com.eAuction.buyer.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/buyer")
public class BidController {

    @Autowired
    private ProductService productService;

    @Autowired
    private BidService bidService;

    @RequestMapping(method = RequestMethod.POST, value = "/place-bid")
    public void placeBid(@RequestBody ProductBidDto productBidDto) {

        if(productBidDto !=null){
            Long productId = productBidDto.getProductId();
                     if(productService.isProductIdValid(productId)){
                         String email = productBidDto.getEmail();
                                if(!bidService.isBidAlreadyPlacedByBidder(email,productId)){
                                    bidService.placeBidForProduct(productBidDto);
                                }else{
                                    throw new AlreadyExistingBidException("Bid is already raised for this product by user");
                                }
             }



            // check the user already bid for the product or not


            //if all okay then place the bid



        }else{

        }

    }

    @RequestMapping(method = RequestMethod.PUT, value = "/update-bid/{productId}/{buyerEmailId}/{newBidAmount}")
    public void updateBid(@PathVariable("productId") String productId,
                          @PathVariable("buyerEmailId") String buyerEmailId,
                          @PathVariable("newBidAmount") double newBidAmount) {


        // get the bid- entry form the productbid table and update that with  new amount and updated date.

    }

    @GetMapping("/demo")
    public String demo() {
        return "Hello This is the  ";
    }
}
