package com.example.nitantsood.moviedb.Adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nitantsood.moviedb.APIResponses.results;
import com.example.nitantsood.moviedb.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by NITANT SOOD on 04-08-2017.
 */

public class MovieSearchAdapter extends RecyclerView.Adapter<MovieSearchAdapter.OneSearchedMovieViewHolder> {
Context mContext;
    ArrayList<results> myList;
    setOnMovieSearchClickListener mListener;
    final int mScreenHeight= Resources.getSystem().getDisplayMetrics().heightPixels;
    final int mScreenWidth=Resources.getSystem().getDisplayMetrics().widthPixels;
    public MovieSearchAdapter(Context mContext, ArrayList<results> myList, setOnMovieSearchClickListener mListener) {
        this.mContext = mContext;
        this.myList = myList;
        this.mListener = mListener;
    }

    public interface setOnMovieSearchClickListener{
        void onMovieClicked(View v,int position);
    }
    @Override
    public OneSearchedMovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.search_movie_list_item,parent,false);
        return  new OneSearchedMovieViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(OneSearchedMovieViewHolder holder, int position) {
        holder.name.setText(myList.get(position).getTitle());
        holder.releaseDate.setText(myList.get(position).getRelease_date());
        holder.vote.setText(myList.get(position).getVote_average()+"");
        Picasso.with(mContext).load("https://image.tmdb.org/t/p/w500" + myList.get(position).getPoster_path()).resize((int) (mScreenWidth / 3),0).into(holder.poster);


    }

    @Override
    public int getItemCount() {
        return myList.size();
    }

    public class OneSearchedMovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name;
        TextView releaseDate;
        TextView vote;
        ImageView poster;
        public OneSearchedMovieViewHolder(View itemView) {
            super(itemView);
            name=(TextView) itemView.findViewById(R.id.movieTitle);
            releaseDate=(TextView) itemView.findViewById(R.id.movieDate);
            vote=(TextView) itemView.findViewById(R.id.movieVote);
            poster=(ImageView) itemView.findViewById(R.id.moviePosterImage);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.onMovieClicked(v,getAdapterPosition());
        }
    }
}
