package org.itzheng.ring.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import java.util.Arrays;


/**
 * Create notification channel
 * 先init();
 * 需要发送通知时使用:{@link NotificationUtils#sendSimpleNotification}
 *
 * @author peter
 */

public class NotificationUtils {
    public static class ChannelID {
        public final static String CRITICAL = "critical";
        public final static String IMPORTANCE = "importance";
        public final static String DEFAULT = "default";
        public final static String LOW = "low";
        public final static String MEDIA = "media";
    }


    private static class ChannelName {
        private static final String CRITICAL = "Critical events";
        private static final String IMPORTANCE = "Importance events";
        private static final String DEFAULT = "Default events";
        private static final String LOW = "Low events";
        private static final String MEDIA = "Media events";
    }

    /**
     * 初始化
     */
    public static void init(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createAllNotificationChannels(context);
        }
    }

    /**
     * 创建面板
     *
     * @param context
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private static void createAllNotificationChannels(Context context) {
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (nm == null) {
            return;
        }
        NotificationChannel mediaChannel = new NotificationChannel(
                ChannelID.MEDIA,
                ChannelName.MEDIA,
                NotificationManager.IMPORTANCE_DEFAULT);
        mediaChannel.setSound(null, null);
        mediaChannel.setVibrationPattern(null);

        nm.createNotificationChannels(Arrays.asList(
                new NotificationChannel(
                        ChannelID.CRITICAL,
                        ChannelName.CRITICAL,
                        NotificationManager.IMPORTANCE_HIGH),
                new NotificationChannel(
                        ChannelID.IMPORTANCE,
                        ChannelName.IMPORTANCE,
                        NotificationManager.IMPORTANCE_DEFAULT),
                new NotificationChannel(
                        ChannelID.DEFAULT,
                        ChannelName.DEFAULT,
                        NotificationManager.IMPORTANCE_LOW),
                new NotificationChannel(
                        ChannelID.LOW,
                        ChannelName.LOW,
                        NotificationManager.IMPORTANCE_MIN),
                //custom notification channel
                mediaChannel
        ));
    }

    /**
     * 发送警告
     *
     * @param context
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static void sendWarningNotification(Context context) {
        sendSimpleNotification(context, "报警提示", "您当前有报警信息，请及时处理",
                android.R.drawable.stat_notify_call_mute);
    }

    /**
     * 发送简单通知
     *
     * @param context
     * @param title
     * @param msg
     * @param icon
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static void sendSimpleNotification(Context context, String title, String msg, int icon) {
        //点击打开APP
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
        PendingIntent pendingIntent = PendingIntent.getActivity(
                context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        // 创建通知(标题、内容、图标)
        Notification.Builder nb = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            nb = new Notification.Builder(context, ChannelID.LOW);
        } else {
            nb = new Notification.Builder(context);
        }
        nb.setContentTitle(title)
                .setContentText(msg)
                .setSmallIcon(icon)
                .setShowWhen(true)
                .setAutoCancel(true)//点击消失
                .setContentIntent(pendingIntent)//跳转到主界面
                .build();
        //发送通知
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(0, nb.build());
    }
}