package com.derassom.three_pixels.focus;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.IntentService;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Intent;
import android.content.Context;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.util.Calendar;
import java.util.List;

import static android.content.ContentValues.TAG;

public class runningAppService extends IntentService {

    public runningAppService() {
        super("runningAppService");
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onHandleIntent(Intent intent) {

    }


}


