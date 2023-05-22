package com.example.dmos5_a04.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dmos5_a04.R;
import com.example.dmos5_a04.model.entities.Task;
import com.example.dmos5_a04.mvp.MainMVP;
import com.example.dmos5_a04.view.RecyclerViewItemClickListener;

import java.util.List;

public class ItemPocketRecyclerAdapter extends RecyclerView.Adapter<ItemPocketRecyclerAdapter.ViewHolder>{
    private Context context;
    private MainMVP.Presenter presenter;
    private List<Task> data;
    private static RecyclerViewItemClickListener clickListener;

    public ItemPocketRecyclerAdapter(Context context, List<Task> data, MainMVP.Presenter presenter){
        this.context = context;
        this.presenter = presenter;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view;
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listview_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        Task task = data.get(position);
        holder.titleTextView.setText(task.getTitle());

        if(task.isPriority()){
            holder.priorityImageView.setColorFilter(context.getColor(R.color.yellow));
        }else{
            holder.priorityImageView.setColorFilter(context.getColor(R.color.gray));
        }
        holder.priorityImageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                priorityClick(task);
            }
        });

        holder.editImageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){ editClick(task); }
        });

        holder.deleteImageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){ deleteClick(task); }
        });
    }

    @Override
    public int getItemCount(){
        return data.size();
    }

    public void setClickListener(RecyclerViewItemClickListener listener){
        clickListener = listener;
    }

    private void priorityClick(Task task){
        presenter.prioritizeTask(task);
        notifyDataSetChanged();
    }

    private void editClick(Task task){
        presenter.editTask(task);
    }

    private void deleteClick(Task task){
        presenter.deleteTask(task);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView titleTextView;
        public ImageView priorityImageView;
        public ImageView editImageView;
        public ImageView deleteImageView;

        public ViewHolder(View itemView){
            super(itemView);
            titleTextView = itemView.findViewById(R.id.text_title_listitem);
            priorityImageView = itemView.findViewById(R.id.image_priority_listitem);
            editImageView = itemView.findViewById(R.id.image_edit_listitem);
            deleteImageView = itemView.findViewById(R.id.image_delete_listitem);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v){
            if(clickListener != null){
                clickListener.onItemClick(getAdapterPosition());
            }
        }
    }
}
