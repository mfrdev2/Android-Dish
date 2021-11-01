package com.frabbi.splashscreendemo.view.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.frabbi.splashscreendemo.databinding.ItemDishesLayoutBinding;
import com.frabbi.splashscreendemo.model.entities.FavDish;

import java.util.ArrayList;
import java.util.List;

public class FavDishesAdapter extends RecyclerView.Adapter<FavDishesAdapter.MyViewHolder> {
    private Fragment fragment;
    private List<FavDish> list = new ArrayList<>();
    public FavDishesAdapter(Fragment fragment) {
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemDishesLayoutBinding mBinding = ItemDishesLayoutBinding.inflate(LayoutInflater.from(fragment.getContext()),parent,false);
        return new MyViewHolder(mBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        FavDish favDish = list.get(position);
        holder.mBinding.tvDishTitle.setText(favDish.title);
        Glide.with(fragment)
                .load(favDish.imagePath)
                .into(holder.mBinding.ivDishImage);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setDishesList(List<FavDish> list){
        this.list = list;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ItemDishesLayoutBinding mBinding;
        public MyViewHolder(@NonNull ItemDishesLayoutBinding itemView) {
            super(itemView.getRoot());
            mBinding = itemView;
        }
    }
}
