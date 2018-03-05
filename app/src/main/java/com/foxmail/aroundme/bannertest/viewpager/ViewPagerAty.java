package com.foxmail.aroundme.bannertest.viewpager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.foxmail.aroundme.banner.indicator.IOnPageChangeListener;
import com.foxmail.aroundme.banner.viewpager.BannerViewPager;
import com.foxmail.aroundme.banner.indicator.IOnItemClickListener;
import com.foxmail.aroundme.bannertest.MainActivity;
import com.foxmail.aroundme.bannertest.R;
import com.foxmail.aroundme.bannertest.recyclerview.RecyclerAty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gzl on 1/2/17.
 *
 */

public class ViewPagerAty extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gaty);

        BannerViewPager bannerViewPager = (BannerViewPager) findViewById(R.id.banner);

        final List<String> urls = new ArrayList<>();
        urls.add("http://img3.imgtn.bdimg.com/it/u=2674591031,2960331950&fm=23&gp=0.jpg");
        urls.add("http://img5.imgtn.bdimg.com/it/u=3639664762,1380171059&fm=23&gp=0.jpg");
        urls.add("http://img0.imgtn.bdimg.com/it/u=1095909580,3513610062&fm=23&gp=0.jpg");
        urls.add("http://img4.imgtn.bdimg.com/it/u=1030604573,1579640549&fm=23&gp=0.jpg");
        urls.add("http://img5.imgtn.bdimg.com/it/u=2583054979,2860372508&fm=23&gp=0.jpg");
        urls.add("http://img4.imgtn.bdimg.com/it/u=1030604573,1579640549&fm=23&gp=0.jpg");

        bannerViewPager.setViewPagerItem(new ItemView(this));

        bannerViewPager.setUrls(urls);

        bannerViewPager.setOnItemClickListener(new IOnItemClickListener() {
            @Override
            public void OnClickListener(int position) {
                Log.d("msg", "viewpager setOnItemClickListener position = " + position);

                startActivity(new Intent(ViewPagerAty.this, RecyclerAty.class));
            }
        });
        bannerViewPager.setOnPageChangeListener(new IOnPageChangeListener() {
            @Override
            public void onPageChangeListener(int position) {
                Log.d("msg", "viewpager setOnPageChangeListener position = " + position);
            }
        });

    }
}
