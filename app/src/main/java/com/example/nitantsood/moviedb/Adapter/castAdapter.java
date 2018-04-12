package com.example.nitantsood.moviedb.Adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.nitantsood.moviedb.APIResponses.CastResponse;
import com.example.nitantsood.moviedb.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by NITANT SOOD on 03-08-2017.
 */

public class castAdapter extends RecyclerView.Adapter<castAdapter.OneCastViewHolder> {
    Context mContext;
    ArrayList<CastResponse.Cast> castList;
    static setOnCastClickedListener mListener;
    final int mScreenHeight= Resources.getSystem().getDisplayMetrics().heightPixels;
    static final int mScreenWidth=Resources.getSystem().getDisplayMetrics().widthPixels;

    public castAdapter(Context mContext, ArrayList<CastResponse.Cast> castList, setOnCastClickedListener mListener) {
        this.mContext = mContext;
        this.castList = castList;
        this.mListener = mListener;
    }
    public interface setOnCastClickedListener{
        void onCastClicked(View v,int position);
    }
    @Override
    public OneCastViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.cast_layout_item,parent,false);
        return new OneCastViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(OneCastViewHolder holder, int position) {
        holder.name.setText(castList.get(position).getName());
        holder.character.setText(castList.get(position).getCharacter());
        Picasso.with(mContext).load("https://image.tmdb.org/t/p/w500" + castList.get(position).getProfile_path()).resize((int) (mScreenWidth/3),(int) (mScreenHeight / 3.2)).into(holder.castImage);
        holder.castImage.setTag(castList.get(position).getId());
    }

    @Override
    public int getItemCount() {
        return castList.size();
    }

    public static class OneCastViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name,character;
        ImageView castImage;
        LinearLayout detailBox;
        public OneCastViewHolder(View itemView) {
            super(itemView);
            detailBox=(LinearLayout) itemView.findViewById(R.id.cast_detail_box);
            name=(TextView) itemView.findViewById(R.id.cast_name);
            character=(TextView) itemView.findViewById(R.id.cast_character);
            character.setMaxWidth(mScreenWidth/3);
            castImage=(ImageView) itemView.findViewById(R.id.cast_image);
            castImage.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.onCastClicked(v,getAdapterPosition());
        }
    }
}
