package com.example.chencj.myapplication;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }
    @Test
    public void f1(){

        int c = 0;
        System.out.println(System.currentTimeMillis());
        Date date = new Date(1605060886187l);
        System.out.println(date.toString());
        /*for (;;) {
            System.out.println("c="+c++);
            if(c == 10){
                return;
            }
        }*/
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                while(true){
                    if(Thread.currentThread().isInterrupted()){
                        System.out.println("Yes,I am interruted,but I am still running");
                        break;

                    }else{
                        System.out.println("not yet interrupted");
                    }
                }
            }
        };
        Thread thread = new Thread(){
            @Override
            public void run() {
                while(true){
                    System.out.println("running..."+isInterrupted());
                    if(isInterrupted()){
                        System.out.println("Yes,I am interruted "+isInterrupted());
                        break;

                    }/*else{
                        System.out.println("not yet interrupted");
                    }*/
                }
                System.out.println("finish");
            }
        };


        int count = 0;
        thread.start();

        try {
            Thread.sleep(100);

            thread.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void f2() throws Exception {
        String s = "啊";
        byte[] bs = new byte[]{85, 74};
        String s1 = new String(bs, "utf-16");
        System.out.println(s1+"  "/*+Arrays.toString(s1.getBytes("utf-8")*/);
        System.out.println(Arrays.toString(s.getBytes("utf-16be")));
    }
    @Test
    public void f3() throws Exception{
        byte[] a = new byte[]{/*(byte) 0xC3,*/(byte) 0xA4};
        String s1 = new String(a, "utf-8");
        //System.out.println(s1+""/*+Arrays.toString(s1.getBytes("utf-8")*/);

        /*String a1 = "ä";
        byte[] a2 = new byte[]{(byte) 0x7B};
        System.out.println(Arrays.toString(a1.getBytes("utf-16be")));
        String s2 = new String(a2);
        System.out.println(s2);*/
        String a1 = "BEGIN:";
        byte[] bytes = a1.getBytes("utf-16be");
        System.out.println(Arrays.toString(bytes));

    }

    @Test
    public void f4(){
        try {
            ServerSocket serverSocket = new ServerSocket(8888);
            Socket socket = serverSocket.accept();
            while (true){

                InputStream inputStream = socket.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String s = bufferedReader.readLine();
                System.out.println("s="+s);

                OutputStream outputStream = socket.getOutputStream();
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
                outputStream.write("hello world".getBytes());
                outputStreamWriter.flush();

                outputStreamWriter.close();
                bufferedReader.close();
            }
        } catch (IOException e) {


        }
    }

    @Test
    public void f5(){
        Socket socket = null;
        try {
            socket = new Socket("10.10.2.197",8888);

            InputStream inputStream = socket.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String s = bufferedReader.readLine();
            System.out.println("s="+s);

            OutputStream outputStream = socket.getOutputStream();
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            outputStream.write("123456".getBytes());
            outputStreamWriter.flush();

            outputStreamWriter.close();
            bufferedReader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void f6(){
        String s1 = "hello";
        String s2 = s1;
        s1 = "world";
        System.out.println(s1);
        System.out.println(s2);
        
    }
}