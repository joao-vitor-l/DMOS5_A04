package com.example.dmos5_a04.model.dao;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.dmos5_a04.model.entities.Tag;
import com.example.dmos5_a04.model.entities.Task;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class TaskDaoSingleton implements ITaskDao{
    private static TaskDaoSingleton instance = null;
    private List<Task> dataset;
    private SharedPreferences sharedPreferences;
    private Gson gson;

    private TaskDaoSingleton(Context context){
        dataset = new ArrayList<>();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        gson = new Gson();
        loadTasksFromSharedPreferences();
    }

    public static TaskDaoSingleton getInstance(Context context){
        if(instance == null)
            instance = new TaskDaoSingleton(context);
        return instance;
    }

    @Override
    public void create(Task task){
        if(task != null){
            task.setCreationDate(new Date());
            dataset.add(task);
            saveTasksToSharedPreferences();
        }
    }

    @Override
    public boolean update(String oldTitle, Task task){
        Task inDataset = findByTitle(oldTitle);
        if(inDataset != null){
            inDataset.setTitle(task.getTitle());
            inDataset.setDescription(task.getDescription());
            inDataset.setPriority(task.isPriority());
            inDataset.getTags().clear();
            inDataset.getTags().addAll(task.getTags());
            saveTasksToSharedPreferences();
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(Task task){
        boolean removed = dataset.remove(task);
        if(removed){
            saveTasksToSharedPreferences();
        }
        return removed;
    }

    @Override
    public Task findByTitle(String title){
        for(Task task : dataset){
            if(task.getTitle().equals(title)){
                return task;
            }
        }
        return null;
    }

    @Override
    public List<Task> findByTag(Tag tag){
        List<Task> selection = new ArrayList<>();
        for(Task task : dataset){
            for(Tag t : task.getTags()){
                if(t.getTagName().equals(tag.getTagName())){
                    selection.add(task);
                }
            }
        }
        return selection;
    }

    @Override
    public List<Task> findAll(){
        List<Task> sortedTasks = new ArrayList<>(dataset);
        Collections.sort(sortedTasks, new Comparator<Task>(){
            @Override
            public int compare(Task task1, Task task2){
                if(task1.isPriority() && !task2.isPriority()){
                    return -1; // task1 é prioridade, task2 não é, task1 fica antes de task2
                } else if (!task1.isPriority() && task2.isPriority()) {
                    return 1; // task1 não é prioridade, task2 é, task1 fica depois de task2
                } else {
                    return 0; // Ambas as tasks são (ou não) prioridade, mantém-se a ordem original
                }
            }
        });

        List<Task> priorityTasks = new ArrayList<>();
        List<Task> nonPriorityTasks = new ArrayList<>();

        for(Task task : sortedTasks){
            if(task.isPriority()){
                priorityTasks.add(task);
            }else{
                nonPriorityTasks.add(task);
            }
        }

        priorityTasks.addAll(nonPriorityTasks);
        return priorityTasks;
    }

    private void loadTasksFromSharedPreferences(){
        String jsonTasks = sharedPreferences.getString("tasks", "");
        Type type = new TypeToken<List<Task>>() {}.getType();
        List<Task> savedTasks = gson.fromJson(jsonTasks, type);
        if(savedTasks != null){
            dataset.clear();
            dataset.addAll(savedTasks);
        }
    }

    private void saveTasksToSharedPreferences(){
        String jsonTasks = gson.toJson(dataset);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("tasks", jsonTasks);
        editor.apply();
    }
}
