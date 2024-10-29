package com.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.core.service.PluginService;

import java.util.List;

@RestController
@RequestMapping("/api/plugins")
public class PluginController {

    @Autowired
    private PluginService pluginService;

    @PostMapping("/heartbeat")
    public void receiveHeartbeat(@RequestParam("pluginId") String pluginId) {
        System.out.println("Received heartbeat from: " + pluginId);
        // Handle heartbeat logic
        pluginService.updateHeartbeat(pluginId);
    }

    // Endpoint to get the list of connected plugins
    @GetMapping("/connected")
    public List<String> getConnectedPlugins() {
        return pluginService.getConnectedPlugins();
    }
}
