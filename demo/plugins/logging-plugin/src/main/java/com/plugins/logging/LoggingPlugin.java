package com.plugins.logging;

import com.core.plugin.Plugin;
import org.springframework.stereotype.Component;

@Component
public class LoggingPlugin implements Plugin {
    boolean running = false;

    @Override
    public String getName() {
        return "logging-plugin";
    }

    @Override
    public void init() {
        start();
    }

    @Override
    public void start() {
        running = true;
    }

    @Override
    public void stop() {
        running = false;
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }

    @Override
    public boolean isEnabled() {
        return running;
    }
}