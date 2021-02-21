package com.example.chencj.myapplication.hook;

import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by CHENCJ on 2020/12/24.
 */

public class HookHelper {

    //需要view，和上下文context
    public static void hookOnClickListener(Context context, View view){
        //是通过反射，增强方法
        //主要hook点在单例对象上面
        //OnClickListener是View中的内部类ListenerInfo的属性

        //第一步先view反射拿到ListenerInfo的实例，通过方法getListenerInfo获得或者通过属性反射mListenerInfo获得实例

        //拿到view的字节码
        Class<? extends View> viewClass = View.class;
        //反射
        try {

            /*
            //方法反射
            Method getListenerInfo = viewClass.getDeclaredMethod("getListenerInfo");
            //私有方法
            getListenerInfo.setAccessible(true);

            Object invokeMethodReturn = getListenerInfo.invoke(view);

            Log.d("HookHelper chencj ", "hookOnClickListener: "+invokeMethodReturn);//android.view.View$ListenerInfo@91f42a

            */

            //属性反射
            Field mListenerInfoField = viewClass.getDeclaredField("mListenerInfo");
            mListenerInfoField.setAccessible(true);
            Object mListenerInfoInstance = mListenerInfoField.get(view);
            Log.d("HookHelper chencj ", "hookOnClickListener: mListenerInfoInstance="+mListenerInfoInstance);//android.view.View$ListenerInfo@91f42a

            //可以看到 使用属性和方法获取到的android.view.View$ListenerInfo都是一样的

            //进一步通过反射获取ListenerInfo中的OnClickListener的属性值

            //拿到ListenerInfo字节码
            Class<?> listenerInfoClass = Class.forName("android.view.View$ListenerInfo");
            Field mOnClickListenerField = listenerInfoClass.getDeclaredField("mOnClickListener");
            mOnClickListenerField.setAccessible(true);

            final Object mOnClickListenerInstance = mOnClickListenerField.get(mListenerInfoInstance);

            Log.d("HookHelper chencj ", "hookOnClickListener: mOnClickListenerInstance="+mOnClickListenerInstance);

            Object proxyOnClickListenerInstance = Proxy.newProxyInstance(context.getClassLoader(), new Class[]{View.OnClickListener.class}, new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    Log.d("HookHelper chencj ", "invoke: click 123 hello");
                    return method.invoke(mOnClickListenerInstance,args);
                }
            });

