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
import java.util.List;

public class TagDaoSingleton implements ITagDao{
    private static TagDaoSingleton instance;
    private List<Tag> dataset;
    private SharedPreferences sharedPreferences;
    private Gson gson;

    private TagDaoSingleton(Context context){
        dataset = new ArrayList<>();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        gson = new Gson();
        loadTagsFromSharedPreferences();
    }

    public static TagDaoSingleton getInstance(Context context){
        if(instance == null)
            instance = new TagDaoSingleton(context);
        return instance;
    }

    @Override
    public void create(Tag tag){
        if(tag != null){
            if(find(tag.getTagName()) == null){
                dataset.add(tag);
                saveTagsToSharedPreferences();
            }
        }
    }

    @Override
    public boolean delete(Tag tag){
        boolean removed = dataset.remove(tag);
        if(removed){
            saveTagsToSharedPreferences();
        }
        return removed;
    }

    @Override
    public Tag find(String tagName){
        return dataset.stream()
                .filter(tag -> tag.getTagName().equals(tagName))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Tag> findAll() {
        return dataset;
    }

    private void loadTagsFromSharedPreferences(){
        String jsonTags = sharedPreferences.getString("tags", "");
        Type type = new TypeToken<List<Tag>>() {}.getType();
        List<Tag> savedTags = gson.fromJson(jsonTags, type);
        if(savedTags != null){
            dataset.clear();
            dataset.addAll(savedTags);
        }
    }

    private void saveTagsToSharedPreferences(){
        String jsonTags = gson.toJson(dataset);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("tags", jsonTags);
        editor.apply();
    }
}
