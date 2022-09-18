package com.eAuction.buyer.controller;

import com.eAuction.buyer.model.ProductBid;
import org.springframework.web.bind.annotation.*;

@RestController
public class BidController {

    @RequestMapping(method = RequestMethod.POST , value="/place-bid" )
    public void  placeBid(@RequestBody ProductBid productBid){

    }

    @RequestMapping(method = RequestMethod.PUT , value="/update-bid/{productId}/{buyerEmailId}/{newBidAmount}" )
    public void updateBid(@PathVariable("productId") String productId ,
                          @PathVariable("buyerEmailId") String buyerEmailId ,
                          @PathVariable("newBidAmount") double newBidAmount ){

    }
     @GetMapping("/demo")
    public String demo(){
        return "Hello This is the  ";
     }
}
