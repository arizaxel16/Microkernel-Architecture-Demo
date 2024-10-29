package com.plugin_template.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class HeartbeatService {

    @Value("${core.url}")
    private String coreUrl;

    @Value("${plugin.id}")
    private String pluginId;

    private final RestTemplate restTemplate;

    public HeartbeatService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Send heartbeat every 5 seconds
    @Scheduled(fixedRate = 5000)
    public void sendHeartbeat() {
        try {
            String url = coreUrl + "?pluginId=" + pluginId;
            restTemplate.postForObject(url, null, String.class);
            log.info("Heartbeat sent from plugin: " + pluginId);
        } catch (Exception e) {
            log.error("Failed to send heartbeat to core", e);
        }
    }
}
