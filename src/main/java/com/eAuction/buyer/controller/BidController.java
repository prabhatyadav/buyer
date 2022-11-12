package com.eAuction.buyer.controller;

import com.eAuction.buyer.dto.ProductBidDto;
import com.eAuction.buyer.exception.InvalidProductBidDetailException;
import com.eAuction.buyer.model.ProductBid;
import com.eAuction.buyer.service.BidService;
import com.eAuction.buyer.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/e-auction/api/v1/buyer")
public class BidController {

    @Autowired
    private ProductService productService;

    @Autowired
    private BidService bidService;

    @RequestMapping(method = RequestMethod.POST, value = "/place-bid")
    public ResponseEntity<ProductBid> placeBid(@RequestBody @NotNull ProductBidDto productBidDto) {
        HttpHeaders respHeaders = new HttpHeaders();
        respHeaders.add("Access-Control-Allow-Origin", "*");

        if (productBidDto != null) {
            Long productId = productBidDto.getProductId();
            if (productService.isProductIdValid(productId)) {
                String email = productBidDto.getEmail();
                if (!bidService.isBidAlreadyPlacedByBidder(email, productId)) {
                    ProductBid newProductBid = bidService.placeBidForProduct(productBidDto);
                    return new ResponseEntity<ProductBid>(newProductBid, respHeaders,HttpStatus.OK);
                }

            }
        }
        return new ResponseEntity<ProductBid>(HttpStatus.NOT_ACCEPTABLE);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/bids/{productId}")
    public ResponseEntity<List<ProductBid>> placeBid(@PathVariable @NotNull Long productId) {
        List<ProductBid> productBids = null;
        HttpHeaders respHeaders = new HttpHeaders();
        respHeaders.add("Access-Control-Allow-Origin", "*");
        if (productId != null) {
            productBids = bidService.getAllProductBid(productId);
        }
        return ResponseEntity.ok().headers(respHeaders).body( productBids);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/update-bid/{productId}/{buyerEmailId}/{newBidAmount}")
    public ProductBid updateBid(@PathVariable("productId") @NotNull Long productId,
                                @PathVariable("buyerEmailId") @NotNull String buyerEmailId,
                                @PathVariable("newBidAmount") @NotNull BigDecimal newBidAmount) {

        HttpHeaders respHeaders = new HttpHeaders();
        respHeaders.add("Access-Control-Allow-Origin", "*");

        //1. get the bid-entry form the productbid table
        ProductBid foundProductBid = bidService.getProductBid(buyerEmailId, productId);

        //2. and update that with  new amount and updated date.
        if (foundProductBid != null) {
            foundProductBid.setBidAmount(newBidAmount);
            ProductBid updatedProductBid = bidService.placeBidForProduct(foundProductBid);
            return updatedProductBid;
        } else {
            throw new InvalidProductBidDetailException(" No Bid is placed for the Product code : " + productId + " By user : " + buyerEmailId);
        }


    }

    @GetMapping("/demo")
    public String demo() {
        return "Hello This is the  ";
    }
}
