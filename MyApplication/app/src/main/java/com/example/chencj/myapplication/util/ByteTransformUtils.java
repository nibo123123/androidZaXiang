package com.example.chencj.myapplication.util;

/**
 * Created by CHENCJ on 2021/1/19.
 */

public class ByteTransformUtils {
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
            ret[i/2] = (byte) ((hexCharToInt(s.charAt(i)) << 4)
                    | hexCharToInt(s.charAt(i+1)));
        }

        return ret;
    }

    public static String
    bytesToHexString(byte[] bytes) {
        if (bytes == null) return null;

        StringBuilder ret = new StringBuilder(2*bytes.length);

        for (int i = 0 ; i < bytes.length ; i++) {
            int b;

            b = 0x0f & (bytes[i] >> 4);

            ret.append("0123456789abcdef".charAt(b));

            b = 0x0f & bytes[i];

            ret.append("0123456789abcdef".charAt(b));
        }

        return ret.toString();
    }

    public static byte[] utf8ToUnicodeByte(byte[] utf8Bytes) {
        byte[] destBytes = new byte[utf8Bytes.length];



        return destBytes;
    }

    /**
     * utf-8 转换成 unicode
     * @author fanhui
     * 2007-3-15
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
                }
                String unicode = "\\u"+result;
                sb.append(unicode.toLowerCase());
            }
        }
        String s = sb.toString();
        System.out.println("utf->unicode="+s);
        return s;
    }


    /**
     * unicode 转换成 utf-8
     * @author fanhui
     * 2007-3-15
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
}
