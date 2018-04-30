package com.derassom.three_pixels.focus.Views;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.derassom.three_pixels.focus.R;
import com.derassom.three_pixels.focus.database.FocusDatabase;
import com.derassom.three_pixels.focus.entity.App;
import com.derassom.three_pixels.focus.utils.TaskAdapter;
import com.derassom.three_pixels.focus.utils.TaskListModle;

import java.util.List;

// Created by Lokmane Krizou on 4/29/2018.
public class mainView  extends Fragment {
    FocusDatabase db;
    Handler mHandler;
   TextView scoredPoints;
   TextView facebookHours;
   TextView messengerHours;
   TextView instegramHours;
   TextView completedTasks;
   TextView uncompletedTasks;

    private long UPDATE_INTERVAL=1000;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_mainview, container, false);
        scoredPoints=(TextView)rootView.findViewById(R.id.scoredPoints);
        facebookHours=(TextView)rootView.findViewById(R.id.facebook_hours);
        messengerHours=(TextView)rootView.findViewById(R.id.messenger_hours);
        instegramHours=(TextView)rootView.findViewById(R.id.instegram_hours);
        completedTasks=(TextView)rootView.findViewById(R.id.completed);
        uncompletedTasks=(TextView)rootView.findViewById(R.id.uncompleted);
        mHandler=new Handler();

        return rootView;
    }

    Runnable UIupdate =new Runnable() {
        @Override
        public void run() {
            try {
                updatUi();
            }finally {
                mHandler.postDelayed(UIupdate,UPDATE_INTERVAL);
            }

        }
    };

    private void updatUi(){
        db = FocusDatabase.getFocusDatabase(getActivity());
        int completdValue=db.taskDao().getCompletedTasks(true).size();
        int uncompletedValue=db.taskDao().getCompletedTasks(false).size();
        long facebookhours=db.appDao().getApp("Facebook").getAppDuration();
        long instegramhours=db.appDao().getApp("Instagram").getAppDuration();
        long messengerhours=db.appDao().getApp("Messenger").getAppDuration();
        int appscore=0;
        for(App app:db.appDao().getAll()){

            if(app.getAppDuration()<app.getTimeAllowed()&& app.isEnabled()){
                appscore++;
            }
        }
        int score=completdValue*15+25*appscore;
        scoredPoints.setText(String.valueOf(score)+" pts");
        facebookHours.setText(String.valueOf(DateUtils.formatElapsedTime(facebookhours / 1000)));
        messengerHours.setText(String.valueOf(DateUtils.formatElapsedTime(messengerhours/ 1000)));
        instegramHours.setText(String.valueOf(DateUtils.formatElapsedTime(instegramhours / 1000)));
        completedTasks.setText(String.valueOf(completdValue));
        uncompletedTasks.setText(String.valueOf(uncompletedValue));

    }
    void startRepeatingTask()
    {
        UIupdate.run();
    }

    void stopRepeatingTask()
    {
        mHandler.removeCallbacks(UIupdate);
    }

    @Override
    public void onResume() {

        super.onResume();
       startRepeatingTask();
    }
    @Override
    public void onDestroy () {

        super.onDestroy();
       stopRepeatingTask();
    }
}
