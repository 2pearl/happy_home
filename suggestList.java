package com.example.happy_home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class suggestList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggest_list);

        //검색버튼
        Button search_button=(Button) findViewById(R.id.suggest_search_bt);
        search_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                search();
            }
        });
    }

    public void search(){

        String search_text= ((EditText) findViewById(R.id.suggest_search_text)).getText().toString();

    }
}