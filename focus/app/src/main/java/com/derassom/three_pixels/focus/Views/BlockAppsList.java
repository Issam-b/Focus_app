package com.derassom.three_pixels.focus.Views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.derassom.three_pixels.focus.MainActivity;
import com.derassom.three_pixels.focus.R;
import com.derassom.three_pixels.focus.database.FocusDatabase;
import com.derassom.three_pixels.focus.entity.App;

// Created by Lokmane Krizou on 4/6/2018.
public class BlockAppsList extends AppCompatActivity {

    public CheckBox facebookBox;
    public CheckBox instegramBox;
    FocusDatabase db;
    App facebook;
    App instagram;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_block_apps_list);

        facebookBox = (CheckBox) findViewById(R.id.facebook);
        instegramBox = (CheckBox) findViewById(R.id.instegram);
        db  = FocusDatabase.getFocusDatabase(this);


        facebook =new App();
        facebook.setPkgName("com.facebook.katana");
        facebook.setAppName("Facebook");
        facebook.setNumAllowed(6);
        facebook.setAppDuration(3);
        facebook.setAppBonus(150);

        instagram =new App();
        instagram.setPkgName("com.instagram.android");
        instagram.setAppName("Instagram");
        instagram.setNumAllowed(6);
        instagram.setAppDuration(3);
        instagram.setAppBonus(150);

        setCheckBox(facebookBox,facebook);
        setCheckBox(instegramBox,instagram);
    }

    private void setCheckBox(CheckBox box,App app){

        if (db.appDao().getPackageName().contains(app.getPkgName())) {

            Log.d("Exist",app.toString());
            if (!box.isChecked()) {
                    box.setChecked(true);

            }
        }
        else{
         //do Nothing
        }

    }
    private void checkBoxClick(CheckBox box,App app){

        if (db.appDao().getPackageName().contains(app.getPkgName())) {

            Log.d("Exist",app.toString());
            if (box.isChecked()) {
                //nothing
            } else {

                app=db.appDao().getApp(app.getAppName());
                db.appDao().delete(app);
            }
        }
        else{
            if(box.isChecked()) {
                db.appDao().insert(app);
                }
            else{
                //do nothing
            }
        }

    }
    public void block(View view) {

        checkBoxClick(facebookBox,facebook);
        checkBoxClick(instegramBox,instagram);

        startActivity(new Intent(this, MainActivity.class));
    }

}

