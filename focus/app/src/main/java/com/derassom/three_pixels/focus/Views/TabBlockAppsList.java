package com.derassom.three_pixels.focus.Views;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.derassom.three_pixels.focus.R;
import com.derassom.three_pixels.focus.database.FocusDatabase;
import com.derassom.three_pixels.focus.entity.App;
import com.derassom.three_pixels.focus.utils.DatabaseInit;

// Created by Lokmane Krizou on 4/16/2018.
public class TabBlockAppsList extends Fragment {
    public CheckBox facebookBox;
    public CheckBox instegramBox;
    public CheckBox messengerBox;
    FocusDatabase db;
    Button submit;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_block_apps_list, container, false);
        facebookBox = (CheckBox) rootView.findViewById(R.id.facebook);
        instegramBox = (CheckBox) rootView.findViewById(R.id.instegram);
        messengerBox = (CheckBox) rootView.findViewById(R.id.messenger);
        submit = (Button)rootView.findViewById(R.id.block);
        db = FocusDatabase.getFocusDatabase(getActivity());
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                block(v);
            }
        });

        setCheckBox(facebookBox,db.appDao().getApp("Facebook"));
        setCheckBox(instegramBox,db.appDao().getApp("Instagram"));
        setCheckBox(messengerBox,db.appDao().getApp("Messenger"));

        return rootView;
    }


    private void setCheckBox(CheckBox box,App app){
            if (app.isEnabled()){
            if (!box.isChecked()) {
                box.setChecked(true);
            }
            else{
                //do nothing
                }
            }
        else{
        if (box.isChecked()) {
            box.setChecked(false);
        }
        else{
            //do nothing
        }
    }

    }
    private void checkBoxClick(CheckBox box,App app){
        if(box.isChecked()){
            app.setEnabled(true);

            db.appDao().update(app);
        }
        else{
            app.setEnabled(false);
            db.appDao().update(app);
        }
    }
    public void block(View view) {

        checkBoxClick(facebookBox,db.appDao().getApp("Facebook"));
        checkBoxClick(instegramBox,db.appDao().getApp("Instagram"));
        checkBoxClick(messengerBox,db.appDao().getApp("Messenger"));
    DatabaseInit.printApps(db);
        Toast.makeText(getActivity(), "Submitted", Toast.LENGTH_SHORT).show();
    }

}
