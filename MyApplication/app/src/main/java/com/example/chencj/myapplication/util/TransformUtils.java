package com.example.chencj.myapplication.util;

import java.util.ArrayList;


/**
 * Created by CHENCJ on 2021/1/19.
 */

public class TransformUtils {
    static int
    hexCharToInt(char c) {
        if (c >= '0' && c <= '9') return (c - '0');
        if (c >= 'A' && c <= 'F') return (c - 'A' + 10);
        if (c >= 'a' && c <= 'f') return (c - 'a' + 10);

        throw new RuntimeException ("invalid hex char '" + c + "'");
    }

    public static byte[]
    hexStringToBytes(String s) {
        byte[] ret;

        if (s == null) return null;

        int sz = s.length();

        ret = new byte[sz/2];

        for (int i=0 ; i <sz ; i+=2) {
            //因为是Hex是16bit的编码，最大时0x0F或0x0f  十进制 15  二进制 0B00001111
            //所以只会用到低四位
            //对于一个byte是8bit存储
            //所以一个byte可以存储两个hex的字符
            //byte的高4位存储 第一个，低四位存储第二个  凑齐一个字节
            //做for把每个byte进行拼接
            ret[i/2] = (byte) ((hexCharToInt(s.charAt(i)) << 4)
                    | hexCharToInt(s.charAt(i+1)));
        }

        return ret;
    }

    public static String
    bytesToHexString(byte[] bytes) {
        if (bytes == null) return null;

        //反过来
        //字节转成hex的字符
        //数量*2
        StringBuilder ret = new StringBuilder(2*bytes.length);

        for (int i = 0 ; i < bytes.length ; i++) {
            int b;
            //一个byte的高四位拿到
            b = 0x0f & (bytes[i] >> 4);

            ret.append("0123456789abcdef".charAt(b));
            //一个byte的低四位拿到
            b = 0x0f & bytes[i];

            ret.append("0123456789abcdef".charAt(b));
        }

        return ret.toString();
    }

    public static String singleBytesToUnicodeString(byte[] bytes) {
        byte[] destBytes = bytes;//new byte[bytes.length];
        String destString = "";

        if(bytes == null)return "";
        String temp = "\\u";

        if(destBytes.length == 1){
            int _temp = destBytes[0] & 0xff;
            String s = Integer.toHexString(_temp);
            temp += "00"+s;
        }else {
            for (int i = 0; i < destBytes.length; i++) {
                int _temp = destBytes[i] & 0xff;
                String s = Integer.toHexString(_temp);
                if (s.length() == 1) {
                    s += "0";
                }
                temp += s;
            }
        }

        destString = unicodeToUtf8String(temp);
        System.out.println("bytesToUnicodeString destString="+destString);
        return destString;
    }

    /**
     * unicode字符 转字符数组
     * @param unicode
     * @return
     */
    public static byte[] unicode2Bytes(String unicode) {

        ArrayList<String> uninCodeList = new ArrayList<>();
        for (int i = 0; i < unicode.length(); ) {
            int i1 = i + 2;

            //防止数组越界
            if(i1 > unicode.length()){
                i1=unicode.length();
            }

            String substring = unicode.substring(i, i1);
            if("\\u".equals(substring)){
                uninCodeList.add(unicode.substring(i1,i+6));
                i = i + 6;
            }else{
                uninCodeList.add(unicode.charAt(i)+"");
                i++;
            }

        }
        int hexLength = uninCodeList.size();

        byte[] bs=new byte[2*hexLength];
        int bsIndex= 0;
        for (int i = 0; i < hexLength; i++) {
            // 转换出每一个代码点
            if(uninCodeList.get(i).length() == 4){
                bs[bsIndex++] = (byte) Integer.parseInt(uninCodeList.get(i).substring(0,2),16);
                bs[bsIndex++] = (byte) Integer.parseInt(uninCodeList.get(i).substring(2),16);
            }else {
                bs[bsIndex++] = 0;
                byte[] bytes = uninCodeList.get(i).getBytes();
                bs[bsIndex++] = bytes[0];
            }
        }
        System.out.println("unicode2Bytes unicode="+ unicode);
        System.out.println("unicode2Bytes bs="+ TransformUtils.bytesToHexString(bs));
        return bs;
    }


