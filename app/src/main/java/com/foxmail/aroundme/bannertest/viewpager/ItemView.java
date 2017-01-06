package com.foxmail.aroundme.bannertest.viewpager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.foxmail.aroundme.banner.viewpager.ShowItemView;
import com.foxmail.aroundme.bannertest.R;

/**
 * Created by gzl on 1/4/17.
 */

public class ItemView implements ShowItemView{

    private Context context;

    public ItemView (Context context) {
        this.context = context;
    }

    @Override
    public View showItemView(ViewGroup parent, String url) {

        View view = LayoutInflater.from(context).inflate(R.layout.item, parent, false);

        ImageView imageView = (ImageView) view.findViewById(R.id.imageview);

        Glide.with(context).load(url).into(imageView);

        return view;
    }
}
