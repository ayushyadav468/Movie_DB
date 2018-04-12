package com.example.nitantsood.moviedb.Adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nitantsood.moviedb.APIResponses.Celebrity;
import com.example.nitantsood.moviedb.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by NITANT SOOD on 02-08-2017.
 */

public class CelebrityListAdapter extends RecyclerView.Adapter<CelebrityListAdapter.OneItemViewHolder> {

    final int mScreenHeight= Resources.getSystem().getDisplayMetrics().heightPixels;
    final int mScreenWidth=Resources.getSystem().getDisplayMetrics().widthPixels;
    Context mContext;
    celebItemClickListener mListener;
    ArrayList<Celebrity> mCelebrity=new ArrayList<>();

    public CelebrityListAdapter(Context context, ArrayList<Celebrity> celebrities,celebItemClickListener listener) {
    this.mContext=context;
        this.mCelebrity=celebrities;
        this.mListener=listener;
    }

    @Override
    public OneItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.popular_celeb_homepage_item,parent,false);
        return  new CelebrityListAdapter.OneItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(OneItemViewHolder holder, int position) {
    Celebrity celebrity=mCelebrity.get(position);
        holder.Name.setText(celebrity.getName());
        Picasso.with(mContext).load("https://image.tmdb.org/t/p/w500" + celebrity.getProfile_path()).resize((int) (mScreenWidth /3.5),0).error(android.R.drawable.stat_notify_error).into(holder.celebImage);
        int KnownForSize=celebrity.getKnown_for().size();
        if(KnownForSize>0) {
            holder.KnownFor1.setText(celebrity.getKnown_for().get(0).getTitle());
        }
        if(KnownForSize>1) {
            holder.KnownFor2.setText(celebrity.getKnown_for().get(1).getTitle());
        }
        if(KnownForSize>2) {
            holder.KnownFor3.setText(celebrity.getKnown_for().get(2).getTitle());
        }
    }

    @Override
    public int getItemCount() {
        return mCelebrity.size();
    }

    public class OneItemViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView Name;
        TextView KnownFor1;
        TextView KnownFor2;
        TextView KnownFor3;
        ImageView celebImage;
        public OneItemViewHolder(View itemView) {
            super(itemView);
            Name=(TextView) itemView.findViewById(R.id.celeb_Name);
            KnownFor1=(TextView) itemView.findViewById(R.id.celeb_Known_for_1);
            KnownFor2=(TextView) itemView.findViewById(R.id.celeb_Known_for_2);
            KnownFor3=(TextView) itemView.findViewById(R.id.celeb_Known_for_3);
            celebImage=(ImageView) itemView.findViewById(R.id.celebImageViewContent);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            mListener.celebItemCLicked(itemView,getAdapterPosition());
        }
    }
    public interface celebItemClickListener{
        void celebItemCLicked(View view,int position);
    }

}
