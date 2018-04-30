package com.derassom.three_pixels.focus.Views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.derassom.three_pixels.focus.R;

// Created by Lokmane Krizou on 4/6/2018.
public class slideController extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;
    public slideController(Context context){
        this.context=context;
    }
    public int[] slideImages={
            R.drawable.start_logo,
            R.drawable.logo_blogapps,
            R.drawable.ic_launcher_background
    };
    public int[] discreptionImages={
            R.drawable.functions,
            R.drawable.list,
            R.drawable.ic_launcher_background
    };
    public String[]slideHeadings={
            "Get Started",
            "",
            "GetStarted"
    };
    public String[]slideContent={
            "description of the app",
            "our objectvie is blalbalbla",
            "click start to start the app"
    };

    @Override
    public int getCount() {
        return slideHeadings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==(RelativeLayout)object;
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position){

        layoutInflater=(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view=layoutInflater.inflate(R.layout.slide_layout,container,false);
        ImageView image= (ImageView)view.findViewById(R.id.image);
        TextView heading=(TextView)view.findViewById(R.id.heading);
       // TextView content=(TextView)view.findViewById(R.id.content);
        ImageView discreption=(ImageView)view.findViewById(R.id.discreption);

        image.setImageResource(slideImages[position]);
        discreption.setImageResource(discreptionImages[position]);
        heading.setText(slideHeadings[position]);
      //  content.setText(slideContent[position]);
        container.addView(view);

        return view;
    }
    @Override
    public void destroyItem(ViewGroup container,int position, Object object){
        container.removeView((RelativeLayout)object);
    }

}
