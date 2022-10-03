package com.gcbuying.app.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.gcbuying.app.R;

import java.util.ArrayList;

public class SplashAdapter extends PagerAdapter {

    private Context context;
    private ArrayList<Integer> list;

    public SplashAdapter(Context context, ArrayList<Integer> list) {
        this.context = context;
        this.list = list;
    }


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

        
        View view = LayoutInflater.from(context).inflate(R.layout.item_viewpager , null );
        ImageView imageView =view.findViewById(R.id.imageview);
        Glide.with(context).asBitmap().load(list.get(position)).into(imageView);
        container.addView(view,0);
        return  view;
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        //remove object from conatiner
        container.removeView((View) object);
    }
}
