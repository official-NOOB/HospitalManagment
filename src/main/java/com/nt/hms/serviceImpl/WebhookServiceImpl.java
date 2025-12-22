package com.nt.hms.serviceImpl;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.nt.hms.service.IWebhookService;

@Service
public class WebhookServiceImpl implements IWebhookService{
	
	private final RestTemplate restTemplate;
	
	public WebhookServiceImpl(RestTemplate restTemplate) {
		this.restTemplate=restTemplate;
		
	}

	@Override
	public void sendWebhook(String url, Map<String, Object> payload) {
		restTemplate.postForObject(url, payload, String.class);
		
	}
	
}
