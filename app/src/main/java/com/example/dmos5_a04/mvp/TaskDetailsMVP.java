package com.example.dmos5_a04.mvp;

import android.os.Bundle;

import java.util.Date;

public interface TaskDetailsMVP{
    interface View{
        void updateUI(String title, String description, Date creationDate);
        Bundle getBundle();
        void close();
    }

    interface Presenter{
        void deatach();
        void verifyUpdate();
    }
}
