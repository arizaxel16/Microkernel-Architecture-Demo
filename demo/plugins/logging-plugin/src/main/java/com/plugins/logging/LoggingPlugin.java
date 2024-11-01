package com.plugins.logging;

import com.core.plugin.Plugin;
import org.springframework.stereotype.Component;

@Component
public class LoggingPlugin implements Plugin {
    private boolean running = false;

    @Override
    public String getName() {
        return "logging-plugin";
    }

    @Override
    public void init() {
        System.out.println("Initializing Logging Plugin");
    }

    @Override
    public void start() {
        running = true;
        System.out.println("Logging Plugin Started");
    }

    @Override
    public void stop() {
        running = false;
        System.out.println("Logging Plugin Stopped");
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }
}