package com.zerowastehub.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ImageConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/allImage/**")
                .addResourceLocations("file:///C:/Users/santa/Downloads/zerowastehub-backend/zerowastehub-backend/allImage/");
    }
}
