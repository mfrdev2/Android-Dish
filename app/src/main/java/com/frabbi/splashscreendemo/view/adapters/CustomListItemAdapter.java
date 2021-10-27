package com.frabbi.splashscreendemo.view.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.frabbi.splashscreendemo.databinding.ItemCustomListBinding;
import com.frabbi.splashscreendemo.view.activities.AddActivity;

import java.util.List;

public class CustomListItemAdapter extends RecyclerView.Adapter<CustomListItemAdapter.MyViewHolder> {
    private Context context;
    private List<String> list;
    private String selection;

    public CustomListItemAdapter(Context context, List<String> list, String selection) {
        this.context = context;
        this.list = list;
        this.selection = selection;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCustomListBinding itemViewBinding = ItemCustomListBinding.inflate(LayoutInflater.from(context), parent, false);
        return new MyViewHolder(itemViewBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String strData = list.get(position);
        holder.tvText.setText(strData);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddActivity activity = (AddActivity) context;
                activity.selectedItemInsertIntoInputField(strData,selection);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    protected static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        final TextView tvText;

        public MyViewHolder(@NonNull ItemCustomListBinding itemViewBinding) {
            super(itemViewBinding.getRoot());
            tvText = itemViewBinding.tvText;
        }

        @Override
        public void onClick(View v) {

        }
    }
}
