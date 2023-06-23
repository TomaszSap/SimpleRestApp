package com.tomaszsap.simplerestapp.model;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

@Component
public class RequestCounterFilter implements Filter {

    private int requestCount = 0;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        requestCount++;
        chain.doFilter(request, response);
    }

    public int getRequestCount() {
        return requestCount;
    }
}