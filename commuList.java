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

public class commuList extends AppCompatActivity {

    String user_residence;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commu_list);

        SharedPreferences sharedPreferences = getSharedPreferences("my_info", MODE_PRIVATE);
        user_residence = sharedPreferences.getString("residence","");//사용자 거주지 저장하는 변수

        //검색버튼
        Button search_button=(Button) findViewById(R.id.commu_search_bt);
        search_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                search();
            }
        });

    }

    public void search(){

        String search_text= ((EditText) findViewById(R.id.commu_search_text)).getText().toString();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference search_Ref = database.getReference("residence/"+user_residence+"/board/commu/");

        /*
        search_Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    String post_name = ds.child("post_name").getValue(String.class);
                    if (post_name.contains(searchName)) {
                        int user_id = ds.child("user_id").getValue(Integer.class);
                        int post_num = ds.child("post_num").getValue(Integer.class);
                        String post_contents = ds.child("post_contents").getValue(String.class);
                        String comment = ds.child("post_comment").getValue(String.class);

                        Q_board q = new Q_board(post_num, post_name, post_contents, comment, user_id);
                        qlist.add(q);
                        IAdapter.notifyDataSetChanged();
                        IAdapter.addItem(q);
                    }
                }
                if (qlist.size() == 0) {
                    Toast.makeText(getApplicationContext(), "검색결과가 없습니다", Toast.LENGTH_SHORT).show();
                }
            }
        }
         */
    }
}