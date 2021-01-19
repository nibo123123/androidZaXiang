package com.example.chencj.myapplication;

import com.example.chencj.myapplication.util.ByteTransformUtils;

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
import java.util.Arrays;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        //System.out.println(Arrays.toString("ä".getBytes("ISO8859-1")));
       // String s = new String("ä".getBytes(), "ISO8859-1");
        byte[] bs = new byte[]{(byte)0xe0,(byte)0x85,(byte)0x82 };
        String s1 = new String(rD8BIT2RF8BIT1(bs));
        System.out.println(s1);

        //ByteTransformUtils.utf8ToUnicodeString("ä");

    }

    /*
    rF8BIT2RD8BIT bytes=e4c4
    rF8BIT2RD8BIT bytesDest=e080a4e08084
    rD8BIT2RF8BIT bytes=e080a4e08084
    rD8BIT2RF8BIT bytesDest=240400000000
     */
    public static byte[] rD8BIT2RF8BIT(byte[] bytes){
        int idx = 0;
        int slen = 0;


        byte[] bytesDest = new byte[bytes.length];
        int i = 0;
        int tmp = 0;
        while(slen < bytes.length){
            i = 0;
            tmp = bytes[slen] & 0xff;
            while ((tmp & 0x80) != 0){
                tmp = tmp << 1;
                i++;
            }
            if(i == 0){
                bytesDest[idx++] = (byte) (bytes[slen++] & 0x7F);
            }else if(i == 2){
                bytesDest[idx++] = (byte) (0x07 & (bytes[slen] >> 2));
                bytesDest[idx++] = (byte) (((bytes[slen] << 6) & 0xC0)|((bytes[slen + 1] & 0x3F)));
                slen += i;
            } else if(i == 3){
                int bytesDests = (int) ((((0xff & bytes[slen+1]) << 6) & 0xC0) | (((0xff & bytes[slen + 2]) & 0x3F)));
                bytesDest[idx++] = (byte) bytesDests;
                slen += i;
            }else{
                slen += i;

            }
            if(slen >= bytes.length){
                break;
            }
        }
        System.out.println("rD8BIT2RF8BIT bytes="+ByteTransformUtils.bytesToHexString(bytes));
        System.out.println("rD8BIT2RF8BIT bytesDest="+ByteTransformUtils.bytesToHexString(bytesDest));
        return bytesDest;
    }

    public static byte[] rD8BIT2RF8BIT1(byte[] bytes){
        int idx = 0;
        int slen = 0;
        byte[] bytesDest = new byte[bytes.length];
        int i = 0;
        int tmp = 0;
        while(slen < bytes.length){
            i = 0;
            tmp = bytes[slen];
            while ((tmp & 0x80) != 0){
                tmp = tmp << 1;
                i++;
            }
            if(i == 0){
                bytesDest[idx++] = (byte) (bytes[slen++] & 0x7F);
            }else if(i == 2){
                bytesDest[idx++] = (byte) (0x07 & (bytes[slen] >> 2));
                bytesDest[idx++] = (byte) (((bytes[slen] << 6) & 0xC0)|((bytes[slen + 1] & 0x3F)));
                slen += i;
            } else if(i == 3){
                //int bytesDests = (int) ((((0xff & bytes[slen+1]) << 6) & 0xC0) | (((0xff & bytes[slen + 2]) & 0x3F)));
                //bytesDest[idx++] = (byte) bytesDests;
                bytesDest[idx++] = (byte)(( (bytes[slen] << 4) &0xF0 ) | ( (bytes[slen+1] >> 2) & 0x0F));
                bytesDest[idx++] =(byte) (( (bytes[slen+1] << 6) & 0xC0 ) | (bytes[slen+2] & 0x3F));
                slen += i;
            }else{
                slen += i;

            }
            if(slen >= bytes.length){
                break;
            }
        }
        return bytesDest;
    }

    public static byte[] rF8BIT2RD8BIT(byte[] bytes){
        int idx = 0;
        int slen = 0;
        int specialInt = 0;
        int i = 0;
        int tmp = 0;
        int length = bytes.length;

        //特殊字符RD 两个字节拆成 三个字节
        byte[] bytesDest = new byte[length/2];

        for (int j = 0; j < length/2; j++) {
            tmp = bytes[j];
            int _tmp = bytes[j+1];
            if ((tmp & 0x80) != 0) {
                bytesDest[idx] = (byte) 0xE0;
                bytesDest[++idx] = (byte)(0x80 | ((tmp << 2) & 0x3C) | (_tmp >> 6) & 0x03);
                bytesDest[++idx] = (byte) (0x80 | (_tmp & 0x3F));
            } else {
                bytesDest[idx] = (byte)tmp;

            }
            idx++;
        }
        System.out.println("rF8BIT2RD8BIT bytes="+ByteTransformUtils.bytesToHexString(bytes));
        System.out.println("rF8BIT2RD8BIT bytesDest="+ByteTransformUtils.bytesToHexString(bytesDest));
        return bytesDest;
    }


   /* case STRC_UCS2UTF8:
    pSrc  = (UINT8*)pText;
    idx = 0;
    if ( (*len)%2 ) return FALSE;
    if ( (*len) < 16 ) pDest = (UINT8*)buf;
    #ifdef APP_CVT_NEW
    else if ( pDest == pText )
    {
        pbuf = GSMMalloc((*len)*2);
        if ( !pbuf ) return FALSE;
        pDest = pbuf;
    }
    #endif *//**//**//**//* APP_CVT_NEW *//**//**//**//*
    for ( i = 0; i < (*len)/2; i++ )
    {
        if (*pSrc || *(pSrc+1) > 0x80) // Unicode
        {
            pDest[idx++] = 0xE0 | ((*pSrc >> 4) & 0x0F);
            pDest[idx++] = 0x80 | ((*pSrc << 2) & 0x3C) | ((*(pSrc+1) >> 6) & 0x03);
            pDest[idx++] = 0x80 | (*(pSrc+1) & 0x3F);
            pSrc += 2;
        }
        else // Ascii
        {
            pSrc++;
            pDest[idx++] = (*pSrc++) & 0x7F;
        }
    }
    pDest[idx] = 0;
    *len = (UINT16)idx;
    if ( pDest != pRDest ) GSMmemcpy(pRDest, pDest, *len );
    #ifdef APP_CVT_NEW
    if ( pbuf ) GSMFree(pbuf);
    #endif *//**//**//**//* APP_CVT_NEW *//**//**//**//*
    return TRUE;
    case STRC_UTF82UCS2:
    idx = 0;
    while ( slen < (*len) )
    {
        i = 0;
        tmp = pText[slen];
        while (tmp & 0x80)
        {  tmp <<= 1;
            i++;
        }

        //if ( !(pText[slen] & 0x80) )
        if (0 == i)
        { // 0xxx xxxx 一个字节
            pDest[idx++] = 0x00;
            pDest[idx++] = pText[slen++] & 0x7F;
        }
        else if (2 == i)
        { // 110x xxxx 10xx xxxx
            pDest[idx++] = 0x07 & (pText[slen] >> 2);
            pDest[idx++] = ( (pText[slen] << 6) & 0xC0 ) | ( pText[slen+1] & 0x3F );
            slen += i;
        }
        //else if ( (pText[slen] & 0xF0) == 0xE0 )
        else if (3 == i)
        { // 1110 xxxx 10xx xxxx 10xx xxxx 三个字节
            pDest[idx++] = ( (pText[slen] << 4) &0xF0 ) | ( (pText[slen+1] >> 2) & 0x0F);
            pDest[idx++] = ( (pText[slen+1] << 6) & 0xC0 ) | (pText[slen+2] & 0x3F);
            slen += i;
        }
        else
        {
            NORMAL_DBG("\n++++++ Special UTF8 code: %x ,not deal with.\n", pText[slen]);
            slen += i;
        }
    }*/

    @Test
    public void f11(){
        String regx = "\\+?\\d{3,}";
        String s = "1000 adc1001 adf1003aff +1002afg ++1008afv +100+";
        Pattern pattern = Pattern.compile(regx);
        Matcher matcher = pattern.matcher(s);
        while (matcher.find()){
            System.out.println(matcher.group());
        }
    }
}