package com.derassom.three_pixels.focus;

import android.annotation.TargetApi;
import android.app.IntentService;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Intent;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class runningAppService extends IntentService {

    Context context;
    private int SERVICE_INTERVAL =10; // check the running application each 10ms.
    private Handler mhandler;

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
        //TODO: Make the service run even onDestroy application
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
                recentApplicationOpened();
            }finally {
                mhandler.postDelayed(runnable, SERVICE_INTERVAL);
            }
        }
    };
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void recentApplicationOpened(){
        UsageStatsManager usageStates = (UsageStatsManager) context.getSystemService( Context.USAGE_STATS_SERVICE);
        Calendar calendar = Calendar.getInstance();
        long endTime = calendar.getTimeInMillis();
        calendar.add(Calendar.SECOND, -30);
        long startTime = calendar.getTimeInMillis();

        List<UsageStats> usageStatsList = usageStates.queryUsageStats(UsageStatsManager.INTERVAL_DAILY,startTime,endTime);
        ArrayList<String> packageNames= new ArrayList<String>();
        long lastTime=0;
        String recentPackage="";
        for (UsageStats u : usageStatsList) {
            if (lastTime < u.getLastTimeUsed()) {
                lastTime = u.getLastTimeUsed();
                recentPackage = u.getPackageName();
            }
            if (u.getPackageName() != null && u.getLastTimeUsed() / 1000 > 0) {
                packageNames.add(u.getPackageName());
                Log.d("PACKAGENAME",  u.getPackageName());

            }
        }
        String facebook = "com.facebook.katana";
        if (recentPackage.equals(facebook)) {
            //TODO CREATE ACTIVITY THAT POPS UP WHEN WE OPEN FACEBOOK;
            Log.d("lock", facebook + " Locked");
            Intent passCodeIntent= new Intent(this,passLocker.class);
            passCodeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(passCodeIntent);
        }

    }

    void startRepeatingTask() {
        runnable.run();
    }

    void stopRepeatingTask() {
        mhandler.removeCallbacks(runnable);
    }
}




