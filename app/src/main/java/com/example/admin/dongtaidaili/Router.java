package com.example.admin.dongtaidaili;

import android.content.Context;
import android.content.Intent;
import android.net.Proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by admin on 2017/3/24.
 */

public class Router {
    private static Context context;

    private Router() {
    }

    public  <T> T create(Class<T> tClass) {

        return (T) java.lang.reflect.Proxy.newProxyInstance(tClass.getClassLoader(), new Class[]{tClass}, new HandlerInvoke());
    }

    static class HandlerInvoke implements InvocationHandler {

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            String className = check(method);
            Intent intent=new Intent();
            intent.setClassName(context.getPackageName(),className);
            context.startActivity(intent);
            return null;
        }

        private String check(Method method) {
            boolean annotationPresent = method.isAnnotationPresent(ActivityRouter.class);
            if (!annotationPresent) {
                throw new IllegalStateException("没有路由");
            }
            return method.getAnnotation(ActivityRouter.class).value();
        }
    }


    static class RouterBuilder {
        public RouterBuilder(Context context) {
            Router.context = context;
        }

        public Router builder() {
            return new Router();
        }
    }
}
