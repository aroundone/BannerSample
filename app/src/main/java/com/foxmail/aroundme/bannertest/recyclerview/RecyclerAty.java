package com.foxmail.aroundme.bannertest.recyclerview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.foxmail.aroundme.banner.indicator.IOnItemClickListener;
import com.foxmail.aroundme.banner.recyclerview.BannerRecyclerView;
import com.foxmail.aroundme.banner.indicator.IOnPageChangeListener;
import com.foxmail.aroundme.bannertest.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gzl on 1/3/17.
 */

public class RecyclerAty extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.raty);

        List<String> urls = new ArrayList<>();
        urls.add("http://img3.imgtn.bdimg.com/it/u=2674591031,2960331950&fm=23&gp=0.jpg");
        urls.add("http://img5.imgtn.bdimg.com/it/u=3639664762,1380171059&fm=23&gp=0.jpg");
        urls.add("http://img0.imgtn.bdimg.com/it/u=1095909580,3513610062&fm=23&gp=0.jpg");
        urls.add("http://img4.imgtn.bdimg.com/it/u=1030604573,1579640549&fm=23&gp=0.jpg");
        urls.add("http://img5.imgtn.bdimg.com/it/u=2583054979,2860372508&fm=23&gp=0.jpg");
        urls.add("http://img3.imgtn.bdimg.com/it/u=2674591031,2960331950&fm=23&gp=0.jpg");
        urls.add("http://img5.imgtn.bdimg.com/it/u=3639664762,1380171059&fm=23&gp=0.jpg");
        urls.add("http://img0.imgtn.bdimg.com/it/u=1095909580,3513610062&fm=23&gp=0.jpg");
        urls.add("http://img4.imgtn.bdimg.com/it/u=1030604573,1579640549&fm=23&gp=0.jpg");
        urls.add("http://img5.imgtn.bdimg.com/it/u=2583054979,2860372508&fm=23&gp=0.jpg");

        BannerRecyclerView bannerRecyclerView = (BannerRecyclerView)findViewById(R.id.banner_recycler);
        bannerRecyclerView.setRecyclerItemView(new NewItem(this));
        bannerRecyclerView.setUrls(urls);

        // TODO: 1/9/17 View还没有添加进去 
        bannerRecyclerView.setIOnPageChangeListener(new IOnPageChangeListener() {
            @Override
            public void onPageChangeListener(int position) {
                Log.d("msg", "recycler position = " + position);
            }
        });

        bannerRecyclerView.setOnItemClickListener(new IOnItemClickListener() {
            @Override
            public void OnClickListener(int position) {
                Log.d("msg", "recycler item click position = " + position);
            }
        });

    }
}
