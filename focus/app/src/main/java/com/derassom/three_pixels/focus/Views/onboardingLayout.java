package com.derassom.three_pixels.focus.Views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.derassom.three_pixels.focus.MainActivity;
import com.derassom.three_pixels.focus.R;

// Created by Lokmane Krizou on 4/6/2018.
public class onboardingLayout extends AppCompatActivity {

    private ViewPager viewPager;
    private LinearLayout dotLayout;
    private slideController mSlideController;
    private TextView[] mDots;
    private Button nextBtn;
    private Button prvBtn;
    private int currentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding_layout);
        viewPager=(ViewPager)findViewById(R.id.viewpager);
        dotLayout=(LinearLayout)findViewById(R.id.dots);
        nextBtn=(Button)findViewById(R.id.nextBtn);
        prvBtn=(Button)findViewById(R.id.prvBtn);
        mSlideController=new slideController(this);
        viewPager.setAdapter(mSlideController);
        addDots(0);
        viewPager.addOnPageChangeListener(viewListener);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nextBtn.getText()!="Start"){
                viewPager.setCurrentItem(currentPage+1);
                }
                else {
                    SharedPreferences.Editor sharedPreferencesEditor =
                            PreferenceManager.getDefaultSharedPreferences(onboardingLayout.this).edit();
                    sharedPreferencesEditor.putBoolean("firstTime", true);
                    sharedPreferencesEditor.apply();
                    startActivity(new Intent(onboardingLayout.this, BlockAppsList.class));
                }
            }
        });
        prvBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(currentPage-1);
            }
        });
    }
    public void addDots(int position){
        mDots=new TextView[mSlideController.slideHeadings.length];
        dotLayout.removeAllViews();
        for (int i=0;i<mDots.length;i++){
            mDots[i]=new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.colorPrimary));
            dotLayout.addView(mDots[i]);
        }
        if(mDots.length>0){
            mDots[position].setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        }
    }
    ViewPager.OnPageChangeListener viewListener =new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            addDots(position);
            currentPage=position;
            if(position==0){
                nextBtn.setEnabled(true);
                prvBtn.setEnabled(false);
                prvBtn.setVisibility(View.INVISIBLE);
                nextBtn.setText("Next");
                prvBtn.setText("");
            }
            else if(position==mDots.length-1){
                nextBtn.setEnabled(true);
                prvBtn.setEnabled(true);
                prvBtn.setVisibility(View.VISIBLE);
                nextBtn.setText("Start");
                prvBtn.setText("Back");
            }
            else {
                nextBtn.setEnabled(true);
                prvBtn.setEnabled(true);
                prvBtn.setVisibility(View.VISIBLE);
                nextBtn.setText("Next");
                prvBtn.setText("Back");
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}
