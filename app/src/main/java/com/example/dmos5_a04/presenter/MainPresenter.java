package com.example.dmos5_a04.presenter;

import android.content.Context;
import android.content.Intent;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dmos5_a04.model.dao.ITaskDao;
import com.example.dmos5_a04.model.dao.TaskDaoSingleton;
import com.example.dmos5_a04.model.entities.Task;
import com.example.dmos5_a04.mvp.MainMVP;
import com.example.dmos5_a04.utils.Constant;
import com.example.dmos5_a04.view.RecyclerViewItemClickListener;
import com.example.dmos5_a04.view.TaskDetailsActivity;
import com.example.dmos5_a04.view.TaskEditingActivity;
import com.example.dmos5_a04.view.adapter.ItemPocketRecyclerAdapter;

public class MainPresenter implements MainMVP.Presenter{
    private MainMVP.View view;
    private ITaskDao dao;
    Task task;

    public MainPresenter(MainMVP.View view, Context context){
        this.view = view;
        dao = TaskDaoSingleton.getInstance(context);
    }

    @Override
    public void deatach() {
        view = null;
    }

    @Override
    public void openEditing(){
        Intent intent = new Intent(view.getContext(), TaskEditingActivity.class);
        view.getContext().startActivity(intent);
    }

    @Override
    public void openEditing(Task task){
        Intent intent = new Intent(view.getContext(), TaskEditingActivity.class);
        intent.putExtra(Constant.ATTR_TITLE, task.getTitle());
        view.getContext().startActivity(intent);
    }

    @Override
    public void openDetails(Task task){
        Intent intent = new Intent(view.getContext(), TaskDetailsActivity.class);
        intent.putExtra(Constant.ATTR_TITLE, task.getTitle());
        view.getContext().startActivity(intent);
    }

    @Override
    public void populateList(RecyclerView recyclerView){
        ItemPocketRecyclerAdapter adapter = new ItemPocketRecyclerAdapter(view.getContext(), dao.findAll(), this);
        adapter.setClickListener(new RecyclerViewItemClickListener(){
            @Override
            public void onItemClick(int position){
                task = dao.findAll().get(position);
                openDetails(task);
            }
        });
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void prioritizeTask(Task task){
        task.setPriority(!task.isPriority());
        dao.update(task.getTitle(), task);
        if(view != null){
            populateList(view.getRecyclerView());
        }
    }

    @Override
    public void editTask(Task task){
        openEditing(task);
    }

    @Override
    public void deleteTask(Task task){
        dao.delete(task);
        if(view != null){
            populateList(view.getRecyclerView());
        }
    }
}
