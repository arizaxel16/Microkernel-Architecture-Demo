package com.core.controller;

import com.core.plugin.Plugin;
import com.core.plugin.PluginManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/plugins")
public class PluginController {
    @Autowired
    private PluginManager pluginManager;

    @GetMapping
    public Map<String, Plugin> getLoadedPlugins() {
        return pluginManager.getLoadedPlugins();
    }

    @PostMapping("/{pluginName}/unload")
    public void unloadPlugin(@PathVariable String pluginName) {
        pluginManager.unregisterPlugin(pluginName);
    }
}