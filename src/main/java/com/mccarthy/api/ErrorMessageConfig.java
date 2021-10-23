package com.mccarthy.api;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
public class ErrorMessageConfig {
    @Bean
    public MessageSource errorMessageSource() {
        ResourceBundleMessageSource source = new ResourceBundleMessageSource();
        source.setBasenames("errors");
        source.setDefaultEncoding("UTF-8");
        source.setUseCodeAsDefaultMessage(false);
        return source;
    }
}
