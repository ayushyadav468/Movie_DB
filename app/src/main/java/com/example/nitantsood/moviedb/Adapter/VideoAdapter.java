package com.example.nitantsood.moviedb.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nitantsood.moviedb.APIResponses.VideoResponse;
import com.example.nitantsood.moviedb.R;

import java.util.ArrayList;

/**
 * Created by NITANT SOOD on 03-08-2017.
 */

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.OneVideoViewHolder>{
Context mContext;
    ArrayList<VideoResponse.Video> videoList;
    setOnVideoClickListener mListener;

    public VideoAdapter(Context mContext, ArrayList<VideoResponse.Video> videoList, setOnVideoClickListener mListener) {
        this.mContext = mContext;
        this.videoList = videoList;
        this.mListener = mListener;
    }

    public interface setOnVideoClickListener{
        void onVideoClicked(View v);
    }

    @Override
    public OneVideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.video_list_item,parent,false);
        return  new OneVideoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(OneVideoViewHolder holder, int position) {
        holder.title.setText(videoList.get(position).getName());
        holder.type.setText(videoList.get(position).getType());
        holder.itemView.setTag(videoList.get(position).getKey());
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    public class OneVideoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        TextView type;
        public OneVideoViewHolder(View itemView) {
            super(itemView);
            title=(TextView) itemView.findViewById(R.id.video_title);
            type=(TextView) itemView.findViewById(R.id.video_type);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.onVideoClicked(v);
        }
    }
}
