package com.core.plugin;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PluginManager {
    private final PluginLoader pluginLoader;
    private final List<Plugin> plugins = new ArrayList<>();

    public PluginManager(@Value("${plugins.directory}") String pluginsDirectory, ApplicationContext context) throws Exception {
        pluginLoader = new PluginLoader();
        pluginLoader.loadPlugins(pluginsDirectory);

        // Add plugins loaded from JAR files
        plugins.addAll(pluginLoader.getLoadedPlugins());

        // Add project plugins loaded by Spring
        Map<String, Plugin> projectPlugins = context.getBeansOfType(Plugin.class);
        plugins.addAll(projectPlugins.values());

        System.out.println("Project plugins loaded: " + plugins);
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
