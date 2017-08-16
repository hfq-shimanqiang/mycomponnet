package com.huifenqi.jedi.spring;

import org.springframework.beans.factory.BeanClassLoaderAware;

public class MyBeanClassLoaderAware implements BeanClassLoaderAware {
    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {

    }
}
