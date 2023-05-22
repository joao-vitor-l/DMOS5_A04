package com.example.dmos5_a04.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dmos5_a04.R;
import com.example.dmos5_a04.mvp.TaskDetailsMVP;
import com.example.dmos5_a04.presenter.TaskDetailsPresenter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TaskDetailsActivity extends AppCompatActivity implements TaskDetailsMVP.View, View.OnClickListener{
    private TaskDetailsMVP.Presenter presenter;
    private TextView titleTextView;
    private TextView descriptionTextView;
    private TextView creationDateTextView;
    private Button returnButton;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);

        presenter = new TaskDetailsPresenter(this, this);
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
        if(v == returnButton){
            close();
        }
    }

    public void updateUI(String title, String description, Date creationDate){
        titleTextView.setText(title);
        descriptionTextView.setText(description);
        creationDateTextView.setText(formatCreationDate(creationDate));
    }

    @Override
    public Bundle getBundle() {
        return getIntent().getExtras();
    }

    @Override
    public void close(){
        presenter.deatach();
        finish();
    }

    private void findViews(){
        titleTextView = findViewById(R.id.textview_title_details);
        descriptionTextView = findViewById(R.id.textview_description_details);
        creationDateTextView = findViewById(R.id.textview_creationdate_details);
        returnButton = findViewById(R.id.button_return);
    }

    private void setListener(){
        returnButton.setOnClickListener(this);
    }

    private void setToolbar(){
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private String formatCreationDate(Date creationDate){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        return sdf.format(creationDate);
    }
}
