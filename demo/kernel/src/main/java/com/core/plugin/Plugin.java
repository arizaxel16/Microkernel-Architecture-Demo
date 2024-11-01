package com.core.plugin;

public interface Plugin {
    String getName();
    void init();
    void start();
    void stop();
    String getVersion();
}