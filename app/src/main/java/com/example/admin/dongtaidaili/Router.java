package com.example.admin.dongtaidaili;

import android.content.Context;
import android.content.Intent;
import android.net.Proxy;
import android.os.Bundle;
import android.util.Log;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

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
            Bundle bundle = paserParmas(args, method);
            intent.putExtras(bundle);
            intent.setClassName(context.getPackageName(),className);
            context.startActivity(intent);
            return null;
        }

        private Bundle paserParmas(Object[] args, Method method) {
            Bundle bundle=new Bundle();
            Annotation[][] parameterAnnotations = method.getParameterAnnotations();
            for (int i = 0; i < parameterAnnotations.length; i++) {
                for (int j = 0; j < parameterAnnotations[i].length; j++) {
                    Annotation annotation = parameterAnnotations[i][j];
                    boolean b = annotation.annotationType() == ParamsRouter.class;
                    if(b){
                        Map<String,String> map = (Map<String, String>) args[i];
                        Set<Map.Entry<String, String>> entries = map.entrySet();
                        for (Map.Entry<String,String> entity:
                                entries
                             ) {
                            bundle.putString(entity.getKey(),entity.getValue());
                        }
                    }
                }
            }
            return bundle;
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
