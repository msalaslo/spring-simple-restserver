package com.verisure.simplerestserver.service.impl;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

import com.verisure.simplerestserver.api.dto.ConfigurationChangeResponseDTO;
import com.verisure.simplerestserver.service.DemoServiceAsync;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DemoServiceAsyncImpl implements DemoServiceAsync {

	@Async
	public DeferredResult<ConfigurationChangeResponseDTO> findById(String id) {
		return findById(id, 0);
	}
	
	@Async
	public DeferredResult<ConfigurationChangeResponseDTO> findById(String id, long wait) {
		DeferredResult<ConfigurationChangeResponseDTO> output = new DeferredResult<ConfigurationChangeResponseDTO>();
		ConfigurationChangeResponseDTO dto = ConfigurationChangeResponseDTO.builder().itemCode(id).itemDescription(id+ "desc").build();
		LOGGER.info("dto:" + dto);
    	LOGGER.info("Processing in separate thread and waiting:" + wait + "miliseconds");
        try {
            Thread.sleep(wait);
        } catch (InterruptedException e) {
        }     
	    output.setResult(dto);
	    return output;
	}
}
