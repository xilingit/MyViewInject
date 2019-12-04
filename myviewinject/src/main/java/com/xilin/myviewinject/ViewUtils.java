package com.xilin.myviewinject;

import android.app.Activity;
import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ViewUtils {

    public static void inject(Activity activity) {
        try {
            bindView(activity);
            bindMethod(activity);
        }catch (Exception e){

        }
    }

    private static void bindView(Activity activity) throws IllegalAccessException {
        //1 获取Activity的字节码
        Class<? extends Activity> aClass = activity.getClass();
        //2 获取到该字节码中所有的Filed
        Field[] declaredFields = aClass.getDeclaredFields();
        //3 遍历字段，判断哪些是想要的字段，只有添加了ViewInject注解的字段
        for (Field declaredField : declaredFields) {
            //获取字段上面的注解
            ViewInject annotation = declaredField.getAnnotation(ViewInject.class);
            if (annotation != null) {
                //获取当前的注解值
                int id = annotation.value();
                //通过调用Activity的findViewById方法获取当前id为resId的控件
                View view = activity.findViewById(id);
                //通过反射设置给遍历好的字段
                declaredField.setAccessible(true);
                //给Activity对象的filed字段设置值
                declaredField.set(activity, view);
            }
        }
    }

    private static void bindMethod(final Activity activity) {
        //获取Activity字节码
        Class<? extends Activity> aClass = activity.getClass();
        //获取当前字节码中所有的方法
        Method[] declaredMethods = aClass.getDeclaredMethods();
        //遍历方法，找出方法上声明了OnClick注解的方法
        for (final Method method : declaredMethods) {
            //获取当前方法上的注解
            OnClick annotation = method.getAnnotation(OnClick.class);
            if (annotation != null){
                //获取注解中的值
                int resId = annotation.value();
                //获取id为resId的View
                final View view = activity.findViewById(resId);
                //给当前的view绑定点击事件
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //通过反射调用当前用户的方法
                        method.setAccessible(true);//暴力反射
                        try {
                            method.invoke(activity,view);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }
    }
}
