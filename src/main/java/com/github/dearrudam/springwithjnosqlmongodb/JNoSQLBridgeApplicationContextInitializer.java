package com.github.dearrudam.springwithjnosqlmongodb;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

public class JNoSQLBridgeApplicationContextInitializer implements
        ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        if (applicationContext.getBeanFactory() instanceof DefaultListableBeanFactory beanFactory) {
            beanFactory.setAutowireCandidateResolver(new WeldAutowireCandidateResolver());
        }
    }
}
