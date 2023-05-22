package com.example.dmos5_a04.model.dao;

import com.example.dmos5_a04.model.entities.Tag;
import com.example.dmos5_a04.model.entities.Task;

import java.util.List;

public interface ITaskDao{
    void create(Task task);
    boolean update(String oldTitle, Task task);
    boolean delete(Task task);
    Task findByTitle(String title);
    List<Task> findByTag(Tag tag);
    List<Task> findAll();
}
