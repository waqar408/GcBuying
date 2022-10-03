//package com.example.gc_buying.Server;
//
//import android.app.NotificationChannel;
//import android.app.NotificationManager;
//import android.app.PendingIntent;
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.graphics.Color;
//import android.media.RingtoneManager;
//import android.net.Uri;
//import android.os.Build;
//
//import androidx.core.app.NotificationCompat;
//import androidx.core.content.ContextCompat;
//
//
//public class MyFirebaseMessagingService extends FirebaseMessagingService {
//
//    String TAG="";
//    public static final String NOTIFICATION_CHANNEL_ID = "52855";
//
//
//    String title,message;
//    Intent intent;
//
//    SharedPreferences sp;
//    int requestCode;
//
//    NotificationChannel mChannel;
//
//    public static boolean fromNotification = false;
//
//    @Override
//    public void onMessageReceived(RemoteMessage remoteMessage) {
//
//        sendNotification(remoteMessage);
//
//    }
//    private void sendNotification(RemoteMessage remote_message) {
//
//
//        sp = getSharedPreferences("REQUESTCODE", MODE_PRIVATE);
//        requestCode = sp.getInt("CODE", 0);
////        sp.edit().putInt("CODE", requestCode).apply();
//
//
//        title = remote_message.getData().get("title");
//        message = remote_message.getData().get("body");
//
//
//
//
//
//        int color = 0xff123456;
//        color = getResources().getColor(R.color.black);
//        color = ContextCompat.getColor(MyFirebaseMessagingService.this, R.color.black);
//
//
//        intent = new Intent(this, MainActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, requestCode ,intent,PendingIntent.FLAG_ONE_SHOT);
//
//        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//
//        NotificationCompat.Builder notificatin_builder = new NotificationCompat.Builder(this);
//
//        notificatin_builder
//                .setSmallIcon(R.drawable.logo)
////                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.logo))
//                .setContentTitle(title)
//                .setContentIntent(pendingIntent)
//                .setContentText(message)
//                .setSound(defaultSoundUri)
//                .setDefaults(android.app.Notification.DEFAULT_ALL)
//                .setAutoCancel(true)
//                .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
//                .setColor(color)
//                .setPriority(android.app.Notification.PRIORITY_HIGH);
//
//        NotificationManager notification_manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
//
//
//
//        // for notification in oreo
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
//        {
//            int importance = NotificationManager.IMPORTANCE_HIGH;
//            mChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "REMINDER_APP_CHANNEL", importance);
//            mChannel.enableLights(true);
//            mChannel.setLightColor(Color.RED);
//            mChannel.enableVibration(true);
////            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
//            assert notification_manager != null;
//            notificatin_builder.setChannelId(NOTIFICATION_CHANNEL_ID);
//            notification_manager.createNotificationChannel(mChannel);
//        }
//        assert notification_manager != null;
//
//        // for notification in oreo
//        notification_manager.notify(requestCode,notificatin_builder.build());
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
//            notification_manager.createNotificationChannel(mChannel);
//        }
//
//        requestCode = requestCode + 1;
//
//        sp = getSharedPreferences("REQUESTCODE", MODE_PRIVATE);
//        sp.edit().putInt("CODE", requestCode).apply();
//
//    }
//
//}
