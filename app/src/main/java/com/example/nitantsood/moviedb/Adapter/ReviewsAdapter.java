package com.example.nitantsood.moviedb.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nitantsood.moviedb.APIResponses.ReviewResponse;
import com.example.nitantsood.moviedb.R;

import java.util.ArrayList;

/**
 * Created by NITANT SOOD on 03-08-2017.
 */

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.OneReviewViewHolder> {
    Context mContext;
    ArrayList<ReviewResponse.Review> reviewList;
    public ReviewsAdapter(Context context,ArrayList<ReviewResponse.Review> reviewList) {
        this.mContext=context;
        this.reviewList=reviewList;
    }

    @Override
    public OneReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(mContext).inflate(R.layout.reviews_item,parent,false);
        return new OneReviewViewHolder(v);
    }

    @Override
    public void onBindViewHolder(OneReviewViewHolder holder, int position) {
        holder.name.setText(reviewList.get(position).getAuthor());
        holder.review.setText(reviewList.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    public class OneReviewViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView review;
        public OneReviewViewHolder(View itemView) {
            super(itemView);
            name=(TextView) itemView.findViewById(R.id.review_name);
            review=(TextView) itemView.findViewById(R.id.review_content);
        }
    }
}
