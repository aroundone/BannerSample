package com.foxmail.aroundme.bannertest;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.foxmail.aroundme.banner.BannerPagerAdapter;
import com.foxmail.aroundme.banner.GViewPager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

public class MainActivity extends AppCompatActivity{

    private GViewPager viewPager;

    private int index;

    private List<String> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = new ArrayList<>();
        initData();
        viewPager = (GViewPager) findViewById(R.id.viewpager);

        BannerPagerAdapter bannerPagerAdapter = new BannerPagerAdapter(this, list);

        viewPager.setAdapter(bannerPagerAdapter);
        viewPager.setCurrentItem(70);
        viewPager.setScrollDuration(2000);

    }

    private void initData() {
        list.add("http://img1.imgtn.bdimg.com/it/u=1826333971,3126752734&fm=21&gp=0.jpg");/*
        list.add("http://www.mtchome.com/d/file/p/2014/06-26/c6ff44b610e7ac891a7aca2d5677a474.jpg");
        list.add("http://www.lnmoto.cn/bbs/data/attachment/forum/201408/12/074018gshshia3is1cw3sg.jpg");
        list.add("http://www.moto2s.com/ppimg/20150922/0922155607nubdxxplphi.jpg");
        list.add("http://www.harley-davidsonbeijing.com/uploads/141113/1-1411131J504X6.jpg");
        list.add("http://image84.360doc.com/DownloadImg/2015/04/1915/52587646_8.jpg");
        list.add("http://www.harley-davidsonbeijing.com/uploads/allimg/141113/1-1411131J6190-L.jpg");*/
    }

}
