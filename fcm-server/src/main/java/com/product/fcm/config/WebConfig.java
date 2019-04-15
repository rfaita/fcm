package com.product.fcm.config;

import com.product.fcm.server.interceptor.TenantOrganizationInteceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private TenantOrganizationInteceptor tenantOrganizationInteceptor;

    @Autowired
    public WebConfig(TenantOrganizationInteceptor tenantOrganizationInteceptor) {
        this.tenantOrganizationInteceptor = tenantOrganizationInteceptor;
    }

    @Bean
    @Autowired
    public LocalValidatorFactoryBean validator(MessageSource messageSource) {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource);
        return bean;
    }

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tenantOrganizationInteceptor);
    }
}