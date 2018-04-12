package com.example.nitantsood.moviedb.Adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.nitantsood.moviedb.APIResponses.OneShowResponse;
import com.example.nitantsood.moviedb.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by NITANT SOOD on 03-08-2017.
 */

public class SeasonsAdapter extends RecyclerView.Adapter<SeasonsAdapter.OneSeasonViewHolder> {
    Context mContext;
    ArrayList<OneShowResponse.Seasons> seasonsList;
    setOnSeasonClickListener mListener;
    final int mScreenHeight= Resources.getSystem().getDisplayMetrics().heightPixels;
    final int mScreenWidth=Resources.getSystem().getDisplayMetrics().widthPixels;
    public SeasonsAdapter(Context mContext, ArrayList<OneShowResponse.Seasons> seasonsList, setOnSeasonClickListener mListener) {
        this.mContext = mContext;
        this.seasonsList = seasonsList;
        this.mListener = mListener;
    }

    public interface setOnSeasonClickListener{
        void onSeasonClicked(View v);
    }
    @Override
    public OneSeasonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(mContext).inflate(R.layout.movie_detail_image_viewer_item,parent,false);
        return new OneSeasonViewHolder(v);
    }

    @Override
    public void onBindViewHolder(OneSeasonViewHolder holder, int position) {
        Picasso.with(mContext).load("https://image.tmdb.org/t/p/w500" + seasonsList.get(position).getPoster_path()).resize(0, (int) (mScreenHeight/3.5)).into(holder.poster);
        holder.poster.setTag(seasonsList.get(position).getPoster_path());
    }

    @Override
    public int getItemCount() {
        return seasonsList.size();
    }

    public class OneSeasonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView poster;
        public OneSeasonViewHolder(View itemView) {
            super(itemView);
            poster=(ImageView) itemView.findViewById(R.id.movie_detail_image_viewer_item);
            poster.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.onSeasonClicked(v);
        }
    }
}
