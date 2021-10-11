package com.example.happy_home;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;


public class Fragment_mem_main extends AppCompatActivity {

    private Fragment_memMenu menu;
    private Fragment_memHome home;
    private Fragment_mypage mypage;

    int menu_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_mem_main);//메인화면

        BottomNavigationView bottomNavigationView = findViewById(R.id.menu_mem);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.bottom_board:
                        changeFrag(0);
                        break;
                    case R.id.bottom_home:
                        changeFrag(1);
                        break;
                    case R.id.bottom_mypage:
                        changeFrag(2);
                        break;
                }
                return true;
            }
        });

        menu = new Fragment_memMenu();
        home = new Fragment_memHome();
        mypage = new Fragment_mypage();

        Intent intent=getIntent();
        menu_type=intent.getIntExtra("menu_type",1);

        changeFrag(menu_type); // 첫 프래그먼트 화면 지정

    }

    // 프레그먼트 교체
    private void changeFrag(int n) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        switch (n) {
            case 0:
                ft.replace(R.id.main_fragment, menu);
                ft.commit();
                break;

            case 1:
                ft.replace(R.id.main_fragment, home);
                ft.commit();
                break;

            case 2:
                ft.replace(R.id.main_fragment, mypage);
                ft.commit();
                break;
        }
    }
}
