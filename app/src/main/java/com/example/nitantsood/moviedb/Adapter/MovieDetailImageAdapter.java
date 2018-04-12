package com.example.nitantsood.moviedb.Adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.nitantsood.moviedb.APIResponses.ImageResponse;
import com.example.nitantsood.moviedb.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by NITANT SOOD on 02-08-2017.
 */

public class MovieDetailImageAdapter extends RecyclerView.Adapter<MovieDetailImageAdapter.OnePhotoViewHolder>  {
    Context mContext;
    ArrayList<ImageResponse.Image> MyPhotoList;
    setOnImageClickListener mListener;
    final int mScreenHeight= Resources.getSystem().getDisplayMetrics().heightPixels;
    final int mScreenWidth=Resources.getSystem().getDisplayMetrics().widthPixels;
    public MovieDetailImageAdapter(Context context, ArrayList<ImageResponse.Image> photoList, setOnImageClickListener listener) {
        this.MyPhotoList=photoList;
        this.mContext=context;
        this.mListener=listener;
    }
    public interface setOnImageClickListener{
        public void onImageClicked(View view);
    }
    @Override
    public OnePhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(mContext).inflate(R.layout.movie_detail_image_viewer_item,parent,false);
        return new OnePhotoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(OnePhotoViewHolder holder, int position) {
        Picasso.with(mContext).load("https://image.tmdb.org/t/p/w500" + MyPhotoList.get(position).getFile_path()).resize(0, (int) (mScreenHeight/3.5)).into(holder.image);
        holder.image.setTag(MyPhotoList.get(position).getFile_path());
    }

    @Override
    public int getItemCount() {
        return MyPhotoList.size();
    }

    public class OnePhotoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView image;
        public OnePhotoViewHolder(View itemView) {
            super(itemView);
            image=(ImageView) itemView.findViewById(R.id.movie_detail_image_viewer_item);
            image.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.onImageClicked(v);
        }
    }
}
