package com.example.dmos5_a04.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.dmos5_a04.R;
import com.example.dmos5_a04.model.entities.Task;
import com.example.dmos5_a04.mvp.MainMVP;

import java.util.List;

public class ItemPocketAdapter extends ArrayAdapter{
    private LayoutInflater inflater;
    private MainMVP.Presenter presenter;

    public ItemPocketAdapter(Context context, List<Task> data, MainMVP.Presenter presenter){
        super(context, R.layout.listview_item, data);
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        final ViewHolder holder;
        if(convertView == null){
            convertView = inflater.inflate(R.layout.listview_item, null);
            holder = new ViewHolder();
            holder.titleTextView = convertView.findViewById(R.id.text_title_listitem);
            holder.priorityImageView = convertView.findViewById(R.id.image_priority_listitem);
            holder.editImageView = convertView.findViewById(R.id.image_edit_listitem);
            holder.deleteImageView = convertView.findViewById(R.id.image_delete_listitem);

            holder.priorityImageView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    priorityClick(position);
                }
            });

            holder.editImageView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) { editClick(position); }
            });

            holder.deleteImageView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) { deleteClick(position); }
            });

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        Task task = (Task) getItem(position);
        holder.titleTextView.setText(task.getTitle());

        if(task.isPriority()){
            holder.priorityImageView.setColorFilter(getContext().getColor(R.color.yellow));
        }else{
            holder.priorityImageView.setColorFilter(getContext().getColor(R.color.gray));
        }

        return convertView;
    }

    private void priorityClick(int position){
        Task task = (Task) getItem(position);
        presenter.prioritizeTask(task);
        notifyDataSetChanged();
    }

    private void editClick(int position){
        Task task = (Task) getItem(position);
        presenter.editTask(task);
    }

    private void deleteClick(int position){
        Task task = (Task) getItem(position);
        presenter.deleteTask(task);
        notifyDataSetChanged();
    }

    private static class ViewHolder{
        public TextView titleTextView;
        public ImageView priorityImageView;
        public ImageView editImageView;
        public ImageView deleteImageView;
    }
}
