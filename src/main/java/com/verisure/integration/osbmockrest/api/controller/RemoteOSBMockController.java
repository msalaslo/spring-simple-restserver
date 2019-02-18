package com.verisure.integration.osbmockrest.api.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.verisure.integration.osbmockrest.api.dto.ConfigurationChangeResponseDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * Controller used as OSB mock to test the remote invocation.
 *
 * @since 3.0.0
 * @author FaaS [faas@securitasdirect.es]
 */
@Slf4j
@RestController
@RequestMapping("/mock/osb/device/configuration/change")
@Api(value = "Remote application demo")
public class RemoteOSBMockController {

	@PostMapping(consumes = "application/json")
	public void postConfigurationChangeResponse(@RequestHeader HttpHeaders headers, @RequestBody String body) {
		LOGGER.debug("OSB Mock configuration change response received, HEADERS:" + headers);
		LOGGER.debug("OSB Mock configuration change response received, BODY:" + body);
	}

	@PostMapping(produces = "application/json", value = "/withdto")
	@ResponseBody
	@ApiOperation(value = "view the list of ALL application items", response = ConfigurationChangeResponseDTO.class)
	public void postConfigurationChangeResponse(@RequestHeader HttpHeaders headers,
			@RequestBody ConfigurationChangeResponseDTO configurationChange) {
		LOGGER.debug("OSB Mock configuration change response received, HEADERS:" + headers);
		LOGGER.debug("OSB Mock configuration change response received:" + configurationChange);
	}
}
