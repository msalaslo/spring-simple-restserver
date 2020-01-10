package com.verisure.simplerestserver.service;

import org.springframework.web.context.request.async.DeferredResult;

import com.verisure.simplerestserver.api.dto.ConfigurationChangeResponseDTO;

public interface DemoServiceAsync {
	public DeferredResult<ConfigurationChangeResponseDTO> findById(String id);
	public DeferredResult<ConfigurationChangeResponseDTO> findById(String id, long wait);
}
