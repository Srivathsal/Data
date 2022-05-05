/*
package com.intuit.accountant.services.dcm.resources.helpers;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.core.HttpHeaders;
import java.util.ArrayList;
import java.util.List;

@Component
public class HttpHeaderReader {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public String getRequestHeader(HttpHeaders headers, String name) {
		if (StringUtils.isEmpty(name))
			throw new InternalServerErrorException("Attempted to read an HttpHeader, however, the header name was not provided.");
		if (headers == null) {
			logger.warn("HttpHeader " + name + " is not available within the current context.");
			logger.debug("Returned null value for header name " + name);
			return null;
		}

		List<String> header = headers.getRequestHeader(name);
		if (header == null || header.size() == 0) {
			logger.warn("HttpHeader " + name + " is not available within the current context.");
			logger.debug("Returned null value for header name " + name);
			return null;
		}

		String headerValue = getRequestHeaders(headers, name).get(0);
		logger.debug("Returned " + headerValue + " for header name " + name);
		return headerValue;
	}

	public List<String> getRequestHeaders(HttpHeaders headers, String name) {
		if (StringUtils.isEmpty(name))
			throw new InternalServerErrorException("Attempted to read an HttpHeader, however, the header name was not provided.");
		if (headers == null) {
			logger.warn("HttpHeader " + name + " is not available within the current context.");
			logger.debug("Returned empty list for header name " + name);
			return new ArrayList<String>();
		}

		List<String> header = headers.getRequestHeader(name);
		if (header == null || header.size() == 0) {			
			logger.warn("HttpHeader " + name + " is not available within the current context.");
			logger.debug("Returned empty list for header name " + name);
			return new ArrayList<String>();
		}
		return header;
	}
}
*/
