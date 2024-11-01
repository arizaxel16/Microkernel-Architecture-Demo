package com.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.core.plugin.PluginManager;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/plugins")
public class PluginController {

    @Autowired
    private PluginManager pluginManager;

    @GetMapping
    public List<Map<String, Object>> getPlugins() {
        return pluginManager.getPluginsStatus();
    }

    @PostMapping("/enable")
    public String enablePlugin(@RequestParam String name) {
        return pluginManager.enablePlugin(name);
    }

    @PostMapping("/disable")
    public String disablePlugin(@RequestParam String name) {
        pluginManager.disablePlugin(name);
        return "Plugin " + name + " disabled.";
    }
}
