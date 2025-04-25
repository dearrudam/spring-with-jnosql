package com.github.dearrudam.springwithjnosqlmongodb;

import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextStoppedEvent;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.EnumerablePropertySource;

public class WeldSpringBridgeApplicationListener implements ApplicationListener<ApplicationEvent> {

    private final static Logger logger = LoggerFactory.getLogger(WeldSpringBridgeApplicationListener.class);
    private static SeContainer weldContainer;

    public static SeContainer getWeldContainer() {
        return weldContainer;
    }

    public static void startWeldContainer() {
        destroyWeldContainer();
        weldContainer = SeContainerInitializer.newInstance().initialize();
        logger.info("Starting Weld container");
    }

    public static void destroyWeldContainer() {
        logger.info("Destroying Weld containerB");
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof ApplicationEnvironmentPreparedEvent) {
            loadJNoSQLSettingsFromApplicationProperties(((ApplicationEnvironmentPreparedEvent) event).getEnvironment());
            startWeldContainer();
        }
        if (event instanceof ContextStoppedEvent) {
            destroyWeldContainer();
        }
    }

    private static void loadJNoSQLSettingsFromApplicationProperties(ConfigurableEnvironment env) {
        env.getPropertySources()
                .stream()
                .filter(propertySource -> propertySource instanceof EnumerablePropertySource)
                .map(EnumerablePropertySource.class::cast)
                .filter(propertySource -> propertySource instanceof EnumerablePropertySource)
                .map(EnumerablePropertySource.class::cast)
                .forEach(propertySource -> {
                    for (String key : propertySource.getPropertyNames()) {
                        if (key.startsWith("jnosql.")) {
                            System.setProperty(key, propertySource.getProperty(key).toString());
                        }
                    }
                });
    }
}