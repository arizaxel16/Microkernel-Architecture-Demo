package com.core.plugin;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class PluginManager {
    private final PluginLoader pluginLoader = new PluginLoader();
    private final ApplicationContext context;
    private final List<Plugin> plugins = new ArrayList<>();

    private String pluginsDirectory = "demo/kernel/src/main/java/com/core/plugins";  // Path to .jar plugin files

    @PostConstruct
    public void init() {
        try {
            pluginLoader.loadPlugins(pluginsDirectory);
            plugins.addAll(pluginLoader.getLoadedPlugins());
            plugins.addAll(context.getBeansOfType(Plugin.class).values());
            System.out.println("Project plugins loaded: " + plugins);
        } catch (Exception e) {
            System.err.println("Error loading plugins: " + e.getMessage());
        }
    }

    public String enablePlugin(String name) {
        for (Plugin plugin : plugins) {
            if (plugin.getName().equals(name)) {
                plugin.init();
                System.out.println("Enabled plugin: " + plugin.getName());
                return plugin.getName();
            }
        }
        System.out.println("Plugin not found: " + name);
        return "Plugin not found (404)";
    }

    public void disablePlugin(String name) {
        for (Plugin plugin : plugins) {
            if (plugin.getName().equals(name)) {
                plugin.stop();
            }
        }
    }

    public List<Map<String, Object>> getPluginsStatus() {
        List<Map<String, Object>> statusList = new ArrayList<>();
        for (Plugin plugin : plugins) {
            Map<String, Object> status = new HashMap<>();
            status.put("name", plugin.getName());
            status.put("enabled", plugin.isEnabled());
            statusList.add(status);
        }
        return statusList;
    }
}
