package com.core.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PluginService {
    private final Map<String, LocalDateTime> pluginHeartbeatMap = new HashMap<>();

    // Timeout after which a plugin is considered disconnected
    private static final long TIMEOUT_SECONDS = 30;

    // Update plugin heartbeat timestamp
    public void updateHeartbeat(String pluginId) {
        pluginHeartbeatMap.put(pluginId, LocalDateTime.now());
    }

    // Get list of connected plugins
    public List<String> getConnectedPlugins() {
        LocalDateTime now = LocalDateTime.now();
        // Remove plugins whose heartbeat is older than the timeout
        pluginHeartbeatMap.entrySet().removeIf(entry -> 
            entry.getValue().isBefore(now.minusSeconds(TIMEOUT_SECONDS)));
        
        return pluginHeartbeatMap.keySet().stream().collect(Collectors.toList());
    }
}
