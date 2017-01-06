package com.foxmail.aroundme.bannertest.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.foxmail.aroundme.banner.recyclerview.RecyclerItem;
import com.foxmail.aroundme.bannertest.R;

/**
 * Created by gzl on 1/5/17.
 */

public class NewItem extends RecyclerItem<Holder> {

    private Context context;

    public NewItem(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateView(LayoutInflater inflater, ViewGroup parent) {

        View view = inflater.inflate(R.layout.recycler_item, parent, false);

        return new Holder(view);
    }

    @Override
    public void onBindView(Holder holder, int position, String url) {

        holder.textView.setText(position  +  "\n" + url);

        Glide.with(context).load(url).listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                Log.d("msg", " error = " + e.getMessage());
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                return false;
            }
        }).into(holder.imageView);
    }
}
