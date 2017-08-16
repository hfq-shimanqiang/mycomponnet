package com.huifenqi.jedi.spring;

import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.env.Environment;

public class JediApplicationListener implements ApplicationListener {
    Environment environment;
    ApplicationContext applicationContext;

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        System.out.println(22222 + event.getClass().getName());

        if (event instanceof ApplicationStartedEvent) {

        }
        if (event instanceof ApplicationEnvironmentPreparedEvent) {
            environment = ((ApplicationEnvironmentPreparedEvent) event).getEnvironment();
            System.out.println(environment.getActiveProfiles()[0] + "=================");
        }

        if (event instanceof ApplicationPreparedEvent) {
            applicationContext = ((ApplicationPreparedEvent) event).getApplicationContext();
        }
        if (event instanceof ContextRefreshedEvent) {

        }
        if (event instanceof ApplicationReadyEvent) {

        }
        if (event instanceof ContextClosedEvent) {
            System.out.println("张总");
        }
    }
}
