package com.huifenqi.jedi.api;

import org.springframework.beans.factory.FactoryBean;

public class ServiceFactoryBean<T> implements FactoryBean<T> {
    private MyProxy myProxy;
    private Class<T> serviceClass;
    public void setServiceClass(Class<T> serviceClass) {
        this.serviceClass = serviceClass;
    }
    public void setMyProxy(MyProxy myProxy) {
        this.myProxy = myProxy;
    }
    @Override
    public T getObject() throws Exception {
        return myProxy.create(serviceClass);
    }
    @Override
    public Class<T> getObjectType() {
        return serviceClass;
    }
    @Override
    public boolean isSingleton() {
        return true;
    }
}