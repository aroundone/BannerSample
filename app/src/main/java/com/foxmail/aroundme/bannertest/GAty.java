package com.foxmail.aroundme.bannertest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.foxmail.aroundme.banner.BannerLayout;

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
        BannerLayout bannerLayout2 = (BannerLayout) findViewById(R.id.banner2);

        final List<String> urls = new ArrayList<>();
        urls.add("http://img3.imgtn.bdimg.com/it/u=2674591031,2960331950&fm=23&gp=0.jpg");
        urls.add("http://img5.imgtn.bdimg.com/it/u=3639664762,1380171059&fm=23&gp=0.jpg");
        urls.add("http://img0.imgtn.bdimg.com/it/u=1095909580,3513610062&fm=23&gp=0.jpg");
        urls.add("http://img4.imgtn.bdimg.com/it/u=1030604573,1579640549&fm=23&gp=0.jpg");
        urls.add("http://img5.imgtn.bdimg.com/it/u=2583054979,2860372508&fm=23&gp=0.jpg");

        bannerLayout.setImageLoader(new GlideImageLoader());
        bannerLayout.setViewUrls(urls);

        //添加监听事件
        bannerLayout.setOnBannerItemClickListener(new BannerLayout.OnBannerItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(GAty.this, String.valueOf(position), Toast.LENGTH_SHORT).show();
            }
        });

        //低于三张
        final List<String> urls2 = new ArrayList<>();
        urls2.add("http://img3.imgtn.bdimg.com/it/u=2674591031,2960331950&fm=23&gp=0.jpg");
        urls2.add("http://img5.imgtn.bdimg.com/it/u=3639664762,1380171059&fm=23&gp=0.jpg");
        bannerLayout2.setImageLoader(new GlideImageLoader());
        bannerLayout2.setViewUrls(urls2);
    }
}
