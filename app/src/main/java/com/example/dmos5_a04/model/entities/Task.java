package com.example.dmos5_a04.model.entities;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Task{
    private String title;
    private String description;
    private Date creationDate;
    private boolean priority;

    private List<Tag> tags;

    private void init(){
        tags = new ArrayList<>();
    }

    public Task(String title, String description){
        this.title = title;
        this.description = description;
        this.creationDate = new Date();
        init();
    }

    public Task(String title, String description, boolean priority){
        this.title = title;
        this.description = description;
        this.creationDate = new Date();
        this.priority = priority;
        init();
    }

    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public String getDescription(){
        return description;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public Date getCreationDate(){
        return creationDate;
    }
    public void setCreationDate(Date creationDate){
        this.creationDate = creationDate;
    }
    public boolean isPriority(){
        return priority;
    }
    public void setPriority(boolean priority){
        this.priority = priority;
    }

    public void addTag(Tag tag){
        this.tags.add(tag);
    }
    public boolean removeTag(Tag tag){
        return this.tags.remove(tag);
    }
    public List<Tag> getTags(){
        return tags;
    }

    @NonNull
    @Override
    public String toString() {
        return "Title: " + title;
    }
}
