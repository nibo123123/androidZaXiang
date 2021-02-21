package com.example.chencj.myapplication;

import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Calendar;

/**
 * Created by CHENCJ on 2021/1/28.
 */

public class Test1 {
    @Test
    public void f1(){
        String s = "<pti:timert1=\"1\"";
        System.out.println(s);


        Calendar   cDay1   =   Calendar.getInstance();
        cDay1.set(Calendar.YEAR,2021);
        cDay1.set(Calendar.MONTH,3);
        int actualMaximum = cDay1.getActualMaximum(Calendar.DAY_OF_MONTH);
        int first = cDay1.getActualMinimum(Calendar.DAY_OF_MONTH);
        System.out.println(actualMaximum);
        System.out.println(first);

        // 设置日历对象的年月日
        Calendar c = Calendar.getInstance();
        c.set(2021, 5, 1); // 其实是这一年的3月1日
        // 把时间往前推一天，就是2月的最后一天
        c.add(Calendar.DATE, -1);

        // 获取这一天输出即可
        System.out.println(c.get(Calendar.DATE));
    }

    @Test
    public void f2(){
        String s = "<pti:timert1=\"1\">";
        String s1 = "<PTI: local ring status=\"1\">";

        s1="<PTI:SMS num2=”abcde”>";
        /*for (int i = 0; i < s1.length(); i++) {
            System.out.println(i+":"+s1.charAt(i)+" "+(new Integer(s1.charAt(i))));
        }*/

//        String regex = new String(new char[]{160,32});
//        System.out.println("'"+ regex +"'"+"\\s");
//
//        s1 = s1.replaceAll("\\u00A0", "");
//
//        String[] split = s1.split(">");
//
//        for (int i = 0; i < split.length; i++) {
//            System.out.println(i+":"+split[i]);
//        }
//
//        char space_1 = new Character(' ');//英文空格
//
//        char space_2 = new Character('');//中文空格
//
//        System.out.println((int)space_1);
//
//        System.out.println((int)space_2);
/*
        for (int i = 0; i < s.length(); i++) {
            System.out.println(i+ ":"+s.charAt(i));
        }
        System.out.println(s);*/
    }

    @Test
    public void f3(){
        int a = 1 << 1;// 0000 0001  << 1  -->  0000 0010 2
                        // ~  1111 1101 取反操作  a+ (~a)= -1  0000 0010 0000 0011
        int b = 0b11111101;
        System.out.println(a + "  "+ (~a)+"  "+(~b));

        new Demo1();
    }
    class Demo<T>{
        public Demo(){
            Type genericSuperclass = this.getClass().getGenericSuperclass();
            if(genericSuperclass!=null) {
                ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;

                if(parameterizedType!=null) {
                    Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();

                    if(actualTypeArguments!=null) {
                        for (int i = 0; i <actualTypeArguments.length; i++) {
                            System.out.println(actualTypeArguments[i]);
                            Class classes = (Class) actualTypeArguments[i];
                            try {
                                Object o = classes.newInstance();
                                System.out.println(o);
                            } catch (InstantiationException e) {

                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
    }
    class Demo1 extends Demo<String>{

    }

    @Test
    public void f4() throws Exception {
        synchronized (Test1.class){
            System.out.println("123456");
            Test1.class.notify();
            System.out.println("123");
        }

        Class<?> aClass = Class.forName("java.lang.Integer");
        Field digits = aClass.getDeclaredField("digits");
        digits.setAccessible(true);
        char[] o = (char[]) digits.get(null);
        System.out.println(Arrays.toString(o));

    }
}
