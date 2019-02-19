package com.verisure.integration.osbmockrest.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * Filter to fill the MDC with the token values in the headers if the MDC is not already filled.
 *
 * @since 1.2.1
 * @author Juan Malpica Romo [juan.malpica@securitasdirect.es]
 */
@Component
@Slf4j
public class MDCFilter implements Filter {

    /** The installation number header key. */
    @Value("${http.headers.installationNumber}")
    private String installationHeader;

    /** Installation number MDC key. */
    @Value("${logging.mdc.installationNumber}")
    protected String installationMDCKey;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        LOGGER.debug("MDCFilter init() method called"); // Do nothing else.
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        LOGGER.debug("MDCFilter doFilter() method called");
        // Ignore non HTTP requests
        if (request instanceof HttpServletRequest) {
            // Get the installation number from the header.
            String installation = ((HttpServletRequest) request).getHeader(installationHeader);
            MDC.put(installationMDCKey, installation);     
        }
        try {
            chain.doFilter(request, response);
        } finally {
            MDC.clear();
        }
    }

    @Override
    public void destroy() {
        LOGGER.debug("MDCFilter destroy() method called"); // Do nothing else.
    }
}

