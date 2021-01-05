package com.example.chencj.myapplication.util;

import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneNumberUtils;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static android.provider.Telephony.Sms.Intents.SMS_DELIVER_ACTION;
import static android.telephony.PhoneNumberUtils.PAUSE;
import static android.telephony.PhoneNumberUtils.WAIT;
import static android.telephony.PhoneNumberUtils.WILD;

/**
 * Created by CHENCJ on 2020/11/11.
 */

public class SendSmsUtil {
    //伪造短信到系统信箱
    private static void createFakeSmsReceiveResport(Context context, String sender,
                                                    String body, long dateTime, String format) {
        Log.d("MessageStatusService" + " chencj", "createFakeSmsReceiveResport: body:"+body);
        byte[] pdu = null;
        byte[] scBytes = PhoneNumberUtils
                .networkPortionToCalledPartyBCD("00000000");//以中心地址为 00000000 作为报告短信的标志
        byte[] senderBytes = PhoneNumberUtils
                .networkPortionToCalledPartyBCD(sender);

        int lsmcs = scBytes.length;

        byte[] dateBytes = null;

        Calendar calendar = Calendar.getInstance();

        calendar.setTimeInMillis(dateTime);

        String formatdate = new SimpleDateFormat("yyMMddHHmmss").format(calendar.getTime());

        dateBytes = reverseByte(formatdate);

        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            bo.write(lsmcs);
            bo.write(scBytes);
            bo.write(0x04);
            bo.write((byte) sender.length());
            bo.write(senderBytes);
            bo.write(0x00);
            bo.write(0x08); // encoding: 0 for default 7bit
            bo.write(dateBytes);
            try {
                /*String sReflectedClassName = "com.android.internal.telephony.GsmAlphabet";
                Class cReflectedNFCExtras = Class.forName(sReflectedClassName);
                Method stringToGsm7BitPacked = cReflectedNFCExtras.getMethod(
                        "stringToGsm7BitPacked", new Class[] { String.class });
                stringToGsm7BitPacked.setAccessible(true);
                byte[] bodybytes = (byte[]) stringToGsm7BitPacked.invoke(null,
                        body);*/
                byte[] bodybytes = encodeUCS2(body,null);
                bo.write(bodybytes);
            } catch (Exception e) {
            }

            pdu = bo.toByteArray();
        } catch (Exception e) {
            Log.e("MessageStatusService" + " chencj", "createFakeSmsReceiveResport:", e);
        }

        Intent intent = new Intent();
        intent.setClassName("com.android.mms",
                "com.android.mms.transaction.SmsReceiverService");
        intent.setAction(SMS_DELIVER_ACTION);
        intent.putExtra("pdus", new Object[] { pdu });
        intent.putExtra("format", format);
        context.startService(intent);

        /*SmsMessage fromPdu = SmsMessage.createFromPdu(pdu,format);
        Log.d("MessageStatusService chencj", "createFakeSmsReceiveResport "+fromPdu.getServiceCenterAddress()+"\n"+fromPdu.getOriginatingAddress()+"\n"
                +fromPdu.getTimestampMillis()+"\n"+(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(fromPdu.getTimestampMillis())))+"\n"
                +fromPdu.getMessageBody());*/
    }
    /*
        * 半字节转换
        * */
    private static byte[] reverseByte(String number) {
        if(number!=null && number.length() > 0) {
            byte[] result = new byte[number.length()];

            int digitCount = 0;
            for (int i = 0; i < number.length(); i++) {
                char c = number.charAt(i);
                if (c == '+') continue;
                int shift = ((digitCount & 0x01) == 1) ? 4 : 0;
                result[(digitCount >> 1)] |= (byte) ((charToBCD(c) & 0x0F) << shift);
                digitCount++;
            }
            return result;
        }
        return null;
    }

    private static int
    charToBCD(char c) {
        if (c >= '0' && c <= '9') {
            return c - '0';
        } else if (c == '*') {
            return 0xa;
        } else if (c == '#') {
            return 0xb;
        /*merged by jiangxuqin from L1860 @ 2015-05-15 begin*/
        /*} else if (c == PAUSE) {
            return 0xc;
        } else if (c == WILD) {
            return 0xd;
        } else if (c == WAIT) {
            return 0xe;*/
        } else if (c == PAUSE) {//gaofeng add Req00000060
            return 0xc;
        } else if (c == WILD || c == WAIT) {
            return 0xd;
         /*merged by jiangxuqin from L1860 @ 2015-05-15 end*/
        } else {
            throw new RuntimeException ("invalid char for BCD " + c);
        }
    }

    /*
    * UCS2文本编码方式
    *
    * */

    private static byte[] encodeUCS2(String message, byte[] header)
            throws Exception {
        byte[] userData, textPart;
        textPart = message.getBytes("utf-16be");

        if (header != null) {
            // Need 1 byte for UDHL
            userData = new byte[header.length + textPart.length + 1];

            userData[0] = (byte)header.length;
            System.arraycopy(header, 0, userData, 1, header.length);
            System.arraycopy(textPart, 0, userData, header.length + 1, textPart.length);
        }
        else {
            userData = textPart;
        }
        byte[] ret = new byte[userData.length+1];
        ret[0] = (byte) (userData.length & 0xff );
        System.arraycopy(userData, 0, ret, 1, userData.length);
        return ret;
    }
}
