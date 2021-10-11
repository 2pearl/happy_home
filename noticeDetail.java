package com.example.happy_home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

public class noticeDetail extends AppCompatActivity {
    int post_num;
    String Writer, Title, Text, Time;
    String ME;


    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_detail);

        Intent intent=getIntent();

        TextView title = (TextView) findViewById(R.id.textViewT_N);
        TextView text = (TextView) findViewById(R.id.textViewEC_N);

        SharedPreferences sharedPreferences = getSharedPreferences("my_info", MODE_PRIVATE);
        ME = sharedPreferences.getString("nick_name", "");//작성자가 누군지 알려줄거임 ~동~호
        String user_residence = sharedPreferences.getString("residence", "");//집주소 알아낼꺼임

        post_num = intent.getIntExtra("post_num",0);
        Title = intent.getExtras().getString("Title");
        Text = intent.getExtras().getString("Text");
        Time = intent.getExtras().getString("Time");
        Writer = intent.getExtras().getString("Writer");

        title.setText(Title);
        text.setText(Text);

        Button edit = (Button) findViewById(R.id.buttonM_N);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ME.equals(Writer)){
                    Intent edit_intent = new Intent (getApplicationContext(),noticeEdit.class);

                    edit_intent.putExtra("post_num",post_num);
                    edit_intent.putExtra("Time",Time);
                    edit_intent.putExtra("Title",Title);
                    edit_intent.putExtra("Text",Text);
                    edit_intent.putExtra("Writer",Writer);

                    startActivity(edit_intent);
                }
                else{
                    Toast.makeText(getApplicationContext(), "권한이 없습니다", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button DEL = (Button)findViewById(R.id.buttonD_N);
        DEL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ME.equals(Writer)||ME.equals("관리자")){
                    Task<Void> Database = FirebaseDatabase.getInstance()
                            .getReference("residence/" + user_residence + "/Notice/"+ post_num).removeValue();
                    startActivity(new Intent(noticeDetail.this,noticeList.class));
                }

                else{
                    Toast.makeText(getApplicationContext(), "권한이 없습니다", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }
}
