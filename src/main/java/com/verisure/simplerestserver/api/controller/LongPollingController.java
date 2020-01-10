package com.verisure.simplerestserver.api.controller;

import java.util.concurrent.ForkJoinPool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import com.verisure.simplerestserver.api.dto.ConfigurationChangeResponseDTO;
import com.verisure.simplerestserver.service.DemoServiceAsync;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

/**
 * Controller used as Long Polling test
 *
 * @since 3.0.0
 * @author FaaS [faas@securitasdirect.es]
 */
@Slf4j
@RestController
@RequestMapping("/long-polling")
@Api(value = "Remote application demo")
public class LongPollingController {
	
	@Autowired
	DemoServiceAsync service;

	
	@GetMapping("/thread-deferredresult")
	public DeferredResult<ResponseEntity<ConfigurationChangeResponseDTO>> handleReqDefResult(String id, long wait) {
	    LOGGER.info("Received async-deferredresult request");
	    DeferredResult<ResponseEntity<ConfigurationChangeResponseDTO>> output = new DeferredResult<>();
	     
	    ForkJoinPool.commonPool().submit(() -> {
	    	LOGGER.info("Processing in separate thread");
	        try {
	            Thread.sleep(wait);
	        } catch (InterruptedException e) {
	        }
		    DeferredResult<ConfigurationChangeResponseDTO> deferredDto = service.findById(id);
	        LOGGER.info("DTO:" + deferredDto.getResult());
	        output.setResult(ResponseEntity.ok((ConfigurationChangeResponseDTO)deferredDto.getResult()));
	    });
	     
	    LOGGER.info("servlet thread freed");
	    return output;
	}
	
	@GetMapping("/async-deferredresult")
	public DeferredResult<ResponseEntity<ConfigurationChangeResponseDTO>> handleReqDefResultWithSyncService(String id, long wait) {
	    LOGGER.info("Received async-deferredresult request");
	    DeferredResult<ResponseEntity<ConfigurationChangeResponseDTO>> output = new DeferredResult<>();
	    DeferredResult<ConfigurationChangeResponseDTO> deferredDto = service.findById(id);
        LOGGER.info("DTO:" + deferredDto.getResult());
        output.setResult(ResponseEntity.ok((ConfigurationChangeResponseDTO)deferredDto.getResult()));
	    LOGGER.info("servlet thread freed");
	    return output;
	}
	
}
