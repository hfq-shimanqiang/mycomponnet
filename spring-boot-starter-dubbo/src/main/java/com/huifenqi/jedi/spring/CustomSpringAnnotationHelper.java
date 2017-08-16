package com.huifenqi.jedi.spring;

import com.huifenqi.jedi.api.Account;
import com.huifenqi.jedi.api.MyProxy;
import com.huifenqi.jedi.api.ServiceFactoryBean;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

//@Component
public class CustomSpringAnnotationHelper implements ApplicationContextAware, BeanDefinitionRegistryPostProcessor {

    ApplicationContext applicationContext;

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        RootBeanDefinition beanDefinition = new RootBeanDefinition();
        //GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(ServiceFactoryBean.class);
        beanDefinition.getPropertyValues().addPropertyValue("myProxy", new MyProxy());
        beanDefinition.getPropertyValues().addPropertyValue("serviceClass", Account.class);
        beanDefinition.setLazyInit(true);
        beanDefinition.setAutowireCandidate(true);
        System.out.println(Account.class.getSimpleName());
        registry.registerBeanDefinition(Account.class.getSimpleName(), beanDefinition);

//        // 扫描带有 RpcService 注解的类并初始化 handlerMap 对象
//        Map<String, Object> serviceBeanMap = applicationContext.getBeansWithAnnotation(SPI.class);
//        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"+serviceBeanMap.size());
//        if (!serviceBeanMap.isEmpty()) {
//            for (Object serviceBean : serviceBeanMap.values()) {
//                System.out.println(serviceBean.getClass().getName());
//                SPI spi = serviceBean.getClass().getAnnotation(SPI.class);
//                if (spi != null) {
//                    //RootBeanDefinition beanDefinition = new RootBeanDefinition();
//                    GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
//                    beanDefinition.setBeanClass(ServiceFactoryBean.class);
//                    beanDefinition.setLazyInit(false);
//                    beanDefinition.setAutowireCandidate(true);
////                    beanDefinition.getPropertyValues().addPropertyValue("myProxy", new MyProxy());
////                    beanDefinition.getPropertyValues().addPropertyValue("serviceClass", serviceBean.getClass());
//
//                    //BeanDefinitionRegistry registry = (BeanDefinitionRegistry) beanFactory;
//                    System.out.println(serviceBean.getClass().getSimpleName()+"@@@@@@@@@@");
//                    registry.registerBeanDefinition("account", beanDefinition);
//                    new RuntimeBeanReference("");
//                }
//
//            }
//        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
