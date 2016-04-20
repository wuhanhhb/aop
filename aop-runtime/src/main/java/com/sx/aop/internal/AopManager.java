package com.sx.aop.internal;

import android.annotation.TargetApi;
import android.os.Build;
import android.util.ArrayMap;

/**
 * Created by hebing on 2016/4/19.
 */
@TargetApi(Build.VERSION_CODES.KITKAT)
public class AopManager {

    private static ArrayMap<Class, Inject> injects = new ArrayMap<>();

    public interface Inject {
        /**
         * if return true,will not call old method
         *
         * @param methodName the name of method
         * @return true or false
         */
        public boolean before(String methodName);

        /**
         * if before reutrn true,you need to do this
         *
         * @param methodName the name of method
         * @param params     the params of method
         * @return return process result
         */
        public Object process(String methodName, Object... params);

        /**
         * @param result the result will be return
         * @return return real result
         */
        public Object after(String methodName, Object result);
    }

    public static void addInject(Class<?> clazz, Inject inject) {
        injects.put(clazz, inject);
    }

    static Inject getInject(Class<?> clazz) {
        return injects.get(clazz);
    }
}
