package com.eAuction.buyer.controller;

import com.eAuction.buyer.dto.ProductBidDto;
import com.eAuction.buyer.dto.ProductBidDtoTest;
import com.eAuction.buyer.exception.InvalidProductBidDetailException;
import com.eAuction.buyer.model.ProductBid;
import com.eAuction.buyer.service.BidService;
import com.eAuction.buyer.service.ProductService;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class BidController {

    @Autowired
    private ProductService productService;

    @Autowired
    private BidService bidService;

    @RequestMapping(method = RequestMethod.POST, value = "/place-bid")
    public ResponseEntity<ProductBid> placeBid(@RequestBody ProductBidDto productBidDto) {
        HttpHeaders respHeaders = new HttpHeaders();
        respHeaders.add("Access-Control-Allow-Origin", "*");
        respHeaders.add("Access-Control-Allow-Credentials", "true");
        respHeaders.add("Access-Control-Allow-Methods", "GET,HEAD,OPTIONS,POST,PUT");
        respHeaders.add("Access-Control-Allow-Headers", "Access-Control-Allow-Headers, Origin,Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");
        log.info("productBidDto : " + productBidDto);

        if (productBidDto != null) {
            Long productId = productBidDto.getProductId();
            double productBidAmount = productBidDto.getBidAmount().doubleValue();
            log.info("productId : " + productId);
            if (productService.isProductBidIsValid(productId, productBidAmount)) {
                String email = productBidDto.getEmail();
                if (!bidService.isBidAlreadyPlacedByBidder(email, productId)) {
                    ProductBid newProductBid = bidService.placeBidForProductDto(productBidDto);
                    return new ResponseEntity<ProductBid>(newProductBid, respHeaders, HttpStatus.OK);
                }

            }
        }
        return new ResponseEntity<ProductBid>(null, respHeaders, HttpStatus.NOT_ACCEPTABLE);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/bids/{productId}")
    public ResponseEntity<List<ProductBidDto>> getAllBid(@PathVariable @NotNull Long productId) {
        List<ProductBidDto> productBids = null;
        HttpHeaders respHeaders = new HttpHeaders();
        respHeaders.add("Access-Control-Allow-Origin", "*");
        respHeaders.add("Access-Control-Allow-Credentials", "true");
        respHeaders.add("Access-Control-Allow-Methods", "GET,HEAD,OPTIONS,POST,PUT");
        respHeaders.add("Access-Control-Allow-Headers", "Access-Control-Allow-Headers, Origin,Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");
        if (productId != null) {
            productBids = bidService.getAllProductBid(productId);
        }
        return ResponseEntity.ok().headers(respHeaders).body(productBids);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/update-bid/{productId}/{buyerEmailId}/{newBidAmount}")
    public ResponseEntity<ProductBid> updateBid(@PathVariable("productId") @NotNull Long productId,
                                                @PathVariable("buyerEmailId") @NotNull String buyerEmailId,
                                                @PathVariable("newBidAmount") @NotNull BigDecimal newBidAmount) {

        HttpHeaders respHeaders = new HttpHeaders();
        respHeaders.add("Access-Control-Allow-Origin", "*");

        //1. get the bid-entry form the product bid table
        ProductBid foundProductBid = bidService.getProductBid(buyerEmailId, productId);

        //2. and update that with  new amount and updated date.
        if (foundProductBid != null) {
            foundProductBid.setBidAmount(newBidAmount);
            ProductBid updatedProductBid = bidService.placeBidForProduct(foundProductBid);
            return new ResponseEntity(updatedProductBid, respHeaders, HttpStatus.OK);
        } else {
            throw new InvalidProductBidDetailException(" No Bid is placed for the Product code : " + productId + " By user : " + buyerEmailId);
        }


    }

    @GetMapping("/demo")
    public String demo() {
        return "Hello This is the  ";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/place-bid-test")
    public ResponseEntity<ProductBid> placeBidTest(@RequestBody ProductBidDtoTest productBidDtoTest) {
        HttpHeaders respHeaders = new HttpHeaders();
        respHeaders.add("Access-Control-Allow-Origin", "*");
        respHeaders.add("Access-Control-Allow-Credentials", "true");
        respHeaders.add("Access-Control-Allow-Methods", "GET,HEAD,OPTIONS,POST,PUT");
        respHeaders.add("Access-Control-Allow-Headers", "Access-Control-Allow-Headers, Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers");
        log.info("productBidDtoTest : ");


        return new ResponseEntity<ProductBid>(new ProductBid(), respHeaders, HttpStatus.OK);
    }
}
