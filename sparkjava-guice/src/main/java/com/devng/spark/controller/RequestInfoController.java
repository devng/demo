package com.devng.spark.controller;

import static com.devng.spark.dto.JsonUtil.*;
import static java.lang.String.*;
import static spark.Spark.*;

import java.lang.String;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * Shows more advanced integration with Guice Servlet (https://github.com/google/guice/wiki/Servlets) by showing basic
 * information about the incoming request and using two different techniques:
 * - by injecting a Guice provider with a REQUEST scope
 * - by using the Spak request object with is passed as a parameter
 */
@Singleton
public class RequestInfoController implements SparkController {

	private static final String JSON_TEMPLATE = "{\"guiceRequest\": %s,\n\"sparkRequest\": %s}";

	// Example of injecting a Guice provider using the REQUEST servlet scope, this is defined in the com.google.inject.servlet.ServletModule
	private final Provider<HttpServletRequest> guiceRequestProvider;

	@Inject
	RequestInfoController(final Provider<HttpServletRequest> guiceRequestProvider) {
		this.guiceRequestProvider = guiceRequestProvider;
	}

	@Override
	public void init() {
		get("/reqinfo", (req, res) -> format(JSON_TEMPLATE, reqInfoJson(guiceRequestProvider.get()), reqInfoJson(req.raw())));
	}

	private static String reqInfoJson(final HttpServletRequest req) {
		final int hashCode;
		if (req instanceof HttpServletRequestWrapper) {
			// Make sure we ware not confused by any wrapper hashCodes, it is the same Request object underneath
			hashCode = System.identityHashCode(((HttpServletRequestWrapper) req).getRequest());
		} else {
			hashCode = System.identityHashCode(req);
		}
		final Map<String, String> map = new LinkedHashMap<>(); // we want a sorted output
		map.put("Java identityHashCode", valueOf(hashCode));
		map.put("URL", valueOf(req.getRequestURL()));
		map.put("Remote Address", req.getRemoteAddr());
		final Enumeration<String> headerNames = req.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			final String headerName = headerNames.nextElement();
			final String headerValue = req.getHeader(headerName);
			map.put("Header " + headerName, headerValue);
		}
		return toJson(map);
	}
}
