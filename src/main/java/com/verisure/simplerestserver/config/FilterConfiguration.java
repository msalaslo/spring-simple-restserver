package com.verisure.simplerestserver.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.verisure.simplerestserver.api.filter.RequestResponseLoggingFilter;

@Configuration
public class FilterConfiguration {

	@Bean
	public FilterRegistrationBean<RequestResponseLoggingFilter> loggingFilter() {
		FilterRegistrationBean<RequestResponseLoggingFilter> filterRegBean = new FilterRegistrationBean<>();
		filterRegBean.setFilter(new RequestResponseLoggingFilter());
		filterRegBean.addUrlPatterns("/*");
		filterRegBean.setName("RequestResponseLoggingFilter");
		return filterRegBean;
	}
}