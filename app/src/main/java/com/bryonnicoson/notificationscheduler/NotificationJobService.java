package com.bryonnicoson.notificationscheduler;



import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;


/**
 * Created by bryon on 3/9/18.
 */

public class NotificationJobService extends JobService {

    private Context mContext = getApplicationContext();
    private static final String CHANNEL_ID = "notification_channel";
    private NotificationManager mNotificationManager;

    @Override
    public boolean onStartJob(JobParameters params) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) createChannel();

        PendingIntent contentPendingIntent = PendingIntent.getActivity(this, 0,
        new Intent(this, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(mContext, CHANNEL_ID);
        notificationBuilder
                .setContentTitle("Job Service")
                .setContentText("Your job is running")
                .setContentIntent(contentPendingIntent)
                .setSmallIcon(R.drawable.ic_job_notification)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setAutoCancel(true);

        mNotificationManager.notify(0, notificationBuilder.build());
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private void createChannel() {
        mNotificationManager =
                (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        // params
        String id = CHANNEL_ID;
        CharSequence name = "Notification";
        String description = "JobService notification scheduler";
        int importance = NotificationManager.IMPORTANCE_LOW;

        NotificationChannel mChannel = new NotificationChannel(id, name, importance);
        mChannel.setDescription(description);
        mChannel.setShowBadge(false);
        mChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
        mNotificationManager.createNotificationChannel(mChannel);
    }
}
