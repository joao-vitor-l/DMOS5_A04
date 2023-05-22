package com.example.dmos5_a04.presenter;

import android.content.Context;
import android.os.Bundle;

import com.example.dmos5_a04.model.dao.ITaskDao;
import com.example.dmos5_a04.model.dao.TaskDaoSingleton;
import com.example.dmos5_a04.model.entities.Task;
import com.example.dmos5_a04.mvp.TaskDetailsMVP;
import com.example.dmos5_a04.utils.Constant;

public class TaskDetailsPresenter implements TaskDetailsMVP.Presenter{
    private TaskDetailsMVP.View view;
    private Task task;
    private ITaskDao dao;

    public TaskDetailsPresenter(TaskDetailsMVP.View view, Context context){
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

    public void close(){
        view.close();
    }
}
