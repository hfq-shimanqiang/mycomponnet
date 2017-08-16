package com.huifenqi.jedi.spring;

import com.huifenqi.jedi.annotations.SPI;
import com.huifenqi.jedi.api.MyProxy;
import com.huifenqi.jedi.api.ServiceFactoryBean;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Map;

public class JediApplicationContextInitializer implements ApplicationContextInitializer {
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {

        System.out.println(1111);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("000000000000000000000000000000");
        }, "hook"));
        final ConfigurableListableBeanFactory beanFactory = applicationContext.getBeanFactory();

//        // 扫描带有 RpcService 注解的类并初始化 handlerMap 对象
//        Map<String, Object> serviceBeanMap = applicationContext.getBeansWithAnnotation(SPI.class);
//        if (!serviceBeanMap.isEmpty()) {
//            for (Object serviceBean : serviceBeanMap.values()) {
//                SPI spi = serviceBean.getClass().getAnnotation(SPI.class);
//                if (spi != null) {
//                    RootBeanDefinition beanDefinition = new RootBeanDefinition();
//                    beanDefinition.setBeanClass(ServiceFactoryBean.class);
//                    beanDefinition.setLazyInit(true);
//                    //beanDefinition.getPropertyValues().addPropertyValue("myProxy", new MyProxy());
//                    beanDefinition.getPropertyValues().addPropertyValue("serviceClass", serviceBean.getClass());
//
//                    BeanDefinitionRegistry beanFactory = (BeanDefinitionRegistry) applicationContext.getBeanFactory();
//                    beanFactory.registerBeanDefinition(serviceBean.getClass().getSimpleName(), beanDefinition);
//                }
//
//            }
//        }

    }
}
