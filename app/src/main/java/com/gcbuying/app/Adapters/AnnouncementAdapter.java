package com.gcbuying.app.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;


import com.gcbuying.app.R;

import java.util.ArrayList;

public class AnnouncementAdapter  extends PagerAdapter {

    private Context context;
    private ArrayList<String> list;

    public AnnouncementAdapter(Context context, ArrayList<String> list) {
        this.context = context;
        this.list = list;
    }
 ///   git remote set-url origin git@bitbucket.org:mtechzpk/gcbuying-androidlatest.git


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {

        // pass object in view
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        //add layout
        // add wediget ....Imageview
        //add in container
        // return view

        View view = LayoutInflater.from(context).inflate(R.layout.item_announcement_viewpager , null );
        TextView textView = view.findViewById(R.id.text);
        textView.setText(list.get(position));
        container.addView(view,0);
        return  view;
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        //remove object from conatiner
        container.removeView((View) object);
    }
}
