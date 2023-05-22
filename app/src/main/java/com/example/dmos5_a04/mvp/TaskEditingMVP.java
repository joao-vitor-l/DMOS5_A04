package com.example.dmos5_a04.mvp;

import android.os.Bundle;

import java.util.Date;

public interface TaskEditingMVP{
    interface View{
        void updateUI(String title, String description, Date creationDate);
        Bundle getBundle();
        void showToast(String message);
        void close();
    }

    interface Presenter{
        void deatach();
        void verifyUpdate();
        void saveTask(String title, String description);
    }
}
