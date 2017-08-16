package com.huifenqi.jedi.api;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class MyProxy {
    public <T> T create(Class<T> serviceClass) {
        return (T) Proxy.newProxyInstance(serviceClass.getClassLoader(),
                new Class[]{serviceClass}, new InvocationHandler() {

                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        //return method.invoke(this, args);
                        return "hi, boys";
                    }
                });
    }

    public static void main(String[] args) {
//        final String test = MyProxy.create(Account.class).test();
//        System.out.println(test);
    }
}
