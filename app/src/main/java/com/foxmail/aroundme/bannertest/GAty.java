package com.foxmail.aroundme.bannertest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.foxmail.aroundme.banner.viewpager.BannerLayout;
import com.foxmail.aroundme.banner.viewpager.IOnItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gzl on 1/2/17.
 */

public class GAty extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gaty);

        BannerLayout bannerLayout = (BannerLayout) findViewById(R.id.banner);

        final List<String> urls = new ArrayList<>();
        urls.add("http://img3.imgtn.bdimg.com/it/u=2674591031,2960331950&fm=23&gp=0.jpg");
        urls.add("http://img5.imgtn.bdimg.com/it/u=3639664762,1380171059&fm=23&gp=0.jpg");
        urls.add("http://img0.imgtn.bdimg.com/it/u=1095909580,3513610062&fm=23&gp=0.jpg");
        urls.add("http://img4.imgtn.bdimg.com/it/u=1030604573,1579640549&fm=23&gp=0.jpg");
        urls.add("http://img5.imgtn.bdimg.com/it/u=2583054979,2860372508&fm=23&gp=0.jpg");
        urls.add("http://img5.imgtn.bdimg.com/it/u=2583054979,2860372508&fm=23&gp=0.jpg");
        urls.add("http://img5.imgtn.bdimg.com/it/u=2583054979,2860372508&fm=23&gp=0.jpg");

        bannerLayout.setUrls(urls);

        bannerLayout.setOnItemClickListener(new IOnItemClickListener() {
            @Override
            public void OnClickListener(int position) {
                Log.d("msg", "position = " + position);
            }
        });

    }
}
