package com.example.slidephoto.utils;

/**
 * Created by CHENCJ on 2020-7-8.
 */

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by CHENCJ on 2020/6/25.
 */

public class UIUtils {

    // /////////////////加载资源文件 ///////////////////////////
    /*
    //获取资源对象
    Resources resources = getResources();
    //获取指定dimen中的值
    resources.getDimension(R.dimen.自定义id);
    //获取显示区域的大小
    DisplayMetrics displayMetrics = resources.getDisplayMetrics();
    //
    float density = displayMetrics.density;//显示屏幕的 密度

    //通过density，可以转换dp与pix

    int height = displayMetrics.heightPixels;//显示屏的高度 单位pix
    int widthPixels = displayMetrics.widthPixels;//显示屏幕的宽度 单位 pix
    */
    // 获取字符串
    public static String getString(Context context,int id) {
        return context.getResources().getString(id);
    }

    //获取字符串数组
    public static String[] getStringArr(Context context,int arrId) {
        return context.getResources().getStringArray(arrId);
    }


    //获取颜色
    public static int getColor(Context context,int colorId) {
        return context.getResources().getColor(colorId);
    }

    // 获取图片
    public static Drawable getDrawable(Context context,int id) {
        return context.getResources().getDrawable(id);
    }


    //根据id获取颜色的状态选择器
    public static ColorStateList getColorStateList(Context context,int id) {
        return context.getResources().getColorStateList(id);
    }


    /**
     * 1dp---1px;
     * 1dp---0.75px;
     * 1dp---0.5px;
     * dp转px
     */
    public static int dp2px(Context context,int dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5);
    }

    //px转dp
    public static int px2dp(Context context,int px) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (px / density + 0.5);
    }

    // 获取尺寸
    public static int getDimen(Context context,int id) {
        return context.getResources().getDimensionPixelSize(id);// 返回具体像素值
    }

    // /////////////////加载布局文件//////////////////////////
    public static View inflate(Context context,int layoutid) {
        return View.inflate(context, layoutid, null);
    }

    //toast的封装
    public static void Toast(Context context,String text, boolean isLong) {
        Toast.makeText(context, text, isLong == true ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT).show();
    }

    //SharedPreferences  sp实例化
    public static SharedPreferences getSharedPreferences(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static SharedPreferences.Editor getSharedPreferencesEditor(Context context){
        return getSharedPreferences(context).edit();
    }

    public static  void installApk(Context context,String downloadApk) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        File file = new File(downloadApk);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri apkUri = FileProvider.getUriForFile(context, context.getPackageName()+".fileprovider", file);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri uri = Uri.fromFile(file);
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
        }
        context.startActivity(intent);

    }


    /**
     * 动态请求权限
     *
     * Manifest.permission.READ_EXTERNAL_STORAGE
     * Manifest.permission.WRITE_EXTERNAL_STORAGE
     */
    public static void executePermissionsRequest(Object object, String[] perms, int requestCode) {
        checkCallingObjectSuitability(object);

        if (object instanceof Activity) {
            ActivityCompat.requestPermissions((Activity) object, perms, requestCode);
        } else if (object instanceof Fragment) {
            ((Fragment) object).requestPermissions(perms, requestCode);
        } else if (object instanceof android.app.Fragment) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                ((android.app.Fragment) object).requestPermissions(perms, requestCode);
            }
        }
    }

    /*
    * 检查权限
    * */
    public static boolean hasPermissions(Context context, String... perms) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }

        for (String perm : perms) {
            boolean hasPerm = (ContextCompat.checkSelfPermission(context, perm) ==
                    PackageManager.PERMISSION_GRANTED);
            if (!hasPerm) {
                return false;
            }
        }

        return true;
    }

    private static void checkCallingObjectSuitability(Object object) {
        // Make sure Object is an Activity or Fragment
        boolean isActivity = object instanceof Activity;
        boolean isSupportFragment = object instanceof Fragment;
        boolean isAppFragment = object instanceof android.app.Fragment;
        boolean isMinSdkM = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;

        if (!(isSupportFragment || isActivity || (isAppFragment && isMinSdkM))) {
            if (isAppFragment) {
                throw new IllegalArgumentException(
                        "Target SDK needs to be greater than 23 if caller is android.app.Fragment");
            } else {
                throw new IllegalArgumentException("Caller must be an Activity or a Fragment.");
            }
        }
    }

    public static List<Activity> getActivitiesByApplication(Application application) {
        List<Activity> list = new ArrayList<>();
        try {
            Class<Application> applicationClass = Application.class;
            Field mLoadedApkField = applicationClass.getDeclaredField("mLoadedApk");
            mLoadedApkField.setAccessible(true);
            Object mLoadedApk = mLoadedApkField.get(application);
            Class<?> mLoadedApkClass = mLoadedApk.getClass();
            Field mActivityThreadField = mLoadedApkClass.getDeclaredField("mActivityThread");
            mActivityThreadField.setAccessible(true);
            Object mActivityThread = mActivityThreadField.get(mLoadedApk);
            Class<?> mActivityThreadClass = mActivityThread.getClass();
            Field mActivitiesField = mActivityThreadClass.getDeclaredField("mActivities");
            mActivitiesField.setAccessible(true);
            Object mActivities = mActivitiesField.get(mActivityThread);
            // 注意这里一定写成Map，低版本这里用的是HashMap，高版本用的是ArrayMap
            if (mActivities instanceof Map) {
                @SuppressWarnings("unchecked")
                Map<Object, Object> arrayMap = (Map<Object, Object>) mActivities;
                for (Map.Entry<Object, Object> entry : arrayMap.entrySet()) {
                    Object value = entry.getValue();
                    Class<?> activityClientRecordClass = value.getClass();
                    Field activityField = activityClientRecordClass.getDeclaredField("activity");
                    activityField.setAccessible(true);
                    Object o = activityField.get(value);
                    list.add((Activity) o);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            list = null;
        }
        return list;
    }

}

