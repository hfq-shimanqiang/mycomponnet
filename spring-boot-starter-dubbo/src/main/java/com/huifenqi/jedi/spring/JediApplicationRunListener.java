package com.huifenqi.jedi.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

public class JediApplicationRunListener implements SpringApplicationRunListener {
    private final SpringApplication application;
    private final String[] args;

    public JediApplicationRunListener(SpringApplication application, String[] args) {
        this.application = application;
        this.args = args;
    }

    @Override
    public void starting() {
        System.out.println(3333);
    }

    @Override
    public void environmentPrepared(ConfigurableEnvironment environment) {
        System.out.println(4444);
    }

    @Override
    public void contextPrepared(ConfigurableApplicationContext context) {
        System.out.println(5555);
    }

    @Override
    public void contextLoaded(ConfigurableApplicationContext context) {
        System.out.println(6666);
    }

    @Override
    public void finished(ConfigurableApplicationContext context, Throwable exception) {
        System.out.println(7777);
    }
}
