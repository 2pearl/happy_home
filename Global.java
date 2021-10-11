package com.example.happy_home;

import android.app.Application;

public class Global extends Application{
    private int post_num;

    @Override
    public void onCreate(){
        post_num=1;
        super.onCreate();
    }

    @Override
    public void onTerminate(){
        super.onTerminate();
    }

    public int getboardkey(){
        return post_num;
    }

    public void setboardkey(int post_num){
        this.post_num=post_num;
    }

}
