package com.nt.hms.service;

import java.util.Map;

public interface IWebhookService {
	
	public void sendWebhook(String url, Map<String, Object> payload);
}
