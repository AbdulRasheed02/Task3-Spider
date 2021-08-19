package com.example.task3spider;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder> implements Filterable {
    private Context mContext;
    private ArrayList<SuperHero> mSuperHeroes;
    private ArrayList<SuperHero> mSuperHeroesFull;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position, ImageView iv_SuperHero);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener=listener;
    }

    public MainAdapter(Context context,ArrayList<SuperHero> Superheroes){
        mContext=context;
        mSuperHeroes=Superheroes;
        mSuperHeroesFull=new ArrayList<>(mSuperHeroes);
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

        String superHeroID=Integer.toString(currentItem.getId());
        String superHeroImageUrl=currentItem.getImages().getLg();
        String superHeroName=currentItem.getName();

        holder.tv_SuperHeroName.setText(superHeroName);
        holder.tv_SuperHeroID.setText("ID : "+superHeroID);

        ViewCompat.setTransitionName(holder.iv_SuperHero,superHeroName);
        Picasso.with(mContext).load(superHeroImageUrl).fit().centerInside().into(holder.iv_SuperHero);
    }

    @Override
    public int getItemCount() {
        return mSuperHeroes.size();
    }

    public class MainViewHolder extends RecyclerView.ViewHolder{
        public ImageView iv_SuperHero;
        public TextView tv_SuperHeroName,tv_SuperHeroID;

        public MainViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_SuperHero=itemView.findViewById(R.id.iv_SuperHero);
            tv_SuperHeroName=itemView.findViewById(R.id.tv_SuperHeroName);
            tv_SuperHeroID=itemView.findViewById(R.id.tv_SuperHeroID);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener!=null){
                        int position=getAbsoluteAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION){
                            mListener.onItemClick(position,iv_SuperHero);
                        }
                    }
                }
            });
        }
    }

    @Override
    public Filter getFilter() {
        return superHeroFilter;
    }

    private Filter superHeroFilter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<SuperHero> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(mSuperHeroesFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (SuperHero item : mSuperHeroesFull) {
                    if (item.getName().toLowerCase().contains(filterPattern) || String.valueOf(item.getId()).toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results=new FilterResults();
            results.values=filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mSuperHeroes.clear();
            mSuperHeroes.addAll((ArrayList) results.values);
            notifyDataSetChanged();
        }
    };
}
