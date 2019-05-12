package com.verisure.integration.osbmockrest.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO representing the error according to vnd.error specification.
 *
 * @since 1.0.0
 * @author FaaS [faas@securitasdirect.es]
 */
@Getter
@Setter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class HelloDTO extends BaseDTO {

    private String msg;
}