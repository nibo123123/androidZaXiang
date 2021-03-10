package com.example.chencj.myapplication.reflect;

/**
 * Created by CHENCJ on 2021/3/10.
 */

import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Method;


public class ReflectionUtil {
    public static final String TAG = "ReflectionUtil";
    private static Method sForNameMethod;
    private static Method sGetDeclaredMethod;
    private static Method sGetFieldMethod;

    static {
        try {
            //操作Class的字节码
            sForNameMethod = Class.class.getDeclaredMethod("forName", String.class);
            sGetDeclaredMethod = Class.class.getDeclaredMethod("getDeclaredMethod", String.class, Class[].class);
            sGetFieldMethod = Class.class.getDeclaredMethod("getDeclaredField", String.class);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    /**
     * 返回Field的实例化的对象
     * @param src //实例化的对象
     * @param clzName //实例化对象的字节码的名字
     * @param fieldName //要反射的field的名字
     * @param defObj //默认值
     * @return
     */
    public static Object getFieldObj( Object src,  String clzName,  String fieldName,  Object defObj) {
        Object result = defObj;
        try {
            Field field = getField(clzName, fieldName);
            if (field != null) {
                result = field.get(src);
                Log.d("ReflectionUtil chencj ", "getFieldObj:"+result);
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return result;
    }


    /**
     * 获得反射的Feild
     * @param clzName //对象字节码的名字
     * @param fieldName ////要反射的属性Field的名字
     * @return
     */
    public static Field getField( String clzName,  String fieldName) {
        Field field = null;
        if (canReflection()) {
            try {
                Class<?> clz = (Class<?>) sForNameMethod.invoke(null, clzName);
                field = (Field) sGetFieldMethod.invoke(clz, fieldName);
                field.setAccessible(true);
                Log.d("ReflectionUtil chencj ", "getField:");
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
        return field;
    }


    /**
     * 把反射的属性Field重新设置到对象中
     * @param src //实例对象
     * @param clzName //实例对象字节码的名字
     * @param fieldName //要反射的属性Field
     * @param tarObj //新的属性实例
     */
    public static void setField( Object src,  String clzName,  String fieldName, Object tarObj) {
        try {
            Field field = getField(clzName, fieldName);
            if (field != null) {
                field.set(src, tarObj);
                Log.d("ReflectionUtil chencj ", "setField:");
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }


    /**
     * 得到反射的method
     * @param clzName //对象的字节码的名字
     * @param methodName//对象方法的名字
     * @param clzArgs//反射方法的参数的字节码
     * @return
     */
    public static Method getMethod( String clzName,  String methodName, Class[] clzArgs) {
        Method method = null;
        if (canReflection()) {
            try {
                Class<?> clz = (Class<?>) sForNameMethod.invoke(null, clzName);
                method = (Method) sGetDeclaredMethod.invoke(clz, methodName, clzArgs);
                method.setAccessible(true);
                Log.d("ReflectionUtil chencj ", "getMethod: ");
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
        return method;
    }


    /**
     * 反射得到指定类的回调Method
     * @param src //实例对象
     * @param clzName //实例对象的字节码的名字
     * @param methodName //要反射的方法名字
     * @param clzArgs //反射方法的参数的字节码
     * @param objArgs //回调方法的参数
     * @return
     */
    public static Object invokeMethod(Object src,  String clzName,  String methodName, Class[] clzArgs, Object... objArgs) {
        Object result = null;
        try {
            Method method = getMethod(clzName, methodName, clzArgs);
            if (method != null) {
                result = method.invoke(src, objArgs);
                Log.d("ReflectionUtil chencj ", "invokeMethod:"+result);
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return result;
    }


    /**
     * 判断是否可以操作反射
     * @return
     */
    private static boolean canReflection() {
        boolean canReflection = true;
        if (sForNameMethod == null || sGetDeclaredMethod == null || sGetFieldMethod == null) {
            canReflection = false;
        }
        return canReflection;
    }
}
