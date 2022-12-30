package com.eAuction.buyer.service;

import com.eAuction.buyer.exception.AlreadyExistingBidException;
import com.eAuction.buyer.model.ProductBid;
import com.eAuction.buyer.repository.ProductBidRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;


@ExtendWith(SpringExtension.class)
public class BidServiceTest {

    @InjectMocks
    private BidService bidService;

    @Mock
    private ProductBidRepository productBidRepository;

    @Mock
    private ModelMapper modelMapper;

    static ProductBid expectedProductBid;
    static String email;
    static Long productId;

    @BeforeAll
    public static void getTestData() {
        expectedProductBid = new ProductBid();
        expectedProductBid.setProductId(1L);
        expectedProductBid.setEmail("email@gmail.com");
        expectedProductBid.setBidAmount(BigDecimal.TEN);

        email = "email@gmail.com";
        productId = Long.valueOf("1");


    }

    @Test
    @DisplayName("Get Product Bid When Bid Present")
    public void getProductBidTest_WithExistingRecord() {

        Mockito.when(productBidRepository.findByEmailAndProductId("email@gmail.com", Long.valueOf("1")))
                .thenReturn(expectedProductBid);
        ProductBid resultProductBid = bidService.getProductBid("email@gmail.com", Long.valueOf("1"));
        Assertions.assertEquals(expectedProductBid.getEmail(), resultProductBid.getEmail());

    }

    @Test
    @DisplayName("Get Product Bid When No Bid Present")
    public void getProductBidTest_WithNonExistingRecord() {
        Mockito.when(productBidRepository.findByEmailAndProductId("email@gmail.com", Long.valueOf("1")))
                .thenReturn(null);
        ProductBid resultProductBid = bidService.getProductBid("email@gmail.com", Long.valueOf("1"));
        Assertions.assertNull(resultProductBid);


    }

    @Test
    @DisplayName("Bid is not already PlacedBy Bidder")
    public void isBidAlreadyPlacedByBidderTest() {
        Mockito.when(bidService.getProductBid(email, productId)).thenReturn(null);
        boolean result = bidService.isBidAlreadyPlacedByBidder(email, productId);
        Assertions.assertFalse(result);
    }

    @Test
    @DisplayName("Bid is already PlacedBy Bidder")
    public void isBidAlreadyPlacedByBidderTest_AlreadyExistingBidException() {
         Mockito.when(bidService.getProductBid(email, productId)).thenReturn(expectedProductBid);
        AlreadyExistingBidException thrown = Assertions.assertThrows(AlreadyExistingBidException.class, () -> {
            bidService.isBidAlreadyPlacedByBidder(email, productId);
        });

        Assertions.assertEquals("Already Bid is done for ProductId : " + productId + " By User : " + email, thrown.getMessage());
    }


}
