package com.example.happy_home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class register_facility extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_facility);

        //편의시설 등록
        Button add_facility_button=(Button) findViewById(R.id.facility_button);
        add_facility_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                add_facility();
            }
        });
    }

    //편의시설 추가
    public void add_facility(){

        String facility_name = ((EditText) findViewById(R.id.facility_name)).getText().toString();
        Integer facility_max_num = Integer.parseInt(((EditText) findViewById(R.id.facility_max_num)).getText().toString());
        Integer facility_recommend_num = Integer.parseInt(((EditText) findViewById(R.id.facility_recommend_num)).getText().toString());

        //관리자 거주지 가져온다
        SharedPreferences sharedPreferences = getSharedPreferences("my_info", MODE_PRIVATE);
        String user_residence = sharedPreferences.getString("residence","");//사용자 거주지 저장하는 변수

        //파이어베이스에 주차시설 추가
        DatabaseReference park_ref = FirebaseDatabase.getInstance().getReference();

        HashMap<String, Object> new_park = new HashMap();
        new_park.put("/residence/"+user_residence+"/facility/"+facility_name+"/최대인원수/",facility_max_num);
        new_park.put("/residence/"+user_residence+"/facility/"+facility_name+"/권장인원수/",facility_recommend_num);
        new_park.put("/residence/"+user_residence+"/facility/"+facility_name+"/현재/",0);
        park_ref.updateChildren(new_park);

        nextActivity(menuAdmin.class);
    }

    public void nextActivity(Class next){

        //다음 화면으로 이동
        Intent intent = new Intent(getApplicationContext(), next);
        startActivity(intent);
    }
}