            mOnClickListenerField.set(mListenerInfoInstance,proxyOnClickListenerInstance);

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("HookHelper chencj ", "hookOnClickListener: ", e);
        }
    }


    public static void hookAMSStartActivity(final Context context)
            throws ClassNotFoundException,
            NoSuchFieldException,
            IllegalAccessException,
            NoSuchMethodException,
            InvocationTargetException {
        //android 25以前，是从ActivityManagerNative中获取AMS的实例,25以后
        Class<?> activityManagerNativeClass = Class.forName("android.app.ActivityManagerNative");
        //通过字节码获取静态属性gDefault
        Field gDefaultField = activityManagerNativeClass.getDeclaredField("gDefault");
        gDefaultField.setAccessible(true);
        //静态属性可以直接获得实例
        Object gDefault = gDefaultField.get(null);
        Log.d("HookHelper chencj ", "hookAMSStartActivity: gDefault="+gDefault);

        Class<?> singletonClass = Class.forName("android.util.Singleton");
        Field mInstanceField = singletonClass.getDeclaredField("mInstance");
        mInstanceField.setAccessible(true);
        final Object mInstance = mInstanceField.get(gDefault);
        Log.d("HookHelper chencj ", "hookAMSStartActivity: mInstance="+mInstance);

        //AOP，增加代理 动态代理，使用接口
        //AMS中的服务接口是android.app.IActivityManager
        Class<?> iActivityManagerClass = Class.forName("android.app.IActivityManager");

        Class<?> activityManagerClass = Class.forName("android.app.ActivityManager");
        Field startActivityField = activityManagerClass.getDeclaredField("START_SUCCESS");
        startActivityField.setAccessible(true);
        final  int START_ACTIVITY = (int) startActivityField.get(null);
        Log.d("HookHelper chencj ", "hookAMSStartActivity:START_ACTIVITY= "+START_ACTIVITY);
        Object proxyIActivityManagerInstance = Proxy.newProxyInstance(context.getClass().getClassLoader(), new Class[]{iActivityManagerClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Log.d("HookHelper chencj ", "invoke: methodName = "+method.getName());
                if(args!=null) {
                    for (int i = 0; i < args.length; i++) {
                        Log.d("HookHelper chencj ", "invoke: args="+args[0]);
                    }
                }

                if("startActivity".equals(method.getName())){
                    //寻找传进来的intent
                    Intent intent = null;
                    int index = 0;
                    for (int i = 0; i < args.length; i++) {
                        Object arg = args[i];
                        if (arg instanceof Intent) {
                            intent = (Intent) args[i];
                            index = i;
                            break;
                        }
                    }
                    Intent newIntent = new Intent();
                    ComponentName componentName = new ComponentName(context, RegisterActivity.class);
                    newIntent.setComponent(componentName);
                    //真实的意图被我隐藏到了键值对中
                    newIntent.putExtra("oldIntent", intent);
                    args[index] = newIntent;//替换为新的Intent
                }
                return method.invoke(mInstance, args);

            }
        });

        mInstanceField.set(gDefault,proxyIActivityManagerInstance);
    }


    public static void hookActivityThreadMh(Context context) {
        try {
            Class<?> mActivityThreadCls = Class.forName("android.app.ActivityThread");
            Field sCurrentActivityThreadField = mActivityThreadCls.getDeclaredField("sCurrentActivityThread");
            sCurrentActivityThreadField.setAccessible(true);
            Object mActivityThreadObj = sCurrentActivityThreadField.get(null);
            Log.d("HookHelper chencj ", "hookActivityThreadMh: mActivityThreadObj="+mActivityThreadObj);
            Field mHField = mActivityThreadCls.getDeclaredField("mH");
            mHField.setAccessible(true);

            final Handler mH = (Handler) mHField.get(mActivityThreadObj);
            Log.d("HookHelper chencj ", "hookActivityThreadMh: mH="+mH);
            Field mCallbackField = Handler.class.getDeclaredField("mCallback");
            mCallbackField.setAccessible(true);

            mCallbackField.set(mH, new ActivityMH(context,mH));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static  class ActivityMH implements Handler.Callback {
        private final Context context;
        private Handler mH;

        public ActivityMH(Context c,Handler mH) {
            this.mH = mH;
            this.context = c;
        }

        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == 100) {//LAUNCH_ACTIVITY ==100
                //加工完一定丢给系统
                handleLuachActivity(msg);
            }
            //做了真正的跳转
            mH.handleMessage(msg);
            return true;
        }

        private void handleLuachActivity(Message msg) {
            Object obj = msg.obj;
            Log.d("ActivityMH chencj ", "handleLuachActivity: obj="+obj);
            try {
                Field intentField = obj.getClass().getDeclaredField("intent");
                intentField.setAccessible(true);
                Intent realIntent = (Intent) intentField.get(obj);
                Log.d("ActivityMH chencj ", "handleLuachActivity: intent="+realIntent);
                Intent oldIntent = realIntent.getParcelableExtra("oldIntent");
                if (oldIntent != null) {
                    {
                        Log.d("ActivityMH chencj ", "handleLuachActivity:oldIntent= "+oldIntent);
                        ComponentName componentName = new ComponentName(context, NoRegisterActivity.class);
                        realIntent.putExtra("extraIntent", oldIntent.getComponent().getClassName());
                        realIntent.setComponent(componentName);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void hookToast(Context context){
        //hook点是toast的正真服务这  INotificationManager的实例

    }

    public static void hookNotificationManager(Context context) throws NoSuchFieldException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException {
        //hook点是NotificationManager的正真服务这  INotificationManager的实例
        //获取NotificationManager的app层的对象
        NotificationManager mNotificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        //获取NotificationManager的字节码
        Class mNotificationManagerClass = mNotificationManager.getClass();
        //从字节码中获取INotificationManager的属性
        Object mINotificationManagerService = null;
        Object mINotificationManagerService1 = null;
        Field mINotificationManagerServiceField = null;

        // 得到系统的 sService
        Method mINotificationManagerServiceMethod = NotificationManager.class.getDeclaredMethod("getService");
        mINotificationManagerServiceMethod.setAccessible(true);
        mINotificationManagerService = mINotificationManagerServiceMethod.invoke(mNotificationManager);


        mINotificationManagerServiceField = mNotificationManagerClass.getDeclaredField("sService");
        mINotificationManagerServiceField.setAccessible(true);
        //由于是静态的属性直接获得 对象 真正服务的对象实例
        mINotificationManagerService1 = mINotificationManagerServiceField.get(null);//有些没有初始化，导致需要使用method的invoke来获取


        if(mINotificationManagerService == null){
            return;
        }
        //由于INotificationManager是接口，android.app.INotificationManager
        //通过java的动态代理，增强代理
        Object proxyINotificationManagerService = null;

        proxyINotificationManagerService = Proxy.newProxyInstance(context.getClassLoader(),//类加载器
                new Class[]{Class.forName("android.app.INotificationManager")},//要去增强的接口
                new INotificationManagerInvocationHandler(mINotificationManagerService, context));//处理增强的回调


        if(proxyINotificationManagerService == null)return;

        mINotificationManagerServiceField.set(mNotificationManager,proxyINotificationManagerService);

    }

    private static class INotificationManagerInvocationHandler implements InvocationHandler{
        Object mINotificationManagerService = null;
        Context c;
        public  INotificationManagerInvocationHandler(Object obj,Context c){
            mINotificationManagerService = obj;
            this.c = c;
        }
        @Override
        public Object invoke(Object o, Method method, Object[] args) throws Throwable {
            Log.d("INotificationManagerInvocationHandler chencj ", "invoke: method="+method.getName());
            if (args != null && args.length > 0) {
                for (Object arg : args) {
                    Log.d("INotificationManagerInvocationHandler chencj ", "invoke:  arg=" + arg);
                }
            }
            Toast.makeText(c,"add proxy",Toast.LENGTH_SHORT).show();
            return method.invoke(mINotificationManagerService,args);
        }
    }
}
