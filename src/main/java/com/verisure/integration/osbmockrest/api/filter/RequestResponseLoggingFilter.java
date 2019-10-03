package com.verisure.integration.osbmockrest.api.filter;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class RequestResponseLoggingFilter extends OncePerRequestFilter {

	@Override
	public void doFilterInternal(HttpServletRequest request, HttpServletResponse res, FilterChain filterChain)
			throws ServletException, IOException {
		logRequest(request, getBody(request));
		filterChain.doFilter(request, res);
		LOGGER.info("Logging Response :{}", res.getContentType());
	}

	private void logRequest(HttpServletRequest request, byte[] body) throws IOException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("===========================request begin================================================");
			LOGGER.debug("URI         : {}", request.getRequestURI());
			LOGGER.debug("Method      : {}", request.getMethod());
			LOGGER.debug("Headers     : {}", getHeaders(request));
			LOGGER.debug("Request body: {}", new String(body, "UTF-8"));
			LOGGER.debug("==========================request end================================================");
		}
	}

	private void logResponse(HttpServletResponse response) throws IOException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("============================response begin==========================================");
			LOGGER.debug("Status code  : {}", response.getStatus());
			LOGGER.debug("Headers      : {}", getHeaders(response));
//			LOGGER.debug("Response body: {}", StreamUtils.copyToString(response.getBody(), Charset.defaultCharset()));
			LOGGER.debug("=======================response end=================================================");
		}
	}
	
//	private byte[] getBody(HttpServletResponse res) {
//		String body = "";
//			try {
//				return getBody(res.getReader());
//			} catch (IOException ex) {
//				// swallow silently -- can't get body, won't
//			}
//		return body.getBytes();
//	}

	private byte[] getBody(HttpServletRequest req) {
		String body = "";
		if (req.getMethod().equals("POST")) {
			try {
				return getBody(req.getReader());
			} catch (IOException ex) {
				// swallow silently -- can't get body, won't
			}
		}
		return body.getBytes();
	}

	private byte[] getBody(BufferedReader reader) {
		String body = "";
		StringBuilder sb = new StringBuilder();
		BufferedReader bufferedReader = null;

		try {
			bufferedReader = reader;
			char[] charBuffer = new char[128];
			int bytesRead;
			while ((bytesRead = bufferedReader.read(charBuffer)) != -1) {
				sb.append(charBuffer, 0, bytesRead);
			}
		} catch (IOException ex) {
			// swallow silently -- can't get body, won't
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException ex) {
					// swallow silently -- can't get body, won't
				}
			}
		}
		body = sb.toString();
		return body.getBytes();
	}

	private Map<String, String> getHeaders(HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		Enumeration<String> headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String key = (String) headerNames.nextElement();
			String value = request.getHeader(key);
			map.put(key, value);
		}

		return map;
	}

	private Map<String, String> getHeaders(HttpServletResponse response) {
		Map<String, String> map = new HashMap<String, String>();
		Collection<String> headerNames = response.getHeaderNames();
		for (Iterator<String> iterator = headerNames.iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			String value = response.getHeader(key);
			map.put(key, value);
		}
		return map;
	}
}
