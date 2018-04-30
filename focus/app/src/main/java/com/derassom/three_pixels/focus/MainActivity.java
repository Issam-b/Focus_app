package com.derassom.three_pixels.focus;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.AppOpsManager;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.derassom.three_pixels.focus.Service.runningAppService;
import com.derassom.three_pixels.focus.Views.TabAppInfo;
import com.derassom.three_pixels.focus.Views.TabBlockAppsList;
import com.derassom.three_pixels.focus.Views.TabTasks;
import com.derassom.three_pixels.focus.Views.mainView;
import com.derassom.three_pixels.focus.Views.onboardingLayout;
import com.derassom.three_pixels.focus.database.FocusDatabase;
import com.derassom.three_pixels.focus.entity.App;
import com.derassom.three_pixels.focus.entity.Task;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    Handler  mhandler;
    int TargetScore=200;
    boolean dismised=false;
    int totalScore;
    FocusDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mhandler=new Handler();
        db=FocusDatabase.getFocusDatabase(this);
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this);
        // Check if we need to display our Onboardinglayout
        if (!sharedPreferences.getBoolean("openedFirstTime", false)) {
            // The user hasn't seen the onboardingLayout yet, so show it
            startActivity(new Intent(this, onboardingLayout.class));
        }
else {

            //run intent service (not used for now)
            Intent i = new Intent(this, runningAppService.class);
            startService(i);


            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
            // Create the adapter that will return a fragment for each of the three
            // primary sections of the activity.
            mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

            // Set up the ViewPager with the sections adapter.
            mViewPager = (ViewPager) findViewById(R.id.container);
            mViewPager.setAdapter(mSectionsPagerAdapter);

            TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

            mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
            tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
            toolbar.setTitle(mSectionsPagerAdapter.getPageTitle(mViewPager.getCurrentItem()));

        }
    }
    Runnable runnable=new Runnable() {
        @Override
        public void run() {

           try{
               checkScore();

           }finally {
               mhandler.postDelayed(runnable,1000);
           }
        }
    };
    public void checkScore(){

        totalScore=updateScore(db);

        if(totalScore>=TargetScore&& !dismised){
            MyCustomAlertDialog();
            dismised=true;
        }else if(totalScore<TargetScore){
            dismised=false;
        }

    }
    protected void onResume() {

        super.onResume();
        if (!checkForPermission(this)){
            // Create alertDialog
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.permission_dialog_message)
                    .setTitle(R.string.permission_dialog_title);
            builder.setPositiveButton(R.string.request, new DialogInterface.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                public void onClick(DialogInterface dialog, int id) {
                    // User clicked request addbutton
                    startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));     }
            });
            builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User cancelled the dialog
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }

        startRepeatingTask();
    }
    //check for permission of enabled usage data
    private boolean checkForPermission(Context context) {
        boolean granted = false;
        AppOpsManager appOps = (AppOpsManager) context
                .getSystemService(Context.APP_OPS_SERVICE);
        int mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,
                android.os.Process.myUid(), context.getPackageName());

        if (mode == AppOpsManager.MODE_DEFAULT) {
            granted = (context.checkCallingOrSelfPermission(android.Manifest.permission.PACKAGE_USAGE_STATS) == PackageManager.PERMISSION_GRANTED);
        } else {
            granted = (mode == AppOpsManager.MODE_ALLOWED);
        }
        return granted;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up addbutton, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(MainActivity.this, com.derassom.three_pixels.focus.Settings.class));

            return true;
        }

        return super.onOptionsItemSelected(item);
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
    public void onDestroy () {

        super.onDestroy();
        stopRepeatingTask();
    }

    @SuppressLint("SetTextI18n")
    public void MyCustomAlertDialog(){
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.customdialog);
        dialog.setTitle("Congratulations");

        // set the custom dialog components - text, image and button
        TextView text = (TextView) dialog.findViewById(R.id.dialogDiscription);
        text.setText("You have  achieved the Target Points Today");
        ImageView image = (ImageView) dialog.findViewById(R.id.imageDialog);
        image.setImageResource(R.drawable.logoimage);
        Button dialogButton = (Button) dialog.findViewById(R.id.hello);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                dismised=true;
            }
        });

        dialog.show();
    }
    private  int updateScore(FocusDatabase db){
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
        Log.d("totalScore",String.valueOf(totalScore));
        return totalScore;
    }

    /**
     * A placeholder fragment containing a simple view.
     */

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

   @Override
        public Fragment getItem(int position) {
            switch(position){
                case 0:
                    mainView tab0= new mainView();
                    return tab0;

                case 1:
                    TabTasks tab1= new TabTasks();
                    return tab1;

                case 2:
                    TabBlockAppsList tab2= new TabBlockAppsList();
                    return tab2;
                case 3:
                    TabAppInfo tab3= new TabAppInfo();
                    return tab3;

            }
            return null;
        }

        @Override
        public CharSequence getPageTitle(int position){

            switch (position){
                case 0:
                    return "Focus";
                case 1:
                    return "Tasks";
                case 2:
                    return "Block Apps";
                case 3:
                    return "App Info";
            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 4;
        }
    }
}
