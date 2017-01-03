package com.foxmail.aroundme.bannertest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.foxmail.aroundme.banner.recyclerview.FixLinearSnapHelper;
import com.foxmail.aroundme.banner.recyclerview.GRecyclerView;
import com.foxmail.aroundme.banner.recyclerview.RecyclerBannerAdapter;
import com.foxmail.aroundme.banner.recyclerview.ScrollSpeedLinearLayoutManger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gzl on 1/3/17.
 */

public class RAty extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler);

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

        GRecyclerView recyclerView = (GRecyclerView)findViewById(R.id.recycler_view);

        LinearLayoutManager linearLayoutManager = new ScrollSpeedLinearLayoutManger(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        new FixLinearSnapHelper().attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(new RecyclerBannerAdapter(this, urls));

    }
}
