package com.example.chencj.myapplication;

import com.example.chencj.myapplication.util.TransformUtils;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Date;

import static com.example.chencj.myapplication.util.TransformUtils.rD8BIT2RF8BIT;
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
    public void f6() throws UnsupportedEncodingException {
        //ä E083A4  228(10)  u00e4(16)   1110 0100
        //Ä E08384
        //a 61E083A4

        //ł e08582  322(10)  u0142(16)  1 0100 0010
        //ź e085ba

        //[0,e4]
        String s = "# 你aäłh好4我是laizi北方ź的";
        s = "BEGIN:VCARD\r\n" +
                "VERSION:2.1\r\n" +
                "N;CHARSET=UTF-8;ENCODING=8BIT:好AÄäüöjü2g好tk好äjźł你\r\n" +
                "TEL;HOME:1008611\r\n" +
                "TEL;CELL:333333\r\n" +
                "TEL;WORK:444444\r\n" +
                "TEL;FAX:555555\r\n" +
                "TEL;VOICE:222222\r\n" +
                "END:VCARD\r\n";
        byte[] unicodes = s.getBytes("unicode");
        /*
        utf->unicode=# \u4f60a\u00e4\u0142h\u597d4
        [0, 35, 0, 32, 79, 96, 0, 97, 0, -28, 1, 66, 0, 104, 89, 125, 0, 52]
        # 你aäłh好4
        002300204f60006100e401420068597d0034
        2320 e4bda0 61 e083a4 e08582 68 e5a5bd 34
         */

        String s1 = TransformUtils.utf8ToUnicodeString(s);
        byte[] bytes = TransformUtils.unicode2Bytes(s1);

        System.out.println("s1="+new String(bytes,"unicode"));

        byte[] bytes1 = TransformUtils.rF8BIT2RD8BIT(bytes);
        //System.out.println(Arrays.toString(rD8BIT2RF8BIT1(bytes)));

        byte[] bytes2 = rD8BIT2RF8BIT(bytes1);
        System.out.println("s2="+new String(bytes2,"unicode"));

        String s2 =
                "424547494e3a56434152440d0a" +
                        "56455253494f4e3a322e310d0a" +
                        "4e3b434841525345543d5554462d383b454e434f44494e473d384249543ae5a5bd41e08384e083a4e083bce083b66ae083bc3267e5a5bd746be5a5bde083a46ae085bae08582e4bda00d0a" +
                        "54454c3b484f4d453a313030383631310d0a" +
                        "54454c3b43454c4c3a3333333333330d0a" +
                        "54454c3b574f524b3a3434343434340d0a" +
                        "54454c3b4641583a3535353535350d0a" +
                        "54454c3b564f4943453a3232323232320d0a" +
                        "454e443a56434152440d0a";
        byte[] bytes3 = TransformUtils.hexStringToBytes(s2);
        byte[] bytes4 = TransformUtils.rD8BIT2RF8BIT(bytes3);
        System.out.println("s3="+new String(bytes4,"unicode"));
    }

    @Test
    public void f7(){
        InterruptRunnable it = new InterruptRunnable();
        Thread t1 = new Thread(it);
        Thread t2 = new Thread(it);

        t1.start();
        t2.start();

        for (int i = 0; i < 50; i++) {
            if (i == 25) {
                // 调用中断方法 来清楚状态
                t1.interrupt();
                t2.interrupt();
                break;
            }
            System.out.println(i + "---");
        }
        System.out.println("主线程结束");
    }
    @Test
    public void f8(){
        System.out.println(haoAddOne("567775566"));
        processIMEI("567775566");
    }
    private static final String STR_FORMAT = "000000000000";
    public static String haoAddOne(String liuShuiHao){
        Double intHao = Double.parseDouble(liuShuiHao);
        //intHao++;
        DecimalFormat df = new DecimalFormat(STR_FORMAT);
        return df.format(intHao);
    }

    //000000-00-000000-0
    private static final String STR_FORMATE = "000000000000000";
    private String processIMEI(String str) {
        String res = null;
        String replaceAll = str.replaceAll("-", "");
        System.out.println("processIMEI: replaceAll1="+replaceAll);
        try {
            if(replaceAll.length() > 15){
                replaceAll = replaceAll.substring(0,15);
            }else if(replaceAll.length() < 15) {
                double parseDouble = Double.parseDouble(replaceAll);
                DecimalFormat decimalFormat = new DecimalFormat(STR_FORMATE);
                replaceAll = decimalFormat.format(parseDouble);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        System.out.println("processIMEI: replaceAll2="+replaceAll);
        String substring1 = replaceAll.substring(0, 6);
        String substring2 = replaceAll.substring(6, 8);
        String substring3 = replaceAll.substring(8, 14);
        String substring4 = replaceAll.substring(14, 15);

        res = substring1 + "-" + substring2 + "-"+substring3+"-"+substring4;
        System.out.println("processIMEI: res="+res);
        return res;

    }
}

class InterruptRunnable implements Runnable {
    public synchronized void run() {
        while(true) {
            try {
                // 线程1进来 带着锁 遇到了wait方法 等待
                //放弃了CPU的执行权 但是 锁 会还回去
                // 线程2也带着锁进来 又遇到wait方法 也等待
                // 相当于两个线程都在这里等待
                // 进入冷冻(中断)状态
                // 解决冷冻(中断)状态
                // 调用interrupt方法 清除该状态
                wait();
            }catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "...run");
        }
    }
}