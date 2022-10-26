package com.eAuction.buyer.exception;

public class AlreadyExistingBidException extends  RuntimeException{
    private String message;

     public AlreadyExistingBidException(){
         super();
     }

     public AlreadyExistingBidException(String message){
         super(message);
     }

}
