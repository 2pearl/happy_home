package com.example.happy_home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class changeResidence extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_residence);

        //변경
        Button changeR_button=(Button) findViewById(R.id.residence_bt);
        changeR_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                changing();
            }
        });
    }

    public void changing(){
        //phone_num 가져오기
        SharedPreferences sharedPreferences = getSharedPreferences("my_info", MODE_PRIVATE);
        String email=sharedPreferences.getString("email","");
        String phone_num=sharedPreferences.getString("phone_num","");

        String residence = ((EditText) findViewById(R.id.reresidence_text)).getText().toString();
        String dong = ((EditText) findViewById(R.id.redong_text)).getText().toString();
        String num = ((EditText) findViewById(R.id.renum_text)).getText().toString();
        String code = ((EditText) findViewById(R.id.recode_text)).getText().toString();

        if(residence.length() == 0 || dong.length() == 0 || num.length() == 0 || code.length() == 0){
            Toast.makeText(getApplicationContext(), "입력하지 않은 곳이 있습니다.", Toast.LENGTH_SHORT).show();
        }
        else {

            FirebaseDatabase database = FirebaseDatabase.getInstance();

            //인증번호 확인
            DatabaseReference code_Ref = database.getReference("residence/"+residence+"/house/"+dong+"동");

            code_Ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot ds : snapshot.getChildren()) {

                        if(ds.getKey().equals(num+"호")){

                            //db에 저장된 인증번호 가져온다
                            String db_code=ds.child("code").getValue(String.class);

                            //사용자 입력과 비교한다
                            if(code.equals(db_code)){

                                //사용자 정보를 Realtime Database에 등록
                                reMem r = new reMem(email,phone_num,residence,dong, num);
                                Map<String, Object> r_val = r.toMap();
                                HashMap<String, Object> new_r = new HashMap();

                                DatabaseReference r_ref = FirebaseDatabase.getInstance().getReference();

                                new_r.put("/residence/"+residence+"/house/"+dong+"동/"+num+"호/member/"+phone_num, 1);
                                r_ref.updateChildren(new_r);

                                new_r.put("/member/"+phone_num, r_val);
                                r_ref.updateChildren(new_r);

                                //sharedReferences 변경
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putInt("authority",1);
                                editor.putString("residence",residence);
                                editor.putString("dong",dong);
                                editor.putString("num",num);
                                editor.putString("nick_name",dong+"동"+num+"호");

                                editor.commit();

                                nextActivity(Fragment_mem_main.class);
                            }
                            else{
                                Toast.makeText(changeResidence.this,"인증번호를 잘못 입력하셨습니다!",Toast.LENGTH_SHORT).show();
                            }
                            break;
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    public void nextActivity(Class next){

        //다음 화면으로 이동
        Intent intent = new Intent(getApplicationContext(), next);
        startActivity(intent);
    }

}
class reMem{
    private String email;
    private String phone_num;
    private String residence;
    private String dong;
    private String num;

    reMem(String email, String phone_num, String residence, String dong, String num){
        this.email=email;
        this.phone_num=phone_num;
        this.residence=residence;
        this.dong=dong;
        this.num=num;
    }
    public Map<String, Object> toMap() {
        HashMap<String, Object> map = new HashMap<>();

        map.put("email",email);
        map.put("phone_num",phone_num);
        map.put("거주지", residence);
        map.put("동",dong);
        map.put("호수", num);

        return map;
    }
}