package com.plugins.metrics;

import com.core.plugin.Plugin;
import org.springframework.stereotype.Component;

@Component
public class MetricsPlugin implements Plugin {
    boolean running = false;

    @Override
    public String getName() {
        return "metrics-plugin";
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
