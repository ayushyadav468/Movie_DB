package com.example.nitantsood.moviedb.Adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.nitantsood.moviedb.APIResponses.TvShows;
import com.example.nitantsood.moviedb.PlaceholderFragment;
import com.example.nitantsood.moviedb.R;
import com.example.nitantsood.moviedb.APIResponses.results;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by NITANT SOOD on 25-07-2017.
 */

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.OneMovieViewHolder>  {

    Context mContext;
    HashMap<Integer,String> mGenre;
    int mCategoryType;
    int mTabType;
    itemClickListener mListener;
    ArrayList<results> movieArrayList;
    ArrayList<TvShows>  tvShowArrayList;
    final int mScreenHeight=Resources.getSystem().getDisplayMetrics().heightPixels;
    final int mScreenWidth=Resources.getSystem().getDisplayMetrics().widthPixels;

    public interface itemClickListener{
        void movieItemCLicked(View view,int position);
        void showItemCLicked(View view,int position);
    }
    public CategoryListAdapter(itemClickListener Listener, Context context, int tabType, int categoryType, ArrayList<results> movieArrayList, ArrayList<TvShows> tvShowsArrayList, HashMap<Integer,String> genre){
        mListener=Listener;
        this.mGenre=genre;
        mContext=context;
        this.mCategoryType=categoryType;
        this.movieArrayList = movieArrayList;
        this.tvShowArrayList= tvShowsArrayList;
        this.mTabType=tabType;
    }
    public static float convertPixelsToDp(float px, Context context){

        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return dp;
    }



    @Override
    public OneMovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.category_list_item,parent,false);
        return  new OneMovieViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(OneMovieViewHolder holder, int position) {
        if(mTabType==PlaceholderFragment.MOVIES) {
            results currentMovie = movieArrayList.get(position);
            Picasso.with(mContext).load("https://image.tmdb.org/t/p/w500" + currentMovie.getPoster_path()).resize((int) (mScreenWidth / 3), (int) (mScreenHeight / 3.2)).into(holder.poster);
//        holder.Name.setText(currentMovie.title);
//        holder.Name.setWidth(mScreenWidth/3);
            holder.detailBox.setMinimumWidth(mScreenWidth / 3);

            holder.Genre.setText(mGenre.get(currentMovie.getGenre_ids().get(0)));
            holder.poster.setTag(currentMovie);
            if (mCategoryType == PlaceholderFragment.POPULAR_MOVIES) {
                holder.ratingBar.setRating(currentMovie.getVote_average() / 2);
                holder.votes.setText("" + currentMovie.getVote_average());
            } else if (mCategoryType == PlaceholderFragment.TOP_RATED_MOVIES) {
                holder.ratingBar.setRating(currentMovie.getVote_average() / 2);
                holder.votes.setText("" + currentMovie.getVote_average());
            } else if (mCategoryType == PlaceholderFragment.UPCOMING_MOVIES) {
                holder.releasingDate.setText("Releasing " + currentMovie.generateDate());
            }
        }else{
            TvShows CurrentShow=tvShowArrayList.get(position);
            Picasso.with(mContext).load("https://image.tmdb.org/t/p/w500" + CurrentShow.getPoster_path()).resize((int) (mScreenWidth / 3), (int) (mScreenHeight / 3.2)).error(android.R.drawable.stat_notify_error).into(holder.poster);
            holder.detailBox.setMinimumWidth(mScreenWidth / 3);
            holder.ratingBar.setRating(CurrentShow.getVote_average() / 2);
            holder.votes.setText("" + CurrentShow.getVote_average());
            holder.Genre.setText(mGenre.get(CurrentShow.getGenre_ids().get(0)));
            holder.poster.setTag(CurrentShow);
        }
    }

    @Override
    public int getItemCount() {
        if(mTabType==PlaceholderFragment.MOVIES) {
            return movieArrayList.size();
        }else return tvShowArrayList.size();
    }

    public  class OneMovieViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView Name;
        RatingBar ratingBar;
        TextView Genre;
        TextView votes;
        ImageView poster;
        TextView releasingDate;
        LinearLayout detailBox;

        public OneMovieViewHolder(View itemView) {
            super(itemView);
            detailBox=(LinearLayout) itemView.findViewById(R.id.list_item_detail_box);
            poster=(ImageView)itemView.findViewById(R.id.list_item_image);
            poster.setOnClickListener(this);
            releasingDate=(TextView) itemView.findViewById(R.id.list_item_releaseDate);
            ratingBar=(RatingBar) itemView.findViewById(R.id.list_item_ratingBar);
//            Name=(TextView) itemView.findViewById(R.id.list_item_name);
            Genre=(TextView) itemView.findViewById(R.id.list_item_genre);
            votes=(TextView) itemView.findViewById(R.id.list_item_vote);
        }

        @Override
        public void onClick(View v) {
            if(mTabType==PlaceholderFragment.MOVIES) {
                mListener.movieItemCLicked(v, getAdapterPosition());
            }else{
                mListener.showItemCLicked(v, getAdapterPosition());
            }
        }
    }

}
