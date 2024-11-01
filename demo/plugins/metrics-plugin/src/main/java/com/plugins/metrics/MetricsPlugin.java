package com.plugins.metrics;

import com.core.plugin.Plugin;
import org.springframework.stereotype.Component;

public class MetricsPlugin implements Plugin {
    private boolean running = false;

    @Override
    public String getName() {
        return "metrics-plugin";
    }

    @Override
    public void init() {
        System.out.println("Initializing metrics Plugin");
    }

    @Override
    public void start() {
        running = true;
        System.out.println("Metrics Plugin Started");
    }

    @Override
    public void stop() {
        running = false;
        System.out.println("Metrics Plugin Stopped");
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }
}
