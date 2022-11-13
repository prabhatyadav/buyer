package com.eAuction.buyer.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
@Component
public class InterceptorConfig extends WebMvcConfigurationSupport {

    @Autowired
    RequestHeaderInterceptor requestHeaderInterceptor;

    @Autowired
    ResponseHeaderInterceptor responseHeaderInterceptor;
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);
        registry.addInterceptor(requestHeaderInterceptor);
        //registry.addInterceptor(responseHeaderInterceptor);
    }
}
