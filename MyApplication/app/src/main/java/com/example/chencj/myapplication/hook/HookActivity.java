package com.example.chencj.myapplication.hook;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.chencj.myapplication.MainActivity;
import com.example.chencj.myapplication.R;
import com.example.chencj.myapplication.activity.EmptyActivity;

/**
 * Created by CHENCJ on 2020/12/24.
 */

public class HookActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hook_view);
        Button button = (Button)findViewById(R.id.hookBtn);
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(HookActivity.this, "click hook", Toast.LENGTH_SHORT).show();
                //startActivity(new Intent(HookActivity.this,NoRegisterActivity.class));
                clickNotification(HookActivity.this);
            }
        };
        Log.d("HookActivity chencj ", "onCreate: onClickListenerProxy="+onClickListener);
        button.setOnClickListener(onClickListener);
        //HookHelper.hookOnClickListener(this,button);
        try {
            //HookHelper.hookActivityThreadMh(this);
            //HookHelper.hookAMSStartActivity(this);
            HookHelper.hookNotificationManager(HookActivity.this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static void clickNotification(Context context){

        Intent intent = new Intent(context, EmptyActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        //String channelId = createNotificationChannel("my_channel_ID", "my_channel_NAME", NotificationManager.IMPORTANCE_HIGH);
        Notification.Builder notification = new Notification.Builder(context)
                .setContentTitle("通知")
                .setContentText("你好，世界!")
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true)
                .setWhen(System.currentTimeMillis());


        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(16657, notification.build());
    }
}
