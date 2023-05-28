package com.elibrary.group4.config.interceptor;

import com.elibrary.group4.interceptor.Interceptor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@NoArgsConstructor
public class InterceptorConfiguration implements WebMvcConfigurer {
    @Autowired
    public Interceptor interceptor;
    public void addInterceptor(InterceptorRegistry registry){
        registry.addInterceptor(interceptor);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor);
    }
}
