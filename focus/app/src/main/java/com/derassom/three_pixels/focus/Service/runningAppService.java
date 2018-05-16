package com.derassom.three_pixels.focus.Service;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Intent;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.derassom.three_pixels.focus.MainActivity;
import com.derassom.three_pixels.focus.R;
import com.derassom.three_pixels.focus.database.FocusDatabase;
import com.derassom.three_pixels.focus.entity.App;
import com.derassom.three_pixels.focus.entity.Task;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static java.util.Calendar.getInstance;

public class runningAppService extends IntentService {

    Context context;
    private int SERVICE_INTERVAL =2000; // check the running application each 10millisecond.
    private Handler mhandler;
    Date currentDate= new Date();
    public runningAppService() {
        super("runningAppService");
    }

    @Override
    public int onStartCommand(Intent intent,int flags,int startID  ){

        context=this;
        mhandler=new Handler();
          return super.onStartCommand(intent,flags,startID);

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onHandleIntent(Intent intent) {
         if (intent != null) {
            synchronized (this) {
                try {
                    wait(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
        startRepeatingTask();
    }

    Runnable runnable= new Runnable() {
        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void run() {

            try {
                currentDate=Calendar.getInstance().getTime();
                recentApplicationOpened();
            }finally {
                mhandler.postDelayed(runnable, SERVICE_INTERVAL);
            }
        }
    };
    @TargetApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void recentApplicationOpened(){
        long lastTime=0;
        long totalTimeUsed=0;
        App blockedApp = null;
        FocusDatabase db = FocusDatabase.getFocusDatabase(this);
        UsageStatsManager usageStates = (UsageStatsManager) context.getSystemService( Context.USAGE_STATS_SERVICE);
        Calendar calendar = getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,1);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        long endTime = calendar.getTimeInMillis();
        calendar.add(Calendar.HOUR_OF_DAY, +23);
        long startTime = calendar.getTime().getTime();
        List<String> appList = db.appDao().getPackageName();
        Log.d("timedifference", String.valueOf(DateUtils.formatElapsedTime(endTime / 1000)));
        assert usageStates != null;
        List<UsageStats> usageStatsList = usageStates.queryUsageStats(UsageStatsManager.INTERVAL_DAILY,endTime,startTime);
        String recentPackage="";
        for (UsageStats u : usageStatsList) {
            if (lastTime < u.getLastTimeUsed()) {
                lastTime = u.getLastTimeUsed();
                recentPackage = u.getPackageName();
                totalTimeUsed=u.getTotalTimeInForeground();
                Log.d("ApplicationInfo",u.getPackageName()+ " "
                        +DateUtils.formatElapsedTime(u.getTotalTimeInForeground() / 1000));

        }
         if (appList.contains(recentPackage)) {
             blockedApp=db.appDao().getAppWithPkgName(recentPackage);
           if(blockedApp.isEnabled()){
            //check if the day ends:
             if(blockedApp.getAppDuration()>blockedApp.getTimeAllowed())
             {
                 notifyUser();
             }
                }
             blockedApp.setAppDuration(totalTimeUsed);
             db.appDao().update(blockedApp);

         }}
            if (currentDate.getHours()==1&& currentDate.getMinutes()==0){
            for(Task task:db.taskDao().getCompletedTasks(true)){
                task.setTaskComplete(false);
                db.taskDao().update(task);
            }
            for (App app: db.appDao().getAll()){
                app.setAppDuration(0);
                db.appDao().update(app);
            }
            }


        }
    public void notifyUser(){
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(getApplicationContext(), "notify_001");
        Intent ii = new Intent(getApplicationContext(),MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, ii, 0);
        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
        bigText.bigText("You have passed the Allowed Time for social media: " +
                "\nYou should close the application");
        bigText.setBigContentTitle("Alert");
        bigText.setSummaryText("You should close the application");
        mBuilder.setSmallIcon(R.mipmap.focus);
        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setContentTitle("Focus Alert");
        mBuilder.setContentText("Passed the Allowed Time");
        mBuilder.setPriority(Notification.PRIORITY_MAX);
        mBuilder.setStyle(bigText);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("notify_001",
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            mNotificationManager.createNotificationChannel(channel);
        }

        mNotificationManager.notify(0, mBuilder.build());

    }



    void startRepeatingTask() {
        runnable.run();
    }

    void stopRepeatingTask() {
        mhandler.removeCallbacks(runnable);
    }

    }





