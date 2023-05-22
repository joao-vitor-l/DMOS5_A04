package com.example.dmos5_a04.mvp;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import com.example.dmos5_a04.model.entities.Task;

public interface MainMVP{
    interface View{
        Context getContext();
        RecyclerView getRecyclerView();
    }

    interface Presenter{
        void deatach();
        void openEditing();
        void openEditing(Task task);
        void openDetails(Task task);
        void populateList(RecyclerView recyclerView);
        void prioritizeTask(Task task);
        void editTask(Task task);
        void deleteTask(Task task);
    }
}
