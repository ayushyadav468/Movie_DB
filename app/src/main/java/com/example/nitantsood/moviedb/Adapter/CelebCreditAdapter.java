package com.example.nitantsood.moviedb.Adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nitantsood.moviedb.APIResponses.CelebCreditResponse;
import com.example.nitantsood.moviedb.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by NITANT SOOD on 04-08-2017.
 */

public class CelebCreditAdapter extends RecyclerView.Adapter<CelebCreditAdapter.OneCreditViewHolder> {
Context mContext;
    ArrayList<CelebCreditResponse.Cast> creditList;
    setOnItemClickListener mListener;
    final int mScreenHeight= Resources.getSystem().getDisplayMetrics().heightPixels;
    final int mScreenWidth=Resources.getSystem().getDisplayMetrics().widthPixels;

    public CelebCreditAdapter(Context mContext, ArrayList<CelebCreditResponse.Cast> creditList, setOnItemClickListener mListener) {
        this.mContext = mContext;
        this.creditList = creditList;
        this.mListener = mListener;
    }

    public interface setOnItemClickListener{
        void onItemClicked(View v,int position);
    }
    @Override
    public OneCreditViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.celeb_credit_list_item,parent,false);
        return  new OneCreditViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(OneCreditViewHolder holder, int position) {
        holder.name.setText(creditList.get(position).getName());
        holder.character.setText(creditList.get(position).getCharacter());
        if(creditList.get(position).getMedia_type().equals("tv")){
            holder.media.setText("TV Show");
        }
        else if(creditList.get(position).getMedia_type().equals("movie")){
            holder.media.setText("Movie");
        }
        Picasso.with(mContext).load("https://image.tmdb.org/t/p/w500" + creditList.get(position).getBackdrop_path()).resize((mScreenWidth),0).into(holder.poster);

    }

    @Override
    public int getItemCount() {
        return creditList.size();
    }

    public class OneCreditViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name,character,media;
        ImageView poster;
        public OneCreditViewHolder(View itemView) {
            super(itemView);
            name=(TextView)itemView.findViewById(R.id.credit_name);
            character=(TextView) itemView.findViewById(R.id.credit_character);
            media=(TextView) itemView.findViewById(R.id.credit_media_type);
            poster=(ImageView) itemView.findViewById(R.id.credit_poster);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.onItemClicked(v,getAdapterPosition());
        }
    }
}
