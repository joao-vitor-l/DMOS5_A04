package com.example.dmos5_a04.view;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dmos5_a04.R;
import com.example.dmos5_a04.mvp.TaskEditingMVP;
import com.example.dmos5_a04.presenter.TaskEditingPresenter;

import java.util.Date;

public class TaskEditingActivity extends AppCompatActivity implements TaskEditingMVP.View, View.OnClickListener{
    private TaskEditingMVP.Presenter presenter;
    private EditText titleEditText;
    private EditText descriptionEditText;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_editor);

        presenter = new TaskEditingPresenter(this, this);
        findViews();
        setListener();
        setToolbar();
    }

    @Override
    protected void onStart(){
        super.onStart();
        presenter.verifyUpdate();
    }

    @Override
    protected void onDestroy(){
        presenter.deatach();
        super.onDestroy();
    }

    @Override
    public void onClick(View v){
        if(v == saveButton){
            presenter.saveTask(
                    titleEditText.getText().toString(),
                    descriptionEditText.getText().toString());
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void updateUI(String title, String description, Date creationDate){
        titleEditText.setText(title);
        descriptionEditText.setText(description);
    }

    @Override
    public Bundle getBundle() {
        return getIntent().getExtras();
    }

    @Override
    public void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void close(){
        presenter.deatach();
        finish();
    }

    private void findViews(){
        titleEditText = findViewById(R.id.edittext_title_details);
        descriptionEditText = findViewById(R.id.edittext_description_details);
        saveButton = findViewById(R.id.button_save_task);
    }

    private void setListener(){
        saveButton.setOnClickListener(this);
    }

    private void setToolbar(){
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
