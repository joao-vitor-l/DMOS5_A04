package com.example.dmos5_a04.presenter;

import android.content.Context;
import android.os.Bundle;

import com.example.dmos5_a04.model.dao.ITaskDao;
import com.example.dmos5_a04.model.dao.TaskDaoSingleton;
import com.example.dmos5_a04.model.entities.Task;
import com.example.dmos5_a04.mvp.TaskEditingMVP;
import com.example.dmos5_a04.utils.Constant;

public class TaskEditingPresenter implements TaskEditingMVP.Presenter{
    private TaskEditingMVP.View view;
    private Task task;
    private ITaskDao dao;

    public TaskEditingPresenter(TaskEditingMVP.View view, Context context){
        this.view = view;
        task = null;
        dao = TaskDaoSingleton.getInstance(context);
    }

    @Override
    public void deatach() {
        this.view = null;
    }

    @Override
    public void verifyUpdate(){
        String title;
        Bundle bundle = view.getBundle();
        if(bundle != null){
            title = bundle.getString(Constant.ATTR_TITLE);
            task = dao.findByTitle(title);
            view.updateUI(task.getTitle(), task.getDescription(), task.getCreationDate());
        }
    }

    @Override
    public void saveTask(String title, String description){
        if(task == null){
            // Nova tarefa
            task = new Task(title, description);
            dao.create(task);
            view.showToast("New task added.");
            view.close();
        }else{
            // Atualizar tarefa existente
            String oldTitle = task.getTitle();
            Task newTask = new Task(title, description, task.isPriority());
            if(dao.update(oldTitle, newTask)){
                view.showToast("Task updated successfully.");
                view.close();
            }else{
                view.showToast("There was an error while trying to update the task.");
            }
        }
    }
}
