package com.core.plugin;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

@Slf4j
public class PluginLoader {
    private final List<Plugin> loadedPlugins = new ArrayList<>();

    public void loadPlugins(String pluginsDirectory) throws Exception {
        File dir = new File(pluginsDirectory);
        if (!dir.exists() || !dir.isDirectory()) {
            throw new IllegalArgumentException("Plugin directory not found: " + pluginsDirectory);
        } else {
            log.info("Directory Found: {}", pluginsDirectory);
        }

        File[] files = dir.listFiles((d, name) -> name.endsWith(".jar"));
        if (files == null || files.length == 0) {
            log.warn("No plugin JAR files found in the directory.");
            return;
        }

        for (File file : files) {
            log.info("Loading JAR: {}", file.getName());

            // Open the JAR file to read the manifest
            try (JarFile jarFile = new JarFile(file)) {
                Manifest manifest = jarFile.getManifest();
                if (manifest == null) {
                    log.warn("No manifest found in JAR: {}", file.getName());
                    continue;
                }

                // Get the Plugin-Class attribute from the manifest
                String pluginClassName = manifest.getMainAttributes().getValue("Plugin-Class");
                if (pluginClassName == null || pluginClassName.isEmpty()) {
                    log.warn("No Plugin-Class attribute in JAR: {}", file.getName());
                    continue;
                }

                log.info("Plugin class found in manifest: {}", pluginClassName);

                // Load the plugin class from the JAR
                try (URLClassLoader classLoader = new URLClassLoader(new URL[]{file.toURI().toURL()}, Plugin.class.getClassLoader())) {
                    Class<?> pluginClass = classLoader.loadClass(pluginClassName);
                    
                    // Check if it implements the Plugin interface
                    if (Plugin.class.isAssignableFrom(pluginClass)) {
                        Plugin plugin = (Plugin) pluginClass.getDeclaredConstructor().newInstance();
                        loadedPlugins.add(plugin);
                        log.info("Plugin loaded: {} (Version: {})", plugin.getName(), plugin.getVersion());
                    } else {
                        log.warn("Class {} does not implement Plugin interface.", pluginClassName);
                    }
                }
            } catch (Exception e) {
                log.error("Error loading plugin from {}: {}", file.getName(), e.getMessage());
            }
        }
    }

    public List<Plugin> getLoadedPlugins() {
        return loadedPlugins;
    }
}
