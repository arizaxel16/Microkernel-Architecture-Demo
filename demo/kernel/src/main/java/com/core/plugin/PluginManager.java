package com.core.plugin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;
import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.jar.JarFile;

@Component
@Configuration
public class PluginManager {
    private static final Logger logger = LoggerFactory.getLogger(PluginManager.class);
    private final Map<String, Plugin> loadedPlugins = new HashMap<>();

    @Value("${plugins.directory:plugins}")
    private String pluginsDirectory;

    @PostConstruct
    public void init() {
        logger.info("Initializing PluginManager...");
        logger.info("Plugins directory: {}", new File(pluginsDirectory).getAbsolutePath());
        loadPlugins();
        logLoadedPlugins();
    }

    private void loadPlugins() {
        File pluginsDir = new File(pluginsDirectory);
        if (!pluginsDir.exists()) {
            logger.info("Creating plugins directory at: {}", pluginsDir.getAbsolutePath());
            pluginsDir.mkdirs();
            return;
        }

        File[] jarFiles = pluginsDir.listFiles((dir, name) -> name.endsWith(".jar"));
        if (jarFiles == null) {
            logger.warn("No plugin JAR files found in directory: {}", pluginsDir.getAbsolutePath());
            return;
        }

        logger.info("Found {} potential plugin files", jarFiles.length);
        for (File jar : jarFiles) {
            try {
                logger.info("Attempting to load plugin from: {}", jar.getName());
                loadPlugin(jar);
            } catch (Exception e) {
                logger.error("Failed to load plugin: " + jar.getName(), e);
            }
        }
    }

    private void loadPlugin(File jarFile) throws Exception {
        URL[] urls = { jarFile.toURI().toURL() };
        try (URLClassLoader classLoader = new URLClassLoader(urls, getClass().getClassLoader());
                JarFile jar = new JarFile(jarFile)) {

            jar.entries().asIterator().forEachRemaining(entry -> {
                if (entry.getName().endsWith(".class")) {
                    try {
                        String className = entry.getName().replace('/', '.').replace(".class", "");
                        Class<?> clazz = classLoader.loadClass(className);

                        if (Plugin.class.isAssignableFrom(clazz) && !clazz.isInterface()) {
                            logger.info("Found plugin class: {}", className);
                            Plugin plugin = (Plugin) clazz.getDeclaredConstructor().newInstance();
                            registerPlugin(plugin);
                        }
                    } catch (Exception e) {
                        logger.debug("Skipping non-plugin class: {}", entry.getName());
                    }
                }
            });
        }
    }

    public void registerPlugin(Plugin plugin) {
        logger.info("Registering plugin: {} (version {})", plugin.getName(), plugin.getVersion());
        plugin.init();
        loadedPlugins.put(plugin.getName(), plugin);
        plugin.start();
        logger.info("Plugin {} successfully started", plugin.getName());
    }

    public void unregisterPlugin(String pluginName) {
        Plugin plugin = loadedPlugins.get(pluginName);
        if (plugin != null) {
            logger.info("Unregistering plugin: {}", pluginName);
            plugin.stop();
            loadedPlugins.remove(pluginName);
            logger.info("Plugin {} successfully unregistered", pluginName);
        } else {
            logger.warn("Attempted to unregister non-existent plugin: {}", pluginName);
        }
    }

    private void logLoadedPlugins() {
        if (loadedPlugins.isEmpty()) {
            logger.info("No plugins currently loaded");
        } else {
            logger.info("Currently loaded plugins:");
            loadedPlugins.forEach((name, plugin) -> 
                logger.info("- {} (version {})", name, plugin.getVersion()));
        }
    }

    @Bean
    public Map<String, Plugin> getLoadedPlugins() {
        return Collections.unmodifiableMap(loadedPlugins);
    }
}