package com.foxmail.aroundme.bannertest;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.foxmail.aroundme.banner.viewpager.BannerLayout;

/**
 * Created by Administrator on 2016/12/21 0021.
 */

public class GlideImageLoader implements BannerLayout.ImageLoader {
    @Override
    public void displayImage(Context context, String path, ImageView imageView) {
        Glide.with(context).load(path).centerCrop().into(imageView);
    }
}
