package com.example.happy_home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class noticeWrite extends AppCompatActivity {
     EditText title, text;   //글 제목, 글 내용
     Button upload;  //작성하기 버튼

    String Writer; //작성자의 닉네임을 저장해 줄 것이다.
    String Time; //시간을 저장해 줄 변수

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_edit);

        Intent intent = getIntent();

        SharedPreferences sharedPreferences = getSharedPreferences("my_info", MODE_PRIVATE);
        Writer = sharedPreferences.getString("nick_name", "");//작성자가 누군지 알려줄거임 ~동~호
        String user_residence = sharedPreferences.getString("residence", "");//집주소 알아낼꺼임

        title = (EditText) findViewById(R.id.title_E);
        text = (EditText) findViewById(R.id.contents_E);

        //  post_num = intent.getIntExtra("post_num",0);
      // Title.setText(intent.getExtras().getString("Title"));
       // Text.setText(intent.getExtras().getString("Text"));

        upload = (Button) findViewById(R.id.button_E);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Title = title.getText().toString();
                String Text = text.getText().toString();

                if (title.equals("")) {
                    Toast.makeText(noticeWrite.this, "제목을 입력해 주세요!", Toast.LENGTH_SHORT).show();
                } else if (text.equals("")) {
                    Toast.makeText(noticeWrite.this, "내용을 입력해 주세요!", Toast.LENGTH_SHORT).show();
                } else {

                    long now = System.currentTimeMillis();
                    Date date = new Date(now);

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-hh:mm");
                    Time = sdf.format(date);

                    DatabaseReference Database = FirebaseDatabase.getInstance().getReference();

                    Global bk = (Global) getApplication();

                    int post_num= bk.getboardkey();
                    bk.setboardkey(post_num+1);

                    HashMap<String, Object> newpost = new HashMap();

                    Notice notice = new Notice(post_num,Time, Title, Writer, Text);

                    Map<String, Object> userValue = notice.toMap();

                    newpost.put("residence/" + user_residence + "/Notice/"+ post_num, userValue);
                    Database.updateChildren(newpost);

                    Intent iitent = new Intent(getApplicationContext(), noticeList.class);

                    startActivity(iitent);


                }
            }
        });


    }
}
