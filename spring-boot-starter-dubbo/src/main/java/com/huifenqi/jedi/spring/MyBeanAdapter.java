package com.huifenqi.jedi.spring;

import com.huifenqi.jedi.annotations.SPI;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class MyBeanAdapter implements InitializingBean ,ApplicationContextAware{
    ApplicationContext applicationContext;
    @Override
    public void afterPropertiesSet() throws Exception {

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        // 扫描带有 RpcService 注解的类并初始化 handlerMap 对象
        Map<String, Object> serviceBeanMap = applicationContext.getBeansWithAnnotation(SPI.class);
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"+serviceBeanMap.size());
    }
}
