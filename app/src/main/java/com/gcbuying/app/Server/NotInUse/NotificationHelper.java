//package com.example.gc_buying.Server;
//
//import android.annotation.TargetApi;
//import android.app.Notification;
//import android.app.NotificationChannel;
//import android.app.NotificationManager;
//import android.app.PendingIntent;
//import android.content.Context;
//import android.content.ContextWrapper;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.graphics.Color;
//import android.media.RingtoneManager;
//import android.net.Uri;
//import android.os.Build;
//import android.os.Bundle;
//import android.util.Log;
//
//import com.example.mintiest.Activities.MainActivity;
//import com.example.mintiest.BuildConfig;
//import com.example.mintiest.R;
//
//import java.util.Map;
//
//public class NotificationHelper extends ContextWrapper {
//    private static final String chanlle_id = BuildConfig.APPLICATION_ID;
//    private static final String chanlle_name = "SEEKS APPS";
//    NotificationManager manager;
//    Bitmap logo;
//
//    public NotificationHelper(Context base) {
//        super(base);
//        creatc_chaneels();
//    }
//
//    @TargetApi(Build.VERSION_CODES.O)
//    private void creatc_chaneels() {
//        NotificationChannel notificationChannel = new NotificationChannel(chanlle_id,
//                chanlle_name, NotificationManager.IMPORTANCE_DEFAULT);
//        notificationChannel.enableLights(true);
//        notificationChannel.enableVibration(true);
//        notificationChannel.setLightColor(Color.GREEN);
//        notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
//        getManager().createNotificationChannel(notificationChannel);
//    }
//
//    public NotificationManager getManager() {
//        if (manager == null)
//            manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        return manager;
//
//    }
//
//
//    @TargetApi(Build.VERSION_CODES.O)
//    public void getnotifictaion(Map<String, String> data) {
//
//        Bundle msg = new Bundle();
//        for (String key : data.keySet()) {
//            Log.e(key, data.get(key));
//            msg.putString(key, data.get(key));
//        }
//
//
//        Intent intent = new Intent(this, MainActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        if (msg.containsKey("action")) {
//            intent.putExtra("action", msg.getString("action"));
//        }
//        PendingIntent pendingIntent = PendingIntent.getActivity(this,
//                1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//        Uri notification_sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        Notification.Builder builder = new Notification.Builder(getApplicationContext(),
//                chanlle_id)
//                .setContentTitle(msg.getString("title"))
//                .setContentText(msg.getString("msg"))
//                .setStyle(new Notification.BigTextStyle()
//                        .bigText(msg.getString("msg")))
//                .setSmallIcon(R.mipmap.ic_launcher)
//                .setSound(notification_sound)
//                .setContentIntent(pendingIntent)
//                .setAutoCancel(false);
//        getManager().notify(1, builder.build());
//    }
//
//}
