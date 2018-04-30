package com.derassom.three_pixels.focus.Views;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerTitleStrip;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.derassom.three_pixels.focus.R;
import com.derassom.three_pixels.focus.database.FocusDatabase;
import com.derassom.three_pixels.focus.entity.App;
import com.derassom.three_pixels.focus.entity.Task;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.ArrayList;
import java.util.List;

// Created by Lokmane Krizou on 4/16/2018.
public class TabAppInfo extends Fragment {
    private static final int UPDATE_INTERVAL =1000 ;
    FocusDatabase db;
    BarChart barChart;
    float[] YData;
    Handler mhandler;
    TextView score;
      public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
          View rootView = inflater.inflate(R.layout.fragment_appinfo, container, false);
          db = FocusDatabase.getFocusDatabase(getActivity());
          mhandler=new Handler();
          barChart=(BarChart)rootView.findViewById(R.id.barchart);
          score=(TextView)rootView.findViewById(R.id.scorepoint);
          return rootView;
      }

        Runnable runnable=new Runnable() {
        @Override
        public void run() {

            try {
                updateGraph();
                updateScore();
            }finally {
                mhandler.postDelayed(runnable, UPDATE_INTERVAL);
            }
        }

        };

          private void updateGraph() {
        ArrayList<BarEntry> barEntries=new ArrayList<>();
        YData=getTimeInHours();
        for (int i=0;i<YData.length;i++) {
            barEntries.add(new BarEntry(i, YData[i]));
        }
        barChart.setDrawBarShadow(false);
        barChart.setFitBars(true);
        barChart.setDrawValueAboveBar(true);
        barChart.setClipValuesToContent(true);
        barChart.setDrawingCacheEnabled(true);
        barChart.animate();
        barChart.dispatchDisplayHint(View.VISIBLE);
        barChart.setMaxVisibleValueCount(24);
        barChart.setPinchZoom(false);
        barChart.setDrawGridBackground(true);
        barChart.setScrollBarSize(1);
        barChart.getDescription().setEnabled(false);
        BarDataSet appDataSet=new BarDataSet(barEntries,"Applications Info");
        appDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        appDataSet.setValueTextColor(Color.rgb(155, 155, 155));
        appDataSet.setValueTextSize(18);
        appDataSet.setFormSize(5f);

        BarData data=new BarData(appDataSet);
        data.setBarWidth(0.4f);
        barChart.notifyDataSetChanged();
        barChart.setData(data);
        XAxis xAxis=barChart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new MyAxisValueFormatter(db.appDao().getAppsName()));
        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setLabelCount(8, false);
        leftAxis.setGranularity(1f);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setValueFormatter(new MyYAxisValueFormatter());
        leftAxis.setSpaceTop(15f);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        YAxis rightAxis = barChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setLabelCount(8, false);
        rightAxis.setSpaceTop(15f);
        rightAxis.setAxisMinimum(0f);
        rightAxis.setEnabled(false);

    }
        private float[] getTimeInHours(){

            List<Long> appInfo= db.appDao().getAppsDuration();

            float[] timeInHours=new float[appInfo.size()];
            Log.d("AppInfo", String.valueOf(appInfo.size()));
           // Log.d("AppInfo", String.valueOf(appInfo.get(1)));

            for (int i=0;i<appInfo.size();i++){
                timeInHours[i]=(float)appInfo.get(i)/(1000*60*60);
                Log.d("AppInfo", String.valueOf(timeInHours[i])+","+String.valueOf(appInfo.get(i)) );
            }

            return timeInHours;
        }
        private  void updateScore(){
        int completedTasks=0;
        int appbonus=0;
            for(Task task:db.taskDao().getCompletedTasks(true)){
                completedTasks++;
            }
            for(App app:db.appDao().getAll()){

                if(app.getAppDuration()<app.getTimeAllowed()&& app.isEnabled()){
                    appbonus++;
                }
            }
            int totalScore=appbonus*25+completedTasks*15;
            Log.d("Score",String.valueOf(totalScore));
            score.setText(String.valueOf(totalScore));
        }
        public class MyAxisValueFormatter implements IAxisValueFormatter{

            private List<String> mValues;
            public MyAxisValueFormatter(List<String> values){

                Log.d("AppInfo", String.valueOf(values.size()));

                this.mValues=values;
            }
            @Override
            public String getFormattedValue(float value, AxisBase axis) {

                return mValues.get((int)value);
            }
        }
        public class MyYAxisValueFormatter implements IAxisValueFormatter{

        public MyYAxisValueFormatter(){


        }
        @Override
        public String getFormattedValue(float value, AxisBase axis) {

            return (int)value+":00H";
        }
    }

    void startRepeatingTask()
    {
        runnable.run();
    }

    void stopRepeatingTask()
    {
        mhandler.removeCallbacks(runnable);
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
