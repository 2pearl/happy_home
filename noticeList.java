package com.example.happy_home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class noticeList extends AppCompatActivity {

    //ListView nListView;
    String Writer;
    String ME;
    ArrayList<Notice> nlist=new ArrayList<>();



    notice_ItemAdapter notice_Adapter;
    String user_residence;
    int post_num;
    String Title,Text;

    EditText Search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_list);

        Intent intent=getIntent();

        SharedPreferences sharedPreferences = getSharedPreferences("my_info", MODE_PRIVATE);
        ME = sharedPreferences.getString("nick_name","");//작성자가 누군지 알려줄거임 ~동~호
        user_residence = sharedPreferences.getString("residence", "");//집위치

        post_num=intent.getIntExtra("post_num",0);

        Search = (EditText)findViewById(R.id.notice_search_text);
        ListView notice_list = (ListView)findViewById(R.id.n_listview);

        notice_Adapter = new notice_ItemAdapter(this,nlist);
        notice_list.setAdapter(notice_Adapter);
        notice_Adapter.notifyDataSetChanged();

        this.make_nlist();


        //notice_list.setAdapter(notice_Adapter);


        //nListView = (ListView)findViewById(R.id.n_listview);


        notice_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //게시글 상세페이지로 이동
                Intent detail_intent= new Intent(getApplicationContext(),noticeDetail.class);

                detail_intent.putExtra("post_num",nlist.get(position).post_num);
                detail_intent.putExtra("Time",nlist.get(position).Time);
                detail_intent.putExtra("Title",nlist.get(position).Title);
                detail_intent.putExtra("Text",nlist.get(position).Text);
                detail_intent.putExtra("Writer",nlist.get(position).Writer);

                startActivity(detail_intent);
            }
        });

        Button notice_write = (Button) findViewById(R.id.notice_towrite_bt);
        //글쓰기 페이지로 이동
        notice_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(ME.equals("관리자")){
                    Intent edit_intent = new Intent(getApplicationContext(), noticeWrite.class);
                    startActivity(edit_intent);
                }

                else{
                    Toast.makeText(getApplicationContext(),"권한이 없습니다",Toast.LENGTH_SHORT).show();
                }
            }
        });

        //검색
        Button Nsearch = (Button)findViewById(R.id.notice_search_bt);
        Nsearch.setOnClickListener(new AdapterView.OnClickListener(){
            public void onClick(View view){

                String searchTitle = Search.getText().toString();


                if(searchTitle.equals("")){
                    Toast.makeText(getApplicationContext(), "검색어를 입력하세요!", Toast.LENGTH_SHORT).show();
                }
                else{

                    NSearch(searchTitle);
                    //notice_Adapter.notifyDataSetChanged();
                }
            }
        });
    }

    public void make_nlist(){


        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("residence/"+user_residence+"/Notice");

        rootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()) {

                    int post_num = ds.child("post_num").getValue(Integer.class);
                    String Time = ds.child("Time").getValue(String.class);
                    Title = ds.child("Title").getValue(String.class);
                    Text = ds.child("Text").getValue(String.class);
                    String Writer = ds.child("Writer").getValue(String.class);

                    Notice N = new Notice(post_num,Time,Title,Writer,Text);
                    nlist.add(N);


                    notice_Adapter.notifyDataSetChanged();
                    notice_Adapter.addItem(N);
                    notice_Adapter.notifyDataSetChanged();
                    //  make_nlist();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void NSearch(String search){

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("residence/"+user_residence+"/Notice");

        rootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()) {

                    String S_Title = ds.child("Title").getValue(String.class);
                    notice_Adapter.clearAllItems();
                    nlist.clear();
                    notice_Adapter.notifyDataSetChanged();

                    if(S_Title.contains(search)){
                        int post_num = ds.child("post_num").getValue(Integer.class);
                        String Time = ds.child("Time").getValue(String.class);
                        Title = ds.child("Title").getValue(String.class);
                        Text = ds.child("Text").getValue(String.class);
                        String Writer = ds.child("Writer").getValue(String.class);




                        Notice N = new Notice(post_num,Time,Title,Writer,Text);
                        nlist.add(N);
                        notice_Adapter.addItem(N);
                        notice_Adapter.notifyDataSetChanged();

                    }

                }

                if(nlist.size()==0||nlist.equals("")){
                    Toast.makeText(getApplicationContext(), "검색결과가 없습니다", Toast.LENGTH_SHORT).show();
                    make_nlist();
                }
                else{
                    Toast.makeText(getApplicationContext(), "if실행안댔지롱", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }




}