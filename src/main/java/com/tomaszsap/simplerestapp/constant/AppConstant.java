package com.tomaszsap.simplerestapp.constant;

import com.tomaszsap.simplerestapp.model.RequestCounterFilter;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

@Configuration
public class AppConstant {
    @Autowired
    private RequestCounterFilter requestCounterFilter;

    @Bean
    public FilterRegistrationBean<RequestCounterFilter> loggingFilter() {
        FilterRegistrationBean<RequestCounterFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(requestCounterFilter);
        registrationBean.addUrlPatterns("/*");

        return registrationBean;
    }

    public static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for (PropertyDescriptor pd : pds) {
            var srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }
}
