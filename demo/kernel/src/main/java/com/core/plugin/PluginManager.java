package com.core.plugin;

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
    private final Map<String, Plugin> loadedPlugins = new HashMap<>();

    @Value("${plugins.directory:plugins}")
    private String pluginsDirectory;

    @PostConstruct
    public void init() {
        loadPlugins();
    }

    private void loadPlugins() {
        File pluginsDir = new File(pluginsDirectory);
        if (!pluginsDir.exists()) {
            pluginsDir.mkdirs();
            return;
        }

        File[] jarFiles = pluginsDir.listFiles((dir, name) -> name.endsWith(".jar"));
        if (jarFiles == null)
            return;

        for (File jar : jarFiles) {
            try {
                loadPlugin(jar);
            } catch (Exception e) {
                System.err.println("Failed to load plugin: " + jar.getName());
                e.printStackTrace();
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
                            Plugin plugin = (Plugin) clazz.getDeclaredConstructor().newInstance();
                            registerPlugin(plugin);
                        }
                    } catch (Exception ignored) {
                    }
                }
            });
        }
    }

    public void registerPlugin(Plugin plugin) {
        plugin.init();
        loadedPlugins.put(plugin.getName(), plugin);
        plugin.start();
    }

    public void unregisterPlugin(String pluginName) {
        Plugin plugin = loadedPlugins.get(pluginName);
        if (plugin != null) {
            plugin.stop();
            loadedPlugins.remove(pluginName);
        }
    }

    @Bean
    public Map<String, Plugin> getLoadedPlugins() {
        return Collections.unmodifiableMap(loadedPlugins);
    }
}