package com.telemetry.open.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.SpanContext;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class AppConfig {

	@Bean
	public RestTemplate restTemplate() {
		SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
		factory.setConnectTimeout(5000);
		factory.setReadTimeout(10000);

		RestTemplate restTemplate = new RestTemplate(factory);

		List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
		interceptors.add((request, body, execution) -> {
			Span currentSpan = Span.current();
			if (currentSpan != null && currentSpan.getSpanContext().isValid()) {
				SpanContext context = currentSpan.getSpanContext();
				String traceParent = String.format("00-%s-%s-01", context.getTraceId(), context.getSpanId());
				request.getHeaders().add("traceparent", traceParent);
			}
			return execution.execute(request, body);
		});

		restTemplate.setInterceptors(interceptors);

		return restTemplate;
	}
}