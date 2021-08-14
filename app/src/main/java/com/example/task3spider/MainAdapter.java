package com.example.task3spider;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder> {
    private Context mContext;
    private ArrayList<SuperHero> mSuperHeroes;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener=listener;
    }

    public MainAdapter(Context context,ArrayList<SuperHero> Superheroes){
        mContext=context;
        mSuperHeroes=Superheroes;
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(mContext).inflate(R.layout.main_menu,parent,false);
        return new MainViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
        SuperHero currentItem=mSuperHeroes.get(position);

        String superHeroImageUrl=currentItem.getImages().getLg();
        String superHeroName=currentItem.getName();

        holder.tv_SuperHeroName.setText(superHeroName);
        Picasso.with(mContext).load(superHeroImageUrl).fit().centerInside().into(holder.iv_SuperHero);
    }

    @Override
    public int getItemCount() {
        return mSuperHeroes.size();
    }

    public class MainViewHolder extends RecyclerView.ViewHolder{
        public ImageView iv_SuperHero;
        public TextView tv_SuperHeroName;

        public MainViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_SuperHero=itemView.findViewById(R.id.iv_SuperHero);
            tv_SuperHeroName=itemView.findViewById(R.id.tv_SuperHeroName);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener!=null){
                        int position=getAbsoluteAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION){
                            mListener.onItemClick(position);
                        }

                    }
                }
            });
        }
    }
}
