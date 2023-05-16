package com.elibrary.group4.config.interceptor;

import com.elibrary.group4.interceptor.Interceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfiguration implements WebMvcConfigurer {
    @Autowired
    Interceptor interceptor;
    public void addInterceptor(InterceptorRegistry registry){
        registry.addInterceptor(interceptor);
    }
}
