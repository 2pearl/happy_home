package com.example.happy_home;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class menuAdmin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_admin);

        //공지게시판
        Button notice_button=(Button) findViewById(R.id.notice_admin_bt);
        notice_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                nextActivity(noticeList.class);
            }
        });

        //소통게시판
        Button comm_button=(Button) findViewById(R.id.comm_admin_bt);
        comm_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                nextActivity(commuList.class);
            }
        });

        //채팅하기
        Button chatList_button=(Button) findViewById(R.id.chatList_admin_bt);
        chatList_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                nextActivity(chatListAdmin.class);
            }
        });

        //주차시설
        Button park_button=(Button) findViewById(R.id.park_admin_bt);
        park_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                nextActivity(register_parking.class);
            }
        });

        //편의시설
        Button facility_button=(Button) findViewById(R.id.facility_admin_bt);
        facility_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                nextActivity(register_facility.class);
            }
        });

        //입주민 초기화
        Button init_button=(Button) findViewById(R.id.codeInit_admin_bt);
        init_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                nextActivity(initialization.class);
            }
        });

        //로그아웃
        Button logout_button=(Button) findViewById(R.id.logout_admin_bt);
        logout_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                logOut();
            }
        });

    }

    //로그아웃
    public void logOut(){

        //SharedPreference 불러온다
        SharedPreferences sharedPreferences = this.getApplicationContext().getSharedPreferences("my_info", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        AlertDialog.Builder builder = new AlertDialog.Builder(menuAdmin.this);
        builder.setTitle("행복한 주거환경");
        builder.setMessage("로그아웃하시겠습니까?");
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                //파이어베이스에서 로그아웃
                FirebaseAuth.getInstance().signOut();

                //Sharedpreference 초기화
                editor.clear();
                editor.commit();

                Toast.makeText(menuAdmin.this,"로그아웃되었습니다!",Toast.LENGTH_SHORT).show();
                nextActivity(Login.class);
            }
        });

        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        builder.create().show();
    }

    public void nextActivity(Class next){

        //다음 화면으로 이동
        Intent intent = new Intent(getApplicationContext(), next);//건물 관리자 화면으로 이동
        startActivity(intent);
    }
}