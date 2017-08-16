package com.huifenqi.jedi.spring;

import com.huifenqi.jedi.api.Account;
import com.huifenqi.jedi.api.MyProxy;
import com.huifenqi.jedi.api.ServiceFactoryBean;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.stereotype.Component;

@Component
public class MyBeanDefinitionPostProcessor implements BeanDefinitionRegistryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
    }
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        System.out.println("QQQQQQQQQQQQQQQQQQQQQQQQQQQQQQ");
        RootBeanDefinition beanDefinition = new RootBeanDefinition();
        //GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(ServiceFactoryBean.class);
        beanDefinition.getPropertyValues().addPropertyValue("myProxy", new MyProxy());
        beanDefinition.getPropertyValues().addPropertyValue("serviceClass", Account.class);
        beanDefinition.setLazyInit(true);
        beanDefinition.setAutowireCandidate(true);

        System.out.println(Account.class.getSimpleName());
        registry.registerBeanDefinition(Account.class.getSimpleName(), beanDefinition);
    }
}