    /**
     * 字符串转换成 unicode编码串
     * @param inStr
     * @return
     */
    public static String utf8ToUnicodeString(String inStr) {
        char[] myBuffer = inStr.toCharArray();

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < inStr.length(); i++) {
            Character.UnicodeBlock ub = Character.UnicodeBlock.of(myBuffer[i]);
            if(ub == Character.UnicodeBlock.BASIC_LATIN){
                //英文及数字等
                sb.append(myBuffer[i]);
            }else if(ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS){
                //全角半角字符
                int j = (int) myBuffer[i] - 65248;
                sb.append((char)j);
            }else{
                //汉字
                short s = (short) myBuffer[i];
                String hexS = Integer.toHexString(s);
                //所有的unicode 使用两个字节进行编码
                int length = hexS.length();
                String result = "";
                if(length == 2){
                    result = "00"+hexS;
                }else if(length == 3){
                    result = "0"+hexS;
                }else {
                    result = hexS;
                }
                String unicode = "\\u"+result;
                sb.append(unicode.toLowerCase());
            }
        }
        String s = sb.toString();
        System.out.println("utf8ToUnicodeString s="+s);
        return s;
    }


    /**
     * unicode字符串 转换成明文串
     * @param theString
     * @return
     */
    public static String unicodeToUtf8String(String theString) {
        char aChar;
        int len = theString.length();
        StringBuffer outBuffer = new StringBuffer(len);
        for (int x = 0; x < len;) {
            aChar = theString.charAt(x++);
            if (aChar == '\\') {
                aChar = theString.charAt(x++);
                if (aChar == 'u') {
                    // Read the xxxx
                    int value = 0;
                    for (int i = 0; i < 4; i++) {
                        aChar = theString.charAt(x++);
                        switch (aChar) {
                            case '0':
                            case '1':
                            case '2':
                            case '3':
                            case '4':
                            case '5':
                            case '6':
                            case '7':
                            case '8':
                            case '9':
                                value = (value << 4) + aChar - '0';
                                break;
                            case 'a':
                            case 'b':
                            case 'c':
                            case 'd':
                            case 'e':
                            case 'f':
                                value = (value << 4) + 10 + aChar - 'a';
                                break;
                            case 'A':
                            case 'B':
                            case 'C':
                            case 'D':
                            case 'E':
                            case 'F':
                                value = (value << 4) + 10 + aChar - 'A';
                                break;
                            default:
                                throw new IllegalArgumentException(
                                        "Malformed   \\uxxxx   encoding.");
                        }
                    }
                    outBuffer.append((char) value);
                } else {
                    if (aChar == 't')
                        aChar = '\t';
                    else if (aChar == 'r')
                        aChar = '\r';
                    else if (aChar == 'n')
                        aChar = '\n';
                    else if (aChar == 'f')
                        aChar = '\f';
                    outBuffer.append(aChar);
                }
            } else
                outBuffer.append(aChar);
        }
        return outBuffer.toString();
    }


    /**
     * rd 从rd的字节码，转成rf适配的unicode字节
     * @param bytes
     * @return
     */
    public static byte[] rD8BIT2RF8BIT(byte[] bytes){
        int idx = 0;
        int slen = 0;

        //特殊字符RD 两个字节拆成 三个字节
        int specialInt = 0;

        //正常的字符RD 是两个字节为一个字节
        int normalInt = 0;

        int length = bytes.length;
        for (int j = 0; j < length;) {
            int j1 = j + 1;
            int j2 = j + 2;
            if(j1 >= length){
                j1 = length - 1;
            }
            if(j2 >= length){
                j2 = length - 1;
            }
            /*
            23 20 e4 bd a0 61 e0 83 a4 e0
            85 82 68 e5 a5 bd 34 e6 88 91
            e6 98 af 6c 61 69 7a 69 e5 8c
            97 e6 96 b9 e0 85 ba e7 9a 84
             */
            int tmp = bytes[j] & 0xff;
            int _tmp = bytes[j1] & 0xff;
            int _tmp2 = bytes[j2] & 0xff;
            if(0!=(tmp & 0x80) && 0!=(_tmp & 0x80) && 0!=(_tmp2 & 0x80)){
                specialInt++;
                j += 3;
            }else {
                normalInt ++;
                j++;
            }
        }
        System.out.println("rD8BIT2RF8BIT specialInt="+specialInt+",normalInt="+normalInt);

        byte[] bytesDest = new byte[length + normalInt - specialInt];
        while(slen < length){
            int i = 0;
            int tmp = bytes[slen] & 0xff;
            while ((tmp & 0x80) != 0){
                tmp = tmp << 1;
                i++;
            }
            if(i == 0){
                bytesDest[idx++] = 0;
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
            if(slen >= length){
                break;
            }
        }

        System.out.println("rD8BIT2RF8BIT bytes="+ TransformUtils.bytesToHexString(bytes));
        System.out.println("rD8BIT2RF8BIT bytesDest="+ TransformUtils.bytesToHexString(bytesDest));
        System.out.println("rD8BIT2RF8BIT byteslen="+bytes.length+",bytesDestlen="+bytesDest.length);
        return bytesDest;
    }

    /**
     * rf 从unicode字节码，转成rd适配的字节
     * @param bytes
     * @return
     */
    public static byte[] rF8BIT2RD8BIT(byte[] bytes){
        int idx = 0;
        int slen = 0;
        int i = 0;
        int length = bytes.length;

        //特殊字符RD 两个字节拆成 三个字节
        int specialInt = 0;

        //正常的字符RD 是两个字节为一个字节
        int normalInt = 0;

        for (int j = 0; j < length; j += 2) {
            int tmp = bytes[j] & 0xff;
            int _tmp = bytes[j+1] & 0xff;
            if(tmp > 0 || 0!=(_tmp & 0x80)){
                specialInt++;
            }else {
                normalInt ++;
            }
        }

        System.out.println("rF8BIT2RD8BIT specialInt="+specialInt+",normalInt="+normalInt);
        byte[] bytesDest = new byte[length - normalInt + specialInt];

        for (int j = 0; j < length; j+=2) {
            int tmp = bytes[j] & 0xff;
            int _tmp = bytes[j+1] & 0xff;
            if(tmp > 0 || 0!=(_tmp & 0x80)){
                bytesDest[idx] = (byte) (0xE0 | (tmp >> 4) & 0x0F);
                bytesDest[++idx] = (byte)(0x80 | ((tmp << 2) & 0x3C) | (_tmp >> 6) & 0x03);
                bytesDest[++idx] = (byte) (0x80 | (_tmp & 0x3F));
            } else {
                bytesDest[idx] = (byte)(_tmp & 0x7F);

            }
            idx++;
        }
        System.out.println("rF8BIT2RD8BIT bytes="+ TransformUtils.bytesToHexString(bytes));
        System.out.println("rF8BIT2RD8BIT bytesDest="+ TransformUtils.bytesToHexString(bytesDest));
        System.out.println("rF8BIT2RD8BIT byteslen="+bytes.length+",bytesDestlen="+bytesDest.length);
        return bytesDest;
    }
}
