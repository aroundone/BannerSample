package com.foxmail.aroundme.recyclerbanner;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by gzl on 1/3/17.
 */

public class RecyclerBannerAdapter extends RecyclerView.Adapter<Holder>{

    private Context context;

    private List<String> urls;

    public RecyclerBannerAdapter(Context context, List<String> urls) {
        this.context = context;
        this.urls = urls;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.banner, parent, false);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

        if(urls == null || urls.get(position) == null) {
            return;
        }


        Glide.with(context).load(urls.get(position)).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return urls.size();
    }
}
